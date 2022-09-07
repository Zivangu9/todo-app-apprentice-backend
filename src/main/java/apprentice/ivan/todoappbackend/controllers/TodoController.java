package apprentice.ivan.todoappbackend.controllers;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import apprentice.ivan.todoappbackend.models.Metrics;
import apprentice.ivan.todoappbackend.models.Priorities;
import apprentice.ivan.todoappbackend.models.Todo;
import apprentice.ivan.todoappbackend.services.TodoService;

@RestController
@RequestMapping("/todos")
public class TodoController {
    @Autowired
    private TodoService service;

    @GetMapping("")
    public Page<Todo> filterTodos(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(required = false) String sort,
            @RequestParam(required = false) Boolean done,
            @RequestParam(required = false) String name,
            @RequestParam(required = false) Priorities priority) {
        Pageable paging = PageRequest.of(page, size);
        return service.filterTodos(paging, sort, done, name, priority);
    }

    @GetMapping("/metrics")
    public Metrics metricsDoneTodos() {
        return service.getMetrics();
    }

    @PostMapping("")
    public ResponseEntity<Todo> createTodo(@Valid @RequestBody Todo todo) {
        Todo createdTodo = service.create(todo);
        return new ResponseEntity<>(createdTodo, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Todo> updateTodo(@PathVariable Integer id, @Valid @RequestBody Todo todo) {
        Todo updatedTodo = service.update(id, todo);
        if (updatedTodo == null)
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(updatedTodo, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Todo> deleteTodo(@PathVariable Integer id) {
        Boolean deleted = service.delete(id);
        if (!deleted)
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping("/{id}/done")
    public ResponseEntity<Todo> markTodoAsDone(@PathVariable Integer id) {
        Todo updatedTodo = service.markTodoAsDone(id);
        if (updatedTodo == null)
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(updatedTodo, HttpStatus.OK);
    }

    @PutMapping("/{id}/undone")
    public ResponseEntity<Todo> markTodoAsUndone(@PathVariable Integer id) {
        Todo updatedTodo = service.markTodoAsUndone(id);
        if (updatedTodo == null)
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(updatedTodo, HttpStatus.OK);
    }
}
