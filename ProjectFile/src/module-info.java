module Project_3 {
	requires javafx.controls;
	requires javafx.graphics;
	requires javafx.base;
	requires java.desktop;
	
	opens A_RunApplication to javafx.graphics, javafx.fxml;
}
