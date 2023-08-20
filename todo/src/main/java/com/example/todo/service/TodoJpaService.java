package com.example.todo.service;

import com.example.todo.dto.TodoDTO;
import com.example.todo.entity.Todo;
import com.example.todo.repository.TodoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

@Service
public class TodoJpaService {

    @Autowired
    private TodoRepository todoRepository;

    public TodoJpaService(TodoRepository todoRepository) {
        this.todoRepository = todoRepository;
    }

    public List<Todo> getAllTodosForUser(String username) {
        return todoRepository.findByUsername(username);
    }

    public Todo deleteTodoById(long id) {
        Todo todo = todoRepository.findById(id).get();
        if(todo != null ) {
            todoRepository.delete(todo);
            return todo;
        }
        return null;
    }

    public Optional<Todo> findTodoById(long id) {
       return todoRepository.findById(id);
    }

    public Todo saveTodo(Todo todo, Long id) {
        if(id == null) {
            Todo todoNew = todoRepository.save(todo);
            return todoNew;
        } else {
            Optional<Todo> todoToUpdate = findTodoById(todo.getId());
            if(todoToUpdate.isPresent()) {
                Todo todo1 = todoToUpdate.get();
                todo1.setDescription(todo.getDescription());
                todo1.setTargetDate(todo.getTargetDate());
                todoRepository.save(todo1);
                return todo1;
            } else {
                return null;
            }
        }
    }

}
