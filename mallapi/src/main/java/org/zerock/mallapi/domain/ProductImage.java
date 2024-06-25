package org.zerock.mallapi.domain;


import jakarta.persistence.Embeddable;
import lombok.*;

@Embeddable // 임베더블 타입
@Getter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProductImage {

	private String fileName;

	private int ord;

	public void setOrd(int ord) {
		this.ord = ord;
	}

}

