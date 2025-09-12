package outil;

import controleur.Controller;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;
import model.Trace;

public class OutilCrayon extends Outil {

    public OutilCrayon(Controller controleur) {
        super(controleur);
    }

    @Override
    public void onMousePress(MouseEvent evt) {
        figure = new Trace(
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
        Trace trace = (Trace) figure;
        trace.addPoint(evt.getX(), evt.getY());
        GraphicsContext gc = controleur.getDessinController()
                                       .getCanvas()
                                       .getGraphicsContext2D();

        gc.strokeLine(
            controleur.getPrevX(), controleur.getPrevY(),
            evt.getX(), evt.getY()
        );

        controleur.setPrev(evt.getX(), evt.getY());
    }
}