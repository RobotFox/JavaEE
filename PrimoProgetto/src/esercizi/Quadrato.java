package esercizi;

public class Quadrato {

	private double lato;

	public Quadrato() {
		setLato(1);
	}

	public Quadrato(double lato) {
		this.lato = lato;
	}

	public double getLato() {
		return lato;
	}

	public void setLato(double lato) {
		this.lato = lato;
	};

	public double perimetro() {

		return getLato() * 4;
	}

	public double area() {

		return Math.pow(getLato(), 2);

	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		long temp;
		temp = Double.doubleToLongBits(lato);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Quadrato other = (Quadrato) obj;
		if (Double.doubleToLongBits(lato) != Double.doubleToLongBits(other.lato))
			return false;
		return true;
	}

}
