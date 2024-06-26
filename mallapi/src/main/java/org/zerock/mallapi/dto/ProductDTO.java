package org.zerock.mallapi.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProductDTO {

	private Long pno;

	private String pname;

	private int price;

	private String pdesc;

	private boolean delFlag;


	// 상품 등록 및 상품 조회용으로 사용하는 DTO
	@Builder.Default
	private List<MultipartFile> files = new ArrayList<>();


	// DB연동용 (파일명)
	@Builder.Default
	private List<String> uploadFileNames = new ArrayList<>();


}
