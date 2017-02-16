package esercizi;

public class TestClassQuadrato {

	public static void main(String[] args) {
		
		Quadrato a = new Quadrato();
		System.out.println("Il perimetro di a è: "+a.perimetro()+"\n"+"L'area di a è: "+a.area());
		
		Quadrato b = new Quadrato(4);
		System.out.println("Il perimetro di a è: "+b.perimetro()+"\n"+"L'area di a è: "+b.area());

	}

}
