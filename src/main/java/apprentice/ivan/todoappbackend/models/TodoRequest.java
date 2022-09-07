package apprentice.ivan.todoappbackend.models;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public class TodoRequest {
    @NotBlank(message = "name is required")
    @Size(max = 120, message = "name length must be less than 120 chars")
    private String name;
    @Pattern(regexp = "((20)\\d\\d)-(0?[1-9]|1[012])-([12][0-9]|3[01]|0?[1-9])", message = "dueDate must be in YYYY-MM-DD format, and also between the years 2000 and 2099")
    private String dueDate;
    @NotNull(message = "priority is required")
    @Pattern(regexp = "(?i)(LOW|MEDIUM|HIGH)", message = "priority can only be LOW, MEDIUM or HIGH")
    private String priority;

    public TodoRequest(String name, String dueDate, String priority) {
        this.name = name;
        this.dueDate = dueDate;
        this.priority = priority;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDueDate() {
        return dueDate;
    }

    public void setDueDate(String dueDate) {
        this.dueDate = dueDate;
    }

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

}
