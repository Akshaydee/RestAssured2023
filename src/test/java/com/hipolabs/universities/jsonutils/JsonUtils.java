package com.hipolabs.universities.jsonutils;

import com.jayway.jsonpath.DocumentContext;
import com.jayway.jsonpath.JsonPath;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;

public class JsonUtils {
	public static <T> T getDataByPath(String filePath, String jsonPath) throws URISyntaxException, IOException {
		String fullJson;
		URI sourceURI = JsonUtils.class.getClassLoader().getResource(filePath).toURI();
		fullJson = Files.readString(Path.of(sourceURI));
		DocumentContext jsonContext = JsonPath.parse(fullJson);
		return jsonContext.read(jsonPath);
	}
}
