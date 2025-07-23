package com.chorniak.api.client;

import com.chorniak.api.model.Author;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

import java.util.List;

import static io.restassured.http.ContentType.JSON;
import static org.apache.http.HttpStatus.SC_OK;

public class AuthorClient extends BaseClient {
    private static final String AUTHORS_ENDPOINT = "/Authors";

    public Author createAuthor(Author author) {
        return getRequestSpec()
                .contentType(ContentType.JSON)
                .body(author)
                .when()
                .post(AUTHORS_ENDPOINT)
                .then()
                .contentType(JSON)
                .statusCode(SC_OK)
                .and()
                .extract()
                .body()
                .as(Author.class);
    }

    public Response sendCreateAuthorRequest(String requestBody) {
        return getRequestSpec()
                .contentType(ContentType.JSON)
                .body(requestBody)
                .when()
                .post(AUTHORS_ENDPOINT);
    }

    public List<Author> getAllAuthors() {
        return getRequestSpec()
                .when()
                .get(AUTHORS_ENDPOINT)
                .then()
                .contentType(JSON)
                .statusCode(SC_OK)
                .extract()
                .body()
                .jsonPath()
                .getList(".", Author.class);
    }

    public Author getAuthorById(int id) {
        return getRequestSpec()
                .when()
                .get(String.format("%s/%d", AUTHORS_ENDPOINT, id))
                .then()
                .contentType(JSON)
                .statusCode(SC_OK)
                .extract()
                .body()
                .as(Author.class);
    }

    public Response sendGetAuthorByIdRequest(int id) {
        return getRequestSpec()
                .when()
                .get(String.format("%s/%d", AUTHORS_ENDPOINT, id));
    }

    public Author updateAuthor(int id, Author author) {
        return getRequestSpec()
                .contentType(ContentType.JSON)
                .body(author)
                .when()
                .put(String.format("%s/%d", AUTHORS_ENDPOINT, id))
                .then()
                .contentType(JSON)
                .statusCode(SC_OK)
                .extract()
                .body()
                .as(Author.class);
    }

    public Response sendUpdateAuthorRequest(int id, String requestBody) {
        return getRequestSpec()
                .contentType(ContentType.JSON)
                .body(requestBody)
                .when()
                .put(String.format("%s/%d", AUTHORS_ENDPOINT, id));
    }

    public Response deleteAuthor(int id) {
        return getRequestSpec()
                .when()
                .delete(String.format("%s/%d", AUTHORS_ENDPOINT, id));
    }
} 