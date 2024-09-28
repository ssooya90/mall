package org.zerock.mallapi.controller;


import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.zerock.mallapi.dto.PageRequestDTO;
import org.zerock.mallapi.dto.PageResponseDTO;
import org.zerock.mallapi.dto.ProductDTO;
import org.zerock.mallapi.service.ProductService;
import org.zerock.mallapi.util.CustomFileUtil;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@Log4j2
@RequiredArgsConstructor
@RequestMapping("/api/products")
public class ProductController {

	private final CustomFileUtil fileUtil;

	private final ProductService productService;

//	@PostMapping("/")
//	public Map<String, String> register(ProductDTO productDTO) throws Exception {
//
//		log.info("register");
//
//		List<MultipartFile> files = productDTO.getFiles();
//		List<String> uploadedFileNames = fileUtil.saveFiles(files);
//
//		productDTO.setUploadFileNames(uploadedFileNames);
//
//		log.info(uploadedFileNames);
//
//		return Map.of("RESULT","SUCCESS");
//
//	}

	// 파일데이터는 json으로 못 받음
	@PostMapping("/")
	public Map<String, Long> register(ProductDTO productDTO) throws Exception {

		log.info("register");

		List<MultipartFile> files = productDTO.getFiles();
		List<String> uploadedFileNames = fileUtil.saveFiles(files);

		productDTO.setUploadFileNames(uploadedFileNames);

		log.info(uploadedFileNames);


		Long pno = productService.register(productDTO);


		Thread.sleep(2000);


		return Map.of("result",pno);

	}


	@GetMapping("/view/{fileName}")
	public ResponseEntity<Resource> viewFileGet(@PathVariable String fileName) throws Exception {

		return fileUtil.getFile(fileName);

	}

	@PreAuthorize("hasAnyRole('ROLE_USER')")
//	@PreAuthorize("hasRole('ROLE_USER')")
	@GetMapping("/list")
	public PageResponseDTO<ProductDTO> list(PageRequestDTO pageRequestDTO){

		return productService.getList(pageRequestDTO);

	}

	@GetMapping("/{pno}")
	public ProductDTO read(@PathVariable Long pno) throws Exception {
		return productService.get(pno);
	}

	@PutMapping("/{pno}")
	public Map<String, String> modify(@PathVariable Long pno, ProductDTO productDTO) throws Exception {

		// 이미지 업로드 저장
		productDTO.setPno(pno);

		// 원래 상품
		ProductDTO oldProductDto = productService.get(pno);

		// file upload (NEW)
		List<MultipartFile> files = productDTO.getFiles();
		List<String> currentUploadFileName = fileUtil.saveFiles(files);


		// kepp files (유지되고 있는 파일)
		List<String> uploadedFileNames = productDTO.getUploadFileNames();

		if(currentUploadFileName != null || !currentUploadFileName.isEmpty()){
			// 1. 업로드된 파일이 있다면 원래파일 + 새로운파일로 합침
			uploadedFileNames.addAll(currentUploadFileName);
		}

		productService.modify(productDTO);

		// 수정 후 안쓰는 파일 삭제
		List<String> oldFileNames = oldProductDto.getUploadFileNames();

		if(oldFileNames != null || !oldFileNames.isEmpty()){

			List<String> removeFiles = oldFileNames.stream().filter(fileName ->
				uploadedFileNames.indexOf(fileName) == -1
			).collect(Collectors.toList());

			fileUtil.deleteFiles(removeFiles);
		}

		// react 파일은 삭제되야 함

		return Map.of("RESULT","SUCCESS");

	}

	@DeleteMapping("/{pno}")
	public Map<String, String> remove(@PathVariable Long pno) throws Exception {

		List<String> oldFileNames = productService.get(pno).getUploadFileNames();

		productService.remove(pno);
		fileUtil.deleteFiles(oldFileNames);

		return Map.of("RESULT","SUCCESS");

	}






	}
