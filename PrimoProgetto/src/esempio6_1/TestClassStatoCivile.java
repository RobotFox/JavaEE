package esempio6_1;

public class TestClassStatoCivile {

	public static void main(String[] args) {

		Persona p = new PersonaStatoCivile("Gianni", Sesso.M, Stato.coniugato);
		System.out.println("" + ((PersonaStatoCivile) p).getSesso());
			
		
		Persona q = new Insegnante("Dario", "ITS Rossi", "Scienze", new String[]{"a1","b3"});
		
		System.out.println(((Insegnante)q).toString());
	
	}

}
