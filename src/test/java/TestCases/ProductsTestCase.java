package TestCases;

import Models.Products;
import Services.PostsServices;
import Services.ProductServices;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class ProductsTestCase {

    ProductServices productServices =new ProductServices();

    @Test
    public void getAllProducts(){

        List<Products> productsList = productServices.getAllProducts();

        assertThat(productsList,not(nullValue()));
        assertThat(productsList,everyItem(hasProperty("id",greaterThan(0))));
        assertThat(productsList,everyItem(hasProperty("title",not(nullValue()))));
        assertThat(productsList,everyItem(hasProperty("price",instanceOf(Double.class))));
        assertThat(productsList,everyItem(hasProperty("rating",instanceOf(Double.class))));
    }

    @Test
    public void getSingleProduct(){
        Response response = productServices.getSingleProduct(1);
        response.then().statusCode(200)
                .header("content-type",containsString("application/json"));

        Products product = response.as(Products.class);
        Assert.assertTrue(product.getId()>0);
        Assert.assertNotNull(product.getTitle());

    }

    @Test
    public void searchProduct(){
        String searchedProduct = "phone";
        Response response = productServices.getSearchedProduct(searchedProduct);

        response.then().statusCode(200)
                .header("content-type",containsString("application/json"))
                .time(lessThan(3000L))
                .body("products",not(nullValue()));

        List<Products> productsList = response.then().extract().jsonPath().getList("products", Products.class);

        for (Products product:productsList) {
            Assert.assertTrue((product.getTitle()+product.getDescription()).toLowerCase().contains(searchedProduct),"ProductName: "+product.getId());
            Assert.assertTrue(product.getId()>0);
            Assert.assertTrue(product.getPrice()>0);
        }
    }

    @Test
    public void getProductsByCategory(){
        String category = "smartphones";
        Response response = productServices.getProductByCategory(category);

        response.then()
                .statusCode(200)
                .header("content-type",containsString("application/json"))
                .time(lessThan(3000L))
//                .body("products",everyItem(hasProperty("category",equalTo(category))))
                .body("products.category", everyItem(equalTo("smartphones")))
                .body("products.id", everyItem(greaterThan(0)))
                .body("products.title", everyItem(not(nullValue())));
//                .body("products",everyItem(hasProperty("id",greaterThan(0))))
//                .body("products",everyItem(hasProperty("title",not(nullValue()))));
    }

    @Test
    public void addNewProduct(){
        Response response =  productServices.addProduct(new Products(11,"Redmi SmartPhone","64 GB Storage, 4Gb Ram variant","smartphones",12500.50,4.3));

        response.then().statusCode(201)
                .time(lessThan(5000L))
//                .body("id",equalTo(11))
                .body("title",equalTo("Redmi SmartPhone"))
                .body("description",equalTo("64 GB Storage, 4Gb Ram variant"))
                .body("category",equalTo("smartphones"))
                .body("price",equalTo(12500.50F))
                .body("rating",equalTo(4.3F));
    }

    @Test
    public void updateProduct(){
        Products productsBefore = productServices.getSingleProduct(1).as(Products.class);


        Response response = productServices.updateProduct(new Products(1,"Redmi SmartPhone","64 GB Storage, 4Gb Ram variant","smartphones",12500.50,4.3),1);
        Products productsAfter = response.as(Products.class);

        System.out.println("Before product Update"+productsBefore.toString());
        System.out.println("After product Update"+productsAfter.toString());

//        Assert.assertNotEquals(productsBefore.getId(),productsAfter.getId());
        Assert.assertNotEquals(productsBefore.getTitle(),productsAfter.getTitle());
        Assert.assertNotEquals(productsBefore.getDescription(),productsAfter.getDescription());
        Assert.assertNotEquals(productsBefore.getPrice(),productsAfter.getPrice());
        Assert.assertNotEquals(productsBefore.getRating(),productsAfter.getRating());
    }


    @Test
    public void deleteProduct(){
        Response response = productServices.deleteProduct(1);

        response.then().statusCode(200)
                .header("content-type",containsString("application/json"))
                .body("isDeleted",equalTo(true))
                .body("deletedOn",not(nullValue()));
    }
}

