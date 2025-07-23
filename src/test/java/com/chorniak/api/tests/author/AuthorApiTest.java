package com.chorniak.api.tests.author;

import com.chorniak.api.client.AuthorClient;
import com.chorniak.api.model.Author;
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
import java.util.Random;

import static org.apache.http.HttpStatus.SC_BAD_REQUEST;
import static org.apache.http.HttpStatus.SC_NOT_FOUND;
import static org.apache.http.HttpStatus.SC_OK;
import static org.assertj.core.api.Assertions.assertThat;

//NOTE: Since there is no DB in the API, we cannot verify the actual creation/deletion/updating of an author.
public class AuthorApiTest extends BaseTest {
    private final AuthorClient authorClient = new AuthorClient();
    private Random random = new Random();

    @Epic("Authors API")
    @Severity(SeverityLevel.CRITICAL)
    @Description("Verify that an author can be created successfully with a valid request body")
    @Test
    void verifyAuthorCreatedSuccessfully() {
        Author request = Author.builder()
                .id(random.nextInt(1, 9999))
                .idBook(random.nextInt(1, 9999))
                .firstName("Test")
                .lastName("Author")
                .build();

        Author result = authorClient.createAuthor(request);

        assertThat(result).isEqualTo(request);
    }

    @Epic("Authors API")
    @Severity(SeverityLevel.NORMAL)
    @Description("Verify that an author can be created successfully with an empty JSON")
    @Test
    void verifyAuthorCreatedSuccessfullyWithEmptyBody() {
        Response result = authorClient.sendCreateAuthorRequest("{}");

        Author expectedResult = Author.builder()
                .id(0)
                .idBook(0)
                .firstName(null)
                .lastName(null)
                .build();

        assertThat(result.getStatusCode()).isEqualTo(SC_OK);
        assertThat(result.getBody().as(Author.class)).isEqualTo(expectedResult);
    }

    @Epic("Authors API")
    @Severity(SeverityLevel.NORMAL)
    @Description("Verify that an author is not created with an incorrect request body")
    @ParameterizedTest
    @ValueSource(strings = {"", " ", "invalid json", "{firstName: 'Test Author'}", "{id: 1, firstName: 'Test Author'}"})
    void verifyAuthorNotCreatedWithInvalidBody(String invalidRequestBody) {
        Response response = authorClient.sendCreateAuthorRequest(invalidRequestBody);

        assertThat(response.getStatusCode()).isEqualTo(SC_BAD_REQUEST);
    }

    @Epic("Authors API")
    @Severity(SeverityLevel.CRITICAL)
    @Description("Verify that all authors can be retrieved successfully")
    @Test
    void verifyGetAllAuthors() {
        List<Author> allAuthors = authorClient.getAllAuthors();

        assertThat(allAuthors).allSatisfy(this::verifyAuthorStructure);
    }

    @Epic("Authors API")
    @Severity(SeverityLevel.CRITICAL)
    @Description("Verify that an author can be retrieved by their ID")
    @Test
    void verifyGetAuthorById() {
        int authorId = 1; // Author list is dynamic, this ID is assumed to exist
        Author author = authorClient.getAuthorById(authorId);

        assertThat(author)
                .isNotNull()
                .extracting(Author::getId)
                .isEqualTo(authorId);
        verifyAuthorStructure(author);
    }

    @Epic("Authors API")
    @Severity(SeverityLevel.CRITICAL)
    @Description("Verify that an author is not found when retrieving by an invalid ID")
    @ParameterizedTest
    @ValueSource(ints = {-1, 0})
    void verifyGetAuthorByIdNotFound(int invalidAuthorId) {
        Response response = authorClient.sendGetAuthorByIdRequest(invalidAuthorId);

        assertThat(response.getStatusCode()).isEqualTo(SC_NOT_FOUND);
    }

    // NOTE: there no validation by author ID in the API, so we can use any value
    @Epic("Authors API")
    @Severity(SeverityLevel.CRITICAL)
    @Description("Verify that an author can be updated successfully")
    @ParameterizedTest
    @ValueSource(ints = {-1, 1})
    void verifyUpdateAuthor(int authorId) {
        Author authorToUpdate = Author.builder()
                .id(authorId)
                .idBook(-1) // Book ID is not validated in the API
                .firstName("Updated")
                .lastName("AuthorName")
                .build();

        Author updatedAuthor = authorClient.updateAuthor(authorId, authorToUpdate);

        assertThat(updatedAuthor).isEqualTo(authorToUpdate);
    }

    @Epic("Authors API")
    @Severity(SeverityLevel.NORMAL)
    @Description("Verify that an author is not updated with an empty request body")
    @ParameterizedTest
    @ValueSource(strings = {"", " ", "invalid json", "{firstName: 'Test Author'}", "{id: 1, firstName: 'Test Author'}"})
    void verifyAuthorNotUpdatedWithInvalidBody(String invalidRequestBody) {
        Response response = authorClient.sendUpdateAuthorRequest(1, invalidRequestBody);

        assertThat(response.getStatusCode()).isEqualTo(SC_BAD_REQUEST);
    }

    // NOTE: there no validation by author ID in the API, so we can use any value
    @Epic("Authors API")
    @Severity(SeverityLevel.CRITICAL)
    @Description("Verify that an author is deleted")
    @ParameterizedTest
    @ValueSource(ints = {-1, 1, 650, 651})
    void verifyAuthorDeletedSuccessfully(int authorId) {
        Response response = authorClient.deleteAuthor(authorId);

        assertThat(response.getStatusCode()).isEqualTo(SC_OK);
    }

    private void verifyAuthorStructure(Author author) {
        assertThat(author.getFirstName()).isEqualTo("First Name " + author.getId());
        assertThat(author.getLastName()).isEqualTo("Last Name " + author.getId());
        assertThat(author.getIdBook()).isBetween(1, 200);
        assertThat(author.getId()).isGreaterThan(0);
    }
}