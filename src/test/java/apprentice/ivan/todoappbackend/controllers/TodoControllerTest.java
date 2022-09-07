package apprentice.ivan.todoappbackend.controllers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.event.annotation.AfterTestMethod;

import apprentice.ivan.todoappbackend.models.Todo;
import apprentice.ivan.todoappbackend.models.TodoRequest;
import apprentice.ivan.todoappbackend.repositories.TodoRepository;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@TestInstance(Lifecycle.PER_CLASS)
public class TodoControllerTest {

    @Autowired
    private TodoController controller;
    @Autowired
    private TestRestTemplate template;

    @BeforeAll
    public void setUp(@Autowired TodoRepository repository){
        repository.addDummyData();
    }

    @Test
    public void contextLoads() {
        assertNotNull(controller);
        assertNotNull(template);
    }

    @Test
    @AfterTestMethod
    public void getTodosWithoutFilters() {
        ResponseEntity<String> entity = template.getForEntity("/todos", String.class);
        assertEquals(HttpStatus.OK, entity.getStatusCode());
    }

    @Test
    public void postTodoCorrect() {
        TodoRequest todoRequest = new TodoRequest("Unit testing", "2022-9-15", "MEDIUM");
        ResponseEntity<Todo> entity = template.postForEntity("/todos", todoRequest, Todo.class);
        assertEquals(HttpStatus.CREATED, entity.getStatusCode());
    }

    @Test
    public void postTodoWithoutName() {
        TodoRequest todoRequest = new TodoRequest("", "2022-9-15", "MEDIUM");
        ResponseEntity<Todo> entity = template.postForEntity("/todos", todoRequest, Todo.class);
        assertEquals(HttpStatus.BAD_REQUEST, entity.getStatusCode());
    }

    @Test
    public void postTodoWithoutDueDate() {
        TodoRequest todoRequest = new TodoRequest("Unit testing", null, "MEDIUM");
        ResponseEntity<Todo> entity = template.postForEntity("/todos", todoRequest, Todo.class);
        assertEquals(HttpStatus.CREATED, entity.getStatusCode());
    }

    @Test
    public void postTodoWithoutPriority() {
        TodoRequest todoRequest = new TodoRequest("Unit testing", "2022-9-15", null);
        ResponseEntity<Todo> entity = template.postForEntity("/todos", todoRequest, Todo.class);
        assertEquals(HttpStatus.BAD_REQUEST, entity.getStatusCode());
    }

    @Test
    public void updateTodoComplete() {
        TodoRequest todoRequest = new TodoRequest("Unit testing", "2022-9-15", "HIGH");
        ResponseEntity<Todo> entity = template.exchange("/todos/2", HttpMethod.PUT,
                new HttpEntity<TodoRequest>(todoRequest), Todo.class);
        assertEquals(HttpStatus.OK, entity.getStatusCode());
    }

    @Test
    public void updateTodoWithoutPriority() {
        TodoRequest todoRequest = new TodoRequest("Unit testing", "2022-9-15", null);
        ResponseEntity<Todo> entity = template.exchange("/todos/2", HttpMethod.PUT,
                new HttpEntity<TodoRequest>(todoRequest), Todo.class);
        assertEquals(HttpStatus.BAD_REQUEST, entity.getStatusCode());
    }

    @Test
    public void updateTodoInvalidId() {
        TodoRequest todoRequest = new TodoRequest("Unit testing", "2022-9-15", "HIGH");
        ResponseEntity<Todo> entity = template.exchange("/todos/9999", HttpMethod.PUT,
                new HttpEntity<TodoRequest>(todoRequest), Todo.class);
        assertEquals(HttpStatus.NOT_FOUND, entity.getStatusCode());
    }

    @Test
    public void markTodoAsDoneWithValidId() {
        ResponseEntity<Todo> entity = template.exchange("/todos/2/done", HttpMethod.PUT, null, Todo.class);
        assertEquals(HttpStatus.OK, entity.getStatusCode());
    }

    @Test
    public void markTodoAsDoneWithInvalidId() {
        ResponseEntity<Todo> entity = template.exchange("/todos/9999/done", HttpMethod.PUT, null, Todo.class);
        assertEquals(HttpStatus.NOT_FOUND, entity.getStatusCode());
    }

    @Test
    public void markTodoAsUndoneWithValidId() {
        ResponseEntity<Todo> entity = template.exchange("/todos/2/undone", HttpMethod.PUT, null, Todo.class);
        assertEquals(HttpStatus.OK, entity.getStatusCode());
    }

    @Test
    public void markTodoAsUndoneWithInvalidId() {
        ResponseEntity<Todo> entity = template.exchange("/todos/9999/undone", HttpMethod.PUT, null, Todo.class);
        assertEquals(HttpStatus.NOT_FOUND, entity.getStatusCode());
    }

    @Test
    public void deleteTodoWithValidId() {
        ResponseEntity<Todo> entity = template.exchange("/todos/1", HttpMethod.DELETE, null, Todo.class);
        assertEquals(HttpStatus.OK, entity.getStatusCode());
    }

    @Test
    public void deleteTodoWithInvalidId() {
        ResponseEntity<Todo> entity = template.exchange("/todos/9999", HttpMethod.DELETE, null, Todo.class);
        assertEquals(HttpStatus.NOT_FOUND, entity.getStatusCode());
    }
}
