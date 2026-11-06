package Services;

import Base.BaseClass;
import Models.Posts;
import com.fasterxml.jackson.databind.ser.Serializers;
import io.restassured.response.Response;

import java.util.List;

public class PostsServices {

    BaseClass baseClass = new BaseClass();
    private static final String POST_PATH="/posts";
//    Get all posts         --> /posts
    public List<Posts> getAllPosts(){
        return baseClass.get(POST_PATH)
                .then().extract().jsonPath()
                .getList("posts",Posts.class);
    }

//    Get a single post     --> /posts/1
    public Response getSinglePost(int id){
        return baseClass.get(POST_PATH+"/"+id);
    }

//    Search posts          --> /posts/search?q=love
    public Response searchPost(String search){
        return baseClass.get(POST_PATH+"/search?q="+search);
    }

//    Limit and skip posts  --> /posts?limit=10&skip=10&select=title,reactions,userId
    public Response limitSkipPost(int limit, int skip){
        return baseClass.get("/posts?limit="+limit+"&skip="+skip+"&select=title,reactions,userId");
    }

//    Sort posts            --> /posts?sortBy=title&order=asc
    public Response sortPosts(){
        return baseClass.get(POST_PATH+"?sortBy=title&order=asc");
    }

//    Get all posts tags    --> /posts/tags
    public Response getAllPostTags(){
        return baseClass.get(POST_PATH+"/tags");
    }

//    Get posts tag list    --> /posts/tag-list
    public Response getPostTagList(){
        return baseClass.get(POST_PATH+"/tag-list");
    }

//    Get all posts by user id-->   /posts/user/5
    public Response getPostsByUserId(int id){
        return baseClass.get(POST_PATH+"/user/"+id);
    }

//    Get post's comments   --> /posts/1/comments
    public Response getPostsComment(int id){
        return baseClass.get(POST_PATH+"/"+id+"/comments");
    }

//    Add a new post        --> /posts/add
    public Response addPost(Object body){
        return baseClass.post("/posts/add",body);
    }

//    Update a post         --> /posts/1
    public Response updatePost(Object body,int id){
        return baseClass.update("/posts/"+id,body);
    }

//    Delete a post         --> /posts/1
    public Response deletePost(int id){
        return baseClass.delete("/posts/"+id);
    }
}
