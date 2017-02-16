package esercizi;

public class Cerchio {

	private Punto centro;
	private double raggio;
	/**
	 * Constructor for the creation of a Circle with a radius.
	 * @param double radius
	 */
	public Cerchio(double raggio) {
		this.raggio = raggio;
	}
	
	/**
	 * Constructor for the creation of a Circle with the location (center) in a Cartesian Plane and the radious..
	 * @param Punto center
	 * @param double radius
	 */
	public Cerchio(Punto centro, double raggio) {
		this.centro = centro;
		this.raggio = raggio;
	}

	public Punto getCentro() {
		return centro;
	}

	public void setCentro(Punto centro) {
		this.centro = centro;
	}

	public double getRaggio() {
		return raggio;
	}

	public void setRaggio(double raggio) {
		this.raggio = raggio;
	}
	/**
	 * Calcolare la circoferenza di un cerchio.
	 * @return double il valore della circoferenza
	 */
	public double circonferenza() {
		return (2 * getRaggio() * Math.PI);
	}
	
	/**
	 * Calcolare l'area di un cerchio.
	 * @return double il valore dell'area.
	 */
	public double area() {
		return (Math.pow(getRaggio(), 2) * Math.PI);
	}

}
