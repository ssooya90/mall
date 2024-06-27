package org.zerock.mallapi.util;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import net.coobird.thumbnailator.Thumbnailator;
import net.coobird.thumbnailator.Thumbnails;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Component
@Log4j2
@RequiredArgsConstructor
public class CustomFileUtil {



	// 스프링 프레임 워크에 있는 @Value 사용
	@Value("${org.zerock.upload.path}")
	private String uploadPath;

	@PostConstruct
	public void init() {

		File tempFloder = new File(uploadPath);

		if(!tempFloder.exists()){
			tempFloder.mkdir();
		}

		uploadPath = tempFloder.getAbsolutePath();

		log.info("---------------------------");
		log.info("uploadPath: " + uploadPath);



	}

	public List<String> saveFiles(List<MultipartFile> files) throws Exception {

		if(files == null || files.size() == 0){
			return List.of();
		}

		List<String> uploadNames = new ArrayList<>();

		for(MultipartFile file : files){
			// 파일명이 겹칠 수 있으니 uuid 활용
			String savedName = UUID.randomUUID().toString()+"_"+file.getOriginalFilename();
			Path savePath = Paths.get(uploadPath, savedName);

			try {

				// 원본파일 업로드
				Files.copy(file.getInputStream(), savePath);
				uploadNames.add(savedName);

				// 이미지인 경우 썸네일
				String contentType = file.getContentType(); // Mime type

				if(contentType != null || contentType.startsWith("image")){
					Path thumbnailPath = Paths.get(uploadPath,"s_"+savedName);

					Thumbnails.of(savePath.toFile()).size(200,200).toFile(thumbnailPath.toFile());
				}



			} catch (IOException e){
				throw new RuntimeException(e);
			}
		}

		return uploadNames;
	}


	public ResponseEntity<Resource> getFile(String fileName) {
		Resource resource = new FileSystemResource(uploadPath+File.separator+fileName);

		if(!resource.isReadable()){
			resource = new FileSystemResource(uploadPath+File.separator+"/default.png");
		}

		HttpHeaders httpHeaders = new HttpHeaders();
		try {
			httpHeaders.add("Content-Type", Files.probeContentType(resource.getFile().toPath()));
		} catch (IOException e) {
			throw new RuntimeException(e);
		}

		return ResponseEntity.ok().headers(httpHeaders).body(resource);
	}


	public void deleteFiles(List<String> fileNames) {

		if(fileNames == null || fileNames.isEmpty()){
			return;
		}

		fileNames.forEach(fileName -> {

			//썸네일 삭제
			String thumbnailFileName = "s_"+fileName;

			Path thumbPath = Paths.get(uploadPath,thumbnailFileName);
			Path filePath = Paths.get(uploadPath,fileName);

			// 파일이 존재한다면 삭제
			try {
				Files.deleteIfExists(filePath);
				Files.deleteIfExists(thumbPath);
			} catch (IOException e) {
				throw new RuntimeException(e);
			}


		});



	}
}
