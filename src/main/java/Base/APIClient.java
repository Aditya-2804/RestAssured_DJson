package Base;

import io.restassured.response.Response;

public interface APIClient {

    public Response get(String path);
    public Response post(String path, Object body);
    public Response delete(String path);
    public Response update(String path, Object body);
}
