package esercizioQuadrato;

public class Quadrato implements Comparable {

	private double lato;

	public Quadrato() {
		lato = 0;
	}

	public Quadrato(int lato) {
		this.lato = lato;
	}

	public double getLato() {
		return lato;
	}

	public void setLato(double lato) {
		this.lato = lato;
	}

	public void setLato(int lato) {
		this.lato = lato;
	}

	@Override
	public int compareTo(Object o) {
		if (lato < ((Quadrato) o).lato)
			return -1;
		if (lato > ((Quadrato) o).lato)
			return 1;
		return 0;
	}

}
