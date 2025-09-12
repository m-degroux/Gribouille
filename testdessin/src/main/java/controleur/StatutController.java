package controleur;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;

public class StatutController {

    private Controller controleur;
    @FXML public GridPane gridpane;
    @FXML public Label labelX, labelY, labelEpaisseur, labelOutil, labelCouleur;

    public void setControleur(Controller controleur) {
        this.controleur = controleur;
        labelX.setText("0");
        labelY.setText("0");
        labelEpaisseur.setText("" + controleur.getEpaisseur());
        labelOutil.setText("Crayon");
        labelCouleur.setText(controleur.getCouleur().toString());
    }
    @FXML
    private void initialize() {
    }
    
    public void afterInit() {
		labelX.textProperty().bind(controleur.prevXProperty().asString("%.2f"));
		labelY.textProperty().bind(controleur.prevYProperty().asString("%.2f"));
		labelEpaisseur.textProperty().bind(controleur.getEpaisseurProperty().asString());
		labelCouleur.textProperty().bind(controleur.getCouleurProperty().asString());
    }
}
