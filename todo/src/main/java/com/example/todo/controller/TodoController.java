package com.example.todo.controller;

import com.example.todo.dto.TodoDTO;
import com.example.todo.entity.Todo;
import com.example.todo.service.TodoHardCodedService;
import com.example.todo.service.TodoJpaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@CrossOrigin( origins = "http://localhost:4200/")
@RestController
public class TodoController {

    @Autowired
    TodoHardCodedService todoHardCodedService;

    @Autowired
    TodoJpaService todoJpaService;

    @GetMapping("/user/{username}/todos")
    public List<TodoDTO> getAllTodoForUser(@PathVariable String username) {
       return todoHardCodedService.getAllTodosForUser(username);
    }

    @GetMapping("/jpa/user/{username}/todos")
    public List<Todo> getAllTodoForUserJPA(@PathVariable String username) {
        return todoJpaService.getAllTodosForUser(username);
    }

    @GetMapping("/user/{username}/todos/{id}")
    public ResponseEntity<TodoDTO> getTodoById(@PathVariable long id) {
        TodoDTO todoDTO = todoHardCodedService.findTodoById(id);
        if(todoDTO != null) {
            return ResponseEntity.of(Optional.of(todoDTO));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/jpa/user/{username}/todos/{id}")
    public ResponseEntity<Todo> getTodoByIdJPA(@PathVariable long id) {
        Optional<Todo> todo = todoJpaService.findTodoById(id);
        if(todo.isPresent()) {
            return ResponseEntity.of(todo);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/user/{username}/todos/{id}")
    public ResponseEntity<TodoDTO> updateTodo(@RequestBody TodoDTO todo, @PathVariable long id) {
        TodoDTO todoDTO = todoHardCodedService.saveTodo(todo, id);
        if(todoDTO != null) {
            return new ResponseEntity<TodoDTO>(todoDTO, HttpStatus.OK);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/jpa/user/{username}/todos/{id}")
    public ResponseEntity<Todo> updateTodoJPA(@RequestBody Todo todo, @PathVariable long id) {
        Todo todoNew = todoJpaService.saveTodo(todo, id);
        if(todoNew != null) {
            return new ResponseEntity<Todo>(todoNew, HttpStatus.CREATED);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/user/{username}/todos")
    public ResponseEntity<Void> addTodo(@RequestBody TodoDTO todo) {
        TodoDTO todoDTOCreated = todoHardCodedService.saveTodo(todo, null);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(todoDTOCreated.getId()).toUri();
        return ResponseEntity.created(uri).build();
    }

    @PostMapping("/jpa/user/{username}/todos")
    public ResponseEntity<Void> addTodoJPA(@RequestBody Todo todo, @PathVariable String username) {
        todo.setUsername(username);
        Todo todoCreated = todoJpaService.saveTodo(todo, null);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(todoCreated.getId()).toUri();
        return ResponseEntity.created(uri).build();
    }


    @DeleteMapping("/user/{username}/todos/{id}")
    public ResponseEntity<Void> deleteTodoByIdForUser(@PathVariable long id, @PathVariable String username) {
        TodoDTO todoDTO = todoHardCodedService.deleteTodoById(id);
        if(todoDTO == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/jpa/user/{username}/todos/{id}")
    public ResponseEntity<Void> deleteTodoByIdForUserJPA(@PathVariable long id, @PathVariable String username) {
        Todo todo = todoJpaService.deleteTodoById(id);
        if(todo == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.noContent().build();
    }
}
