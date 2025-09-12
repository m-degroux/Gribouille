package outil;


import controleur.Controller;
import javafx.scene.input.MouseEvent;
import model.Etoile;
import model.Point;

public class OutilEtoile extends Outil {

    public OutilEtoile(Controller controleur) {
        super(controleur);
    }

    @Override
    public void onMousePress(MouseEvent evt) {
    	figure = new Etoile(
            controleur.getEpaisseur(),
            controleur.getCouleurProperty().get().toString(),
            evt.getX(),
            evt.getY()
        );
    	
        controleur.getDessin().addFigure(figure);
        controleur.setPrev(evt.getX(), evt.getY());
    	controleur.setEpaisseur(controleur.getEpaisseurProperty().get());
    	controleur.setCouleur(controleur.getCouleurProperty().get());
    }

    @Override
    public void onMouseDrag(MouseEvent evt) {
        if (figure != null) {
        	Etoile etoile = (Etoile) figure;
        	etoile.addPoint(new Point(evt.getX(), evt.getY()));
        	controleur.getDessinController().trace(etoile.getCentre().getX(), etoile.getCentre().getY(),
        			evt.getX(), evt.getY());
        }
    }
}
