package esercizi;

public class Data2 {
	
	static int[] giorniInMese = {31,27,31,30,31,30,31,31,30,31,30,31};
	int mese;
	int giorno;
	int anno;
	
	public Data2(int giorno, int mese, int anno){
		this.giorno=giorno;
		this.mese=mese;
		this.anno=anno;
	}
	
	public int massimoGiorni(){
		return giorniInMese[mese-1];
	}
	
	public Data2 aggiungiGiorni(int numeroGiorni) {
		int giorno = this.giorno+=numeroGiorni;
		int mese = this.mese;
		int anno = this.anno;
		while (giorno>massimoGiorni()) {
			giorno -=massimoGiorni();
			mese++;
			if (mese>12) {
				anno++;
				mese=1;
			}
		}
		return new Data2(giorno, mese, anno);
	}
	
	public DifferenzaDate differenza(int anni) {
			return new DifferenzaDate(this.anno-anni, 0, 0);
	}
	
	public DifferenzaDate differenza(int anni, int mesi) {
		return new DifferenzaDate(this.anno-anni, this.mese-mesi, 0);
	}
	
	public DifferenzaDate differenza(int anni, int mesi, int giorni) {
		return new DifferenzaDate(this.anno-anni, this.mese-mesi, this.giorno-giorni);
	}
}
