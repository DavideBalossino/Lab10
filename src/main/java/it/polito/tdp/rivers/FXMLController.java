/**
 * Sample Skeleton for 'Scene.fxml' Controller Class
 */

package it.polito.tdp.rivers;

import java.net.URL;
import java.util.ResourceBundle;

import it.polito.tdp.rivers.model.Model;
import it.polito.tdp.rivers.model.River;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.event.ActionEvent;

public class FXMLController {
	
	private Model model;

    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="boxRiver"
    private ComboBox<River> boxRiver; // Value injected by FXMLLoader

    @FXML // fx:id="txtStartDate"
    private TextField txtStartDate; // Value injected by FXMLLoader

    @FXML // fx:id="txtEndDate"
    private TextField txtEndDate; // Value injected by FXMLLoader

    @FXML // fx:id="txtNumMeasurements"
    private TextField txtNumMeasurements; // Value injected by FXMLLoader

    @FXML // fx:id="txtFMed"
    private TextField txtFMed; // Value injected by FXMLLoader

    @FXML // fx:id="txtK"
    private TextField txtK; // Value injected by FXMLLoader

    @FXML // fx:id="btnSimula"
    private Button btnSimula; // Value injected by FXMLLoader

    @FXML // fx:id="txtResult"
    private TextArea txtResult; // Value injected by FXMLLoader
    
    @FXML
    void tendina(ActionEvent event) {
    	River r=boxRiver.getValue();
    	if(r!=null) {
    		model.setFiume(r);
    		txtStartDate.setText(""+r.getFlows().get(0).getDay());
    		txtEndDate.setText(""+r.getFlows().get(r.getFlows().size()-1).getDay());
    		txtNumMeasurements.setText(""+r.getFlows().size());
    		txtFMed.setText(""+String.format("%.2f", r.getFlowAvg()));
    	}
    } 
    
    @FXML
    void doSimula(ActionEvent event) {
    	String s_k=txtK.getText();
    	River r=boxRiver.getValue();
    	if(r==null) {
    		txtResult.setText("Selezionare un fiume");
    		return;
    	}
    	if(s_k=="") {
    		txtResult.setText("Inserire il numero k");
    		return;
    	}
    	try {
    		double k=Double.parseDouble(s_k);
    		model.simula(r.getFlowAvg(), r.getFlows().get(0).getDay(),r.getFlows().get(r.getFlows().size()-1).getDay() , k, r.getFlows());
    		txtResult.appendText("Il numero di giorni in cui non si e potuta garantire l'erogazione minima e : "+model.getGiorniNoMinimo()+"\n");
    		txtResult.appendText("La media dell'occupazione del bacino e: "+String.format("%.2f", model.mediaC()));
    	} catch(NumberFormatException e) {
    		txtResult.setText("Inserire un numero");
    	}
    }
    
    

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        assert boxRiver != null : "fx:id=\"boxRiver\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtStartDate != null : "fx:id=\"txtStartDate\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtEndDate != null : "fx:id=\"txtEndDate\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtNumMeasurements != null : "fx:id=\"txtNumMeasurements\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtFMed != null : "fx:id=\"txtFMed\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtK != null : "fx:id=\"txtK\" was not injected: check your FXML file 'Scene.fxml'.";
        assert btnSimula != null : "fx:id=\"btnSimula\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'Scene.fxml'.";
    }
    
    public void setModel(Model model) {
    	this.model = model;
    	boxRiver.getItems().addAll(model.getAllFiumi());
    }
}
