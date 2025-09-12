module iut.gon.testdessin {
    requires transitive javafx.controls;
    requires javafx.fxml;
	requires transitive javafx.graphics;
	requires java.desktop;
	requires javafx.swing;
	
	requires javafx.base;

	opens controleur to javafx.fxml;
    exports controleur;

    opens testdessin to javafx.fxml;
    exports testdessin;

    exports model;
    exports outil;
}
