package com.controller;
import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.model.Lancamentos;
import com.model.PlanoContas;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Callback;

import com.repository.PlanoContasRepository;
import com.service.LivrorazaoService;


@Controller
public class LivrorazaoController {
  
    @FXML private TableView<Lancamentos> tabelaLancamentos;
    @FXML private TableColumn<Lancamentos, String> colunaDescricao;
    @FXML private TableColumn<Lancamentos, LocalDate> colunaCdata;
    @FXML private TableColumn<Lancamentos,Double>colunaValor;
    @FXML private TableColumn<Lancamentos,String>colunaContaDebito;
    @FXML private TableColumn<Lancamentos,String>colunaContaCredito;
    @FXML private DatePicker campoDatainicio;  
    @FXML private DatePicker campoDatafinal;  
     
   
    private LivrorazaoService service;
    
    @Autowired
    private PlanoContasRepository planoContasRepository;

    @FXML private ComboBox<PlanoContas> comboBoxDebito;

    public LivrorazaoController(LivrorazaoService service){
        
        this.service = service;
    }


@FXML
public void initialize() {    
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

    // 1. Buscar a lista de todas as contas do banco de dados.
    List<PlanoContas> todasAsContas = planoContasRepository.findAll();

    // 2. Popular ambos os ComboBoxes com a mesma lista de contas.
    comboBoxDebito.getItems().setAll(todasAsContas);
    

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

  
   

    // 5. (Opcional) Limpar campos ao iniciar.
  //  limparCampos();

  
}

 @FXML
   private void buscarDadosData(){
    LocalDate dataInicio = campoDatainicio.getValue();
    LocalDate dataFim = campoDatafinal.getValue();
    PlanoContas contadebito =  comboBoxDebito.getValue();
    List<Lancamentos> todasAsContas = service.buscarExtratoDaContaPorPeriodo(contadebito,dataInicio,dataFim);
        tabelaLancamentos.setItems(FXCollections.observableArrayList(todasAsContas));

   }

}
