
public class Pluto {
	// esempio di Composizione
	int i = 0;
	Pippo p;

	public Pluto() {
		p = new Pippo();
	}

	public int getI() {
		return i;
	}

	public void setI(int i) {
		this.i = i;
	}

	public Pippo getP() {
		return p;
	}

	public void setP(Pippo p) {
		this.p = p;
	}

}
