package TestCases;

import Models.ToDo;
import Services.ToDoServices;
import io.restassured.response.Response;
import org.hamcrest.core.Every;
import org.testng.Assert;
import org.testng.annotations.Test;

import javax.tools.Tool;
import java.util.List;

import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.*;

public class ToDoTestCases {

    @Test
    public static void getTodo(){
        ToDoServices toDoServices = new ToDoServices();
        Response response = toDoServices.getTodo(1);

        response.then()
                .statusCode(200)
                .body(matchesJsonSchemaInClasspath("Schemas/todo-schema.json"));

        ToDo toDo = response.as(ToDo.class);
        Assert.assertEquals(1,toDo.getId());
        System.out.println(toDo.getUserId());
    }

    @Test
    public void getAllTodo(){
        ToDoServices toDoServices = new ToDoServices();
        List<ToDo> allTodos = toDoServices.getAllToDoList();

        ToDo toDo = allTodos.get(0);
        System.out.println(toDo);

//        assertThat(allTodos,hasSize(10));
        assertThat(allTodos, not(empty()));
        assertThat(allTodos, hasItems(
                hasProperty("id",equalTo(1)),
                hasProperty("id",equalTo(2))
        ));
        assertThat(allTodos,everyItem(hasProperty("id",notNullValue())));
    }

    @Test
    public void getTodoByUsreId(){
        int userID = 1;
        ToDoServices toDoServices = new ToDoServices();
        Response response = toDoServices.getToDosByUserId(userID);

        response.then()
                .statusCode(200)
                .header("Content-Type", containsString("application/json"));

        List<ToDo> todos = response.then()
                .extract().jsonPath()
                .getList("todos", ToDo.class);

        for (ToDo toDo: todos) {
            Assert.assertEquals(userID,toDo.getUserId(),"ID's of each Todo should be equal to "+userID);
            Assert.assertNotNull(toDo.getTodo(),"TODO Title should not be null");
            Assert.assertTrue(toDo.getId()>0,"ID should be greater that 0");
        }
    }

    @Test
    public void limitSkipToDO(){
        ToDoServices toDoServices = new ToDoServices();
        int limit = 5, skip =9;
        Response response = toDoServices.limitSkipTodo(limit,skip);

        response.then()
                .statusCode(200)
                .header("Content-Type", containsString("application/json"))
                .body("skip",equalTo(skip))
                .body("limit",equalTo(limit));


    }

    @Test
    public void addTODO(){
        ToDoServices toDoServices = new ToDoServices();
        ToDo toDo = new ToDo(2,"Adding it to the Todo List",true,5);
        Response response = toDoServices.addToDO(toDo);

        response.then()
                .statusCode(201)
                .header("Content-Type", containsString("application/json"));

        ToDo todoCreated =response.as(ToDo.class);
//        Assert.assertEquals(todoCreated.getId(),2,"Newly created toDO should have the same Id as provided");
        Assert.assertEquals(todoCreated.getTodo(),"Adding it to the Todo List","Newly created toDO should have the same title as provided");
        Assert.assertTrue(todoCreated.isCompleted(), "Newly created toDO should have the same Completed as provided");
        Assert.assertEquals(todoCreated.getUserId(),5,"Newly created toDO should have the same UserId as provided");

    }

    @Test
    public void updateTodo(){
        ToDoServices toDoServices = new ToDoServices();
        int id =7;
        ToDo toDo = new ToDo(id,true,7);
        Response response = toDoServices.updateTodo(id,toDo);

        response.then()
                .statusCode(200)
                .header("Content-Type", containsString("application/json"));

        ToDo todoCreated =response.as(ToDo.class);
        Assert.assertEquals(todoCreated.getId(),7,"Newly created toDO should have the same Id as provided");
        Assert.assertTrue(todoCreated.isCompleted());
//        Assert.assertEquals(todoCreated.getUserId(),7,"Newly created toDO should have the same UserId as provided");

    }

    @Test
    public void deleteTODO(){
        ToDoServices toDoServices = new ToDoServices();
        ToDo toDo = new ToDo(2,"Adding it to the Todo List",true,5);
        Response response = toDoServices.deleteTodo();

        response.then()
                .statusCode(200)
                .header("Content-Type", containsString("application/json"))
                .body("isDeleted",equalTo(true))
                .body("deletedOn",notNullValue());
    }

}
