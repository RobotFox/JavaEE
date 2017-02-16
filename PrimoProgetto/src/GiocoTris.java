import java.util.Arrays;

public class GiocoTris {

	private String[][] matrice;

	public GiocoTris() {
		matrice = new String[3][3];
	}

	public void inserisciX(int riga, int posizione) {
		matrice[riga][posizione] = "X";
		System.out.println(toString());
		vincitore(riga, posizione, "X");
	}

	public void inserisciO(int riga, int posizione) {

		matrice[riga][posizione] = "O";
		System.out.println(toString());
		vincitore(riga, posizione, "O");
	}

	public void vincitore(int posizione, int riga, String carattere) {

		if (controlloColonna(riga)) {
			System.out.println("Ha vinto: " + carattere);
		} else if (controlloRiga(posizione)) {
			System.out.println("Ha vinto: " + carattere);
		} else if (controlloDiagonali()) {
			System.out.println("Ha vinto: " + carattere);
		}
	}

	@Override
	public String toString() {
		int righe = matrice.length;
		int colonne = matrice[0].length;
		String stringa = "";
		for (int i = 0; i < righe; i++) {
			for (int j = 0; j < colonne; j++) {
				stringa += matrice[i][j]+"\t";
			}
			stringa+="|\n";
		}
		return stringa;
	}

	public boolean controlloDiagonali() {

		return ((matrice[0][0] == matrice[1][1] && matrice[1][1] == matrice[2][2])
				|| (matrice[0][2] == matrice[1][1] && matrice[1][1] == matrice[2][0]));
	}

	public boolean controlloRiga(int numeroRiga) {
		return (matrice[numeroRiga][0] == matrice[numeroRiga][1] && matrice[numeroRiga][1] == matrice[numeroRiga][2]);
	}

	public boolean controlloColonna(int numeroColonna) {
		return (matrice[0][numeroColonna] == matrice[1][numeroColonna]
				&& matrice[1][numeroColonna] == matrice[2][numeroColonna]);
	}
}
