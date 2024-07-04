package org.zerock.mallapi.domain;


import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Builder
@Table(name = "tbl_member")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@ToString(exclude = "memberRoleList") //연관 관계까 있을 경우 무조건 추가
public class Member {

	@Id
	private String email;

	private String pw;

	private String nickname;

	private boolean social;

	@ElementCollection(fetch = FetchType.LAZY)
	@Builder.Default
	private List<MemberRole> memberRoleList = new ArrayList<>();

	public void addRole(MemberRole memberRole){
		memberRoleList.add(memberRole);
	}

	public void clearRole(){
		memberRoleList.clear();
	}


}
