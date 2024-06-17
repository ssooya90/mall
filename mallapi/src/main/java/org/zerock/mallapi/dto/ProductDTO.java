package org.zerock.mallapi.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
public class ProductDTO {

	private Long pno;

	private String pname;

	private int price;

	private String pdesc;

	private boolean delFlag = false;





	// 상품 등록 및 상품 조회용으로 사용하는 DTO
	private List<MultipartFile> files = new ArrayList<>();

	private List<String> uploadedFileNames = new ArrayList<>();

}
