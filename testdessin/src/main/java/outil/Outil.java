package outil;

import controleur.Controller;
import javafx.scene.input.MouseEvent;
import model.Figure;

public abstract  class Outil {
	protected Controller controleur;
	protected Figure figure;

    public Outil(Controller controleur) {
        this.controleur = controleur;
    }
    public Figure getFigure() {
        return figure;
    }
	public abstract void onMousePress(MouseEvent evt);
    public abstract void onMouseDrag(MouseEvent evt);
}
