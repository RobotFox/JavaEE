package esercizioOperazione;

public class Addizione extends Operazione {

	@Override
	public double calcolo(Operazione operazione, int operando1, int operando2) {
		double risultato=0;
		if (operazione instanceof Addizione) {
			risultato = operando1 + operando2;
		}
		return risultato;
	}

}
