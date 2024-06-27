package org.zerock.mallapi.domain;


import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Builder
@Table(name = "tbl_product")
@AllArgsConstructor
@NoArgsConstructor
@ToString(exclude = "imageList") // 연관관계는 빼줌
public class Product {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long pno;

	private String pname;

	private int price;

	private String pdesc;

	private boolean delFlag;

	// 해당 강의는 manyToOne이 아닌 엘리먼트 컬렉션으로 관리함

	@ElementCollection
	@Builder.Default
	private List<ProductImage> imageList = new ArrayList<>();


	public void setPrice(int price) {
		this.price = price;
	}


	public void setDesc(String desc) {
		this.pdesc = desc;
	}


	public void setName(String name) {
		this.pname = name;
	}

	public void isDelFlag(boolean delFlag){
		this.delFlag = delFlag;
	}

	public void addImgae(ProductImage image) {
		image.setOrd(imageList.size());
		imageList.add(image);
	}

	public void addImageString(String fileName){

		ProductImage productImage = ProductImage.builder()
						.fileName(fileName)
						.build();

		addImgae(productImage);
	}


	public void clearList() {
		this.imageList.clear();
	}

}
