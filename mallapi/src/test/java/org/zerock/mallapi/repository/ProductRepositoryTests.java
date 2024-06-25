package org.zerock.mallapi.repository;


import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;
import org.springframework.transaction.annotation.Transactional;
import org.zerock.mallapi.domain.Product;

import java.util.UUID;

@SpringBootTest
@Log4j2
public class ProductRepositoryTests {

	@Autowired
	private ProductRepository productRepository;

	@Test
	public void testInsert(){

		Product product = Product.builder().pname("Test ").pdesc("Test Desc").price(1000).build();

		product.addImageString(UUID.randomUUID()+"_"+"IMAGE1.jpg");
		product.addImageString(UUID.randomUUID()+"_"+"IMAGE2.jpg");

		productRepository.save(product);
	}

	@Commit
	@Transactional
	@Test
	public void testDelete(){

		Long pno = 3L;

		productRepository.updateToDelete(2L, true);


	}

	@Test
	public void testUpdate() {

		Product product = productRepository.selectOne(1L).get();

		product.setPrice(3000);

		product.clearList();

		product.addImageString(UUID.randomUUID()+"_"+"PIMAGE1.jpg");
		product.addImageString(UUID.randomUUID()+"_"+"PIMAGE2.jpg");
		product.addImageString(UUID.randomUUID()+"_"+"PIMAGE3.jpg");

		productRepository.save(product);


	}
}

