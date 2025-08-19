package com.controller;

import com.model.PlanoContas;
import com.model.enums.GrupoEnum;
import com.model.enums.NaturezaEnum;
import com.model.enums.SubgrupoEnum;
import com.service.PlanoContasService;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import org.springframework.stereotype.Component;
import java.util.List;

/**
 * Controller da tela PlanoContasView.fxml.
 * @Component: Permite que o Spring gerencie esta classe e injete dependências nela.
 * A responsabilidade deste controller é puramente gerenciar a tela:
 * - Receber eventos (cliques de botão ).
 * - Chamar o serviço para obter dados.
 * - Atualizar a tabela com os dados recebidos.
 */
@Component
public class PlanoContasController {

    private final PlanoContasService service;

    // @FXML conecta estes atributos aos componentes definidos no FXML com o mesmo fx:id
    @FXML private TextField txtBusca;
    @FXML private TableView<PlanoContas> tabelaContas;
    @FXML private TableColumn<PlanoContas, String> colunaCodigo;
    @FXML private TableColumn<PlanoContas, String> colunaDescricao;
    @FXML private TableColumn<PlanoContas,Double>colunaSaldo;
    @FXML private TableColumn<PlanoContas,GrupoEnum>colunaGrupo;
    @FXML private TableColumn<PlanoContas,SubgrupoEnum>colunaSubGrupo;
    @FXML private TableColumn<PlanoContas,NaturezaEnum>colunaNatureza;
    public PlanoContasController(PlanoContasService service) {
        this.service = service;
    }

    /**
     * Método especial do JavaFX. É chamado automaticamente depois que o FXML é carregado.
     * Perfeito para configurações iniciais.
     */
    @FXML
    public void initialize() {
        // Configura as colunas da tabela. O texto em PropertyValueFactory<> deve ser
        // o nome EXATO do atributo na classe PlanoContas (codigo, descricao).
        colunaCodigo.setCellValueFactory(new PropertyValueFactory<>("codigo"));
        colunaDescricao.setCellValueFactory(new PropertyValueFactory<>("descricao"));
        colunaSaldo.setCellValueFactory(new PropertyValueFactory<>("saldo"));
        colunaGrupo.setCellValueFactory(new PropertyValueFactory<>("grupo"));
        colunaSubGrupo.setCellValueFactory(new PropertyValueFactory<>("subgrupo"));
        colunaNatureza.setCellValueFactory(new PropertyValueFactory<>("natureza"));
        // Inicia a tela carregando todos os dados.
        handleLimpar();
    }

    /**
     * Chamado pelo botão "Buscar" ou ao pressionar Enter no campo de texto.
     */
    @FXML
    private void handleBuscar() {
        String termo = txtBusca.getText();
        List<PlanoContas> resultados = service.buscarPorTermo(termo);
        tabelaContas.setItems(FXCollections.observableArrayList(resultados));
    }

    /**
     * Chamado pelo botão "Limpar". Limpa a busca e recarrega todos os dados.
     */
    @FXML
    private void handleLimpar() {
        txtBusca.clear();
        List<PlanoContas> todasAsContas = service.listarTodos();
        tabelaContas.setItems(FXCollections.observableArrayList(todasAsContas));
    }
}
