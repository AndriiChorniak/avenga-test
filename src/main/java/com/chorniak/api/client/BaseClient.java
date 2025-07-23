package com.chorniak.api.client;

import com.chorniak.api.config.ApiConfig;
import io.restassured.specification.RequestSpecification;

import static io.restassured.RestAssured.given;

public abstract class BaseClient {

    protected RequestSpecification getRequestSpec() {
        return given()
                .baseUri(ApiConfig.getBaseUri())
                .basePath(ApiConfig.getBasePath());
    }

}
