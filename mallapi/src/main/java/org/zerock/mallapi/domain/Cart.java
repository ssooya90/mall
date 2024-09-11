package org.zerock.mallapi.domain;


import jakarta.persistence.*;
import lombok.*;


@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@ToString(exclude = "owner")
@Table(
		name ="tbl_cart",
		indexes = { @Index(name="idx_cart_email", columnList = "member_owner")}
)
public class Cart {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long cno;

	@OneToOne
	@JoinColumn(name="member_owner") // 컬럼명을 사용하는 이유는 인덱스를 쓰기 위해서
	private Member owner;

}
