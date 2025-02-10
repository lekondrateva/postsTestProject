package api;

import io.qameta.allure.AllureId;
import models.Post;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import requests.PostService;

import java.util.stream.Stream;

import static dataGenerators.TestDataGenerator.*;
import static specs.Specifications.unauthorizedUserSpec;

public class ApiPostsTest extends BaseApiTest {

    private Post postToCreate = null;
    private PostService postService;

    @BeforeEach
    public void setupTestData() {
        postToCreate = generatePost();
        postService = new PostService(unauthorizedUserSpec());
    }

    @AfterEach
    public void cleanData() {
        for (Integer id : postService.getCreatedPostsIds()) {
            postService.delete(id);
        }
    }

    @AllureId("1")
    @Test
    public void createAPost() {
        postService.create(postToCreate);
    }

    @AllureId("2")
    @Test
    public void readAPost() {
        postService.create(postToCreate);

        postService.read(postToCreate.getId());
    }

    @AllureId("3")
    @ParameterizedTest
    @MethodSource("providePostWithValidParams")
    public void updateAPost(Post postToCreate, Post postToUpdate) {
        postService.create(postToCreate);

        postService.update(postToUpdate, postToCreate.getId());
    }

    @AllureId("4")
    @Test
    public void deleteAPost() {
        postService.create(postToCreate);

        postService.delete(postToCreate.getId());
    }

    @AllureId("5")
    @ParameterizedTest
    @MethodSource("providePostWithInvalidParams")
    public void postSourceWithInvalidParams(Post invalidPost) {
        postService.create(invalidPost, HttpStatus.SC_BAD_REQUEST);
    }

    @AllureId("6")
    @ParameterizedTest
    @MethodSource("providePostWithInvalidParams")
    public void putSourceWithInvalidParams(Post tempPost, Post invalidPost) {
        postService.create(tempPost);

        postService.update(tempPost.getUserId(), invalidPost, HttpStatus.SC_BAD_REQUEST);
    }

    @AllureId("7")
    @ParameterizedTest
    @MethodSource("provideInvalidIds")
    public void readWithInvalidParams(Integer id) {
        postService.read(id, HttpStatus.SC_BAD_REQUEST);
    }

    @AllureId("8")
    @ParameterizedTest
    @MethodSource("provideInvalidIds")
    public void deleteWithInvalidParams(Integer id) {
        postService.delete(id, HttpStatus.SC_BAD_REQUEST);
    }

    @AllureId("9")
    @Test
    public void createIfIdExists() {
        postService.create(postToCreate);
        postService.create(postToCreate);
    }

    public static Stream<Arguments> providePostWithValidParams() {
        Post tempPost = generatePost();

        return Stream.of(
                Arguments.of(tempPost,
                        tempPost.toBuilder()
                                .userId(generateRandomInt(1, 30))
                                .build()),
                Arguments.of(tempPost,
                        tempPost.toBuilder()
                                .title(generateRandomString(30))
                                .build()),
                Arguments.of(tempPost,
                        tempPost.toBuilder()
                                .body(generateRandomString(200))
                                .build()));
    }

    public static Stream<Integer> provideInvalidIds() {
        return Stream.of(
                generateRandomInt(-100, -1),
                0,
                generateRandomInt(10, 500));
    }

    public static Stream<Arguments> providePostWithInvalidParams() {
        Post tempPost = generatePost();

        return Stream.of(
                Arguments.of(tempPost,
                        tempPost.toBuilder()
                                .userId(generateRandomInt(-30, -1))
                                .build()),
                Arguments.of(tempPost,
                        tempPost.toBuilder()
                                .userId(generateRandomInt(31, 100))
                                .build()),
                Arguments.of(tempPost,
                        tempPost.toBuilder()
                                .userId(0)
                                .build()),

                Arguments.of(tempPost,
                        tempPost.toBuilder()
                                .id(generateRandomInt(-30, -1))
                                .build()),
                Arguments.of(tempPost,
                        tempPost.toBuilder()
                                .id(generateRandomInt(31, 100))
                                .build()),
                Arguments.of(tempPost,
                        tempPost.toBuilder()
                                .id(0)
                                .build()),

                Arguments.of(tempPost,
                        tempPost.toBuilder()
                                .title("")
                                .build()),
                Arguments.of(tempPost,
                        tempPost.toBuilder()
                                .title(null)
                                .build()),
                Arguments.of(tempPost,
                        tempPost.toBuilder()
                                .title(generateRandomString(31, 100))
                                .build()),
                Arguments.of(tempPost,
                        tempPost.toBuilder()
                                .title(generateRandomSpecialCharString(1, 20))
                                .build()),

                Arguments.of(tempPost,
                        tempPost.toBuilder()
                                .body("")
                                .build()),
                Arguments.of(tempPost,
                        tempPost.toBuilder()
                                .body(null)
                                .build()),
                Arguments.of(tempPost,
                        tempPost.toBuilder()
                                .body(generateRandomString(201, 300))
                                .build()),
                Arguments.of(tempPost,
                        tempPost.toBuilder()
                                .body(generateRandomSpecialCharString(1, 20))
                                .build()));
    }

}