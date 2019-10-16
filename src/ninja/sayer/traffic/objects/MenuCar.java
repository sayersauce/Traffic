package ninja.sayer.traffic.objects;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

import java.util.Random;

public class MenuCar extends Car{
	
	private int minY;
	private int maxY;

	public MenuCar(int x, int y, int a, int minY, int maxY) {
		super(x, y, a);
		Random rand = new Random();
		setVelocity(rand.nextInt(6) + 5);
		this.minY = minY;
		this.maxY = maxY;
		w = 120;
		h = 96;
		image = new Image("menucar.png");
	}
	
	public void draw(GraphicsContext gc) {
		gc.save();
		gc.translate(x + w/2, y + h/2);
		gc.rotate(angle);
		gc.translate(-(x + w/2), -(y + h/2));
		gc.drawImage(image, x, y);
		gc.restore();
		checkEdge();
	}
	
	public void checkEdge() {
		if(y > maxY) {
			setY(-100);
		}
		if(y < minY) {
			setY(maxY - 60);
		}
	}
	
}
