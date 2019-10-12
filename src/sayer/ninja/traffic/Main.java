package sayer.ninja.traffic;
	
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;


public class Main extends Application {
	
	final static int WIDTH = 1200;
	final static int HEIGHT = 800;
	
	@Override
	public void start(Stage primaryStage) {
		primaryStage.setTitle("Traffic");
		primaryStage.setWidth(WIDTH);
		primaryStage.setHeight(HEIGHT);
		primaryStage.setResizable(false);
		primaryStage.getIcons().add(new Image("car.png"));


		Group root = new Group();
		Scene menuScene = new Scene(root, Color.WHITE);
		primaryStage.setScene(menuScene);
		
		Canvas canvas = new Canvas(1200, 800);
		root.getChildren().add(canvas);
		new Menu(primaryStage, canvas, WIDTH, HEIGHT);
		 
		primaryStage.show();
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
