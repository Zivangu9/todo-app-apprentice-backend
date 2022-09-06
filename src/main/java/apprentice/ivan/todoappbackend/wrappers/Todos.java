package apprentice.ivan.todoappbackend.wrappers;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.stream.Collectors;

import apprentice.ivan.todoappbackend.models.Priorities;
import apprentice.ivan.todoappbackend.models.Todo;

public class Todos {
    private List<Todo> DUMMY_DATA = List.of(
            new Todo(0, "Create backend app", Date.valueOf("2022-09-07"), true, null, Priorities.HIGH),
            new Todo(0, "Create frontend app", Date.valueOf("2022-09-09"), false, null, Priorities.HIGH),
            new Todo(0, "Test BE", Date.valueOf("2022-09-09"), true, null, Priorities.MEDIUM),
            new Todo(0, "Test FE", Date.valueOf("2022-09-09"), false, null, Priorities.MEDIUM),
            new Todo(0, "Create tests for BE", Date.valueOf("2022-09-09"), false, null, Priorities.LOW),
            new Todo(0, "Create tests for FE", Date.valueOf("2022-09-09"), false, null, Priorities.MEDIUM),
            new Todo(0, "Deploy FrontEnd", Date.valueOf("2022-09-09"), false, null, Priorities.MEDIUM),
            new Todo(0, "Deply BackEnd", Date.valueOf("2022-09-09"), false, null, Priorities.HIGH),
            new Todo(0, "Create VM", Date.valueOf("2022-09-05"), true, null, Priorities.LOW),
            new Todo(0, "Set UP VM", Date.valueOf("2022-09-05"), false, null, Priorities.HIGH),
            new Todo(0, "Test app on VM", Date.valueOf("2022-09-05"), false, null, Priorities.MEDIUM));
    private List<Todo> todos = new ArrayList<>();
    private Integer currentId = 1;

    public void addDummyData() {
        for (Todo todo : DUMMY_DATA) {
            if (todo.getDoneFlag())
                todo.setDoneDate(
                        Date.from(todo.getCreationDate().toInstant().plusSeconds(new Random().nextInt(150) + 100)));
            this.addTodo(todo);
        }
    }

    private Todo addTodo(Todo todo) {
        todo.setTodoId(currentId++);
        todos.add(todo);
        return todo;
    }

    private Todo updateTodo(Todo todo) {
        Integer index = this.indexOf(todo);
        todos.set(index, todo);
        return todos.get(index);
    }

    private Integer indexOf(Todo todo) {
        for (int i = 0; i < todos.size(); i++) {
            if (todos.get(i).getTodoId() == todo.getTodoId())
                return i;
        }
        return -1;
    }

    public Todo save(Todo todo) {
        Optional<Todo> currentTodo = findById(todo.getTodoId());
        if (currentTodo.isPresent()) {
            return updateTodo(currentTodo.get());
        }
        return addTodo(todo);
    }

    public List<Todo> findAll() {
        return Collections.unmodifiableList(todos);
    }

    public Optional<Todo> findById(Integer id) {
        return new ArrayList<>(todos).stream().filter(todo -> todo.getTodoId() == id).findFirst();
    }

    public List<Todo> filterTodos(String sort, Boolean done, String name, Priorities priority) {
        List<Todo> result = new ArrayList<>(todos);
        if (done != null)
            result = result.stream().filter(todo -> todo.getDoneFlag() == done).collect(Collectors.toList());
        if (name != null)
            result = result.stream().filter(todo -> todo.getName().indexOf(name) >= 0).collect(Collectors.toList());
        if (priority != null)
            result = result.stream().filter(todo -> todo.getPriority() == priority).collect(Collectors.toList());
        if (sort != null) {
            String[] filters = sort.split(" ");
            for (String filter : filters) {
                switch (filter) {
                    case "priority-asc":
                        Collections.sort(result, (a, b) -> a.getPriority().compareTo(b.getPriority()));
                        break;
                    case "priority-dec":
                        Collections.sort(result, (a, b) -> b.getPriority().compareTo(a.getPriority()));
                        break;
                    case "due_date-asc":
                        Collections.sort(result, (a, b) -> a.getDueDate().compareTo(b.getDueDate()));
                        break;
                    case "due_date-dec":
                        Collections.sort(result, (a, b) -> b.getDueDate().compareTo(a.getDueDate()));
                        break;
                }
            }
        }
        return Collections.unmodifiableList(result);
    }
}
