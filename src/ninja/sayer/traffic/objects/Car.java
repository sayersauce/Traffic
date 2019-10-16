package ninja.sayer.traffic.objects;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class Car {
	protected double x;
	protected double y;
	protected int w = 40;
	protected int h = 32;
	protected int velocity = 3;
	protected int angle = 0;
	protected Image image = new Image("car.png");
	
	public Car(int x, int y, int a) {
		this.x = x;
		this.y = y;
		this.angle = a;
	}
	
	public void draw(GraphicsContext gc) {
		gc.save();
		gc.translate(x + (w/2), y + (h/2));
		gc.rotate(angle);
		gc.translate(-(x + w/2), -(y + h/2));
		gc.drawImage(image, x, y);
		gc.restore();
	}
	
	public void drive() {
		x += Math.cos(Math.toRadians(angle)) * velocity;
		y += Math.sin(Math.toRadians(angle)) * velocity;
	}
	
	public void setVelocity(int v) {
		this.velocity = v;
	}
	
	public void setX(int x) {
		this.x = x;
	}
	
	public void setY(int y) {
		this.y = y;
	}
	
	public double getX() {
		return this.x;
	}
	
	public double getY() {
		return this.y;
	}

}
