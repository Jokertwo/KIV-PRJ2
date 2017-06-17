package project2;

public class Vrchol {

	
	
	
	String klic;
	String predchudce;
	
	char barva;
	
	int predek;
	int vzdalenost;
	int objeven;
	int dokoncen;
	int pocetSousedu;
	int stupenVstup;
	int stupenVystup;
	

	
	
	Vrchol(String klic, char barva){
		this.klic = klic;
		this.barva = barva;
		pocetSousedu = 0;
		vzdalenost = Integer.MAX_VALUE;
		predchudce = null;
		stupenVstup = 0;
		stupenVystup =0;
		
		
	}

	void tiskVrcholu(){
		System.out.print(klic+ " ");
	}
}
