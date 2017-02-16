package esercizi;

public class Triangolo {

	private double base, altezza;

	public double getBase() {
		return base;
	}

	public void setBase(double base) {
		this.base = base;
	}

	public double getAltezza() {
		return altezza;
	}

	public void setAltezza(double altezza) {
		this.altezza = altezza;
	}

	public double ipotenusa() {

		return Math.sqrt((Math.pow(getBase(), 2) + (Math.pow(getAltezza(), 2))));
	}

	public double perimetro() {
		return getBase() + getAltezza() + ipotenusa();
	}

	public double area() {

		return ((getBase() * getAltezza()) / 2);

	}

}
