package org.zerock.mallapi.repository;

import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;
import org.springframework.transaction.annotation.Transactional;
import org.zerock.mallapi.domain.Cart;
import org.zerock.mallapi.domain.CartItem;
import org.zerock.mallapi.domain.Member;
import org.zerock.mallapi.domain.Product;
import org.zerock.mallapi.dto.CartItemListDTO;

import java.util.List;
import java.util.Optional;

@SpringBootTest
@Log4j2
public class CartRepositoryTests {

	@Autowired
	private CartRepository cartRepository;


	@Autowired
	private CartItemRepository cartItemRepository;

	@Transactional // 실제 DB에 넣어야 할 경우 해당 어노테이션 추가
	@Commit
	@Test
	public void testInsertByProduct() {

		String email = "user1@aaa.com";
		Long pno = 6L;
		int qty = 3;

		// 이메일 기준으로 상품번호로 장바구니 아이템 확인 없으면 추가 있으면 수량 변경해서 저장
		CartItem cartItem = cartItemRepository.getItemOfPno(email,pno);

		// 이미 해당 상품이 사용자의 장바구니에 담겨 있을 때
		if(cartItem != null){
			cartItem.changeQty(qty);
			cartItemRepository.save(cartItem);
			return;
		}

		// 장바구니 자체가 없을 경우
		Optional<Cart> result = cartRepository.getCartOfMember(email);
		Cart cart = null;

		if(result.isEmpty()){

			// 없을 경우 (회원이지만 한번도 장바구니를 담지 않았을 경우)
			Member member = Member.builder().email(email).build(); //회원 객체 만듬
			Cart tempCart = Cart.builder().owner(member).build(); //카트 객체 만듬

			cart = cartRepository.save(tempCart);
		}else{

			// 카트는 존재하지만 해당 아이템이 없는 경우
			cart = result.get();
		}

		Product product = Product.builder().pno(pno).build();
		cartItem = CartItem.builder()
				.cart(cart)
				.product(product)
				.qty(qty)
				.build();

		cartItemRepository.save(cartItem);



	}

	@Test
	public void testListOfMember() {

		String email = "user1@aaa.com";
//		cartItemRepository.getItemsOfCartDTOByEmail(email);

		List<CartItemListDTO> cartItemListDTOList = cartItemRepository.getItemsOfCartDTOByEmail(email);

		for (CartItemListDTO dto : cartItemListDTOList) {
			log.info(dto);
		}
	}



	@Transactional
	@Commit
	@Test
	public void testUpdateByCino() {

		Long cino = 2L;
		int qty = 6;

		Optional<CartItem> result = cartItemRepository.findById(cino);

		CartItem cartItem = result.orElseThrow();

		cartItem.changeQty(qty);

		cartItemRepository.save(cartItem);

	}

	@Transactional
	@Commit
	@Test
	public void testDeleteThenList() {

		Long cino = 2L;
		Long cno = cartItemRepository.getCartFromItem(cino);

		cartItemRepository.deleteById(cno);

		List<CartItemListDTO> cartItemList = cartItemRepository.getItemsOfCartDTOByCart(cno);

		for(CartItemListDTO dto : cartItemList){
			log.info(dto);
		}

	}


}
