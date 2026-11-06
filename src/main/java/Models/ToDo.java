package Models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ToDo {

    private int id;
    private String todo;
    private boolean completed;
    private int userId;

    public ToDo() {
    }

    public ToDo(int id, boolean completed,int userId) {
        this.id = id;
        this.completed=completed;
        this.userId = userId;
    }

    public ToDo(int id, String todo, boolean completed, int userId) {
        this.id = id;
        this.todo = todo;
        this.completed = completed;
        this.userId = userId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTodo() {
        return todo;
    }

    public void setTodo(String todo) {
        this.todo = todo;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    @Override
    public String toString() {
        return "ToDo{" +
                "id=" + id +
                ", todo='" + todo + '\'' +
                ", completed=" + completed +
                ", userId=" + userId +
                '}';
    }
}
