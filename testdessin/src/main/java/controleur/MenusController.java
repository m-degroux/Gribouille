package controleur;

import javax.imageio.ImageIO;
import javafx.scene.image.WritableImage;
import javafx.application.Platform;
import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXML;
import javafx.scene.control.ToggleGroup;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.ButtonBar;
import javafx.stage.FileChooser;
import testdessin.Dialogues;
import javafx.fxml.Initializable;
import javafx.scene.control.ButtonType;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.RadioMenuItem;
import javafx.scene.control.Toggle;

import java.io.File;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;


public class MenusController implements Initializable {
	@FXML private MenuBar menus;
	@FXML private RadioMenuItem itemCrayon;
	@FXML private RadioMenuItem itemEtoile;
	@FXML private ToggleGroup outils,epaisseur;
	@FXML private MenuItem menuCharger, menuSauvegarder, menuExporter, menuQuitter, menuEffacer ; 
	private Controller controleur;


	public void setControleur(Controller controleur) {
		this.controleur = controleur;
	}
	@FXML
	public void onQuitte() {
		menuQuitter();
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		itemCrayon.setToggleGroup(outils);
		itemEtoile.setToggleGroup(outils);
		itemCrayon.setSelected(true);

		outils.selectedToggleProperty().addListener((obs, oldVal, newVal) -> {
			if (newVal == itemCrayon) {
				controleur.onCrayon();
			} else if (newVal == itemEtoile) {
				controleur.onEtoile();
			}
		});

		if (!epaisseur.getToggles().isEmpty()) {
			epaisseur.selectToggle(epaisseur.getToggles().get(0));
		}
		epaisseur.selectedToggleProperty().addListener((obs, oldVal, newVal) -> {
			if (controleur == null) return;
			if (newVal != null) {
				int ep = parseEpaisseur(newVal);
				controleur.setEpaisseur(ep);
			}
		});
	}

	private int parseEpaisseur(Toggle toggle) {
		if (toggle instanceof RadioMenuItem) {
			String text = ((RadioMenuItem) toggle).getText();
			try {
				return Integer.parseInt(text.trim());
			} catch (NumberFormatException e) {
				return 1;
			}
		}
		return 1;
	}


	@FXML
	public boolean menuSauvegarder() {
		if (controleur == null || controleur.getDessin() == null) return false;

		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle("Sauvegarder le dessin");
		fileChooser.getExtensionFilters().addAll(
				new FileChooser.ExtensionFilter("Fichiers Dessin (*.dessin)", "*.dessin"),
				new FileChooser.ExtensionFilter("Tous les fichiers", "*.*")
				);

		File file = fileChooser.showSaveDialog(null);
		if (file != null) {
			try {
				controleur.getDessin().sauveSous(file.getAbsolutePath());
				return true;
			} catch (Exception e) {
				Dialogues.erreur("Erreur lors de la sauvegarde : " + e.getMessage());
			}
		}
		return false;
	}

	@FXML
	public void menuCharger() {
		if (controleur == null || controleur.getDessin() == null) return;

		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle("Charger un dessin");
		fileChooser.getExtensionFilters().addAll(
				new FileChooser.ExtensionFilter("Fichiers Dessin (*.dessin)", "*.dessin"),
				new FileChooser.ExtensionFilter("Tous les fichiers", "*.*")
				);

		File file = fileChooser.showOpenDialog(null);
		if (file != null) {
			try {
				controleur.charge(file);
			} catch (Exception e) {
				Dialogues.erreur("Erreur lors du chargement : " + e.getMessage());
			}
		}
	}

	@FXML
	public void menuQuitter() {
		if (controleur == null || controleur.getDessin() == null) {
	        Platform.exit();
	        return;
	    }

	    if (!controleur.getDessin().estModifieProperty().get()) {
	        Platform.exit();
	        return;
	    }

	    Optional<ButtonType> result = Dialogues.confirmationQuitter();
	    if (!result.isPresent()) {
	        return;
	    }

	    ButtonType choix = result.get();
	    ButtonBar.ButtonData data = choix.getButtonData();

	    if (data == ButtonBar.ButtonData.NO) {
	    	 Platform.exit();
	    } else if (data == ButtonBar.ButtonData.YES) {
	        boolean sauvegardeOK = menuSauvegarder();
	        if (sauvegardeOK) {
	            Platform.exit();
	        }
	    }
	}

	@FXML
	public void menuExporter() {
		if (controleur == null || controleur.getDessinCanvas() == null) return;
		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle("Exporter en PNG");
		fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Fichier PNG", "*.png"));
		File file = fileChooser.showSaveDialog(null);
		if (file != null) {
			try {
				WritableImage image = controleur.getDessinCanvas().snapshot(null, null);
				ImageIO.write(SwingFXUtils.fromFXImage(image, null), "png", file);
			} catch (Exception e) {
				Dialogues.erreur("Erreur lors de l'export : " + e.getMessage());
			}
		}
	}
	@FXML
	private void menuEffacer() {
		controleur.getDessin().effacerTout();
	    GraphicsContext gc = controleur.getDessinCanvas().getGraphicsContext2D();
	    gc.clearRect(0, 0, controleur.getDessinCanvas().getWidth(), controleur.getDessinCanvas().getHeight());
	}

}

