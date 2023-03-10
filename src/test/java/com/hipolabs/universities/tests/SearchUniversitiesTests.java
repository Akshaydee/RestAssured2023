package com.hipolabs.universities.tests;

import com.hipolabs.universities.jsonutils.JsonUtils;
import com.hipolabs.universities.search.SearchUniversities;
import io.restassured.http.Method;
import io.restassured.response.Response;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;

import static io.restassured.path.json.JsonPath.from;

public class SearchUniversitiesTests {
	@ParameterizedTest
	@DisplayName("Request universities search by country name parameter only. Parameter's case should be ignored.")
	@ValueSource(strings = {"Lithuania", "LITHUANIA"})
	public void searchUniversitiesByCountryTest(String country) throws URISyntaxException, IOException {
		Response response = SearchUniversities.requestSearchUniversities(null, country);

		List<String> actualUniversitiesList = response.getBody().jsonPath().getList("$.");
		List<String> universitiesListFromFile = JsonUtils.getDataByPath("world_universities_and_domains.json",
				String.format("$[?(@.country == '%s')]", country));
		Assertions.assertThat(actualUniversitiesList)
				.as("Actual universities list for %s is less than expected", country)
				.containsAll(universitiesListFromFile);
	}
/*
	@ParameterizedTest
	@DisplayName("Request universities by university name parameter only. Parameter's case should be ignored.")
	@ValueSource(strings = {"Kaunas Medical Academy", "KAUNAS MEDICAL ACADEMY"})
	public void searchUniversityByFullNameTest(String name) {
		String response = SearchUniversities.requestSearchUniversities(name, null).asString();
		List<String> actualUniversitiesList = from(response).getList("$.");

		Assertions.assertThat(actualUniversitiesList.size())
				.as("List should contain only one university record with name \"%s\"", name)
				.isEqualTo(1);

		String actualUniversityName = from(response).getString("[0].name");

		Assertions.assertThat(actualUniversityName)
				.as("Actual university record does not contain \"%s\"", name)
				.isEqualToIgnoringCase(name);
	}
*/
	@ParameterizedTest
	@DisplayName("Request universities by partial university name parameter only. Parameter's case should be ignored.")
	@ValueSource(strings = {"Kaunas", "KAUNAS"})
	public void searchUniversityByPartialNameTest(String name) throws URISyntaxException, IOException {
		String response = SearchUniversities.requestSearchUniversities(name, null).asString();
		List<String> actualUniversitiesList = from(response).getList("$.");
		List<String> universitiesListFromFile = JsonUtils
				.getDataByPath("world_universities_and_domains.json", String.format("$[?(@.name =~ /.*%s.*/i)]", name));

		Assertions.assertThat(actualUniversitiesList)
				.as("Actual universities list for \"%s\" is less than expected", name)
				.containsAll(universitiesListFromFile);
	}

	@ParameterizedTest
	@DisplayName("Request universities by incorrect pairs of country and university name. Should return empty list.")
	@CsvSource({"Kaunas Medical Academy, Latvia", "Kaunas Medical Academy, Lith", "Fantasy academy, Lithuania"})
	public void negativeUniversitiesSearchByNameAndCountryTest(String name, String country) {
		var response = SearchUniversities.requestSearchUniversities(name, country);

		List<String> actualUniversitiesList = response.getBody().jsonPath().getList("$.");
		Assertions.assertThat(actualUniversitiesList)
				.as("Actual universities list contains records, but should not")
				.isEmpty();
	}

	@Test
	@DisplayName("Send POST request to '/search' endpoint. HTTP 405 response should be returned")
	public void negativeUniversitiesSearchByNameAndCountryTest() {
		String response = SearchUniversities
				.requestSearchUniversities("Kaunas Medical Academy", null, Method.POST, 405)
				.asString();

		Assertions.assertThat(response)
				.as("Unexpected response code")
				.contains("The method is not allowed for the requested URL");
	}
}
