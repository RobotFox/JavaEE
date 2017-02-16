package esercizioOperazione;

public class Sottrazione extends Operazione{

	@Override
	public double calcolo(Operazione operazione, int operando1, int operando2) {
		double risultato=0;
		if (operazione instanceof Sottrazione) {
			risultato = operando1-operando2;
		}
		return risultato;
	}
	
	

}
