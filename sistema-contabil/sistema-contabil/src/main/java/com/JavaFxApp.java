package com;
// JavaFxApp.java


import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;
import java.net.URL;

public class JavaFxApp extends Application {

    private ConfigurableApplicationContext springContext;

    // 1. ANTES DA JANELA ABRIR: Inicia o Spring
    @Override
    public void init() {
        springContext = new SpringApplicationBuilder(SistemaContabilApplication.class).run();
    }

    // 2. QUANDO A JANELA ABRE: Carrega a tela principal
    @Override
    public void start(Stage stage) throws Exception {
        // Carrega o menu principal que você criou
        URL fxmlLocation = getClass().getResource("/fxml/MainMenu.fxml"); // Verifique o nome da pasta e do arquivo
        FXMLLoader fxmlLoader = new FXMLLoader(fxmlLocation);

        // **A MÁGICA DA INTEGRAÇÃO**: Diz ao JavaFX para pedir ao Spring para criar os controllers
        fxmlLoader.setControllerFactory(springContext::getBean);

        Parent root = fxmlLoader.load();
        Scene scene = new Scene(root, 1024, 768);
        stage.setTitle("Sistema Contábil");
        stage.setScene(scene);
        stage.show();
    }

    // 3. QUANDO A JANELA FECHA: Encerra o Spring
    @Override
    public void stop() {
        springContext.close();
        Platform.exit();
    }
}