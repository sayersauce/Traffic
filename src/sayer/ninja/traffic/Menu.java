package sayer.ninja.traffic;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javafx.animation.AnimationTimer;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import sayer.ninja.traffic.objects.MenuCar;

public class Menu {

	private GraphicsContext gc;
	private AnimationTimer animation;
	private int h;
	
	private Image bg = new Image("bg.png");
	private Image title = new Image("title.png");
	private Image mcdonalds = new Image("mcdonalds.png");
	private Image start = new Image("start.png");
	
	private String[] types = {"A In", "A Out", "B In", "B Out", "C In", "C Out", "D In", "D out", "Spawn Rate"};
	private int[] values = {75, 10, 10, 40, 10, 30, 5, 20, 2};
	private int selected = 0;
	
	private List<MenuCar> cars = new ArrayList<MenuCar>();   

	public Menu(Stage stage, Canvas canvas, int w, int h) {
		this.gc = canvas.getGraphicsContext2D();
		this.h = h;
		
		addCars();
		
		// Menu loop.
		animation = new AnimationTimer()
	    {
	        public void handle(long currentNanoTime)
	        {
	        	gc.drawImage(bg, 0, 0);
	            drawCars();
	            drawTitle();
	            drawMenu();
	        }
	    };
	    animation.start();
	    
	    // Key Handling.
	    stage.getScene().addEventHandler(KeyEvent.KEY_PRESSED, (key) -> {
	        if(key.getCode()==KeyCode.ENTER) {
	            stopMenu();
	            Group root = new Group();
	    		Scene demoScene = new Scene(root, Color.WHITE);
	    		stage.setScene(demoScene);
	    		Canvas c = new Canvas(1200, 800);
	    		root.getChildren().add(c);
	    		new Demo(c, w, h, values);
	        } else if (key.getCode()==KeyCode.UP) {
	        	if(selected == 0) {
	        		selected = values.length - 1;
	        	} else {
	        		selected--;
	        	}
	        } else if (key.getCode()==KeyCode.DOWN) {
	        	if(selected == values.length - 1) {
	        		selected = 0;
	        	} else {
	        		selected++;
	        	}
	        } else if (key.getCode()==KeyCode.RIGHT) {
	        	if(selected == 8 && values[selected] < 5) {
	        		values[selected]++;
	        	} else if (values[selected] < 100 && selected != 8) {
	        		values[selected] += 5;
	        	}
	        } else if (key.getCode()==KeyCode.LEFT) {
	        	if(selected == 8 && values[selected] > 1) {
	        		values[selected]--;
	        	} else if (values[selected] > 0 && selected != 8) {
	        		values[selected] -= 5;
	        	}
	        }
	  });
	}
	
	private void drawTitle() {
		gc.drawImage(title, 400, 100);
		gc.drawImage(mcdonalds, 450, 230);
	}
	
	private void drawMenu() {
		int fontSize = 20;
		gc.drawImage(start, 475, 300);
		gc.setTextAlign(TextAlignment.CENTER);
		gc.setFont(new Font("Verdana Bold", fontSize));
		for(int i = 0; i < values.length; i++) {
			if(i == selected) {
				gc.setFill(Color.RED);
			} else {
				gc.setFill(Color.GOLD);
			}
			gc.fillText(types[i] + " : " + values[i], 600, 400 + (i*fontSize));
		}
	}
	
	private void addCars() {
		Random rand = new Random();
		int noCars = 8;
		int[] angles = {90, -90};
		for(int i = 0; i <= noCars; i++) {
			cars.add(new MenuCar(27 + 150 * i, 300, angles[rand.nextInt(angles.length)], -200, h+200));
		}
	}
	
	private void drawCars() {
		for(int i = 0; i < cars.size(); i++) {
			cars.get(i).drive();
			cars.get(i).draw(gc);
		}
	}
	
	private void stopMenu() {
		animation.stop();
	}
	
}
