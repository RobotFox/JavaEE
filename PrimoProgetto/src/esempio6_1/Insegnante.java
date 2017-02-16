package esempio6_1;

import java.util.Arrays;

public class Insegnante extends Persona {

	String scuola;
	String materia;
	String[] classi;

	public Insegnante(String nome, String scuola, String materia, String[] classi) {
		super(nome);
		this.scuola = scuola;
		this.materia = materia;
		this.classi = classi;
	}

	@Override
	public String toString() {
		return "Insegnante [nome="+super.getNome()+", scuola=" + scuola + ", materia=" + materia + ", classi=" + Arrays.toString(classi) + "]";
	}
	
}
