package com.chorniak.api.client;

import com.chorniak.api.model.Book;
import io.restassured.response.Response;

import java.util.List;

import static io.restassured.http.ContentType.JSON;
import static org.apache.http.HttpStatus.SC_OK;

public class BookClient extends BaseClient {
    private static final String BOOKS_ENDPOINT = "/Books";

    public Book createBook(Book book) {
        return getRequestSpec()
                .contentType(JSON)
                .body(book)
                .when()
                .post(BOOKS_ENDPOINT)
                .then()
                .contentType(JSON)
                .statusCode(SC_OK)
                .and()
                .extract()
                .body()
                .as(Book.class);
    }

    public Response sendCreateBookRequest(String requestBody) {
        return getRequestSpec()
                .contentType(JSON)
                .body(requestBody)
                .when()
                .post(BOOKS_ENDPOINT);
    }

    public List<Book> getAllBooks() {
        return getRequestSpec()
                .when()
                .get(BOOKS_ENDPOINT)
                .then()
                .contentType(JSON)
                .statusCode(SC_OK)
                .extract()
                .body()
                .jsonPath()
                .getList(".", Book.class);
    }

    public Book getBookById(int id) {
        return getRequestSpec()
                .when()
                .get(String.format("%s/%d", BOOKS_ENDPOINT, id))
                .then()
                .contentType(JSON)
                .statusCode(SC_OK)
                .extract()
                .body()
                .as(Book.class);
    }

    public Response sendGetBookByIdRequest(int id) {
        return getRequestSpec()
                .when()
                .get(String.format("%s/%d", BOOKS_ENDPOINT, id));
    }

    public Book updateBook(int id, Book book) {
        return getRequestSpec()
                .contentType(JSON)
                .body(book)
                .when()
                .put(String.format("%s/%d", BOOKS_ENDPOINT, id))
                .then()
                .contentType(JSON)
                .statusCode(SC_OK)
                .extract()
                .body()
                .as(Book.class);
    }

    public Response sendUpdateBookRequest(int id, String requestBody) {
        return getRequestSpec()
                .contentType(JSON)
                .body(requestBody)
                .when()
                .put(String.format("%s/%d", BOOKS_ENDPOINT, id));
    }

    public Response deleteBook(int id) {
        return getRequestSpec()
                .when()
                .delete(String.format("%s/%d", BOOKS_ENDPOINT, id));
    }
} 