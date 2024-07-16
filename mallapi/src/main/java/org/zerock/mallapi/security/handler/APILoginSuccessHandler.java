package org.zerock.mallapi.security.handler;

import com.google.gson.Gson;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.zerock.mallapi.dto.MemberDTO;
import org.zerock.mallapi.util.JWTUtil;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

// Bean으로 등록하지 않음
// Security config에서 해당 클래스를 추가해서 사용하기 때문

@Log4j2
public class APILoginSuccessHandler implements AuthenticationSuccessHandler {

	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authentication) throws IOException, ServletException {
		AuthenticationSuccessHandler.super.onAuthenticationSuccess(request, response, chain, authentication);
	}

	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {

		log.info("---------------------------");
		log.info(authentication);
		log.info("---------------------------");

		MemberDTO memberDTO = (MemberDTO) authentication.getPrincipal();

		Map<String, Object> claims = memberDTO.getClaims();

		String accessToken = JWTUtil.generateToken(claims, 10);
		String refreshToken = JWTUtil.generateToken(claims, 60*24);

		claims.put("accessToken",accessToken);
		claims.put("refreshToken",refreshToken);

		Gson gson = new Gson();

		String jsonString = gson.toJson(claims);

		response.setContentType("application/json; charset=UTF-8");

		PrintWriter printWriter = response.getWriter();
		printWriter.println(jsonString);
		printWriter.close();

	}
}
