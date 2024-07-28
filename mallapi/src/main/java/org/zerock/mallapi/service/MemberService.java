package org.zerock.mallapi.service;

import org.springframework.transaction.annotation.Transactional;
import org.zerock.mallapi.dto.MemberDTO;

@Transactional
public interface MemberService {

	MemberDTO getKakaoMember(String accessToken);





}
