package project2;

public class Hrana {
	
	
	String zacatek;
	String konec;
	
	int cas;
	
	char barva;
	char typ;
	
	Hrana(String zacatek, String konec,char barva, char typ){
		this.zacatek = zacatek;
		this.konec = konec;
		this.barva = barva;
		this.typ = typ;
		
	}

	
	void tisk(){
		System.out.println(zacatek + " -> " + konec );
	}
}
