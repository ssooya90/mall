package org.zerock.mallapi.security.filter;

import com.google.gson.Gson;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;
import org.zerock.mallapi.dto.MemberDTO;
import org.zerock.mallapi.util.JWTUtil;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Map;

@Log4j2
public class JWTCheckFilter extends OncePerRequestFilter {

	// OncePerRequestFilter 모든 리퀘스트에 필터함


	@Override
	protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {


		String path = request.getRequestURI();

		log.info("check uri ---------------" + path);


		if(path.startsWith("/api/member/")){
			return true;
		}


		// return false -> 체크 함
		// return true -> 체크 안함

//		return super.shouldNotFilter(request);
		return false;
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {


		log.info("------------");
		log.info("------------");
		log.info("------------");
		log.info("------------");


		String authHeader = request.getHeader("Authorization");

		//Bearer //7 (JWT 문자열)


		try {
			String accessToken = authHeader.substring(7);
			Map<String,Object> claims = JWTUtil.validateToken(accessToken);

			log.info("JWT claims : " + claims);

			//dest 성공하면 다음 목적지로 보내, 실패하면 실패처리
//			filterChain.doFilter(request, response);


			// 토큰이 성공했으면 사용자의 정보를 가져옴

			String email = (String) claims.get("email");
			String pw = (String) claims.get("pw");
			String nickname = (String) claims.get("nickname");
			Boolean social = (Boolean) claims.get("social");
			List<String> roleNames = (List<String>) claims.get("roleNames");

			MemberDTO memberDTO = new MemberDTO( email, pw, nickname, social.booleanValue(),
					roleNames);
			log.info("-----------------------------------");
			log.info(memberDTO);
			log.info(memberDTO.getAuthorities());
			UsernamePasswordAuthenticationToken authenticationToken
					= new UsernamePasswordAuthenticationToken(memberDTO,pw,memberDTO.getAuthorities());
			SecurityContextHolder.getContext().setAuthentication(authenticationToken);
			filterChain.doFilter(request, response);




		} catch (Exception e){
			log.error("JWT Check Error ...");
			log.error(e.getMessage());

			Gson gson = new Gson();
			String msg = gson.toJson(Map.of("error","ERROR_ACCESS_TOKEN"));

			response.setContentType("application/json");
			PrintWriter printWriter = response.getWriter();
			printWriter.println(msg);
			printWriter.close();

		}

	}
}
