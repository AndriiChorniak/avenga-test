package com.chorniak.api.client;

import com.chorniak.api.model.Author;
import io.restassured.response.Response;

import static io.restassured.RestAssured.*;

public class AuthorClient {
    private static final String BASE_PATH = "/api/v1/Authors";

    public Response getAllAuthors() {
        return given()
                .when()
                .get(BASE_PATH);
    }

    public Response getAuthorById(int id) {
        return given()
                .when()
                .get(BASE_PATH + "/" + id);
    }

    public Response createAuthor(Author author) {
        return given()
                .contentType("application/json")
                .body(author)
                .when()
                .post(BASE_PATH);
    }

    public Response updateAuthor(int id, Author author) {
        return given()
                .contentType("application/json")
                .body(author)
                .when()
                .put(BASE_PATH + "/" + id);
    }

    public Response deleteAuthor(int id) {
        return given()
                .when()
                .delete(BASE_PATH + "/" + id);
    }
} 