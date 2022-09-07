package apprentice.ivan.todoappbackend.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import apprentice.ivan.todoappbackend.models.Priorities;
import apprentice.ivan.todoappbackend.models.Todo;
import apprentice.ivan.todoappbackend.wrappers.Todos;

@Repository
public class TodoRepository {
    private Todos todosWrapper = new Todos();

    public TodoRepository() {
        todosWrapper.addDummyData(); // Add dummyData for testing
    }

    public List<Todo> findAll() {
        return todosWrapper.findAll();
    }

    public Page<Todo> filterTodos(Pageable paging, String sort, Boolean done, String name, Priorities priority) {
        List<Todo> todos = todosWrapper.filterTodos(sort, done, name, priority);
        return getPage(paging, todos);
    }

    private Page<Todo> getPage(Pageable paging, List<Todo> todos) {
        int start = paging.getPageNumber() * paging.getPageSize();
        int end = start + paging.getPageSize();
        if (start > todos.size())
            start = todos.size();
        if (end > todos.size())
            end = todos.size();

        return new PageImpl<>(todos.subList(start, end), paging, todos.size());
    }

    public Optional<Todo> findById(Integer id) {
        return todosWrapper.findById(id);
    }

    public Todo save(Todo todo) {
        return todosWrapper.save(todo);
    }

    public Boolean delete(Integer id) {
        return todosWrapper.delete(id);
    }
}
