package esercizi;

public class TestClassQuadrato {

	public static void main(String[] args) {
		
		Quadrato a = new Quadrato();
		System.out.println("Il perimetro di a �: "+a.perimetro()+"\n"+"L'area di a �: "+a.area());
		
		Quadrato b = new Quadrato(4);
		System.out.println("Il perimetro di a �: "+b.perimetro()+"\n"+"L'area di a �: "+b.area());

	}

}
