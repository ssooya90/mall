package org.zerock.mallapi.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;
import org.zerock.mallapi.domain.Member;
import org.zerock.mallapi.domain.MemberRole;
import org.zerock.mallapi.dto.MemberDTO;
import org.zerock.mallapi.repository.MemberRepository;

import java.util.LinkedHashMap;
import java.util.Optional;


@Service
@RequiredArgsConstructor
@Log4j2
public class MemberServiceImpl implements MemberService{

	private final MemberRepository memberRepository;

	private final PasswordEncoder passwordEncoder;




	@Override
	public MemberDTO getKakaoMember(String accessToken) {

		String email = getEmailFromKakaoAcessToken(accessToken);

		//accessToken을 이용해서 사용자 정보 가져오기

		// 기존에 DB에 회원 정보가 있는 경우 / 없는 경우

		Optional<Member> result = memberRepository.findById(email);

		if(result.isPresent()){

			MemberDTO memberDTO = entityToDTO(result.get());

			log.info("existed....." + memberDTO);

			 return memberDTO;
		}

		Member socialMember = makeSocialMember(email);

		memberRepository.save(socialMember);

		MemberDTO memberDTO = entityToDTO(socialMember);

		return memberDTO;
	}

	private Member makeSocialMember(String email){

		String tempPassword = makeTempPassword();

		log.info("tempPassword: " + tempPassword);

		Member member = Member.builder()
				.email(email)
				.pw(passwordEncoder.encode(tempPassword))
				.nickname("social Member")
				.social(true)
				.build();

		member.addRole(MemberRole.USER);

		return member;

	}


	private String getEmailFromKakaoAcessToken(String accessToken) {

		String kakaoGetUser = "https://kapi.kakao.com/v2/user/me";

		RestTemplate restTemplate = new RestTemplate();

		HttpHeaders headers = new HttpHeaders();
		headers.add("Authorization", "Bearer " + accessToken);
		headers.add("Content-Type", "application/x-www-form-urlencoded;charset=utf-8");

		HttpEntity<String> entity = new HttpEntity<String>(headers);

		UriComponents uriComponents = UriComponentsBuilder.fromHttpUrl(kakaoGetUser).build();

		ResponseEntity<LinkedHashMap> response =
				restTemplate.exchange(uriComponents.toUri(), HttpMethod.GET, entity, LinkedHashMap.class);


		log.info("response.......................");
		log.info(response);


		LinkedHashMap<String, LinkedHashMap> bodyMap = response.getBody();
		LinkedHashMap<String, LinkedHashMap> kakaoAccount = bodyMap.get("kakao_account");


		log.info(bodyMap);
		log.info("kakaoAccount " + kakaoAccount.get("email"));

		String email = String.valueOf(kakaoAccount.get("email"));

		return email;



	}


	private String makeTempPassword() {

		StringBuffer buffer = new StringBuffer();

		for(int i = 0; i< 10 ; i++){
			buffer.append((char)((int)(Math.random()*55) + 65));
		}

		return buffer.toString();
	}
}
