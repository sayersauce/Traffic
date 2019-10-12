package sayer.ninja.traffic.objects;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class Light {
	
	private int x;
	private int y;
	private int stopPoint;
	private int carsWaiting = 0;
	private String direction;
	private int angle;
	private String colour = "red";
	private Image image = new Image("redlight.png");
	
	public Light(int x, int y, int a, int sp, String direction) {
		this.x = x;
		this.y = y;
		this.angle = a;
		this.stopPoint = sp;
		this.direction = direction;
	}
	
	public void draw(GraphicsContext gc) {
		gc.save();
		gc.translate(x, y);
		gc.rotate(angle);
		gc.translate(-x, -y);
		gc.drawImage(image, x, y);
		gc.restore();
	}
	
	public void changeColour(String col) {
		image = new Image(col + "light.png");
		this.colour = col;
	}
	
	public void setCarsWaiting(int i) {
		this.carsWaiting = i;
	}
	
	public int getCarsWaiting() {
		return this.carsWaiting;
	}
	
	public boolean isRed() {
		if(this.colour.equals("red")) return true;
		return false;
	}
	
	public int getStopPoint() {
		return this.stopPoint;
	}
	
	public boolean isA() {
		if (direction.equals("a")) return true;
		return false;
	}
	
	public boolean isB() {
		if (direction.equals("b")) return true;
		return false;
	}
	
	public boolean isC() {
		if (direction.equals("c")) return true;
		return false;
	}
	
	public boolean isD() {
		if (direction.equals("d")) return true;
		return false;
	}

}
