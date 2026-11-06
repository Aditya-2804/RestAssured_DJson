package Services;

import Base.BaseClass;
import Models.Cart;
import Models.Posts;
import Models.ToDo;
import Models.Users;
import io.restassured.response.Response;

import java.util.List;

public class UserServices {

    BaseClass baseClass = new BaseClass();

//    Get all users     --> /users
    public List<Users> getAllUsers(){
        return baseClass.get("/users")
                .then()
                .extract()
                .jsonPath()
                .getList("users",Users.class);
    }

//    Get a single user --> /users/1
    public Response getSingleUser(int id){
        return baseClass.get("/users/"+id);
    }

//    Search users      --> /search?q=John
    public List<Users> getSearchedUser(String searchedUser){
        return baseClass.get("/users/search?q="+searchedUser)
                .then()
                .extract()
                .jsonPath()
                .getList("users",Users.class);
    }

//    Get user's carts by user id   --> /users/6/carts
    public List<Cart> getCartsByUserId(int id){
        return baseClass.get("/users/"+id+"/carts")
                .then().extract().jsonPath()
                .getList("carts",Cart.class);
    }

//    Get user's posts by user id   --> /users/5/posts
    public List<Posts> getPostsByUserId(int id){
        return baseClass.get("/users/"+id+"/posts")
                .then().extract().jsonPath()
                .getList("posts",Posts.class);
    }

//    Get user's todos by user id   --> /users/5/todos
    public List<ToDo> getTodoByUserId(int id){
        return baseClass.get("/users/"+id+"/todos")
                .then().extract().jsonPath()
                .getList("todos",ToDo.class);
    }

//    Add a new user    --> /users/add
    public Response addUser(Object body){
        return baseClass.post("/users/add",body);
    }

//    Update a user     --> /users/2
    public Response updateUser(int id,Object body){
        return baseClass.update("/users/"+id,body);
    }

//    Delete a user     --> /users/1
    public Response deleteUser(int id){
        return baseClass.delete("/users/"+id);
    }

//

}
