package controleur;

import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;


public class DessinController {

    private Controller controleur;

    @FXML public Pane pane;
    @FXML public Canvas canvas;

    public void setControleur(Controller controleur) {
        this.controleur = controleur;
    }

    @FXML
    private void initialize() {
        canvas.widthProperty().bind(pane.widthProperty());
        canvas.heightProperty().bind(pane.heightProperty());
        
        canvas.setOnMousePressed(this::onMousePressed);
        canvas.setOnMouseDragged(this::onMouseDragged);
        pane.setFocusTraversable(true);
        pane.requestFocus();
        
        canvas.widthProperty().addListener(evt -> redessinerTout());
        canvas.heightProperty().addListener(evt -> redessinerTout());
    }

    @FXML
    public void onMousePressed(MouseEvent event) {
        controleur.onMousePressed(event);
    }

    @FXML
    public void onMouseDragged(MouseEvent event) {
        controleur.onMouseDragged(event);
    }
        
    @FXML
    public void onKeyPressed(KeyEvent event) {
        String key = event.getText().toLowerCase();
        controleur.onKeyPressed(key);
    }

    public void efface() {
        canvas.getGraphicsContext2D().clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
    }

    public void trace(double x1, double y1, double x2, double y2) {
    	canvas.getGraphicsContext2D().strokeLine(x1, y1, x2, y2);
    }
    
    public void redessinerTout() {
        efface();
        controleur.dessine();
    }
    
    public Canvas getCanvas() {
        return canvas;
    }
    
    public GraphicsContext getGraphicsContext() {
        return canvas.getGraphicsContext2D();
    }
    
    public void setEpaisseur(int e) {
    	canvas.getGraphicsContext2D().setLineWidth(e);
    }
    
    public void setCouleur(Color c) {
    	canvas.getGraphicsContext2D().setStroke(c);
    }
}
