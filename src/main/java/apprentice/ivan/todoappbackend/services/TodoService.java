package apprentice.ivan.todoappbackend.services;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import apprentice.ivan.todoappbackend.models.Metrics;
import apprentice.ivan.todoappbackend.models.Priorities;
import apprentice.ivan.todoappbackend.models.Todo;
import apprentice.ivan.todoappbackend.models.TodoRequest;
import apprentice.ivan.todoappbackend.repositories.TodoRepository;

@Service
public class TodoService {
    private TodoRepository repository;

    @Autowired
    public TodoService(TodoRepository repository) {
        this.repository = repository;
    }

    public Page<Todo> allTodos(Pageable paging) {
        return repository.findAll(paging);
    }

    public Page<Todo> filterTodos(int page, int size, String sort, Boolean done, String name,
            Priorities priority) {
        Sort pageableSort = Sort.unsorted();
        Sort pageableAndSort = Sort.unsorted();
        if (sort != null) {
            String[] filters = sort.split(" ");
            if(filters[0].startsWith("priority")){
                pageableSort = Sort.by("priority");
            }
            else if(filters[0].startsWith("due_date")){
                pageableSort = Sort.by("dueDate");
            }
            if(filters[0].endsWith("asc")){
                pageableSort = pageableSort.ascending();
            }
            else if(filters[0].endsWith("desc")){
                pageableSort = pageableSort.descending();
            }
            if(filters.length == 2) {
                if(filters[1].startsWith("priority")){
                    pageableAndSort = Sort.by("priority");
                }
                else if(filters[1].startsWith("due_date")){
                    pageableAndSort = Sort.by("dueDate");
                }
                if(filters[1].endsWith("asc")){
                    pageableAndSort = pageableAndSort.ascending();
                }
                else if(filters[1].endsWith("desc")){
                    pageableAndSort = pageableAndSort.descending();
                }
                pageableSort = pageableSort.and(pageableAndSort);
            }
        }
        Pageable paging = PageRequest.of(page, size, pageableSort);

        if (done == null && name == null && priority == null)
            return repository.findAll(paging);
        if (name == null && priority == null)
            return repository.findByDone(done, paging);
        if (done == null && priority == null)
            return repository.findByNameLike(name, paging);
        if (done == null && name == null)
            return repository.findByPriority(priority, paging);
        if (priority == null)
            return repository.findByDoneAndNameLike(done, name, paging);
        if (name == null)
            return repository.findByDoneAndPriority(done, priority, paging);
        if (done == null)
            return repository.findByNameLikeAndPriority(name, priority, paging);
        return repository.findByDoneAndNameLikeAndPriority(done, name, priority, paging);
    }

    public Todo crete(TodoRequest todoRequest) {
        return repository.save(mapTodo(todoRequest));
    }

    public Todo update(Integer id, TodoRequest todoRequest) {
        Optional<Todo> todoData = repository.findById(id);
        if (todoData.isPresent()) {
            Todo todo = mapTodo(todoRequest);
            Todo updatedTodo = todoData.get();
            updatedTodo.setName(todo.getName());
            updatedTodo.setDueDate(todo.getDueDate());
            updatedTodo.setPriority(todo.getPriority());
            return repository.save(updatedTodo);
        }
        return null;
    }

    public Boolean delete(Integer id) {
        Optional<Todo> todoData = repository.findById(id);
        if (todoData.isPresent()) {
            repository.deleteById(id);
            return true;
        }
        return false;
    }

    public Todo markTodoAsDone(Integer id) {
        Optional<Todo> todoData = repository.findById(id);
        if (todoData.isPresent()) {
            Todo updatedTodo = todoData.get();
            if (!updatedTodo.getDone()) {
                updatedTodo.setDone(true);
                updatedTodo.setDoneDate(LocalDateTime.now());
            }
            return repository.save(updatedTodo);
        }
        return null;
    }

    public Todo markTodoAsUndone(Integer id) {
        Optional<Todo> todoData = repository.findById(id);
        if (todoData.isPresent()) {
            Todo updatedTodo = todoData.get();
            if (updatedTodo.getDone()) {
                updatedTodo.setDone(false);
                updatedTodo.setDoneDate(null);
            }
            return repository.save(updatedTodo);
        }
        return null;
    }

    private Todo mapTodo(TodoRequest todoRequest) {
        Todo todo = new Todo();
        todo.setName(todoRequest.getName());
        if (todoRequest.getDueDate() != null)
            todo.setDueDate(LocalDate.parse(todoRequest.getDueDate()));
        todo.setPriority(Priorities.valueOf(todoRequest.getPriority().toUpperCase()));
        return todo;
    }

    public Metrics getMetrics() {
        Iterable<Todo> allTodos = repository.findAll();
        List<Todo> filteredTodos= StreamSupport.stream(allTodos.spliterator(), false).filter(todo -> todo.getDone() == true).collect(Collectors.toList());
        Metrics metrics = new Metrics();
        metrics.setGeneralAvg(getMetric(filteredTodos));
        metrics.setLowAvg(getMetricByPriority(filteredTodos, Priorities.LOW));
        metrics.setMediumAvg(getMetricByPriority(filteredTodos, Priorities.MEDIUM));
        metrics.setHighAvg(getMetricByPriority(filteredTodos, Priorities.HIGH));
        return metrics;
    }

    private Long getMetricByPriority(List<Todo> todos, Priorities priority) {
        return getMetric(
                todos.stream().filter(todo -> todo.getPriority().equals(priority)).collect(Collectors.toList()));
    }

    private Long getMetric(List<Todo> todos) {
        AtomicInteger count = new AtomicInteger();
        Long sum = todos.stream()
                .mapToLong(todo -> (Duration.between(todo.getCreationDate(), todo.getDoneDate()).getSeconds()))
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
}
