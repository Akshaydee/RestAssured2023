package com.hipolabs.universities.search;

import com.hipolabs.universities.properties.PropertiesReader;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.Method;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import static io.restassured.RestAssured.given;

public class SearchUniversities {
	private static final String baseUrl = PropertiesReader.getProperties().getProperty("base.url");
	private static final String SEARCH_ENDPOINT = "/search";

	public static Response requestSearchUniversities(String name, String country) 
	{
		return requestSearchUniversities(name, country, Method.GET, 200);
	}

	public static Response requestSearchUniversities(String name, String country, Method method, int expectedStatus) {
		RequestSpecification reqSpec = given()
				.baseUri(baseUrl)
				.log().method()
				.log().uri()
				.log().ifValidationFails(LogDetail.ALL);

		if (name != null) 
		{
			reqSpec = reqSpec.param("name", name);
		}
		if (country != null) {
			reqSpec = reqSpec.param("country", country);
		}

		return reqSpec.request(method, SEARCH_ENDPOINT)
				.then()
				//.assertThat().statusCode(expectedStatus)
				//.log().status()
				//.log().body(true)
				.extract().response();
	}
}
