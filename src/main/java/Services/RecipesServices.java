package Services;

import Base.BaseClass;
import Models.Recipes;
import io.restassured.response.Response;

import java.util.List;

public class RecipesServices {


    BaseClass baseClass = new BaseClass();

//    Get all recipes       --> https://dummyjson.com/recipes
    public List<Recipes> getAllRecies(){
        return baseClass.get("/recipes")
                .then()
                .extract()
                .jsonPath()
                .getList("recipes",Recipes.class);
    }

//    Get a single recipe   --> /recipes/1
    public Response getSingleRecipe(int id){
        return baseClass.get("/recipes/"+id);
    }

//    Search recipes        --> /recipes/search?q=Margherita
    public List<Recipes> searchRecipe(String searchString){
        return baseClass.get("/recipes/search?q="+searchString)
                .then()
                .extract()
                .jsonPath()
                .getList("recipes",Recipes.class);
    }

//    Limit and skip recipes    --> /recipes?limit=10&skip=10&select=name,image'
    public Response limitSkipRecipes(int limit, int skip){
        return baseClass.get("/recipes?limit="+limit+"&skip="+skip+"&select=name,image");
    }

//    Add Recipe            --> /recipes/add
    public Response addRecipe(Object body){
        return baseClass.post("/recipes/add",body);
    }

//    Update Recipe         --> /recipes/1
    public Response updateRecipe(int id, Object body){
        return baseClass.update("/recipes/"+id,body);
    }

//    Delete Recipe         --> /recipes/1
    public Response deleteRecipes(int id){
        return baseClass.delete("/recipes/"+id);
    }
}
