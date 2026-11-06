package Services;

import Base.BaseClass;
import Models.Products;
import io.restassured.response.Response;

import java.util.List;

public class ProductServices {

    BaseClass baseClass = new BaseClass();
    private static final String PRODUCT_PATH = "/products";

//    Get all products      --> /products
    public List<Products> getAllProducts(){
        return baseClass.get(PRODUCT_PATH).then()
                .extract().jsonPath()
                .getList("products",Products.class);
    }

//    Get a single product  --> /products/1
    public Response getSingleProduct(int id){
        return baseClass.get(PRODUCT_PATH+"/"+id);
    }

//    Search products       --> /products/search?q=phone
    public Response getSearchedProduct(String productName){
        return baseClass.get(PRODUCT_PATH+"/search?q="+productName);
    }

//    Get products category list    --> /products/category-list
    public Response getProductsCategory(String productCategory){
        return baseClass.get(PRODUCT_PATH+"/"+productCategory);
    }

//    Get products by a category    --> /products/category/smartphones
    public Response getProductByCategory(String categoryName){
        return baseClass.get(PRODUCT_PATH+"/category/"+categoryName);
    }

//    Add a new product     --> /products/add
    public Response addProduct(Object body){
        return baseClass.post("/products/add",body);
    }

//    Update a product      --> /products/1
    public Response updateProduct(Object body,int id){
        return baseClass.update("/products/"+id,body);
    }

//    Delete a product      --> /products/1
    public Response deleteProduct(int id){
        return baseClass.delete(PRODUCT_PATH+"/"+id);
    }
}
