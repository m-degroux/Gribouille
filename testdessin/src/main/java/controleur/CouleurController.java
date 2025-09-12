package controleur;

import javafx.fxml.FXML;
import javafx.scene.control.ColorPicker;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class CouleurController {

    private Controller controleur;
    
    @FXML public VBox vbox;
    @FXML public ColorPicker colorPicker;
    @FXML public Rectangle rectRouge, rectBleu, rectVert, rectCyan, rectRose, rectJaune, rectNoir, rectBlanc;
    private Rectangle rectangleSelectionne = null;
    
    public void setControleur(Controller controleur) {
    	this.controleur = controleur;

        colorPicker.valueProperty().bindBidirectional(controleur.getCouleurProperty());
        
        ajouterGestionRectangle(rectRouge);
        ajouterGestionRectangle(rectBleu);
        ajouterGestionRectangle(rectVert);
        ajouterGestionRectangle(rectCyan);
        ajouterGestionRectangle(rectRose);
        ajouterGestionRectangle(rectJaune);
        ajouterGestionRectangle(rectNoir);
        ajouterGestionRectangle(rectBlanc);
        onChoixCouleur(rectNoir);
    }

    @FXML
    private void initialize() {
    }
    
    private void selecRectangle(Rectangle r) {
        r.setStroke(Color.BLACK);
        r.setStrokeWidth(3);
        r.setArcWidth(15);
        r.setArcHeight(15);
    }

    private void deselectRectangle(Rectangle r) {
        r.setStroke(null);
        r.setStrokeWidth(0);
        r.setArcWidth(0);
        r.setArcHeight(0);
    }
    
    public void onChoixCouleur(Rectangle r) {
    	if (rectangleSelectionne == r) return;
        controleur.getCouleurProperty().set((Color) r.getFill());

        if (rectangleSelectionne != null) {
            deselectRectangle(rectangleSelectionne);
        }
        selecRectangle(r);
        rectangleSelectionne = r;
    }
    
    private void ajouterGestionRectangle(Rectangle r) {
        r.setOnMouseClicked(e -> onChoixCouleur(r));
    }
    

}
