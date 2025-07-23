package com.chorniak.api.tests.book;

import com.chorniak.api.client.BookClient;
import com.chorniak.api.model.Book;
import com.chorniak.api.tests.base.BaseTest;
import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.List;
import java.util.stream.IntStream;

import static org.apache.http.HttpStatus.SC_BAD_REQUEST;
import static org.apache.http.HttpStatus.SC_NOT_FOUND;
import static org.apache.http.HttpStatus.SC_OK;
import static org.assertj.core.api.Assertions.assertThat;

//NOTE: Since there is no DB in the API, we cannot verify the actual creation/deletion/updating of a book.
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
    @Description("Verify that a book is not created with an incorrect request body")
    @ParameterizedTest
    @ValueSource(strings = {"", " ", "invalid json", "{title: 'Test Book'}", "{id: 1, title: 'Test Book'}"})
    void verifyBookNotCreatedWithEmptyBody(String invalidRequestBody) {
        Response response = bookClient.sendCreateBookRequest(invalidRequestBody);

        assertThat(response.getStatusCode()).isEqualTo(SC_BAD_REQUEST);
    }

    @Epic("Books API")
    @Severity(SeverityLevel.CRITICAL)
    @Description("Verify that all books can be retrieved successfully")
    @Test
    void verifyGetAllBooks() {
        List<Book> allBooks = bookClient.getAllBooks();

        List<Integer> expectedIds = IntStream.range(1, 201)
                .boxed()
                .toList();

        assertThat(allBooks)
                .hasSize(200)
                .extracting(Book::getId)
                .containsExactlyElementsOf(expectedIds);
        assertThat(allBooks).allSatisfy(this::verifyBookStructure);
    }

    @Epic("Books API")
    @Severity(SeverityLevel.CRITICAL)
    @Description("Verify that a book can be retrieved by its ID")
    @ParameterizedTest
    @ValueSource(ints = {1, 10, 200})
    void verifyGetBookById(int bookId) {
        Book book = bookClient.getBookById(bookId);

        assertThat(book)
                .isNotNull()
                .extracting(Book::getId)
                .isEqualTo(bookId);
        verifyBookStructure(book);
    }

    @Epic("Books API")
    @Severity(SeverityLevel.CRITICAL)
    @Description("Verify that a book is not found when retrieving by an invalid ID")
    @ParameterizedTest
    @ValueSource(ints = {-1, 0, 201})
    void verifyGetBookByIdNotFound(int invalidBookId) {

        Response response = bookClient.sendGetBookByIdRequest(invalidBookId);

        assertThat(response.getStatusCode()).isEqualTo(SC_NOT_FOUND);
    }

    // NOTE: there no validation by book ID in the API, so we can use any value
    @Epic("Books API")
    @Severity(SeverityLevel.CRITICAL)
    @Description("Verify that a book can be updated successfully")
    @ParameterizedTest
    @ValueSource(ints = {-1, 1})
    void verifyUpdateBook(int bookId) {
        Book bookToUpdate = Book.builder()
                .id(bookId)
                .title("Updated Book Title")
                .description("Updated description for the book")
                .pageCount(150)
                .excerpt("Updated excerpt for the book")
                .publishDate("2025-07-22T15:33:42.132Z")
                .build();

        Book updatedBook = bookClient.updateBook(bookId, bookToUpdate);

        assertThat(updatedBook).isEqualTo(bookToUpdate);
    }

    @Epic("Books API")
    @Severity(SeverityLevel.CRITICAL)
    @Description("Verify that a book is not updated with an empty request body")
    @ParameterizedTest
    @ValueSource(strings = {"", " ", "invalid json", "{title: 'Test Book'}", "{id: 1, title: 'Test Book'}"})
    void verifyBookNotUpdatedWithEmptyBody(String invalidRequestBody) {
        Response response = bookClient.sendUpdateBookRequest(1, invalidRequestBody);

        assertThat(response.getStatusCode()).isEqualTo(SC_BAD_REQUEST);
    }

    // NOTE: there no validation by book ID in the API, so we can use any value
    @Epic("Books API")
    @Severity(SeverityLevel.CRITICAL)
    @Description("Verify that a book is deleted")
    @ParameterizedTest
    @ValueSource(ints = {-1, 1, 200, 201})
    void verifyBookDeletedSuccessfully(int bookId) {
        Response response = bookClient.deleteBook(bookId);

        assertThat(response.getStatusCode()).isEqualTo(SC_OK);
    }

    private void verifyBookStructure(Book book) {
        assertThat(book.getTitle()).isEqualTo("Book " + book.getId());
        assertThat(book.getPageCount()).isEqualTo(book.getId() * 100);
        assertThat(book.getDescription()).startsWith("Lorem lorem lorem");
        assertThat(book.getExcerpt()).startsWith("Lorem lorem lorem");
        assertThat(book.getPublishDate()).isNotNull();
    }


}