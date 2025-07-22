package com.chorniak.api.client;

import com.chorniak.api.model.Book;
import io.restassured.response.Response;

import static io.restassured.RestAssured.*;

public class BookClient {
    private static final String BASE_PATH = "/api/v1/Books";

    public Response createBook(Book book) {
        return given()
                .contentType("application/json")
                .body(book)
                .when()
                .post(BASE_PATH);
    }

    public Response getAllBooks() {
        return given()
                .when()
                .get(BASE_PATH);
    }

    public Response getBookById(int id) {
        return given()
                .when()
                .get(BASE_PATH + "/" + id);
    }

    public Response updateBook(int id, Book book) {
        return given()
                .contentType("application/json")
                .body(book)
                .when()
                .put(BASE_PATH + "/" + id);
    }

    public Response deleteBook(int id) {
        return given()
                .when()
                .delete(BASE_PATH + "/" + id);
    }
} 