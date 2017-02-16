package esercizi;

public class TestClassTriangolo {

	public static void main(String[] args) {

		Triangolo a = new Triangolo();
		a.setAltezza(12);
		a.setBase(24);
		System.out.println("L'ipotenusa è: " + a.ipotenusa());
		System.out.println("Il perimetro è: " + a.perimetro());
		System.out.println("L'area è: " + a.area());

	}

}
