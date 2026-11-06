package Services;

import Base.BaseClass;
import Models.Cart;
import io.restassured.response.Response;

import java.util.List;

public class CartServices {

    BaseClass baseClass = new BaseClass();
    private static final String CART_PATH = "/carts";

//    Get all carts     --> /carts
    public List<Cart> getAllCarts(){
        return baseClass.get(CART_PATH).then()
                .extract().jsonPath().getList("carts",Cart.class);
    }

//    Get a single cart --> /carts/1
    public Response getSingleCart(int id){
        return baseClass.get(CART_PATH+"/"+id);
    }

//    Get carts by a user-->    /carts/user/5
    public Response getCartByUser(int id){
        return baseClass.get(CART_PATH+"/user/"+id);
    }

//    Limit and skip carts-->   /carts?limit=2&skip=10&select=total,userId
    public Response limitSkipCart(int limit, int skip){
        return baseClass.get("/carts?limit="+limit+"&skip="+skip+"&select=total,userId");
    }

//    Add a new cart    --> /carts/add
    public Response addCart(Object body){
        return baseClass.post(CART_PATH+"/add",body);
    }

//    Update a cart     --> /carts/1
    public Response updateCart(int id, Object body){
        return baseClass.update(CART_PATH+"/"+id,body);
    }

//    Delete a cart     --> /carts/1
    public Response deleteCArt(int id){
        return baseClass.delete(CART_PATH+"/"+id);
    }
}
