package org.zerock.mallapi.repository.search;

import com.querydsl.jpa.JPQLQuery;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.zerock.mallapi.domain.QTodo;
import org.zerock.mallapi.domain.Todo;

@Log4j2
public class TodoSearchImpl extends QuerydslRepositorySupport implements TodoSearch {

	public TodoSearchImpl() {
		super(Todo.class);
	}

	@Override
	public Page<Todo> search1() {

		log.info("search1..................");

		// 쿼리를 날리기 위한 객채 (Q도메인이라고 부름) Querydsl
		QTodo todo = QTodo.todo;

		JPQLQuery<Todo> query = from(todo);
		query.where(todo.title.contains("1"));

		Pageable pageable = PageRequest.of(1,10, Sort.by("tno").descending());

		// querydsl paginging 처
		this.getQuerydsl().applyPagination(pageable, query);

		query.fetch(); // 목록 데이터

		query.fetchCount();



		return null;
	}
}
