package apprentice.ivan.todoappbackend.services;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import apprentice.ivan.todoappbackend.models.Metrics;
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

    public Metrics getMetrics() {
        List<Todo> todos = new ArrayList<>(repository.findAll());
        todos = todos.stream().filter(todo -> todo.getDoneFlag() == true).collect(Collectors.toList());
        Metrics metrics = new Metrics();
        metrics.setGeneralAvg(getMetric(todos));
        metrics.setLowAvg(getMetricByPriority(todos, Priorities.LOW));
        metrics.setMediumAvg(getMetricByPriority(todos, Priorities.MEDIUM));
        metrics.setHighAvg(getMetricByPriority(todos, Priorities.HIGH));
        return metrics;
    }
    private Long getMetricByPriority(List<Todo> todos, Priorities priority) {
        return getMetric(todos.stream().filter(todo -> todo.getPriority().equals(priority)).collect(Collectors.toList()));
    }

    private Long getMetric(List<Todo> todos) {
        AtomicInteger count = new AtomicInteger();
        Long sum = todos.stream()
                .mapToLong(todo -> (todo.getDoneDate().getTime() - todo.getCreationDate().getTime()) / 1000)
                .reduce(0, (s, e) -> {
                    count.incrementAndGet();
                    return s + e;
                });
        try {
            return sum / count.get();    
        } catch (ArithmeticException e) {
            return Long.valueOf(0);
        }
        
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

    public Todo markTodoAsUndone(Integer id) {
        Optional<Todo> todoData = repository.findById(id);
        if (todoData.isPresent()) {
            Todo updatedTodo = todoData.get();
            if (updatedTodo.getDoneFlag()) {
                updatedTodo.setDoneFlag(false);
                updatedTodo.setDoneDate(null);
            }
            return repository.save(updatedTodo);
        }
        return null;
    }
}
