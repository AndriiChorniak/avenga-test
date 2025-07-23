package com.chorniak.api.client;

import com.chorniak.api.model.Book;
import io.restassured.response.Response;

import java.util.List;

import static io.restassured.RestAssured.given;
import static io.restassured.http.ContentType.JSON;
import static org.apache.http.HttpStatus.SC_OK;

public class BookClient {
    private static final String BASE_PATH = "/api/v1/Books";

    public Book createBook(Book book) {
        return given()
                .contentType(JSON)
                .body(book)
                .when()
                .post(BASE_PATH)
                .then()
                .contentType(JSON)
                .statusCode(SC_OK)
                .and()
                .extract()
                .body()
                .as(Book.class);
    }

    public Response sendCreateBookRequest(String requestBody) {
        return given()
                .contentType(JSON)
                .body(requestBody)
                .when()
                .post(BASE_PATH);
    }

    public List<Book> getAllBooks() {
        return given()
                .when()
                .get(BASE_PATH)
                .then()
                .contentType(JSON)
                .statusCode(SC_OK)
                .extract()
                .body()
                .jsonPath()
                .getList(".", Book.class);
    }

    public Book getBookById(int id) {
        return given()
                .when()
                .get(String.format("%s/%d", BASE_PATH, id))
                .then()
                .contentType(JSON)
                .statusCode(SC_OK)
                .extract()
                .body()
                .as(Book.class);
    }

    public Response sendGetBookByIdRequest(int id) {
        return given()
                .when()
                .get(String.format("%s/%d", BASE_PATH, id));
    }

    public Book updateBook(int id, Book book) {
        return given()
                .contentType(JSON)
                .body(book)
                .when()
                .put(String.format("%s/%d", BASE_PATH, id))
                .then()
                .contentType(JSON)
                .statusCode(SC_OK)
                .extract()
                .body()
                .as(Book.class);
    }

    public Response sendUpdateBookRequest(int id, String requestBody) {
        return given()
                .contentType(JSON)
                .body(requestBody)
                .when()
                .put(String.format("%s/%d", BASE_PATH, id));
    }

    public Response deleteBook(int id) {
        return given()
                .when()
                .delete(String.format("%s/%d", BASE_PATH, id));
    }
} 