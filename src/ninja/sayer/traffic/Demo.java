package ninja.sayer.traffic;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javafx.animation.AnimationTimer;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import ninja.sayer.traffic.objects.DemoCar;
import ninja.sayer.traffic.objects.Light;

public class Demo {
	
	// INGRESS
	private int AIN;
	private int BIN;
	private int CIN;
	private int DIN;
	
	// EGRESS
	private int AOUT;
	private int BOUT;
	private int COUT;
	private int DOUT;
	
	// OTHER CONSTANTS
	private double SPAWNRATE;
	private int SPEED;
	
	private GraphicsContext gc;
	private Image intersection = new Image("intersection.png");
	private int carTimer = 0;
	private Random random = new Random();
	private List<Light> lights = new ArrayList<Light>();
	private List<DemoCar> cars = new ArrayList<DemoCar>();
	
	public Demo(Canvas c, int w, int h, int[] values) {
		this.AIN = values[0];
		this.AOUT = values[1];
		this.BIN = values[2];
		this.BOUT = values[3];
		this.CIN = values[4];
		this.COUT = values[5];
		this.DIN = values[6];
		this.DOUT = values[7];
		this.SPAWNRATE = values[8];
		this.SPEED = values[9] + 2;
		
		this.gc = c.getGraphicsContext2D();
		addLights();
		
		long startTime = System.nanoTime();
		new AnimationTimer()
	    {
	        public void handle(long currentNanoTime)
	        {
	        	long timeElapsed = (currentNanoTime - startTime)/1000000000;
	        	removeCarsWaiting();
	        	// Make a new car every second.
	        	if(timeElapsed % SPAWNRATE == 0 && carTimer != timeElapsed / SPAWNRATE) {
	        		newCar();
	        		carTimer = (int) (timeElapsed / SPAWNRATE);
	        	}
	        	gc.drawImage(intersection, 0, 0);
	        	manageCars();
	        	handleLights(timeElapsed);
	        	drawLights();
	        }
	    }.start();
	}
	
	// Light Handling.
	
	private void addLights() {
		lights.add(new Light(485, 335, 90, 400, "a"));
		lights.add(new Light(695, 310, 195, 285, "b"));
		lights.add(new Light(740, 475, 240, 705, "c"));
		lights.add(new Light(530, 540, -15, 510, "d"));
	}
	
	private void drawLights() {
		for(int i = 0; i < lights.size(); i++) {
			lights.get(i).draw(gc);
		}
	}
	
	// Car Handling.
	
	private void removeCarsWaiting() {
		for(int i = 0; i < lights.size(); i++) {
			lights.get(i).setCarsWaiting(0);
		}
	}
	
	private void newCar() {
		int randomRoadInt = random.nextInt(AIN + BIN + CIN + DIN);
		if(randomRoadInt < AIN) {
			newCarA();
		} else if (randomRoadInt < AIN + BIN) {
			newCarB();
		} else if (randomRoadInt < AIN + BIN + CIN) {
			newCarC();
		} else {
			newCarD();
		}
	}
	
	private void newCarA() {
		int randomRoadInt = random.nextInt(BOUT + COUT + DOUT);
		if(randomRoadInt < BOUT) {
			// B
			cars.add(new DemoCar(-100, 370, 0, -75, 500, lights.get(0), SPEED));
		} else if (randomRoadInt < BOUT + COUT) {
			// C
			cars.add(new DemoCar(-100, 370, 0, -30, 635, lights.get(0), SPEED));
		} else {
			// D
			cars.add(new DemoCar(-100, 370, 0, 75, 540, lights.get(0), SPEED));
		}
	}
	
	private void newCarB() {
		int randomRoadInt = random.nextInt(AOUT + COUT + DOUT);
		if(randomRoadInt < AOUT) {
			// A
			cars.add(new DemoCar(730, -100, 105, 75, 380, lights.get(1), SPEED));
		} else if (randomRoadInt < AOUT + COUT) {
			// C
			cars.add(new DemoCar(730, -100, 105, -135, 275, lights.get(1), SPEED));
		} else {
			// D
			cars.add(new DemoCar(730, -100, 105, -30, 390, lights.get(1), SPEED));
		}
	}
	
	private void newCarC() {
		int randomRoadInt = random.nextInt(AOUT + BOUT + DOUT);
		if(randomRoadInt < AOUT) {
			// A
			cars.add(new DemoCar(1300, 65, 150, 30, 695, lights.get(2), SPEED));
		} else if (randomRoadInt < AOUT + BOUT) {
			// B
			cars.add(new DemoCar(1300, 65, 150, 135, 625, lights.get(2), SPEED));
		} else {
			// D
			cars.add(new DemoCar(1300, 65, 150, -75, 650, lights.get(2), SPEED));
		}
	}
	
	private void newCarD() {
		int randomRoadInt = random.nextInt(AOUT + BOUT + COUT);
		if(randomRoadInt < AOUT) {
			// A
			cars.add(new DemoCar(670, 900, 255, -75, 465, lights.get(3), SPEED));
		} else if (randomRoadInt < AOUT + BOUT) {
			// B
			cars.add(new DemoCar(670, 900, 255, 30, 420, lights.get(3), SPEED));
		} else {
			// C
			cars.add(new DemoCar(670, 900, 255, 75, 475, lights.get(3), SPEED));
		}
	}
	
	private void manageCars() {
		for(int i = 0; i < cars.size(); i++) {
			DemoCar c = cars.get(i);
			c.drive();
			c.draw(gc);
			if(c.getX() < -200 || c.getX() > 1400 || c.getY() < -200 || c.getY() > 1000) {
				cars.remove(i);
			}
		}
	}
	
	// Light Logic.
	
	private void handleLights(long timeElapsed) {
		Light a = lights.get(0);
		Light b = lights.get(1);
		Light c = lights.get(2);
		Light d = lights.get(3);
		
		double total = (AIN + BIN + CIN + DIN)/10 + 3;
		float at = AIN/10;
		float bt = BIN/10;
		float ct = CIN/10;
		
		while(timeElapsed > total) {
			timeElapsed -= total;
		}
				
		if(timeElapsed <= at) {
			a.changeColour("green");
		} else {
			a.changeColour("red");
		}
		
		if(timeElapsed > at + 1 && timeElapsed <= at + bt + 1) {
			b.changeColour("green");
		} else {
			b.changeColour("red");
		}
		
		if(timeElapsed > at + bt + 2 && timeElapsed <= at + bt + ct + 2) {
			c.changeColour("green");
		} else {
			c.changeColour("red");
		}
		
		if(timeElapsed > at + bt + ct + 3) {
			d.changeColour("green");
		} else {
			d.changeColour("red");
		}
	}
	
}
