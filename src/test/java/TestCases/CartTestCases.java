package TestCases;

import Models.Cart;
import Models.Products;
import Services.CartServices;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import java.util.List;

import static io.restassured.RestAssured.given;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class CartTestCases {

    CartServices cartServices = new CartServices();
    List<Products> products;

    @Test
    public void getAllCart(){

        List<Cart> cartList = cartServices.getAllCarts();

        assertThat(cartList,not(nullValue()));
        assertThat(cartList,everyItem(hasProperty("id",greaterThan(0))));
        assertThat(cartList,everyItem(hasProperty("userId",notNullValue())));

    }

    @Test
    public void getSingleCart(){

        int cartId = 5;
        Response response= cartServices.getSingleCart(cartId);

        response.then()
                .statusCode(200)
                .time(lessThan(3000L))
                .body(matchesJsonSchemaInClasspath("Schemas/cart-schema.json"))
                .body("id",equalTo(cartId))
                .body("userId",greaterThan(0))
                .body("total", instanceOf(Float.class));

        products = response.then().extract().jsonPath()
                .getList("products", Products.class);

    }

    @Test
    public void cartbyUserId(){
        int userID = 3;
        Response response = cartServices.getCartByUser(userID);

        response.then().statusCode(200)
                .header("content-type",containsString("application/json"))
                .time(lessThan(3000L))
                .body("carts.userId",everyItem(equalTo(userID)))
                .body("total",equalTo(1));

    }

    @Test
    public void limitSkipCartItems(){
        int limit =5;
        int skip= 12;
        Response response =cartServices.limitSkipCart(limit,skip);

        Response res = given().baseUri("https://dummyjson.com")
                        .when().get("/carts?limit="+limit+"&skip="+skip+"&select=total,userId");

        res.then().statusCode(200)
                .header("content-type",containsString("application/json"))
                .time(lessThan(3000L))
                .body("limit",equalTo(limit))
                .body("skip",equalTo(skip))
                .body("carts",hasSize(5));
    }

    @Test(dependsOnMethods = "getSingleCart")
    public void addNewCart(){

        Cart cart = new Cart(25,products,5);
        Response response = cartServices.addCart(cart);

        response.then().statusCode(201)
                .header("content-type",containsString("application/json"))
//                .body("id",equalTo(25))
                .body("products",not(empty()))
                .body("userId",equalTo(5));
    }

    @Test
    public void updateCart(){

        Cart cartBefore = cartServices.getSingleCart(1).as(Cart.class);
        System.out.println("Cart before Updating: "+cartBefore.toString());

        int cartUpdateID= 23;
        int cartUserid = 123;
        Response response = cartServices.updateCart(1,new Cart(cartUpdateID,products,cartUserid));
        Cart cartUpdate = response.as(Cart.class);

        Assert.assertNotEquals(cartBefore.getUserId(),cartUpdate.getUserId());
        Assert.assertNotEquals(cartBefore.getId(),cartUpdate.getId());
//        Assert.assertFalse(cartUpdate.getProducts().isEmpty());

    }

    @Test
    public void deleteCart(){
        Response response = cartServices.deleteCArt(1);

        response.then().statusCode(200)
                .body("isDeleted",equalTo(true))
                .body("deletedOn",notNullValue())
                .body("id",greaterThan(0));
    }

    @AfterMethod
    public void tearDown() {
        // Clears any global modifications, query parameters, and settings
        System.out.println("Tear Down");
        RestAssured.reset();
    }

}
