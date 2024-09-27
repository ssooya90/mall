package org.zerock.mallapi.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.zerock.mallapi.domain.Cart;
import org.zerock.mallapi.domain.CartItem;
import org.zerock.mallapi.domain.Member;
import org.zerock.mallapi.domain.Product;
import org.zerock.mallapi.dto.CartItemDTO;
import org.zerock.mallapi.dto.CartItemListDTO;
import org.zerock.mallapi.repository.CartItemRepository;
import org.zerock.mallapi.repository.CartRepository;

import java.util.List;
import java.util.Optional;

@Service
@Log4j2
@RequiredArgsConstructor
public class CartServiceImpl implements CartService{

	private final CartRepository cartRepository;

	private final CartItemRepository cartItemRepository;

	@Override
	public List<CartItemListDTO> addOrModify(CartItemDTO cartItemDTO) {

		String email = cartItemDTO.getEmail();
		Long pno = cartItemDTO.getPno();
		int qty = cartItemDTO.getQty();
		Long cino = cartItemDTO.getCino();

		// 이미 장바구니에 아이템이 있는 경우
		if(cino != null){

			Optional<CartItem> cartItemResult = cartItemRepository.findById(cino);

			CartItem cartitem = cartItemResult.orElseThrow();

			cartitem.changeQty(qty);

			cartItemRepository.save(cartitem);

			return getCartItems(email);
		}

		Cart cart = getCart(email);

		CartItem cartItem = null;

		cartItem = cartItemRepository.getItemOfPno(email, pno);


		// 장바구니에 해당 항목이 없을 경우
		if(cartItem == null){

			Product product = Product.builder()
					.pno(pno)
					.build();

			cartItem = CartItem.builder()
					.cart(cart)
					.qty(qty)
					.product(product)
					.build();
		}else{

			// 장바구니에 해당 항목이 있을 경우
			cartItem.changeQty(qty);
		}


		return getCartItems(email);
	}

	private Cart getCart(String email) {

		// 해당 이메일이 장바구니가 있으면 반환

		Cart cart = null;

		Optional<Cart> result = cartRepository.getCartOfMember(email);

		if(result.isEmpty()){

			log.info("Cart of the member is not exist!!");

			Member member = Member.builder()
					.email(email)
					.build();

			Cart tempCart = Cart.builder()
					.owner(member)
					.build();

			cart = cartRepository.save(tempCart);

		}else{

			cart = result.get();

		}

		return cart;

		// 없으면 Cart 객채 생성하고 반환

	}

	@Override
	public List<CartItemListDTO> getCartItems(String email) {
		return cartItemRepository.getItemsOfCartDTOByEmail(email);
	}

	@Override
	public List<CartItemListDTO> remove(Long cino) {

		Long cno = cartItemRepository.getCartFromItem(cino);
		cartItemRepository.deleteById(cino);

		return cartItemRepository.getItemsOfCartDTOByCart(cno);
	}
}
