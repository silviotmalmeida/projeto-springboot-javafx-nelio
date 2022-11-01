package com.silviotmalmeida.app;

import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;

import com.silviotmalmeida.app.javafx.ViewController;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import net.rgielen.fxweaver.core.FxWeaver;

// classe inicial do JavaFX
public class JavaFXApplication extends Application {

    private ConfigurableApplicationContext contextoSpring;

    // definição das ações a serem realizadas antes da aplicação iniciar
    // deve ser configurada para iniciar o Spring
    @Override
    public void init() {

        String[] args = getParameters().getRaw().toArray(new String[0]);

        this.contextoSpring = new SpringApplicationBuilder()
                .sources(AppApplication.class)
                .run(args);
    }

    // definição das ações a serem realizadas após o encerramento da aplicação
    // deve ser configurada para encerrar o Spring
    @Override
    public void stop() {
        this.contextoSpring.close();
        Platform.exit();
    }

    // definição das ações a serem realizadas no início da aplicação
    // deve ser configurada para abrir o controlador da view inicial
    @Override
    public void start(Stage stage) {
        FxWeaver fxWeaver = contextoSpring.getBean(FxWeaver.class);
        Parent root = fxWeaver.loadView(ViewController.class);
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
}
