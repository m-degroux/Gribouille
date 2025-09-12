package testdessin;

import javafx.application.Application;
import javafx.beans.binding.Bindings;
import javafx.fxml.FXMLLoader;

import javafx.scene.Parent;
import javafx.scene.Scene;

import javafx.stage.Stage;
import javafx.util.Pair;


import java.io.IOException;

import controleur.Controller;

/**
 * JavaFX App
 */
public class App extends Application {

    private static Scene scene;
    
    @Override
    public void start(Stage stage) throws IOException {
        Pair<Parent, Controller> chargement = loadFXML("CadreGribouille");
        Controller controleur = chargement.getValue();
        Parent root = chargement.getKey();
        scene = new Scene(root, 1280, 720);
        stage.setScene(scene);
        stage.show();
        
        stage.setOnCloseRequest(event -> {
        	controleur.getMenusController().onQuitte();
            event.consume();
        });
        
        scene.setOnKeyPressed(event -> {
            String keyText = event.getText().toUpperCase();
            controleur.onKeyPressed(keyText);
        });
        
        stage.titleProperty().bind(
        	    Bindings.concat(
        	        Bindings.when(controleur.getDessin().estModifieProperty())
        	            .then("*")
        	            .otherwise(""),
        	       controleur.getDessin().nomDuFichierProperty()
        	    )
        	);
        
    }
    
    private static Pair<Parent, Controller> loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
        Controller controleur = new Controller();
        fxmlLoader.setController(controleur);
        Parent root = fxmlLoader.load();
        return new Pair<>(root, controleur);
    }
    static void setRoot(String fxml) throws IOException {
        scene.setRoot(loadFXML(fxml).getKey());
    }

    public static void main(String[] args) {
        launch();
    }

}