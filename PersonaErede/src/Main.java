
public class Main {

	public static void main(String[] args) {

		Persona[] pers = new Persona[6];

		pers[0] = new Studente();
		pers[0].setEta(20);
		pers[0].setNome("Paolo");
		pers[0].setCognome("Antonelli");

		pers[1] = new Studente();
		pers[1].setEta(19);
		pers[1].setNome("Marco");
		pers[1].setCognome("Polo");

		pers[2] = new Studente();
		pers[2].setEta(22);
		pers[2].setNome("Filippo");
		pers[2].setCognome("Masala");

		pers[3] = new Dipendente();
		pers[3].setEta(30);
		pers[3].setNome("Paola");
		pers[3].setCognome("Verdi");

		pers[4] = new Dipendente();
		pers[4].setEta(33);
		pers[4].setNome("Stefania");
		pers[4].setCognome("Bianchi");

		pers[5] = new DatoreLavoro();
		pers[5].setEta(78);
		pers[5].setNome("Gigi");
		pers[5].setCognome("Proietti");

		for (int i = 0; i < pers.length; i++) {
			if (pers[i] instanceof Studente) {
				System.out.println(pers[i].calcolaReddito(2));
			} else if (pers[i] instanceof Dipendente) {
				System.out.println(pers[i].calcolaReddito(20));
			} else if (pers[i] instanceof DatoreLavoro) {
				System.out.println(pers[i].calcolaReddito(40));
			}
		}

	}

	Interfaccia i = new ImplentaInterfaccia();

}
