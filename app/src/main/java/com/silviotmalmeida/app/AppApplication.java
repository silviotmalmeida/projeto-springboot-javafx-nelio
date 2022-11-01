package com.silviotmalmeida.app;

import org.springframework.boot.autoconfigure.SpringBootApplication;

import javafx.application.Application;

// Classe inicial do Spring
// deve ser configurada para chamar a classe inicial do JavaFX
@SpringBootApplication
public class AppApplication {

	public static void main(String[] args) {
		Application.launch(JavaFXApplication.class, args);
	}

}
