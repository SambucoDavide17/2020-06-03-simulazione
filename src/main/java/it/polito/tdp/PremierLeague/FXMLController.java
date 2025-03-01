/**
 * Sample Skeleton for 'Scene.fxml' Controller Class
 */

package it.polito.tdp.PremierLeague;

import java.net.URL;
import java.util.ResourceBundle;

import it.polito.tdp.PremierLeague.model.Model;
import it.polito.tdp.PremierLeague.model.Player;
import it.polito.tdp.PremierLeague.model.PlayerEDelta;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class FXMLController {
	
	private Model model;

    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="btnCreaGrafo"
    private Button btnCreaGrafo; // Value injected by FXMLLoader

    @FXML // fx:id="btnTopPlayer"
    private Button btnTopPlayer; // Value injected by FXMLLoader

    @FXML // fx:id="btnDreamTeam"
    private Button btnDreamTeam; // Value injected by FXMLLoader

    @FXML // fx:id="txtK"
    private TextField txtK; // Value injected by FXMLLoader

    @FXML // fx:id="txtGoals"
    private TextField txtGoals; // Value injected by FXMLLoader

    @FXML // fx:id="txtResult"
    private TextArea txtResult; // Value injected by FXMLLoader

    @FXML
    void doCreaGrafo(ActionEvent event) {
    	
    	txtResult.clear();
    	double soglia = 0.0;
    	try {
    		soglia = Double.parseDouble(txtGoals.getText());
    	} catch (NumberFormatException e) {
    		txtResult.clear();
        	txtResult.appendText("Inserisci un valore numerico!\n");
        	return ;
    	}
    	model.creaGrafo(soglia);
    	txtResult.appendText("Grafo creato!\n");
    	txtResult.appendText("# Vertici: " + model.vertexNumber() + "\n");
    	txtResult.appendText("# Archi: " + model.edgeNumber() + "\n");

    }

    @FXML
    void doDreamTeam(ActionEvent event) {
    	
    	txtResult.clear();
    	if(model.getGrafo() == null) {
    		txtResult.appendText("Prima creare il grafo!\n");
    		return;
    	}
    	int nPlayer = 0;
    	try {
    		nPlayer = Integer.parseInt(txtK.getText());
    	} catch (NumberFormatException e) {
    		txtResult.clear();
        	txtResult.appendText("Inserisci un valore numerico intero!\n");
        	return ;
    	}
    	txtResult.appendText("Dream team: \n");
    	for(Player p: model.DreamTeam(nPlayer)){
    		txtResult.appendText(p.toString() + "\n");
    	}
    	txtResult.appendText("Grado di titolarità: \n");
    	txtResult.appendText(model.getBestGrado() + "\n");
    }

    @FXML
    void doTopPlayer(ActionEvent event) {
    	txtResult.clear();
    	txtResult.appendText("TOP PLAYER: " + model.topPlayer() + "\n");
    	txtResult.appendText("AVVERSARI BATTUTI: " + "\n");
    	for(PlayerEDelta p: model.Sconfitti()) {
    		txtResult.appendText(p.toString());
    	}
    }

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        assert btnCreaGrafo != null : "fx:id=\"btnCreaGrafo\" was not injected: check your FXML file 'Scene.fxml'.";
        assert btnTopPlayer != null : "fx:id=\"btnTopPlayer\" was not injected: check your FXML file 'Scene.fxml'.";
        assert btnDreamTeam != null : "fx:id=\"btnDreamTeam\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtK != null : "fx:id=\"txtK\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtGoals != null : "fx:id=\"txtGoals\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'Scene.fxml'.";

    }
    
    public void setModel(Model model) {
    	this.model = model;
    }
}
