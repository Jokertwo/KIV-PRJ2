package project2;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;

import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Projekt2 
 * Program který zjištuje jestli je gram Eulerovský nebo ne. 
 * Program nejdøíve vytvoøí graf reprezentovaný maticí sousednosti.
 * Poté ho prohledá buï BFS nebo DSF zpùsobem. 
 * Dále pokud najde všechny vrcholy (graf je souvislý) zkontroluje stupnì vrcholù.
 * Aby mohl být graf Eulerovský musí být všechny vrcholy bud sudé nebo každy vstupní stupeò
 *  vrcholu musí být stejnì velký jako výstupní stupeò.
 * 
 * Pokud jsou obì tyto podmínky splnìny zaène prohledávání grafu tak že pokaždé když algoritmus po nìjaké hranì projde 
 * obarví jí a jde tak dlouho dokud nìjaká existuje.Pokud už z vrcholu nevede žádná neobarvená hrana, uloží vrchol do pole a vrátí se o jeden vrchol zpìt.
 * a opìt se pokusí najít nìjakou neobarvenou hranu.Pokud žádnou nenajde, opìt uloží vrchol do pole a vrátí se po cestì které pøišel... a tak stále dokola 
 * 
 * 
 * Program mùže být spuštìn jak s argumentem(textový soubor se stejnou strukturou souboru jako u semestrální práce) tak se zadáním grafu z konzole.
 * 
 * @author Jokertwo Petr Laštovka A15B0055K
 *
 */



public class Main {
	static MaticeSousednosti g1;
	
	static int pocetVrcholu;
	static int pocetHran;
	
	static String a,b = null;
	static String pom;
	static String start = "";
	
	static String[] pole;
	
	/**
	 * rozhodne jesti se k prohledavani grafu  pouzije metoda BFS nebo DFS
	 * @param a
	 * @return
	 */
	static int rozhodni(String a){
		
		if(a.contains("SBFS") || a.contains("MBFS"))
			return 1;
		
		if(a.contains("SDFS") || a.contains("MDFS"))
			return 2;
		
		return -1;
	}
	/**
	 * zadani hodnot do grafu 
	 * repreyentacce grafu pomoci matice sousednosti
	 * @param g3
	 * @throws NumberFormatException
	 * @throws IOException
	 */
	static void nacti(MaticeSousednosti g3,BufferedReader cti,int uzivatel) throws NumberFormatException, IOException{
		
		for(int i = 1;i <= pocetVrcholu;i++){
			if(uzivatel == 1){
			tisk(i + ". vrchol grafu: ");
			}
			g3.novyVrchol(cti.readLine());
		}
		if(uzivatel == 1){
		tisk("Zadejte pocet hran :");
		}
		pocetHran = (new Integer(cti.readLine())).intValue();
		
		g3.pocetHran(pocetHran);
		
		for(int i = 0; i < pocetHran; i++){
			if(uzivatel == 1){
			tisk("Zadejte "+ (i+1)+ ". hranu (a-b pro neorien./ a>b pro orient)");
			}
			pom = cti.readLine();
			
			
			if(pom.contains("-")){
				pole = pom.split("-");
				g3.Hrana(pole[0], pole[1],'-');
			}
			
			if(pom.contains(">")){
				pole = pom.split(">");
				g3.Hrana(pole[0], pole[1],'>');
			}
			
		}
		if(uzivatel == 1){
		tisk("Zadejte zacatek odkud se ma graf zacit prohledavat");
		}
		start = cti.readLine();
		}
	/**
	 * metoda ktera pouze tiskne do konzole
	 * @param string
	 */
	static void tisk(String string){
		System.out.println(string);
	}
	
	
	public static void main(String[] args) throws IOException {
		
		
		String volba;
		
		
		//v pripade ze byl zvoleny nejaky ten argument
		if(args.length>0){
			//otevre soubor ktery byl zadan jako argument
			BufferedReader buf = null;
			try {
				buf = new BufferedReader(
						new FileReader(args[0]));
			
	
			volba = buf.readLine();
			int roz = rozhodni(volba);
			
			
			pocetVrcholu = (new Integer(buf.readLine())).intValue();
			
			
			g1 = new MaticeSousednosti(pocetVrcholu);
			nacti(g1,buf,0);
			
			if(roz == 1){
				g1.BFS(start);
				tisk(g1.vytiskniBFS());
			}
			if(roz == 2){
				g1.DFS(start);
				tisk(g1.vytiskniDFS());
			}
			
			tisk("");
			
			//tento blok probehne pouze pokud jsou splneny vsechny podminky
			//pro eulerovsky graf
			if(g1.testSouvislostiAStupneVrcholu()){
				tisk(g1.vys);
				g1.Euler(start);
				String cesta = g1.vystiskniCestu();
				tisk(cesta);
			}
			//tento blok vypise proc nemuze byt graf eulerovsky
			else{
				tisk(g1.vys);
			}
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				System.err.println("Soubor se zadanym nazvem nebyl nalezen");
				
			}
			
		}
		else{
			tisk("Vybete mezi MDFS nebo MBFS: ");
			BufferedReader buf = new BufferedReader(new InputStreamReader(System.in));
			
			volba  = buf.readLine();
			int roz = rozhodni(volba);
			tisk("Zadete pocet vrcholu grafu: ");
			pocetVrcholu = (new Integer(buf.readLine())).intValue();
			
			
			g1 = new MaticeSousednosti(pocetVrcholu);
			tisk("Zadejte jednotlive vrcholy: ");
			nacti(g1,buf,1);
			
			if(roz == 1){
				g1.BFS(start);
				tisk(g1.vytiskniBFS());
			}
			if(roz == 2){
				g1.DFS(start);
				tisk(g1.vytiskniDFS());
			}
			
			tisk("");
			
			//tento blok probehne pouze pokud jsou splneny vsechny podminky
			//pro eulerovsky graf
			if(g1.testSouvislostiAStupneVrcholu()){
				tisk(g1.vys);
				g1.Euler(start);
				String cesta = g1.vystiskniCestu();
				tisk(cesta);
			}
			//tento blok vypise proc nemuze byt graf eulerovsky
			else{
				tisk(g1.vys);
			}
			
		}
		
	}
	
}
