package org.zerock.mallapi.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;
import org.zerock.mallapi.dto.MemberDTO;
import org.zerock.mallapi.repository.MemberRepository;

import java.util.LinkedHashMap;


@Service
@RequiredArgsConstructor
@Log4j2
public class MemberServiceImpl implements MemberService{

	private final MemberRepository memberRepository;

	@Override
	public MemberDTO getKakaoMember(String accessToken) {

		getEmailFromKakaoAcessToken(accessToken);

		//accessToken을 이용해서 사용자 정보 가져오기

		// 기존에 DB에 회원 정보가 있는 경우 / 없는 경우



		return null;
	}


	private void getEmailFromKakaoAcessToken(String accessToken) {

		String kakaoGetUser = "https://kapi.kakao.com/v2/user/me";

		RestTemplate restTemplate = new RestTemplate();

		HttpHeaders headers = new HttpHeaders();
		headers.add("Authorization", "Bearer " + accessToken);
		headers.add("Content-Type", "application/x-www-form-urlencoded;charset=utf-8");

		HttpEntity<String> entity = new HttpEntity<String>(headers);

		UriComponents uriComponents = UriComponentsBuilder.fromHttpUrl(kakaoGetUser).build();

		ResponseEntity<LinkedHashMap> response =
				restTemplate.exchange(uriComponents.toUri(), HttpMethod.GET, entity, LinkedHashMap.class);


		log.info(response);


		LinkedHashMap<String, LinkedHashMap> bodyMap = response.getBody();

		log.info("===========");
		log.info(bodyMap);


	}
}
