package org.zerock.mallapi.repository;


import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.zerock.mallapi.domain.Todo;
import org.zerock.mallapi.dto.PageRequestDTO;
import org.zerock.mallapi.service.TodoService;

import java.time.LocalDate;
import java.util.Optional;

@SpringBootTest
@Log4j2
public class TodoRepositoryTests {

	@Autowired
	private TodoRepository todoRepository;

	@Autowired
	private TodoService todoService;

	@Test
	public void test1(){

		Assertions.assertNotNull(todoRepository);

		log.info(todoRepository.getClass().getName());

	}

	@Test
	public void testInsert () {

		for ( int i = 0 ; i < 100 ; i++){

			Todo todo = Todo.builder()
					.title("Title" + i)
					.content("Contents..." + i)
					.dueDate(LocalDate.of(2023,12,30))
					.build();

			Todo result = todoRepository.save(todo);
			log.info(result);

		}


	}

	@Test
	public void testRead () {

		Long tno = 1L;

		Optional<Todo> result = todoRepository.findById(tno);

		Todo todo = result.orElseThrow();

		log.info(todo);
	}


	@Test
	public void testUpdate () {

		Long tno = 1L;

		Optional<Todo> result = todoRepository.findById(tno);

		Todo todo = result.orElseThrow();

		todo.setTitle("Update Title");
		todo.setContent("Update Content");

		todoRepository.save(todo);
	}

	@Test
	public void testPaging(){

		Pageable pageable = PageRequest.of(0, 10, Sort.by("tno").descending());

		Page<Todo> result = todoRepository.findAll(pageable);

		log.info(result.getTotalElements());
		log.info(result.getContent());



		// 페이지 번호가 0부터
	}

//	@Test
//	public void testSearch1(){
//		todoRepository.search1();
//	}

	@Test
	public void testGetList(){

		PageRequestDTO pageRequestDTO = PageRequestDTO.builder().page(11).build();

		log.info(todoService.getList(pageRequestDTO));
	}

}
