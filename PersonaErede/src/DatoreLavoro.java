
public class DatoreLavoro extends Persona {

	@Override
	public double calcolaReddito(int anniLavorati) {
		double reddito =(anniLavorati-5)*50000.46;
		return reddito;
	}

}
