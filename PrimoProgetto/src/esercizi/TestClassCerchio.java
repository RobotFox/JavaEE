package esercizi;

public class TestClassCerchio {
	
	public static void main(String[] args) {
		
		Cerchio c1 = new Cerchio(20.5);
		System.out.println("La circoferenza �: "+c1.circonferenza()+"\n"+"(L'area �: "+c1.area());		
		
		Cerchio c2 = new Cerchio(new Punto(10, 10),42.2);
		System.out.println("La circoferenza �: "+c2.circonferenza()+"\n"+"(L'area �: "+c2.area());
	}

}
