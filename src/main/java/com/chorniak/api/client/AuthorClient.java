package com.chorniak.api.client;

import com.chorniak.api.model.Author;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

import java.util.List;

import static io.restassured.RestAssured.given;
import static io.restassured.http.ContentType.JSON;
import static org.apache.http.HttpStatus.SC_OK;

public class AuthorClient {
    private static final String BASE_PATH = "/api/v1/Authors";

    public Author createAuthor(Author author) {
        return given()
                .contentType(ContentType.JSON)
                .body(author)
                .when()
                .post(BASE_PATH)
                .then()
                .contentType(JSON)
                .statusCode(SC_OK)
                .and()
                .extract()
                .body()
                .as(Author.class);
    }

    public Response sendCreateAuthorRequest(String requestBody) {
        return given()
                .contentType(ContentType.JSON)
                .body(requestBody)
                .when()
                .post(BASE_PATH);
    }

    public List<Author> getAllAuthors() {
        return given()
                .when()
                .get(BASE_PATH)
                .then()
                .contentType(JSON)
                .statusCode(SC_OK)
                .extract()
                .body()
                .jsonPath()
                .getList(".", Author.class);
    }

    public Author getAuthorById(int id) {
        return given()
                .when()
                .get(String.format("%s/%d", BASE_PATH, id))
                .then()
                .contentType(JSON)
                .statusCode(SC_OK)
                .extract()
                .body()
                .as(Author.class);
    }

    public Response sendGetAuthorByIdRequest(int id) {
        return given()
                .when()
                .get(String.format("%s/%d", BASE_PATH, id));
    }

    public Author updateAuthor(int id, Author author) {
        return given()
                .contentType(ContentType.JSON)
                .body(author)
                .when()
                .put(String.format("%s/%d", BASE_PATH, id))
                .then()
                .contentType(JSON)
                .statusCode(SC_OK)
                .extract()
                .body()
                .as(Author.class);
    }

    public Response sendUpdateAuthorRequest(int id, String requestBody) {
        return given()
                .contentType(ContentType.JSON)
                .body(requestBody)
                .when()
                .put(String.format("%s/%d", BASE_PATH, id));
    }

    public Response deleteAuthor(int id) {
        return given()
                .when()
                .delete(String.format("%s/%d", BASE_PATH, id));
    }
} 