package TestCases;

import Models.Posts;
import Services.PostsServices;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.Matchers.notNullValue;

public class PostTestCases {

    PostsServices postsServices = new PostsServices();

    @Test
    public void getAllPosts(){
        List<Posts> postsList = postsServices.getAllPosts();

        assertThat(postsList,not(nullValue()));
        assertThat(postsList, everyItem(hasProperty("title",not(nullValue()))));
        assertThat(postsList,everyItem(hasProperty("id",greaterThan(0))));
        assertThat(postsList,everyItem(hasProperty("views",greaterThan(0))));
        assertThat(postsList,everyItem(hasProperty("views",instanceOf(Integer.class))));
        assertThat(postsList,everyItem(hasProperty("body",instanceOf(String.class))));
    }

    @Test
    public void getSinglePost(){
        int id=5;
        Response response = postsServices.getSinglePost(id);
        response.then().statusCode(200)
                .header("content-type",containsString("application/json"))
                .body("id",equalTo(id))
                .body("userId",notNullValue())
                .body("views",greaterThan(0))
                .body("title",instanceOf(String.class))
                .body("title",notNullValue());
    }

    @Test
    public void searchPost(){
        String searchString = "love";
        Response response = postsServices.searchPost(searchString);

        response.then()
                .statusCode(200)
                .header("content-type",containsString("application/json"))
                .time(lessThan(3000L));

        List<Posts> postsList = response.then().extract()
                .jsonPath().getList("posts", Posts.class);

        for (Posts post: postsList){
            Assert.assertTrue((post.getTitle().toLowerCase() + post.getBody().toLowerCase()).contains(searchString)
            ,"Searched String should present in the Body/Title");
            Assert.assertTrue(post.getViews()>0);
            Assert.assertTrue(post.getUserId() > 0);
        }
    }

//    @Test
//    public void limitAndSkipPost(){
//        int limit =4, skip =10;
//        Response response = postsServices.limitSkipPost(limit,skip);
//
//        response.then()
//                .statusCode(200)
//                .header("content-type",containsString("application/json"))
//                .time(lessThan(3000L))
//                .body("limit",equalTo(limit))
//                .body("skip",equalTo(skip))
//                .body("posts",hasSize(limit));
//    }

    @Test
    public void SortPosts(){
        Response response = postsServices.sortPosts();
    }

//    @Test
//    public void getAllPostTags(){
//
//    }
//
//    @Test
//    public void getPostsTagsList(){
//
//    }

    @Test
    public void getPostByUserId(){
        int id =5;
        Response response = postsServices.getPostsByUserId(id);

        response.then().statusCode(200)
                .header("content-type",containsString("application/json"))
                .time(lessThan(3000L))
                .body("posts.userId",everyItem(equalTo(id)))
                .body("posts.id",everyItem(greaterThan(0)))
                .body("posts.views",everyItem(greaterThan(0)))
                .body("posts.title",everyItem(not(nullValue())));
    }

//    @Test
//    public void getPostComments(){
//
//    }

    @Test
    public void addNewPost(){
        Posts post = new Posts(1,"Rest Assured API","Designing a scalable framework from scratch",123);
        Response response = postsServices.addPost(post);
        response.then().statusCode(201)
                .header("content-type",containsString("application/json"))
//                .body("id",equalTo(1))
                .body("title",equalTo("Rest Assured API"))
                .body("body",equalTo("Designing a scalable framework from scratch"))
                .body("userId",equalTo(123));

    }

    @Test
    public void updatePost(){

        Posts postBefore = postsServices.getSinglePost(1).as(Posts.class);
        System.out.println(postBefore.toString());

        Response response = postsServices.updatePost(new Posts(1,"New Update","Adding body to the post",121),1);

        response.then().statusCode(200)
                .header("content-type",containsString("application/json"));
        Posts postAfter = response.as(Posts.class);

        Assert.assertNotEquals(postAfter.getTitle(), postBefore.getTitle());
        Assert.assertNotEquals(postAfter.getBody(),postBefore.getBody());
//        Assert.assertNotEquals(postAfter.getUserId(),postBefore.getUserId());
        Assert.assertTrue(postAfter.getId()>0);

    }

    @Test
    public void deletePost(){
        Response response = postsServices.deletePost(1);
        response.then().statusCode(200)
                .header("content-type",containsString("application/json"))
                .body("isDeleted",equalTo(true))
                .body("deletedOn",not(nullValue()));

    }

    @AfterMethod
    public void tearDown(){
        RestAssured.reset();
    }
}
