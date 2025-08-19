package com.controller;


import java.io.IOException;
import java.net.URL;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.layout.BorderPane;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

@Component
public class MainMenuController {

    @FXML
    private BorderPane painelPrincipal; // O nosso "casco"

    // O Spring injeta o contexto da aplicação para que possamos carregar outros beans (controllers )
    private final ApplicationContext applicationContext;

    public MainMenuController(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    @FXML
    public void abrirTelaLancamentos() {
        System.out.println("Abrindo tela de Lançamentos...");
        carregarTela("TelaLancamento"); // O nome do arquivo FXML sem a extensão
    }

    @FXML
    public void abrirTelaPlanoDeContas() {
        System.out.println("Abrindo tela de Plano de Contas...");
        carregarTela("TelaPlanoDeContas"); // Implementação futura
    }

    @FXML
    public void abrirRelatorioBalancete() {
        System.out.println("Abrindo relatório de Balancete...");
        // carregarTela("RelatorioBalancete"); // Implementação futura
    }

    @FXML
    public void abrirTelaSobre() {
        System.out.println("Abrindo tela Sobre...");
        // carregarTela("TelaSobre"); // Implementação futura
    }

    /**
     * Método auxiliar para carregar um FXML e colocá-lo no centro do BorderPane.
     * @param nomeTela O nome do arquivo FXML (sem a extensão .fxml).
     */
    private void carregarTela(String nomeTela) {
        try {
            URL fxmlLocation = getClass().getResource("/fxml/" + nomeTela + ".fxml");
            if (fxmlLocation == null) {
                System.err.println("ERRO: Não foi possível encontrar a tela: " + nomeTela);
                return;
            }

            // Usa o contexto do Spring para criar o FXMLLoader, garantindo que o
            // controller da tela carregada também seja gerenciado pelo Spring.
            FXMLLoader loader = new FXMLLoader(fxmlLocation);
            loader.setControllerFactory(applicationContext::getBean);

            Parent tela = loader.load();
            painelPrincipal.setCenter(tela); // Coloca a nova tela no centro do nosso "casco"

        } catch (IOException e) {
            System.err.println("Erro ao carregar a tela: " + nomeTela);
            e.printStackTrace();
        }
    }
}
