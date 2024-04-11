package org.zerock.mallapi.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.zerock.mallapi.domain.Todo;
import org.zerock.mallapi.dto.TodoDTO;
import org.zerock.mallapi.repository.TodoRepository;

import java.util.Optional;

@Service
@Log4j2
@RequiredArgsConstructor
@Transactional
public class TodoServiceImpl implements TodoService{

	private final TodoRepository todoRepository;

	@Override
	public TodoDTO get(Long tno) {

		Optional<Todo> result = todoRepository.findById(tno);
		Todo todo = result.orElseThrow();

		return entityToDTO(todo);
	}

	@Override
	public Long register(TodoDTO dto) {

		Todo todo = dtoToEntity(dto);
		Todo result = todoRepository.save(todo);

		return result.getTno();
	}

	@Override
	public void modify(TodoDTO dto) {

		Optional<Todo> result = todoRepository.findById(dto.getTno());
		Todo todo = result.orElseThrow();

		todo.setTitle(dto.getTitle());
		todo.setContent(dto.getContent());
		todo.setComplete(dto.isComplete());

		todoRepository.save(todo);




	}

	@Override
	public void remove(Long tno) {

		todoRepository.deleteById(tno);

	}
}
