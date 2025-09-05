package com.controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Component;
import com.model.Lancamentos;
import com.model.PlanoContas;
import com.service.LivrodiarioService;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;


@Component
public class LivrodiarioController {
   private  LivrodiarioService service;

      // @FXML conecta estes atributos aos componentes definidos no FXML com o mesmo fx:id

    @FXML private TableView<Lancamentos> tabelaLancamentos;
    @FXML private TableColumn<Lancamentos, String> colunaDescricao;
    @FXML private TableColumn<Lancamentos, LocalDate> colunaCdata;
    @FXML private TableColumn<Lancamentos,Double>colunaValor;
    @FXML private TableColumn<Lancamentos,String>colunaContaDebito;
    @FXML private TableColumn<Lancamentos,String>colunaContaCredito;
    @FXML private DatePicker campoData;
    
    


    public LivrodiarioController(LivrodiarioService service){
        this.service = service;
    }

    @FXML
    public void initialize() {

        // Configura as colunas da tabela. O texto em PropertyValueFactory<> deve ser
        // o nome EXATO do atributo na classe PlanoContas (codigo, descricao).
      
        colunaDescricao.setCellValueFactory(new PropertyValueFactory<>("descricao"));
        colunaCdata.setCellValueFactory(new PropertyValueFactory<>("cdata"));
        colunaValor.setCellValueFactory(new PropertyValueFactory<>("valor"));


        // 2. Colunas com objetos aninhados 
        // Para a coluna de débito, pegamos o objeto Lancamentos,
        // entramos em getContadebito() e pegamos a getDescricao().
        colunaContaDebito.setCellValueFactory(cellData -> {
            PlanoContas contaDebito = cellData.getValue().getContadebito();
            if (contaDebito != null) {
                return new SimpleStringProperty(contaDebito.getDescricao());
            } else {
                return new SimpleStringProperty(""); // Ou "N/A", etc.
            }
        });

        // Fazemos o mesmo para a coluna de crédito.
        colunaContaCredito.setCellValueFactory(cellData -> {
            PlanoContas contaCredito = cellData.getValue().getContacredito();
            if (contaCredito != null) {
                return new SimpleStringProperty(contaCredito.getDescricao());
            } else {
                return new SimpleStringProperty("");
            }
        });
        
        carregarDados();
    }



      @FXML
    private void carregarDados() {
        List<Lancamentos> todasAsContas = service.filtrarLancamentos();
        tabelaLancamentos.setItems(FXCollections.observableArrayList(todasAsContas));
    }

   @FXML
   private void buscarDadosData(){
    LocalDate dataSelecionada = campoData.getValue();
    List<Lancamentos> todasAsContas = service.buscarPorData(dataSelecionada);
        tabelaLancamentos.setItems(FXCollections.observableArrayList(todasAsContas));

   }

   
}
 