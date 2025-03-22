package requests;

import io.restassured.specification.RequestSpecification;
import lombok.Getter;
import models.Post;
import org.apache.http.HttpStatus;

import java.util.ArrayList;
import java.util.List;

import static io.restassured.RestAssured.given;
import static java.lang.String.format;
import static org.hamcrest.Matchers.notNullValue;

public class PostService implements CrudRepository<Post> {

    private static final String POSTS_URI = "/posts";
    private final RequestSpecification requestSpecification;
    @Getter
    private final List<Integer> createdPostsIds = new ArrayList<>();

    public PostService(RequestSpecification requestSpecification) {
        this.requestSpecification = requestSpecification;
    }

    @Override
    public Post create(Post item) {
        Post post = given()
                .spec(requestSpecification)
                .body(item)
                .post(POSTS_URI)
                .then()
                .assertThat()
                .statusCode(HttpStatus.SC_CREATED)
                .extract()
                .as(Post.class);

        createdPostsIds.add(post.getId());

        return post;
    }

    @Override
    public Post update(Post item, int id) {
        return given()
                .spec(requestSpecification)
                .body(item)
                .put(format(POSTS_URI + "/%d", id))
                .then()
                .assertThat()
                .statusCode(HttpStatus.SC_OK)
                .extract()
                .as(Post.class);
    }

    @Override
    public Post read(int id) {
        return given()
                .spec(requestSpecification)
                .get(format(POSTS_URI + "/%d", id))
                .then()
                .assertThat()
                .statusCode(HttpStatus.SC_OK)
                .extract()
                .as(Post.class);
    }

    @Override
    public String delete(int id) {
        given()
                .spec(requestSpecification)
                .get(format(POSTS_URI + "/%d", id))
                .then()
                .assertThat()
                .statusCode(HttpStatus.SC_NO_CONTENT);

        return "Post id = " + id + " has been deleted successfully";
    }

    public void create(Post item, int statusCode) {
        given()
                .spec(requestSpecification)
                .body(item)
                .post(POSTS_URI)
                .then()
                .assertThat()
                .statusCode(statusCode)
                .body("error", notNullValue());
    }

    public void update(int id, Post item, int statusCode) {
        given()
                .spec(requestSpecification)
                .body(item)
                .put(format(POSTS_URI + "/%d", id))
                .then()
                .assertThat()
                .statusCode(statusCode)
                .body("error", notNullValue());
    }

    public void read(int id, int statusCode) {
        given()
                .spec(requestSpecification)
                .get(format(POSTS_URI + "/%d", id))
                .then()
                .assertThat()
                .statusCode(statusCode)
                .body("error", notNullValue());
    }

    public void delete(int id, int statusCode) {
        given()
                .spec(requestSpecification)
                .delete(format(POSTS_URI + "/%d", id))
                .then()
                .assertThat()
                .statusCode(statusCode)
                .body("error", notNullValue());
    }

}