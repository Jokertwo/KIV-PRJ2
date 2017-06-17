package project2;

public class Fronta {
	private String[] f;
	private int zac,kon;
	int n;
	
	
	Fronta(int max) {
		n = max+1;
		f = new String[n];
		zac = 0;
		kon = 0;
		// TODO Auto-generated constructor stub
	}
	
	boolean jePrazdna(){
		if(zac == kon){
			return true;
		}
		return false;
	}
	void vloz(String klic){
		kon++;
		f[kon] = klic;
		kon = kon%n;
		
	}
		
		
	String vyber(){
		zac++;
		String a  = f[zac];
		zac = zac%n;
		return a;
	}
	
		
		
		
}
