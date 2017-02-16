package esercizioQuadrato;

import java.util.Arrays;

public class ProvaQuadrato {
	
	public static void main(String[] args) {
		Quadrato[] quadrati = new Quadrato[20];
		for (int i = 0; i < 20; i++) {
			quadrati[i]=new Quadrato((int)(Math.random()*100));
		}
		for (int i = 0; i < 20; i++) {
			System.out.println((((Quadrato)quadrati[i]).getLato()+" "));
		}
		System.out.println("");
		Arrays.sort(quadrati);
		for (int i = 0; i < quadrati.length; i++) {
			System.out.println(((Quadrato)quadrati[i]).getLato()+" ");
		}
		System.out.println("");
		
	}
	
	
}
