package esercizi;

public class Punto {

	private double x, y;

	public Punto(double x, double y) {
		setX(x);
		setY(y);
	}

	public double getX() {
		return x;
	}

	public void setX(double x) {
		this.x = x;
	}

	public double getY() {
		return y;
	}

	public void setY(double y) {
		this.y = y;
	}

	public double distanza(Punto xy) {

		return Math.sqrt((Math.pow((xy.getX() - x), 2)) + (Math.pow((xy.getY() - y), 2)));
	}

}
