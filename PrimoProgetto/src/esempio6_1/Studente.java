package esempio6_1;

public class Studente extends Persona {

	private String scuola;
	private String classe;

	public Studente(String nome, String scuola) {
		super(nome);
		this.scuola = scuola;
	}

	public Studente(String nome, String scuola, String classe) {
		this(nome, scuola);
		this.classe = classe;
	}

	public String getScuola() {
		return scuola;
	}

	public void setScuola(String scuola) {
		this.scuola = scuola;
	}

	public String getClasse() {
		return classe;
	}

	public void setClasse(String classe) {
		this.classe = classe;
	}
	
	public void stampaDati() {
		super.stampaDati();
		System.out.println(getScuola()+" "+getClasse());
		
	}
	
}
