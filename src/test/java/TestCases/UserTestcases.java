package TestCases;

import Base.BaseClass;
import Models.*;
import Services.UserServices;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.mozilla.javascript.NativeJSON;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.List;

import static io.restassured.RestAssured.given;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static java.util.Optional.empty;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class UserTestcases {

    UserServices userServices = new UserServices();
    String jwt="",refreshToken="";

    @Test
    public void  getAllUsers(){

        List<Users> usersList = userServices.getAllUsers();
        Assert.assertFalse(usersList.isEmpty(),"User List should not be empty");

        Users user = usersList.get(0);
        Assert.assertTrue(user.getId()>0 , "User Id Should be positive");
        Assert.assertTrue(user.getAge()>12 , "User should be older than 12");
        Assert.assertTrue(user.getEmail().contains("@") && user.getEmail().contains(".com") , "Email should contains @ and .com");
    }

    @Test
    public void getUser(){
        int id = 5;
        Response response = userServices.getSingleUser(id);
        response.then()
                .statusCode(200)
                .header("content-type", containsString("application/json"))
                .body(matchesJsonSchemaInClasspath("Schemas/user-schema.json"))
                .time(lessThan(2000L));

        Users user = response.as(Users.class);

        Assert.assertEquals(user.getId(),id,"IDs should match");
        Assert.assertTrue(user.getAge()>10);
        Assert.assertTrue(user.getEmail().contains("@") && user.getEmail().contains(".com") , "Email should contains @ and .com");

        System.out.println(user.toString());
    }

    @Test
    public void logIn(){
        String username = "emilys";
        String password = "emilyspass";
        int expiresInMins= 30;
        Auth auth = new Auth(username,password,expiresInMins);
        Response response = given().baseUri("https://dummyjson.com")
                .body(auth)
//                .headers: { 'Content-Type': 'application/json' },
                .contentType(ContentType.JSON)
                .filter(new RequestLoggingFilter())
                .filter(new ResponseLoggingFilter())
                .when().post("https://dummyjson.com/user/login");
        response.then()
                .statusCode(200)
                .time(lessThan(5000L))
                .body("username",equalTo(username))
                .body("id",greaterThan(0));

        jwt = response.then().extract().jsonPath().getString("accessToken");
        refreshToken = response.then().extract().jsonPath().getString("refreshToken");
        System.out.println(jwt+" : "+refreshToken);
    }

    @Test(dependsOnMethods = "logIn")
    public void getCurrentAuthenticatedUser(){

        Response response = given()
                .header("Authorization","Bearer "+jwt)
                .contentType(ContentType.JSON)
                .filter(new RequestLoggingFilter())
                .filter(new ResponseLoggingFilter())
                .when().get("https://dummyjson.com/user/me");

        response.then()
                .statusCode(200)
                .body("firstName",equalTo("Emily"))
                .body("lastName",equalTo("Johnson"))
                .body("username",equalTo("emilys"))
                .body("password",equalTo("emilyspass"))
        ;


    }


    @Test
    public void filterUsers(){
        BaseClass baseClass = new BaseClass();
        String key = "lastName";
        String value="Johnson";
        Response response = baseClass.get("/users/filter?key="+key+"&value="+value);

        response.then()
                .statusCode(200)
                .time(lessThan(3000L))
                .body("users.lastName",everyItem(containsString(value)));
    }


    @Test
    public void searchUsr(){
        String userFirstName  = "John";
        List<Users> userList = userServices.getSearchedUser(userFirstName);

        assertThat(userList,notNullValue());
        for (Users user: userList){
            Assert.assertTrue((user.getFirstName()+user.getLastName()).contains(userFirstName),"FirstName and LastName should match the Searched Results");
            Assert.assertFalse(user.getFirstName().isBlank() || user.getFirstName().isEmpty());

        }
    }

    @Test
    public void getCartsByUserId(){
        int userId = 5;
        List<Cart> cartList = userServices.getCartsByUserId(userId);

        assertThat(cartList,not(empty()));
        assertThat(cartList, hasItem(hasProperty("userId",equalTo(userId))));
        assertThat(cartList,everyItem(hasProperty("userId",equalTo(userId))));
        assertThat(cartList,everyItem(hasProperty("id",greaterThan(0))));
    }

    @Test
    public void getPostsByUserId(){
        int userId = 5;
        List<Posts> postsList = userServices.getPostsByUserId(userId);

        assertThat(postsList,not(empty()));
        assertThat(postsList, hasItem(hasProperty("userId",equalTo(userId))));
        assertThat(postsList,everyItem(hasProperty("userId",equalTo(userId))));
        assertThat(postsList,everyItem(hasProperty("id",greaterThan(0))));

    }

    @Test
    public void getTODOsByUsrId(){
        int userId = 1;
        List<ToDo> toDoList = userServices.getTodoByUserId(userId);

        assertThat(toDoList,not(empty()));
        assertThat(toDoList, hasItem(hasProperty("userId",equalTo(userId))));
        assertThat(toDoList,everyItem(hasProperty("userId",equalTo(userId))));
        assertThat(toDoList,everyItem(hasProperty("id",greaterThan(0))));
    }

    @Test
    public void addNewUser(){

        Users user = new Users(23,"Aditya","Naik",31,"asn@gmail.com","929272193","asnaik","@asn@2012");
        Response response = userServices.addUser(user);

        response.then().statusCode(201)
                .time(lessThan(5000L))
                .header("Content-Type",containsString("application/json"));
        Users resUser1 = response.as(Users.class);
//        Assert.assertEquals(resUser1.getId(),23);
        Assert.assertEquals(resUser1.getFirstName(),"Aditya");
        Assert.assertEquals(resUser1.getLastName(),"Naik");
        Assert.assertEquals(resUser1.getAge(),31);
        Assert.assertEquals(resUser1.getEmail(),"asn@gmail.com");
        Assert.assertEquals(resUser1.getPassword(),"@asn@2012");
        Assert.assertEquals(resUser1.getPhone(),"929272193");
        Assert.assertEquals(resUser1.getUsername(),"asnaik");
    }

    @Test
    public void updateUser(){
        int id =5;
        Users user = userServices.getSingleUser(id).as(Users.class);
        System.out.println("Before Updating: "+user.toString());
        Users user1 = new Users(24,"Adityaaa","asn123@gmail.com","8379277302");
        Users userUpdated = userServices.updateUser(id,user1).as(Users.class);

        Assert.assertNotEquals(user.getId(),userUpdated.getId(),"IDs should not match after upgrade");
        Assert.assertNotEquals(user.getFirstName(),userUpdated.getFirstName(),"FirstName should not match after upgrade");
        Assert.assertNotEquals(user.getEmail(),userUpdated.getEmail(),"Emails should not match after upgrade");
        Assert.assertNotEquals(user.getPhone(),userUpdated.getPhone(),"Phone Number should not match after upgrade");
    }

    @Test
    public void deleteUser(){
        Response response = userServices.deleteUser(5);

        response.then().statusCode(200)
                .header("Content-Type",containsString("application/json"))
                .body("isDeleted",equalTo(true))
                .body("id",equalTo(5))
                .body("deletedOn",notNullValue());

    }
}
