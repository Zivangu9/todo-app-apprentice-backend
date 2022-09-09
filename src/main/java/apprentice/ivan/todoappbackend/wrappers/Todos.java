package apprentice.ivan.todoappbackend.wrappers;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.stream.Collectors;

import apprentice.ivan.todoappbackend.models.Priorities;
import apprentice.ivan.todoappbackend.models.Todo;

public class Todos {
    private List<Todo> DUMMY_DATA = List.of(
            new Todo(null, "Create backend app", LocalDate.of(2022, 9, 7), true, null, Priorities.HIGH),
            new Todo(null, "Create frontend app", LocalDate.of(2022, 9, 9), false, null, Priorities.HIGH),
            new Todo(null, "Test BE", LocalDate.of(2022, 9, 9), true, null, Priorities.MEDIUM),
            new Todo(null, "Test FE", LocalDate.of(2022, 9, 9), false, null, Priorities.MEDIUM),
            new Todo(null, "Create tests for BE", LocalDate.of(2022, 9, 9), false, null, Priorities.LOW),
            new Todo(null, "Create tests for FE", LocalDate.of(2022, 9, 9), false, null, Priorities.MEDIUM),
            new Todo(null, "Deploy FrontEnd", LocalDate.of(2022, 9, 9), false, null, Priorities.MEDIUM),
            new Todo(null, "Deply BackEnd", LocalDate.of(2022, 9, 9), false, null, Priorities.HIGH),
            new Todo(null, "Create VM", LocalDate.of(2022, 9, 5), true, null, Priorities.LOW),
            new Todo(null, "Set UP VM", LocalDate.of(2022, 9, 5), false, null, Priorities.HIGH),
            new Todo(null, "Test app on VM", LocalDate.of(2022, 9, 5), false, null, Priorities.MEDIUM));
    private List<Todo> todos = new ArrayList<>();
    private Integer currentId = 1;

    public void addDummyData() {
        for (Todo todo : DUMMY_DATA) {
            if (todo.getDoneFlag())
                todo.setDoneDate(todo.getCreationDate().plusSeconds(new Random().nextInt(150) + 100));
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

    public Boolean delete(Integer id) {
        Optional<Todo> currentTodo = findById(id);
        if (currentTodo.isPresent()) {
            todos.remove((int) this.indexOf(currentTodo.get()));
            return true;
        }
        return false;
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
                    case "priority-desc":
                        Collections.sort(result, (a, b) -> b.getPriority().compareTo(a.getPriority()));
                        break;
                    case "due_date-asc":
                        result = result.stream()
                                .sorted(Comparator.comparing(Todo::getDueDate,
                                        Comparator.nullsLast(Comparator.reverseOrder())))
                                .collect(Collectors.toList());
                        break;
                    case "due_date-desc":
                        result = result.stream()
                                .sorted(Comparator.comparing(Todo::getDueDate,
                                        Comparator.nullsLast(Comparator.naturalOrder())))
                                .collect(Collectors.toList());
                        break;
                }
            }
        }
        return Collections.unmodifiableList(result);
    }
}
