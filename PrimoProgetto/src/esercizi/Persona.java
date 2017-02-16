package esercizi;

public class Persona {

	String nome;
	Data dataDiNascita;

	public Persona(String nome, Data dataDiNascita) {
		this.nome = nome;
		this.dataDiNascita = dataDiNascita;
	}

	public void calcolaEta(int anni) {
		dataDiNascita.differenza(anni);
	}

	public void calcolaEta(int anni, int mesi) {
		dataDiNascita.differenza(anni, mesi);
	}

	public void calcolaEta(int anni, int mesi, int giorni) {
		dataDiNascita.differenza(anni, mesi, giorni);
	}

}
