package controleur;

import java.io.File;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import model.Dessin;
import model.Etoile;
import model.Figure;
import model.Point;
import model.Trace;
import outil.Outil;
import outil.OutilCrayon;
import outil.OutilEtoile;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.canvas.Canvas;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;

public class Controller implements Initializable {

	@FXML
	private MenusController menusController;
	@FXML
	private DessinController dessinController;
	@FXML
	private CouleurController couleursController;
	@FXML
	private StatutController statutController;

	private final Dessin dessin = new Dessin();
	private SimpleDoubleProperty prevX = new SimpleDoubleProperty();
	private SimpleDoubleProperty prevY = new SimpleDoubleProperty();
	private SimpleIntegerProperty epaisseur = new SimpleIntegerProperty(2);
	private SimpleObjectProperty<Color> couleur = new SimpleObjectProperty<Color>(Color.BLACK);
	private Outil outilActif = new OutilCrayon(this);
	private Trace trace;

	public void setOutilActif(Outil outil) {
		this.outilActif = outil;
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		menusController.setControleur(this);
		dessinController.setControleur(this);
		couleursController.setControleur(this);
		statutController.setControleur(this);
		
		statutController.afterInit();
	}


	public void onMousePressed(MouseEvent evt) {
		System.out.println(dessin.getFigures());
		prevX.set(evt.getX());
		prevY.set(evt.getY());

		if (outilActif != null) {
			outilActif.onMousePress(evt);
		}
	}

	public void onMouseDragged(MouseEvent evt) {
		if (outilActif != null) {
			outilActif.onMouseDrag(evt);
		}
		prevX.set(evt.getX());
		prevY.set(evt.getY());
	}

	public void dessine() {
		for (Figure figure : dessin.getFigures()) {
			setCouleur(Color.web(figure.getCouleur()));
			setEpaisseur(figure.getEpaisseur());
			if (figure instanceof Trace) {
				Trace trace = (Trace) figure;
				List<Point> points = trace.getPoints();
				if (points != null && points.size() > 1) {
					for (int i = 0; i < points.size() - 1; i++) {
						Point p1 = points.get(i);
						Point p2 = points.get(i + 1);
						dessinController.trace(p1.getX(), p1.getY(), p2.getX(), p2.getY());
					}
				}
			} else if (figure instanceof Etoile) {
				Etoile etoile = (Etoile) figure;
				Point centre = etoile.getCentre();
				List<Point> points = etoile.getPoints();
				if (centre != null && points != null) {
					for (Point p : points) {
						if (p != null && !p.equals(centre)) {
							dessinController.trace(centre.getX(), centre.getY(), p.getX(), p.getY());
						}
					}
				}
			}
		}
	}

	public void onCrayon() {
		setOutilActif(new OutilCrayon(this));
		statutController.labelOutil.setText("Crayon");
	}

	public MenusController getMenusController() {
		return menusController;
	}

	public CouleurController getCouleurController() {
		return couleursController;
	}

	public StatutController getStatutController() {
		return statutController;
	}

	public DessinController getDessinController() { 
		return dessinController; 
	}
	
	public Trace getTrace() {
		return trace;
	}

	public void setTrace(Trace trace) {
		this.trace = trace;
	}

	public Outil getOutilActif() {
		return outilActif;
	}

	public void setPrevX(double prevX) {
		this.prevX.set(prevX);;
	}

	public void setPrevY(double prevY) {
		this.prevY.set(prevY);;
	}

	public void setEpaisseur(int epaisseur) {
		this.epaisseur.set(epaisseur);
		dessinController.setEpaisseur(epaisseur);
	}

	public void setCouleur(Color couleur) {
		this.couleur.set(couleur);
		dessinController.setCouleur(couleur);
	}

	public void setPrev(double x, double y) {
		prevX.set(x);
		prevY.set(y);
	}

	public void onEtoile() {
		setOutilActif(new OutilEtoile(this));
		statutController.labelOutil.setText("Etoile");
	}

	public double getPrevX() { 
		return prevX.get(); 
	}

	public double getPrevY() { 
		return prevY.get(); 
	}

	public Color getCouleur() { 
		return couleur.get(); 
	}

	public int getEpaisseur() { 
		return epaisseur.get(); 
	}

	public Dessin getDessin() { 
		return dessin; 
	}
	
	public SimpleDoubleProperty prevXProperty() {
		return prevX;
	}
	
	public SimpleDoubleProperty prevYProperty() {
		return prevY;
	}

	public SimpleObjectProperty<Color> getCouleurProperty() {
		return couleur;
	}

	public SimpleIntegerProperty getEpaisseurProperty() {
		return epaisseur;
	}

	public void onKeyPressed(String keyText) {
		if ("1".equals(keyText)) {
			onCrayon();
		} 
		if ("2".equals(keyText)) {
			onEtoile();
		} 
		if ("r".equals(keyText)) {
			couleursController.onChoixCouleur(couleursController.rectRouge);;
		} 
		if ("b".equals(keyText)) {
			couleursController.onChoixCouleur(couleursController.rectBleu);
		} 
		if ("v".equals(keyText)) {
			couleursController.onChoixCouleur(couleursController.rectVert);
		} 
		if ("j".equals(keyText)) {
			couleursController.onChoixCouleur(couleursController.rectJaune);
		} 
		if ("n".equals(keyText)) {
			couleursController.onChoixCouleur(couleursController.rectNoir);
		} 
		if ("w".equals(keyText)) {
			couleursController.onChoixCouleur(couleursController.rectBlanc);
		} 
		if ("p".equals(keyText)) {
			couleursController.onChoixCouleur(couleursController.rectRose);
		} 
		if ("c".equals(keyText)) {
			couleursController.onChoixCouleur(couleursController.rectCyan);
		} 

		if ("+".equals(keyText)) {
			epaisseur.set(epaisseur.get() + 1);
		} 
		if ("-".equals(keyText)) {
			int ep = epaisseur.get();
			if (ep > 1) epaisseur.set(ep - 1);
		}
		if (outilActif != null && outilActif.getFigure() != null) {
			Color c = couleur.get();
			String couleurString = toHexString(c);
			outilActif.getFigure().changeCouleur(couleurString);
			outilActif.getFigure().changeEpaisseur(epaisseur.get());
		}
	}

	public static String toHexString(Color color) {
		int r = (int) (color.getRed() * 255);
		int g = (int) (color.getGreen() * 255);
		int b = (int) (color.getBlue() * 255);
		return String.format("#%02X%02X%02X", r, g, b);
	}

	public Canvas getDessinCanvas() {
		return dessinController.getCanvas();
	}
	public void charge(File file) {
	    if (file != null && dessin != null) {
	        dessin.charge(file.getAbsolutePath());
	        dessinController.efface();
	        dessine();                     
	    }
	}

}