package testdessin;

import java.util.Optional;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;

public class Dialogues {

	public static boolean confirmation() {
   	 Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
	     alert.setTitle("Confirmation");
	     alert.setHeaderText("Vous avez quitter le programme");
	     alert.setContentText("Choisissez une option.");
	     Optional<ButtonType> result = alert.showAndWait();
	     return result.isPresent() && result.get() == ButtonType.OK;
	}
	
	public static Optional<ButtonType> confirmationQuitter() {
        ButtonType quitterSansSauvegarder = new ButtonType("Quitter sans sauvegarder", ButtonData.NO);
        ButtonType sauvegarderEtQuitter = new ButtonType("Sauvegarder et quitter", ButtonData.YES);
        ButtonType annuler = new ButtonType("Annuler", ButtonData.CANCEL_CLOSE);

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation de fermeture");
        alert.setHeaderText("Le dessin a été modifié.");
        alert.setContentText("Que souhaitez-vous faire ?");
        
        // On remplace les boutons par nos propres boutons
        alert.getButtonTypes().setAll(quitterSansSauvegarder, sauvegarderEtQuitter, annuler);

        return alert.showAndWait();
    }

	public static void erreur(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Erreur");
        alert.setHeaderText("Une erreur est survenue");
        alert.setContentText(message);
        alert.showAndWait();
    }
}
