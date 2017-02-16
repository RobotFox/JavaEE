package esercizi;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class Data {

	private int giorno, mese, anno;
	Calendar calendar;
	SimpleDateFormat simpleDateFormat;

	/**
	 * Costruttore per creazione data
	 * 
	 * @param giorno
	 * @param mese
	 * @param anno
	 */
	public Data(int giorno, int mese, int anno) {
		
		
		
		
		
		calendar = Calendar.getInstance();
		
		simpleDateFormat = new SimpleDateFormat("dd/MM/yyy");

		if (giorno > -1 && giorno <= 31 && mese > 0 && mese <= 12) {
			setGiorno(giorno);
			setMese(mese - 1);
			setAnno(anno);
			calendar.set(Calendar.DAY_OF_MONTH, getGiorno());
			calendar.set(Calendar.MONTH, getMese());
			calendar.set(Calendar.YEAR, getAnno());
		} else {
			System.out.println("Immetti dei dati veri!");
			setGiorno(0);
			setMese(0);
			setAnno(0);
			calendar.set(Calendar.DAY_OF_MONTH, getGiorno());
			calendar.set(Calendar.MONTH, getMese());
			calendar.set(Calendar.YEAR, getAnno());
		}
	}

	public int getGiorno() {
		return giorno;
	}

	public void setGiorno(int giorno) {
		this.giorno = giorno;
	}

	public int getMese() {
		return mese;
	}

	public void setMese(int mese) {
		this.mese = mese;
	}

	public int getAnno() {
		return anno;
	}

	public void setAnno(int anno) {
		this.anno = anno;
	}

	public String sommaGiorni(int giorni) {
		calendar.add(Calendar.DAY_OF_MONTH, giorni);
		return simpleDateFormat.format(calendar.getTime());
	}

	public DifferenzaDate differenza(int anni) {
		calendar.add(Calendar.YEAR, -anni);
		return new DifferenzaDate(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH),
				calendar.get(Calendar.DAY_OF_MONTH));
	}

	public DifferenzaDate differenza(int anni, int mesi) {
		calendar.add(Calendar.YEAR, -anni);
		calendar.add(Calendar.MONTH, -mesi + 1);
		return new DifferenzaDate(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH),
				calendar.get(Calendar.DAY_OF_MONTH));
	}

	public DifferenzaDate differenza(int anni, int mesi, int giorni) {
		calendar.add(Calendar.YEAR, -anni);
		calendar.add(Calendar.MONTH, -mesi + 1);
		calendar.add(Calendar.DAY_OF_MONTH, -giorni);
		return new DifferenzaDate(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH),
				calendar.get(Calendar.DAY_OF_MONTH));
	}
}