package TestCases;

import Base.BaseClass;
import Models.Products;
import Models.Recipes;
import Services.RecipesServices;
import io.restassured.RestAssured;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.List;

import static io.restassured.RestAssured.given;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class RecipesTestcases extends BaseClass {

    RecipesServices recipesServices = new RecipesServices();

    @Test
    public void getAllRecipes(){
        List<Recipes> recipesList = recipesServices.getAllRecies();

        assertThat(recipesList,not(nullValue()));
        assertThat(recipesList,everyItem(hasProperty("id",greaterThan(0))));
        assertThat(recipesList,everyItem(hasProperty("name",not(nullValue()))));
        assertThat(recipesList,everyItem(hasProperty("userId",instanceOf(Integer.class))));
        assertThat(recipesList,everyItem(hasProperty("rating",instanceOf(Double.class))));
    }

    @Test
    public void getSingleProduct(){
        Response response = recipesServices.getSingleRecipe(1);
        response.then().statusCode(200)
                .header("content-type",containsString("application/json"))
                .body("rating",instanceOf(Float.class));

        Recipes recipes = response.as(Recipes.class);
        Assert.assertTrue(recipes.getId()>0);
        Assert.assertNotNull(recipes.getName());
        Assert.assertTrue(recipes.getCookTimeMinutes()>0);
    }

    @Test
    public void searchRecipe(){

        String searchString = "Margherita";
        List<Recipes> recipesList = recipesServices.searchRecipe(searchString);

        assertThat(recipesList,everyItem(hasProperty("name",containsString(searchString))));
        assertThat(recipesList,everyItem(hasProperty("id",notNullValue())));
        assertThat(recipesList,everyItem(hasProperty("cookTimeMinutes",instanceOf(Integer.class))));
    }

//    @Test
//    public void limitSkipRecipes(){
//        int limit=4, skip=10;
//        Response response = recipesServices.limitSkipRecipes(limit,skip);
//
//        response.then().statusCode(200)
//                .time(lessThan(3000L))
//                .body("limit",equalTo(limit))
//                .body("skip",equalTo(skip))
//                .body("recipes",hasSize(limit));
//
//    }

    @Test
    public void addNewRecipe(){

        Response response = recipesServices.addRecipe(new Recipes(1,"PuranPoli",240,"Hard"));

        response.then()
                .statusCode(201)
                .time(lessThan(3000L))
                .body("name",equalTo("PuranPoli"))
                .body("cookTimeMinutes",equalTo(240))
                .body("difficulty",equalTo("Hard"));
    }

    @Test
    public void updateRecipe(){
        Recipes recipesBefore = recipesServices.getSingleRecipe(1).as(Recipes.class);
        System.out.println(recipesBefore.toString());

        Response response = recipesServices.updateRecipe(1,new Recipes(1,"KothimbirVadi",120,"Hard"));

        Recipes recipesAfter = response.as(Recipes.class);

        Assert.assertEquals(recipesAfter.getName(),"KothimbirVadi");
        Assert.assertEquals(recipesAfter.getCookTimeMinutes(),120);
        Assert.assertEquals(recipesAfter.getDifficulty(),"Hard");

        Assert.assertNotEquals(recipesBefore.getName(),recipesAfter.getName());
        Assert.assertNotEquals(recipesBefore.getDifficulty(),recipesAfter.getDifficulty());

    }

    @Test
    public void deleteRecipe(){
        Response response = recipesServices.deleteRecipes(1);

        response.then().statusCode(200)
                .header("content-type",containsString("application/json"))
                .body("isDeleted",equalTo(true))
                .body("deletedOn",not(nullValue()));
    }

    @AfterMethod
    public void tearDown() {
        // Resets all global configurations, parameters, and specs to default
        RestAssured.reset();
    }


}
