package esercizi;

public class DifferenzaDate {
	
	int anno,mese,giorno;

	public DifferenzaDate(int anno, int mese, int giorno) {
		this.anno = anno;
		this.mese = mese;
		this.giorno = giorno;
	}

	@Override
	public String toString() {
		return "DifferenzaDate [Differenza Anno=" + anno + ",Differenza Mese=" + mese + ", Differenza Giorno=" + giorno + "]";
	}
	
}