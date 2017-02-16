package esempio6_1;

enum Sesso {
	M, F
};

enum Stato {
	celibe, nubile, coniugato, vedovo, divorziato
};

public class PersonaStatoCivile extends Persona {

	private Sesso sesso;
	private Stato stato;

	public PersonaStatoCivile(String nome, Sesso sesso, Stato stato) {
		super(nome);
		this.sesso = sesso;
		this.stato = stato;
	}

	public Sesso getSesso() {
		return sesso;
	}

	public void setSesso(Sesso sesso) {
		this.sesso = sesso;
	}

	public Stato getStato() {
		return stato;
	}

	public void setStato(Stato stato) {
		this.stato = stato;
	}

}
