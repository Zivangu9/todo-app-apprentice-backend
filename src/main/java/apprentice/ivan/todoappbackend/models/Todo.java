package apprentice.ivan.todoappbackend.models;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Todo {
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Integer todoId = -1;
    private String name;
    private Date dueDate;
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Boolean doneFlag = false;
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Date doneDate;
    private Priorities priority;
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Date creationDate = new Date();

    public Todo() {
    }

    public Todo(Integer todoId, String name, Date dueDate, Boolean doneFlag, Date doneDate, Priorities priority) {
        this.todoId = todoId;
        this.name = name;
        this.dueDate = dueDate;
        this.doneFlag = doneFlag;
        this.doneDate = doneDate;
        if (doneFlag && doneDate == null)
            this.doneDate = new Date();
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

    public Date getDueDate() {
        return dueDate;
    }

    public void setDueDate(Date dueDate) {
        this.dueDate = dueDate;
    }

    public Boolean getDoneFlag() {
        return doneFlag;
    }

    public void setDoneFlag(Boolean doneFlag) {
        this.doneFlag = doneFlag;
    }

    public Date getDoneDate() {
        return doneDate;
    }

    public void setDoneDate(Date doneDate) {
        this.doneDate = doneDate;
    }

    public Priorities getPriority() {
        return priority;
    }

    public void setPriority(Priorities priority) {
        this.priority = priority;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

}
