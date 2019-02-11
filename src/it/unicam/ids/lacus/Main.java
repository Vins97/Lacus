package it.unicam.ids.lacus;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class Main extends Application {
	private double x,y;
	private static Stage primaryStage;

	@Override
	public void start(Stage primaryStage) throws Exception{
		Parent root = FXMLLoader.load(getClass().getResource("view/Home.fxml"));
		primaryStage.setScene(new Scene(root));
		primaryStage.initStyle(StageStyle.UNDECORATED);
		primaryStage.setTitle("Lacus");
		this.primaryStage = primaryStage;

		//fa traslare la finestra senza ridimensionarla
		root.setOnMousePressed(event -> {
			x = event.getSceneX();
			y = event.getSceneY();
		});
		root.setOnMouseDragged(event -> {
			primaryStage.setX(event.getScreenX()-x);
			primaryStage.setY(event.getScreenY()-y);
		});
		primaryStage.show();
	}

	public void minimize() {
		primaryStage.setIconified(true);
	}

	public static void main(String[] args) {
		launch(args);
	}
}