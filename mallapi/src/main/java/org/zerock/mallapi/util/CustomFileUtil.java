package org.zerock.mallapi.util;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.List;

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
		return null;
	}
}
