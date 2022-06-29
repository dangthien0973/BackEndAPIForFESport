package com.example.ApiDoAn.controller;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
@RestController
@RequestMapping("/api/Ratio")
// lấy tỉ số 
public class RatioController {
	@GetMapping("/getRatio")
	public ResponseEntity<?> toolsaveProduct() throws IOException, InterruptedException {
		HttpRequest request = HttpRequest.newBuilder()
				.uri(URI.create("https://api-football-beta.p.rapidapi.com/timezone"))
				.header("X-RapidAPI-Key", "67fca16b72msh44278b430b39ea3p13b23ejsnf34d39d4dae0")
				.header("X-RapidAPI-Host", "api-football-beta.p.rapidapi.com")
				.method("GET", HttpRequest.BodyPublishers.noBody())
				.build();
		HttpResponse<String> response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
		System.out.println(response.body());
		return ResponseEntity.status(HttpStatus.OK).body(response.body());
	}
	
}
