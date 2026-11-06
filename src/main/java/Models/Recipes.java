package Models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Recipes {

    private int id;
    private String name;
    private List<String> ingredients;
    private List<String> instructions;
    private int cookTimeMinutes;
    private String difficulty;
    private String cuisine;
    private int caloriesPerServing;
    private int userId;
    private double rating;

    public Recipes() {

    }

    public Recipes(int id, String name,int cookTimeMinutes,String difficulty) {
        this.id = id;
        this.name = name;
        this.cookTimeMinutes =cookTimeMinutes;
        this.difficulty=difficulty;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<String> getIngredients() {
        return ingredients;
    }

    public void setIngredients(List<String> ingredients) {
        this.ingredients = ingredients;
    }

    public List<String> getInstructions() {
        return instructions;
    }

    public void setInstructions(List<String> instructions) {
        this.instructions = instructions;
    }

    public int getCookTimeMinutes() {
        return cookTimeMinutes;
    }

    public void setCookTimeMinutes(int cookTimeMinutes) {
        this.cookTimeMinutes = cookTimeMinutes;
    }

    public String getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(String difficulty) {
        this.difficulty = difficulty;
    }

    public String getCuisine() {
        return cuisine;
    }

    public void setCuisine(String cuisine) {
        this.cuisine = cuisine;
    }

    public int getCaloriesPerServing() {
        return caloriesPerServing;
    }

    public void setCaloriesPerServing(int caloriesPerServing) {
        this.caloriesPerServing = caloriesPerServing;
    }

    public int getUserId() {
        return userId;
    }

    public void setUseId(int useId) {
        this.userId = useId;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    @Override
    public String toString() {
        return "Recipes{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", ingredients=" + ingredients +
                ", instructions=" + instructions +
                ", cookTimeMinutes=" + cookTimeMinutes +
                ", difficulty='" + difficulty + '\'' +
                ", cuisine='" + cuisine + '\'' +
                ", caloriesPerServing=" + caloriesPerServing +
                ", useId=" + userId +
                ", rating=" + rating +
                '}';
    }
}
