package Services;

import Base.BaseClass;
import Models.ToDo;
import io.restassured.response.Response;

import java.util.List;

public class ToDoServices extends BaseClass {

    private static final String TODO_PATH  = "/todos";
    BaseClass baseClass = new BaseClass();

//    Get All ToDos -> /todos
    public List<ToDo> getAllToDoList(){
        return baseClass.get(TODO_PATH)
                .then()
                .extract()
                .jsonPath()
                .getList("todos",ToDo.class);
    }


//    Get a Single Todo -> /todos/1
    public Response getTodo(int id){
        return baseClass.get(TODO_PATH+"/"+id);
    }


//    Get a random todo ->  /todos/random
    public Response getRandomTodo(){
        return baseClass.get(TODO_PATH+"/random");
    }

//    Limit and skip todos -> /todos?limit=3&skip=10&select=todo,completed'
    public Response limitSkipTodo(int limit, int skip){
        return baseClass.get("/todos?limit="+limit+"&skip="+skip+"&select=todo,completed'");
    }

//    Get all todos by user id  -> /todos/user/5
    public Response getToDosByUserId(int userId){
        return baseClass.get(TODO_PATH+"/user/"+userId);
    }

//    Add a new todo ->  todos/add
    public Response addToDO(Object todoPayload){
        return  baseClass.post(TODO_PATH+"/add",todoPayload);
    }

//    Update a todo -> /todos/1
    public Response updateTodo(int id,Object body){
        return baseClass.update(TODO_PATH+"/"+id,body);
    }

//    Delete a todo -> /todos/1
    public Response deleteTodo(){
        return baseClass.delete(TODO_PATH+"/1");
    }
}
