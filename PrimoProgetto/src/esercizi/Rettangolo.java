package esercizi;

public class Rettangolo {

	private double base, altezza;

	public Rettangolo(double base, double altezza) {
		this.base = base;
		this.altezza = altezza;
	}

	/**
	 * Returns a double value that is the perimeter of a Rectangle Object.
	 * 
	 * @return the perimenter of the Rectangle
	 */
	public double perimetro() {
		return 2 * (getBase() + getAltezza());
	}

	/**
	 * Returns a double value that is the area of a Rectangle Object.
	 * 
	 * @return the area of the Rectangle
	 */
	public double area() {
		return getBase() * getAltezza();
	}

	/**
	 * Returns a boolean value after the control of a Rectangle if it is a
	 * Square.
	 * 
	 * @return the perimenter of the Rectangle
	 */
	public boolean isSquare() {
		return getAltezza() == getBase();
	}

	public boolean maggioreArea(Rettangolo b) {
		return area() > b.area();
	}

	public boolean maggioreBase(Rettangolo b) {
		return getBase() > b.getBase();
	}

	public boolean maggioreAltezza(Rettangolo b) {
		return getAltezza() > b.getAltezza();
	}

	public boolean canContain(Rettangolo b) {
		return (maggioreAltezza(b) && maggioreBase(b));
	}

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

}
