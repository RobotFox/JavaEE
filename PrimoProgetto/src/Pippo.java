
public class Pippo {

	private String nome = "pippo";
	private int anni = 20;
	private boolean sposato = true;

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public int getAnni() {
		return anni;
	}

	public void setAnni(int anni) {
		this.anni = anni;
	}

	public boolean isSposato() {
		return sposato;
	}

	public void setSposato(boolean sposato) {
		this.sposato = sposato;
	}

	public String saluta() {
		String saluto = "Ciao mi chiamo: " + nome + " e ho: " + anni + " anni";
		return saluto;
	}

	public void cresci(int anniPassati) {
		anni = anni + anniPassati;
	}

	public void scappa(String nomeFalso) {
		System.out.println(nome + " devi scappare! Ora il tuo nome è: " + nomeFalso);
		nome = nomeFalso;
	}

	public String sposato() {
		return nome + " è sposato? " + sposato;
	}

	public Double moltiplica(Double number) {
		return anni * number;
	}

	public Double isAdditionOrMoltiplitication(Double number) {

		return anni + number * number;
	}

	public void divorzio(boolean divorzio) {
		if (divorzio) {
			sposato = false;
		} else {
			sposato = true;
		}
	}
}
