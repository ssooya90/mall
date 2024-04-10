package org.zerock.mallapi.domain;


import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Comment;

import java.time.LocalDate;

@Entity
@ToString
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor

@Table(name = "tbl_todo")
public class Todo {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Comment("타이틀")
	private Long tno;

	@Column(length = 500, nullable = false)
	private String title;

	private String content;

	private boolean complete;

	private LocalDate dueDate;

	public void setTitle(String title) {
		this.title = title;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public void setComplete(boolean complete) {
		this.complete = complete;
	}
}
