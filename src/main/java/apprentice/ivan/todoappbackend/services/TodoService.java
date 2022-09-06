package apprentice.ivan.todoappbackend.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import apprentice.ivan.todoappbackend.models.Priorities;
import apprentice.ivan.todoappbackend.models.Todo;
import apprentice.ivan.todoappbackend.repositories.TodoRepository;

@Service
public class TodoService {
    @Autowired
    private TodoRepository repository;

    public Page<Todo> filterTodos(Pageable paging, String sort, Boolean done, String name, Priorities priority) {
        // Validate
        return repository.filterTodos(paging, sort, done, name, priority);
    }

    public Todo create(Todo todo) {
        // validate
        return repository.createTodo(todo);
    }
}
