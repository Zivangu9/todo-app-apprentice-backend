package apprentice.ivan.todoappbackend.services;

import java.util.Date;
import java.util.Optional;

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
        return repository.save(todo);
    }

    public Todo update(Integer id, Todo todo) {
        Optional<Todo> todoData = repository.findById(id);
        if (todoData.isPresent()) {
            // Validate
            Todo updatedTodo = todoData.get();
            updatedTodo.setName(todo.getName());
            updatedTodo.setDueDate(todo.getDueDate());
            updatedTodo.setPriority(todo.getPriority());
            return repository.save(updatedTodo);
        }
        return null;
    }
    
    public Todo markTodoAsDone(Integer id) {
        Optional<Todo> todoData = repository.findById(id);
        if (todoData.isPresent()) {
            Todo updatedTodo = todoData.get();
            if (!updatedTodo.getDoneFlag()) {
                updatedTodo.setDoneFlag(true);
                updatedTodo.setDoneDate(new Date());
            }
            return repository.save(updatedTodo);
        }
        return null;
    }
}
