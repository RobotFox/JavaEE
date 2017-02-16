package esercizi;

public class TestClassPunto {

	public static void main(String[] args) {
		Punto a = new Punto(2.0, 4.0);
		Punto b = new Punto(5.0, 10.0);

		System.out.println("il punto a dista da b: " + a.distanza(b));
	}
}
