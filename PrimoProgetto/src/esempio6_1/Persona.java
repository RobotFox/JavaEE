package esempio6_1;

public class Persona {

	private String nome;
	private String dataNascita;
	private String indirizzo;

	public Persona(String nome) {
		this.nome = nome;
	}

	public Persona(String nome, String dataNascita) {
		this(nome);
		this.dataNascita = dataNascita;
	}

	public Persona(String nome, String dataNascita, String indirizzo) {
		this(nome, dataNascita);
		this.indirizzo = indirizzo;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getDataNascita() {
		return dataNascita;
	}

	public void setDataNascita(String dataNascita) {
		this.dataNascita = dataNascita;
	}

	public String getIndirizzo() {
		return indirizzo;
	}

	public void setIndirizzo(String indirizzo) {
		this.indirizzo = indirizzo;
	}

	public void stampaDati() {
		System.out.println(getNome() + " " + getDataNascita() + " " + getIndirizzo());
	}
}
