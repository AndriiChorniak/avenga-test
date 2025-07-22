package com.chorniak.api.tests.book;

import com.chorniak.api.client.BookClient;
import com.chorniak.api.model.Book;
import com.chorniak.api.tests.base.BaseTest;
import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Step;
import io.qameta.allure.Story;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;

import static org.apache.http.HttpStatus.SC_BAD_REQUEST;
import static org.apache.http.HttpStatus.SC_OK;
import static org.assertj.core.api.Assertions.assertThat;

public class BookApiTest extends BaseTest {
    private final BookClient bookClient = new BookClient();

    @Epic("Books API")
    @Severity(SeverityLevel.CRITICAL)
    @Description("Verify that a book can be created successfully with a valid request body")
    @Test
    void verifyBookCreatedSuccessfully() {
        Book request = Book.builder()
                .id(111)
                .title("The Test Book")
                .description("A request for testing purposes")
                .pageCount(200)
                .excerpt("This is a sample excerpt from the request")
                .publishDate("2025-07-22T15:33:42.132Z")
                .build();

        Book result = bookClient.createBook(request);


        assertThat(result).isEqualTo(request);
    }

    @Epic("Books API")
    @Severity(SeverityLevel.CRITICAL)
    @Description("Verify that a book can be created successfully with an empty JSON")
    @Test
    void verifyBookCreatedSuccessfullyWithEmptyBody() {
        Response result = bookClient.sendCreateBookRequest("{}");

        Book expectedResult = Book.builder()
                .id(0)
                .title(null)
                .description(null)
                .pageCount(0)
                .excerpt(null)
                .publishDate("0001-01-01T00:00:00")
                .build();

        assertThat(result.getStatusCode()).isEqualTo(SC_OK);
        assertThat(result.getBody().as(Book.class)).isEqualTo(expectedResult);
    }

    @Epic("Books API")
    @Severity(SeverityLevel.CRITICAL)
    @Description("Verify that a book is not created with an empty request body")
    @Test
    void verifyBookNotCreatedWithEmptyBody() {
        Response response = bookClient.sendCreateBookRequest("");

        assertThat(response.getStatusCode()).isEqualTo(SC_BAD_REQUEST);
    }


}