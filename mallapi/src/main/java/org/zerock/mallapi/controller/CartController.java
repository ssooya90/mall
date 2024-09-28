package org.zerock.mallapi.controller;


import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.zerock.mallapi.dto.CartItemDTO;
import org.zerock.mallapi.dto.CartItemListDTO;
import org.zerock.mallapi.service.CartService;

import java.security.Principal;
import java.util.List;

@RestController
@RequiredArgsConstructor
@Log4j2
@RequestMapping("/api/cart")
public class CartController {

	private final CartService cartService;

	@PreAuthorize("#cartItemDTO.email == authentication.name") // 현재 로그인한 사용자와 DTO에 있는 사용자가 동일할 경우만
	@PostMapping("/change")
	public List<CartItemListDTO> changeCart(@RequestBody CartItemDTO cartItemDTO){

		log.info(cartItemDTO);

		if(cartItemDTO.getQty() <= 0){
			return cartService.remove(cartItemDTO.getCino());
		}

		return cartService.addOrModify(cartItemDTO);

	}

	@PreAuthorize("hasRole('ROLE_USER')")
	@GetMapping("/items")
	public List<CartItemListDTO> getCartItems(Principal principal){

		String email = principal.getName();

		log.info(email);

		return cartService.getCartItems(email);
	}



	@PreAuthorize("hasRole('ROLE_USER')")
	@DeleteMapping("/{cino}")
	public List<CartItemListDTO> removeFormCart(@PathVariable("cino") Long cino){
		return cartService.remove(cino);
	}


}
