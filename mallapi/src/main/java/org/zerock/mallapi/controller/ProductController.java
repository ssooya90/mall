package org.zerock.mallapi.controller;


import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.zerock.mallapi.dto.ProductDTO;
import org.zerock.mallapi.util.CustomFileUtil;

import java.util.List;
import java.util.Map;

@RestController
@Log4j2
@RequiredArgsConstructor
@RequestMapping("/api/products")
public class ProductController {

	private final CustomFileUtil fileUtil;

	// 파일데이터는 json으로 못 받음
	@PostMapping("/")
	public Map<String, String> register(ProductDTO productDTO) throws Exception {

		log.info("register");

		List<MultipartFile> files = productDTO.getFiles();
		List<String> uploadedFileNames = fileUtil.saveFiles(files);

		productDTO.setUploadedFileNames(uploadedFileNames);

		log.info(uploadedFileNames);

		return Map.of("RESULT","SUCCESS");

	}


	@GetMapping("/view/{fileName}")
	public ResponseEntity<Resource> viewFileGet(@PathVariable String fileName) throws Exception {

		return fileUtil.getFile(fileName);

	}






}
