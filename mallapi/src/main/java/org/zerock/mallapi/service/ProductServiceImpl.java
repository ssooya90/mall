package org.zerock.mallapi.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.zerock.mallapi.domain.Product;
import org.zerock.mallapi.domain.ProductImage;
import org.zerock.mallapi.dto.PageRequestDTO;
import org.zerock.mallapi.dto.PageResponseDTO;
import org.zerock.mallapi.dto.ProductDTO;
import org.zerock.mallapi.repository.ProductRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Log4j2
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService{

	private final ProductRepository productRepository;

	@Override
	public PageResponseDTO<ProductDTO> getList(PageRequestDTO pageRequestDTO) {

		Pageable pageable = PageRequest.of(pageRequestDTO.getPage() - 1, pageRequestDTO.getSize(), Sort.by("pno").descending());

		Page<Object[]> result = productRepository.selectList(pageable);

		//Object[] => 0 product , 1 productImage

		List<ProductDTO> dtoList = result.get().map(arr -> {

			ProductDTO productDTO = null;

			Product product = (Product) arr[0];
			ProductImage productImage = (ProductImage) arr[1];

			productDTO = productDTO.builder()
					.pno(product.getPno())
					.pname(product.getPname())
					.pdesc(product.getPdesc())
					.price(product.getPrice())
					.build();

			String imageStr = productImage.getFileName();
			productDTO.setUploadFileNames(List.of(imageStr));

			return productDTO;
		}).collect(Collectors.toList());

		long totalCount = result.getTotalElements();


		return PageResponseDTO.<ProductDTO>withAll()
				.dtoList(dtoList)
				.totalCount(totalCount)
				.pageRequestDTO(pageRequestDTO)
				.build();
	}


	private Product dtoToEntity(ProductDTO productDTO){


		Product product = Product.builder()
				.pno(productDTO.getPno())
				.pname(productDTO.getPname())
				.pdesc(productDTO.getPdesc())
				.price(productDTO.getPrice())
				.build();


		// 업로드가 끝나면 문자열로 만듬
		List<String> uploadFileNames = productDTO.getUploadFileNames();

		if(uploadFileNames == null){
			return product;
		}

		uploadFileNames.forEach(fileName -> {
			product.addImageString(fileName);

		});


		return product;


	}

	@Override
	public Long register(ProductDTO productDTO) {


		Product product = dtoToEntity(productDTO);


		log.info("======================================");
		log.info(product);
		log.info(product.getImageList());

		Long pno = productRepository.save(product).getPno();

		return pno;
	}

	private ProductDTO entityToDto(Product product){

		ProductDTO productDto = ProductDTO.builder()
				.pno(product.getPno())
				.pname(product.getPname())
				.pdesc(product.getPdesc())
				.price(product.getPrice())
				.delFlag(product.isDelFlag())
				.build();

		List<ProductImage> imageList = product.getImageList();

		if(imageList == null || imageList.isEmpty()){
			return productDto;
		}

		List<String> fileNameList = imageList.stream().map(productImage ->
						productImage.getFileName()).toList();

		productDto.setUploadFileNames(fileNameList);

		return productDto;
	}

	@Override
	public ProductDTO get(Long pno) {

		Optional<Product> result = productRepository.selectOne(pno);

		Product product = result.orElseThrow();

		return entityToDto(product);
	}

	@Override
	public void modify(ProductDTO productDTO) {

		//1. 조회
		Optional<Product> result = productRepository.findById(productDTO.getPno());

		Product product = result.orElseThrow();

		//2. 변경
		product.setName(productDTO.getPname());
		product.setDesc(productDTO.getPdesc());
		product.setPrice(productDTO.getPrice());
		product.isDelFlag(productDTO.isDelFlag());


		//이미지처리
		List<String> uploadFileNames = productDTO.getUploadFileNames();

		// 어떤건 저장이 됐고, 저장이 안됐는지 알 수 없음
		// 그래서 일단 삭제
		product.clearList();

		if(uploadFileNames != null && !uploadFileNames.isEmpty()){
			uploadFileNames.forEach(uploadName -> {
				product.addImageString(uploadName);
			});
		}

		//3. 저장
		productRepository.save(product);




	}


	@Override
	public void remove(Long pno) {
		productRepository.deleteById(pno);
	}
}
