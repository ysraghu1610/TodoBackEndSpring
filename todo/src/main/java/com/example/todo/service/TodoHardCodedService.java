package com.example.todo.service;

import com.example.todo.dto.TodoDTO;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class TodoHardCodedService {

    private static List<TodoDTO> todoDTOList = new ArrayList<>();
    private static long iCounter = 0;
    static {
        todoDTOList.add(new TodoDTO(++iCounter, "ram", "take bath", new Date(), true ));
        todoDTOList.add(new TodoDTO(++iCounter, "ram", "visit temple", new Date(), false ));
        todoDTOList.add(new TodoDTO(++iCounter, "ram", "read books", new Date(), true ));
        todoDTOList.add(new TodoDTO(++iCounter, "ram", "go for shopping", new Date(), false ));
        todoDTOList.add(new TodoDTO(++iCounter, "ram", "have lunch", new Date(), true ));
    }
    public List<TodoDTO> getAllTodosForUser(String username) {
        return todoDTOList;
    }

    public TodoDTO deleteTodoById(long id) {
        TodoDTO todo = findTodoById(id);
        if(todo != null ) {
            todoDTOList.remove(todo);
            return todo;
        }
        return null;
    }

    public TodoDTO findTodoById(long id) {
        for(TodoDTO todo :todoDTOList) {
            if(todo.getId() == id) {
                return todo;
            }
        }

        return null;
    }

    public TodoDTO saveTodo(TodoDTO todo, Long id) {
        if(id == null) {
            long newId = ++iCounter;
            todoDTOList.add(new TodoDTO(newId, todo.getUsername(), todo.getDescription(), todo.getTargetDate(), todo.isDone()));
            return findTodoById(newId);
        } else {
            TodoDTO todo1 = findTodoById(id);
            if(todo1 != null) {
                todo1.setDescription(todo.getDescription());
                todo1.setTargetDate(todo.getTargetDate());
            }
            return todo1;
        }
    }
}
