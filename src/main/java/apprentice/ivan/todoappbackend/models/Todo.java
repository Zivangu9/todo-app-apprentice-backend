package apprentice.ivan.todoappbackend.models;

import java.time.LocalDate;
import java.time.LocalDateTime;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)

public class Todo {
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Integer todoId = -1;
    @NotBlank(message = "name is required")
    @Size(max = 120, message = "name length must be less than 120 chars")
    private String name;
    private LocalDate dueDate;
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Boolean doneFlag = false;
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private LocalDateTime doneDate;
    private Priorities priority;
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private LocalDateTime creationDate = LocalDateTime.now();

    public Todo() {
    }

    public Todo(Integer todoId, String name, LocalDate dueDate, Boolean doneFlag, LocalDateTime doneDate, Priorities priority) {
        this.todoId = todoId;
        this.name = name;
        this.dueDate = dueDate;
        this.doneFlag = doneFlag;
        this.doneDate = doneDate;
        this.priority = priority;
    }

    public Integer getTodoId() {
        return todoId;
    }

    public void setTodoId(Integer todoId) {
        this.todoId = todoId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public void setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
    }

    public Boolean getDoneFlag() {
        return doneFlag;
    }

    public void setDoneFlag(Boolean doneFlag) {
        this.doneFlag = doneFlag;
    }

    public LocalDateTime getDoneDate() {
        return doneDate;
    }

    public void setDoneDate(LocalDateTime doneDate) {
        this.doneDate = doneDate;
    }

    public Priorities getPriority() {
        return priority;
    }

    public void setPriority(Priorities priority) {
        this.priority = priority;
    }

    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDateTime creationDate) {
        this.creationDate = creationDate;
    }

}
