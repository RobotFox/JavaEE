package esercizi;

public class TestClassData {

	public static void main(String[] args) {
		Data data = new Data(31, 9, 1989);
		// aggiunta giorni
		System.out.println(data.sommaGiorni(12));

		// differenza anni
		System.out.println(data.differenza(800).toString());

		// differenza anni e mesi
		System.out.println(data.differenza(10, 2).toString());

		// differenza anni, mesi e giorni
		System.out.println(data.differenza(8, 5, 3).toString());
	}

}
