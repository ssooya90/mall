package org.zerock.mallapi.repository;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.zerock.mallapi.domain.Member;


// 이메일이 아이디이기때문에 String 타입
public interface MemberRepository extends JpaRepository<Member, String> {

	@EntityGraph(attributePaths = {"memberRoleList"})
	@Query("select m from Member m where m.email = :email")
	Member getWithRole(@Param("email") String email);

}
