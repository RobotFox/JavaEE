package esercizi;

import java.util.Date;

public class Messaggio {

	String mittente, destinatario, testoMessaggio;
	Date data;

	public String getMittente() {
		return mittente;
	}

	public void setMittente(String mittente) {
		this.mittente = mittente;
	}

	public String getDestinatario() {
		return destinatario;
	}

	public void setDestinatario(String destinatario) {
		this.destinatario = destinatario;
	}

	public String getTestoMessaggio() {
		return testoMessaggio;
	}

	public void setTestoMessaggio(String testoMessaggio) {
		this.testoMessaggio = testoMessaggio;
	}

	public Date getData() {
		return data;
	}

	public void setData(Date data) {
		this.data = data;
	}

}
