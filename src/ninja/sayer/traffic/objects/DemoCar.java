package ninja.sayer.traffic.objects;

import javafx.scene.canvas.GraphicsContext;

public class DemoCar extends Car {

	private boolean turning = false;
	private int finalAngle;
	private boolean positiveTurn = false;
	private int turnPoint;
	private Light light;

	public DemoCar(int x, int y, int a, int a2, int tp, Light l, int speed) {
		super(x, y, a);
		setVelocity(speed);
		this.finalAngle = angle + a2;
		this.turnPoint = tp;
		this.light = l;
		if (a2 > 0) {
			this.positiveTurn = true;
		}
	}
	
	public void draw(GraphicsContext gc) {
		gc.save();
		gc.translate(x + w/2, y + h/2);
		gc.rotate(angle);
		gc.translate(-(x + w/2), -(y + h/2));
		gc.drawImage(image, x, y);
		gc.restore();
		turn();
	}
	
	public void drive() {
		// Check if car is in stopping range, and if the light is red. If turning, already passed red so can ignore.
		int stoppingDistance = 60;
		if(light.isRed() && !turning) {
			if(light.isA()) {
				if(x < light.getStopPoint()) {
					light.setCarsWaiting(light.getCarsWaiting() + 1);
				}
				int stopPoint = light.getStopPoint() - light.getCarsWaiting() * stoppingDistance;
				if(x < stopPoint + 3 && x > stopPoint - 3) {
					return;
				}
			} else if (light.isB()) {
				if(y < light.getStopPoint()) {
					light.setCarsWaiting(light.getCarsWaiting() + 1);
				}
				int stopPoint = light.getStopPoint() - light.getCarsWaiting() * stoppingDistance;
				if(y < stopPoint + 3 && y > stopPoint - 3) {
					return;
				}
			} else if (light.isC()) {
				if(x > light.getStopPoint()) {
					light.setCarsWaiting(light.getCarsWaiting() + 1);
				}
				int stopPoint = light.getStopPoint() + light.getCarsWaiting() * stoppingDistance;
				if(x < stopPoint + 3 && x > stopPoint - 3) {
					return;
				}
			} else if (light.isD()) {
				if(y > light.getStopPoint()) {
					light.setCarsWaiting(light.getCarsWaiting() + 1);
				}
				int stopPoint = light.getStopPoint() + light.getCarsWaiting() * stoppingDistance;
				if(y < stopPoint + 3 && y > stopPoint - 3) {
					return;
				}
			}
		}
		// Otherwise continue to drive;
		x += Math.cos(Math.toRadians(angle)) * velocity;
		y += Math.sin(Math.toRadians(angle)) * velocity;
	}
	
	private void turn() {
		if(light.isA() && x >= turnPoint) {
			turning = true;
		} else if (light.isB() && y >= turnPoint) {
			turning = true;
		} else if (light.isC() && x <= turnPoint) {
			turning = true;
		} else if (light.isD() && y <= turnPoint) {
			turning = true;
		}
		if(turning && positiveTurn) {
			if(angle < finalAngle) {
				angle += velocity;
			} else {
				angle = finalAngle;
			}
		} else if (turning && !positiveTurn) {
			if(angle > finalAngle) {
				angle -= velocity;
			} else {
				angle = finalAngle;
			}
		}
	}
	
	public Light getLight() {
		return this.light;
	}

}
