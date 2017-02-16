
public class Dipendente extends Persona {

	@Override
	public double calcolaReddito(int anniLavorati) {
		double reddito = anniLavorati*25000.77;
		return reddito;
	}

}
