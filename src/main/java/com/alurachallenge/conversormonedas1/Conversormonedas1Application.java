package com.alurachallenge.conversormonedas1;

import com.google.gson.Gson;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

@SpringBootApplication
public class Conversormonedas1Application implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(Conversormonedas1Application.class, args);
	}

	@Override
	public void run(String... args) {
		Conversor conversor = new Conversor();
		conversor.muestraElMenu();
	}
}




