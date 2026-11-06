package Base;

import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.testng.annotations.BeforeMethod;

import static io.restassured.RestAssured.given;

public class BaseClass implements APIClient{

    private static final String BASE_URL = "https://dummyjson.com";

    private  RequestSpecification requestSpecification;


//    @BeforeMethod // <--- This runs fresh before EVERY test method
//    public void setUpRequestSpec() {
//        requestSpecification = given()
//                .baseUri("https://dummyjson.com")
//                .contentType(ContentType.JSON)
//                .filter(new RequestLoggingFilter())
//                .filter(new ResponseLoggingFilter());
//    }
    public BaseClass(){
        requestSpecification= given().baseUri(BASE_URL)
                .contentType(ContentType.JSON)
                .filter(new RequestLoggingFilter())
                .filter(new ResponseLoggingFilter());
        ;
    }

    @Override
    public Response get(String path) {
//        return requestSpecification.when().get(path);
        return given()
                .spec(requestSpecification)
                .when()
                .get(path);
    }

    @Override
    public Response post(String path, Object body) {
        return requestSpecification.body(body).when().post(path);
    }

    @Override
    public Response delete(String path) {
        return requestSpecification.when().delete(path);
    }

    @Override
    public Response update(String path, Object body) {
        return requestSpecification.body(body).when().patch(path);
    }

}
