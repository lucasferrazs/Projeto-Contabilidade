package com.controller;


import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.model.PlanoContas;
import com.repository.PlanoContasRepository;
import com.service.LancamentoService;

import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.util.Callback;



@Component // Para que o Spring possa injetar o Service
public class LancamentosController {

    // --- Injeção do Service ---
    // O Spring vai fornecer a instância do LancamentoService automaticamente
    @Autowired
    private LancamentoService lancamentoService;

    @Autowired
    private PlanoContasRepository planoContasRepository;
    // --- Componentes da Interface Gráfica (FXML) ---
    @FXML private TextField campoDescricao;
    @FXML private TextField campoValor;
    @FXML private DatePicker campoData;
    @FXML private ComboBox<PlanoContas> comboBoxDebito;
    @FXML private ComboBox<PlanoContas> comboBoxCredito;
    @FXML private Label labelErro; // Para feedback ao usuário

  @FXML
public void initialize() {
    // 1. Buscar a lista de todas as contas do banco de dados.
    List<PlanoContas> todasAsContas = planoContasRepository.findAll();

    // 2. Popular ambos os ComboBoxes com a mesma lista de contas.
    comboBoxDebito.getItems().setAll(todasAsContas);
    comboBoxCredito.getItems().setAll(todasAsContas);

    // 3. Configurar como os objetos PlanoContas serão exibidos.
    // Criamos uma função (callback) que será usada para renderizar cada célula.
     Callback<ListView<PlanoContas>, ListCell<PlanoContas>> cellFactory = lv -> new ListCell<>() {
        @Override
        protected void updateItem(PlanoContas conta, boolean empty) {
            super.updateItem(conta, empty);
            if (empty || conta == null) {
                setText(null);
            } else {
                // Formato de exibição: "CODIGO - DESCRICAO"
                setText(conta.getCodigo() + " - " + conta.getDescricao());
            }
        }
    };

    // 4. Aplicar a formatação para a lista suspensa E para o campo principal.
    comboBoxDebito.setCellFactory(cellFactory);
    comboBoxDebito.setButtonCell(cellFactory.call(null)); // Usa a mesma lógica para o item selecionado

    comboBoxCredito.setCellFactory(cellFactory);
    comboBoxCredito.setButtonCell(cellFactory.call(null)); // Reutiliza a mesma configuração

    // 5. (Opcional) Limpar campos ao iniciar.
    limparCampos();
}
    /**
     * Método chamado quando o usuário clica no botão "Salvar".
     * Ele coleta, valida e envia os dados para a camada de serviço.
     */
    @FXML
    private void handleSalvarLancamento() {
        try {
            // --- 1. Limpar feedback de erro anterior ---
            labelErro.setText(""); // Esconde mensagens de erro antigas

            // --- 2. Coleta e Validação dos Dados da Tela ---
            String descricao = campoDescricao.getText();
            if (descricao == null || descricao.trim().isEmpty()) {
                exibirErro("A descrição é obrigatória.");
                return; // Para a execução
            }

            PlanoContas contaDebito = comboBoxDebito.getValue();
            if (contaDebito == null) {
                exibirErro("Selecione uma conta de débito.");
                
                return;
            }

            PlanoContas contaCredito = comboBoxCredito.getValue();
            if (contaCredito == null) {
                exibirErro("Selecione uma conta de crédito.");
                return;
            }

            LocalDate data = campoData.getValue();
            if (data == null) {
                exibirErro("A data é obrigatória.");
                return;
            }

            // Validação específica para o valor (que é texto e precisa ser convertido)
            BigDecimal valor = validarEConverterValor(campoValor.getText());
            if (valor == null) {
                // A mensagem de erro já foi exibida pelo método auxiliar
                return;
            }

            // --- 3. Chamada para a Camada de Serviço ---
            // Entregamos os dados já validados e nos tipos corretos para o Service
            lancamentoService.criarNovoLancamento(descricao, data, valor, contaDebito, contaCredito);

            // --- 4. Feedback de Sucesso e Limpeza da Tela ---
            exibirSucesso("Lançamento salvo com sucesso!");
            limparCampos();

        } catch (IllegalArgumentException ex) {
            // Captura erros de LÓGICA DE NEGÓCIO vindos do Service
            // Ex: "Valor não pode ser negativo", "Conta de débito igual à de crédito"
            exibirErro("Erro: " + ex.getMessage());

        } catch (Exception ex) {
            // Captura qualquer outro erro inesperado (ex: falha de conexão com o banco)
            exibirErro("Ocorreu um erro inesperado. Tente novamente.");
            // É uma boa prática logar o erro completo para depuração
            ex.printStackTrace();
        }
    }

    /**
     * Método auxiliar para validar e converter o valor de String para BigDecimal.
     */
    private BigDecimal validarEConverterValor(String textoValor) {
        if (textoValor == null || textoValor.trim().isEmpty()) {
            exibirErro("O valor é obrigatório.");
            return null;
        }
        try {
            // Substitui vírgula por ponto para aceitar ambos os formatos (ex: 1.500,25)
            BigDecimal valor = new BigDecimal(textoValor.replace(",", "."));
            // O Service vai validar se é positivo, mas podemos fazer uma checagem aqui também
            if (valor.compareTo(BigDecimal.ZERO) <= 0) {
                exibirErro("O valor deve ser maior que zero.");
                return null;
            }
            return valor;
        } catch (NumberFormatException e) {
            exibirErro("Valor inválido. Use apenas números.");
            return null;
        }
    }

    /**
     * Limpa todos os campos do formulário após um salvamento bem-sucedido.
     */
    private void limparCampos() {
        campoDescricao.clear();
        campoValor.clear();
        campoData.setValue(null);
        comboBoxDebito.getSelectionModel().clearSelection();
        comboBoxCredito.getSelectionModel().clearSelection();
    }

    /**
     * Exibe uma mensagem de erro na interface.
     */
    private void exibirErro(String mensagem) {
        labelErro.setText(mensagem);
        labelErro.setStyle("-fx-text-fill: red;"); // Deixa o texto vermelho
    }

    /**
     * Exibe uma mensagem de sucesso temporária na interface.
     */
    private void exibirSucesso(String mensagem) {
        labelErro.setText(mensagem);
        labelErro.setStyle("-fx-text-fill: green;"); // Deixa o texto verde
    }

    // Outros métodos do seu controller...
    // Por exemplo, um método @FXML initialize() para carregar as contas nos ComboBoxes.
}
