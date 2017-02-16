package esercizi;

public class Distanza {

	private double kilometri;
	private Double miglia;
	private final double migliaKilometri = 1.609;

	private static Distanza instance;

//	 public static Distanza getInstance(double valore, boolean test) {
//	 if (instance==null){
//	 instance = new Distanza(valore, test);
//	 }
//	 return instance;
//	 }

	public Distanza(double valore, boolean test) {
		if (test) {
			new Distanza(valore);
		} else {
			new Distanza(new Double(valore));
		}
	}

	public Distanza(double kilometri) {
		setMiglia(kilometri / migliaKilometri);
	}

	public Distanza(Double miglia) {
		setKilometri(miglia * migliaKilometri);
	}

	public double getKilometri() {
		return kilometri;
	}

	public void setKilometri(double kilometri) {
		this.kilometri = kilometri;
	}

	public double getMiglia() {
		return miglia;
	}

	public double getMigliaKilometri() {
		return migliaKilometri;
	}

	public void setMiglia(double miglia) {
		this.miglia = miglia;
	}

}
