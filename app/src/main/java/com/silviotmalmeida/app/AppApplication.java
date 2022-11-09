package com.silviotmalmeida.app;

import java.util.Locale;

import org.springframework.boot.autoconfigure.SpringBootApplication;

import javafx.application.Application;

// Classe inicial do Spring
// deve ser configurada para chamar a classe inicial do JavaFX
@SpringBootApplication
public class AppApplication {

	public static void main(String[] args) {

		// definindo o local
		Locale.setDefault(Locale.US);

		// iniciando a aplicação
		Application.launch(JavaFXApplication.class, args);
	}

}
