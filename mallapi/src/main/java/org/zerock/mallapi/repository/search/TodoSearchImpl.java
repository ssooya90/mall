package org.zerock.mallapi.repository.search;

import com.querydsl.jpa.JPQLQuery;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.zerock.mallapi.domain.QTodo;
import org.zerock.mallapi.domain.Todo;
import org.zerock.mallapi.dto.PageRequestDTO;

import java.util.List;

@Log4j2
public class TodoSearchImpl extends QuerydslRepositorySupport implements TodoSearch {

	public TodoSearchImpl() {
		super(Todo.class);
	}

	@Override
	public Page<Todo> search1(PageRequestDTO pageRequestDTO) {

		log.info("search1..................");

		// 쿼리를 날리기 위한 객채 (Q도메인이라고 부름) Querydsl
		QTodo todo = QTodo.todo;

		JPQLQuery<Todo> query = from(todo);

		//검색 조건 영역
//		query.where(todo.title.contains("1"));

		Pageable pageable = PageRequest.of(pageRequestDTO.getPage() - 1,pageRequestDTO.getSize(), Sort.by("tno").descending());

		// querydsl paginging 처
		this.getQuerydsl().applyPagination(pageable, query);

		List<Todo> list = query.fetch(); // 목록 데이터

		long total = query.fetchCount();

		return new PageImpl<>(list , pageable ,total);
	}


}
