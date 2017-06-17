package project2;

import java.util.ArrayList;





public class MaticeSousednosti {
	ArrayList<String> pole = new ArrayList<String>();
	ArrayList<String> cesta = new ArrayList<String>();
	Vrchol[] vrchP;
	Hrana[] hrana;
	int[][] matice;
	int pocet =0;
	int pocetHr =0;
	int pocetVrcholu;
	int casObjeveni = 0;
	String vys = "";
	
	
	
	public MaticeSousednosti(int pocetVrcholu) {
			this.pocetVrcholu = pocetVrcholu;
			this.matice = new int [pocetVrcholu][pocetVrcholu];
			this.vrchP = new Vrchol[pocetVrcholu];
			//this.hrana = new Hrana[7];
			for(int i = 0;i< vrchP.length;i++){
				vrchP[i] = null;
				}
	}
	
	
	
	/**
	 * rozhodne jestli je hrana orientovana nebo ne
	 * pokud ano vraci false
	 * pokud je neorinetovana vraci true
	 * 
	 * @param zac String zacatek hrany(vrchol)
	 * @param cil String konec hrany(vrchol)
	 * @return vraci false pro orientovane hrany
	 */
	public boolean oriNeboNeoriHrana(String zac,String cil){
		int indexZac = index(zac);
		int indexKon = index(cil);
		
		if(matice[indexZac][indexKon] == 1 && matice[indexZac][indexKon] == 1){
			return true;
		}
		return false;
	}
	
	/**
	 * pokud jsou vsechny hrany v grafu neorientovane vrati 0
	 * pokud jsou vsechnz hrany v grafu orientovane vrati 1
	 * pokud nejsou vsechny hrany ori nebo neori vrati 2
	 * @return
	 */
	public int orientaceGrafu(){
		int poc = 0;
		for(int i = 0 ; i < hrana.length ; i++){
			if(!oriNeboNeoriHrana(hrana[i].zacatek,hrana[i].konec))
			 poc++;
		}
		if(poc == 0 )
			return 0;
		
		if(poc == pocetHr)
			return 1;
		
		
			return 2;
		 
		
					
	}
	/**
	 * secte vstupni a vystupni stupne vsech vrcholu
	 * pokud nejsou vsechny sudeho stupne vraci false
	 * jinak true  
	 * @return
	 */
	public boolean stupneProNeoriGraf(){
		int pom = 0;
		for(int i = 0;i<vrchP.length;i++){
			int pom2  = vrchP[i].stupenVstup + vrchP[i].stupenVystup;
			pom2  = pom2 % 2;
			if(pom2 != 0)
				pom++;
		}
		if(pom != 0)
			return false;
		
		return true;
	}
	
	
	
	/**
	 * zjisti pro orientovan graf jestli pro vsechny stupne orientovaneho
	 * grafu plati ze vstupni stupen je stejny jako vystupni; 
	 * @return
	 */
	public boolean stupneProOriGraf(){
		int pom = 0;
		for(int i = 0; i < vrchP.length;i++){
			if(vrchP[i].stupenVstup != vrchP[i].stupenVystup)
				pom++;
		}
		if(pom != 0)
			return false;
		
		return true;
	}
	
	/**
	 * vraci true pouze pokud jsou splneny obe podminky souvislosti
	 * jinak vrati false
	 * navic nastavi do retezece vys d;vod prc nemuze byt graf eulerovsky
	 * 
	 * 
	 * @return 
	 */
	public boolean testSouvislostiAStupneVrcholu(){
		
		if(!souvislost()){
			vys = "Graf neni souvisly a proto nemuze byt ani Eulerovsky.";
			return false;
		}
		if(!muzeBytEuler()){
			vys = "Stupne vrcholu nejsou bud sude nebo nebo si neodpovida jejich vstupni a vystupni stupen.";
			return false;
		}
		if(muzeBytEuler() && souvislost()){
			vys ="Graf je souvisly a zaroven vsechny stupne jsou sude nebo jejich vstupni a vystupni stupen je roven.";
			return true;
		}
		return true;
	}
	
	
	
	/**
	 * pokud je graf orientovany zjisti jestli je vstupni vrchol roven vzstupnimu
	 * pokud je graf neorientovany urci jestli jsou vsechny stupne vrcholu sude
	 */
	public boolean muzeBytEuler(){
		urciStupneVrcholu();
		//zjisti jestli je graf orientovany
		int druhGrafu = orientaceGrafu();
		
		if(druhGrafu == 0){
			//pokud nejsou vsechny vrcholy sudeho stupne vrati false
			if(!stupneProNeoriGraf()){
				return false;
			}
		}
		if(druhGrafu == 1){
			//pokud nejsou vstupni a vystupni stupne u kazdeho vrchlu si rovny 
			//vrati false
			if(!stupneProOriGraf()){
				return false;
			}
		}
		if(druhGrafu == 2){
			System.out.println("Cast grafu je orientovana a cast neorientovana");
			return false;
		}
		//pokud jsou splneny podminky stupnu vraci true;
		return true;
		
	}
		
	/**
	 * nastavi pole uchovavajici pocet hran v grafu
	 * @param pocet
	 */
	public void pocetHran(int pocet){
		this.hrana = new Hrana[pocet];
		for(int i = 0;i < hrana.length ; i++ ){
			hrana[i] = null;
		}
	}
	
		/**
	 * urci u vrcholu jeho stupne
	 * @param klic
	 */
	public void stupenVrcholu(String klic){
		int indexKlic = index(klic);
		for(int i = 0; i < matice.length;i++){
			if(matice[i][indexKlic] == 1 )
				vrchP[indexKlic].stupenVstup++;
			
			if(matice[indexKlic][i] == 1)
					vrchP[indexKlic].stupenVystup++;
			}
				
		
	}
	
	/**
	 * postupne pro vsechny vrcholy zavola metodu ktera urci jeho stupne
	 */
	public void urciStupneVrcholu(){
		for(int i = 0;i < vrchP.length; i++){
			stupenVrcholu(vrchP[i].klic);
		}
	}
	
	
	/**
	 * metoda zjistujici index na kterem je ulozen vrchol
	 * 
	 * @param klic vrchol u ktereho chci zjitit index
	 * @return index vrchlu z parametru
	 */
	public int index(String klic){
		int pom = -1;
		for(int i = 0;i< pocet;i++){
			if(vrchP[i].klic.equals(klic)){
				pom = i;
			}
		}	
	return pom;
	}
	
	
	
	/**
	 * vraci index hrany v poli
	 * @param zacatek kde hrana zacina
	 * @param cil kde hrana konci
	 * @return index na kterem je ulozena v poli
	 */
	public int indexHrany(String zacatek,String cil){
		int pom = -1;
		for(int i = 0;i < pocetHr; i++){
			//v pripade ze jsou hrany neorintovane
			if(hrana[i].typ == '-'){
				if(hrana[i].zacatek.equals(zacatek) || hrana[i].zacatek.equals(cil)){
					if(hrana[i].konec.equals(cil) || hrana[i].konec.equals(zacatek)){
						pom = i;
					}
				}
			}
			//v pripade ze jsou hrany orientovane
			if(hrana[i].typ == '>'){
				if(hrana[i].zacatek.equals(zacatek)){
					if(hrana[i].konec.equals(cil)){
						pom = i;
					}
					
				}
				
			}
		}
		return pom;
	}
	
	
	/**
	 * metoda pro pridani noveho vrcholu
	 * @param klic
	 */
	void novyVrchol(String klic){
		Vrchol pom = new Vrchol(klic,'B');
		
		vrchP[pocet] = pom;
		pocet++;
	
	}
	
	
	/**
	 * metoda pro vlozeni hrany
	 * @param start
	 * @param cil
	 */
	void Hrana(String start,String cil, char druh){
		int iStart = index(start);
		int iCil = index(cil);
		
		if(iStart == -1)
			return;
		if(iCil == -1)
			return;
		//orientovana hrana
		if(druh == '-' || druh == '>'){
			matice[iStart][iCil] = 1;
			vrchP[iStart].pocetSousedu = vrchP[iStart].pocetSousedu +1;
		}
		
		//neorientovana
		if(druh == '-'){
			matice[iCil][iStart] = 1;
			vrchP[iCil].pocetSousedu = vrchP[iCil].pocetSousedu +1;	
		}
		
		Hrana pom = new Hrana(start,cil,'W',druh);
		hrana[pocetHr] = pom;
		pocetHr++;
		
		
	}
	
	
	/**
	 * prohledavani do hloubky
	 * @param start
	 */
	void DFS(String start){
		int s  = index(start);
		DFSR(s);
	}
	void DFSR(int start){
		vrchP[start].barva = 'S';
		casObjeveni++;
		vrchP[start].objeven = casObjeveni;
		
		int pom = matice.length-1;
		
		for(int i = 1;i<= vrchP[start].pocetSousedu;i++){
			while(matice[start][pom] ==0){
				pom--;
				}
			int v = pom--;
			if(vrchP[v].barva =='B'){
				vrchP[v].predchudce = vrchP[start].klic;
				DFSR(v);
			}
		}
		vrchP[start].barva = 'C';
		vrchP[start].dokoncen = casObjeveni = casObjeveni+ 1;
		pole.add(vrchP[start].klic);
	}
	
	
	/**
	 * nalezeni eulerovi cesty/tahu v grafu
	 * @param start
	 */
	void Euler(String start){
		int s  = index(start);
		EulerRek(s);
	}
	void EulerRek(int start){
		
		
		int pom =0;
		
		for( int i = 0;i < matice.length;i++){
			
			if(matice[start][i]== 1){
				pom = indexHrany(vrchP[start].klic,vrchP[i].klic);
			
					if(hrana[pom].barva == 'W'){
				
					
						vrchP[i].predchudce = vrchP[start].klic;
						hrana[pom].barva = 'R';
						EulerRek(i);
					}
				
		}
		}
		
		
		cesta.add(vrchP[start].klic);
		
	}
	
	
	
	
	/**
	 * metoda pro vypsani matice sousednosti do konzole
	 */
	void matice(){
		for(int i = 0;i<matice.length;i++){
			for(int j =0; j< matice[i].length;j++){
				System.out.print(matice[i][j] + " ");
			}
			System.out.println();
		}
	}
	
	
	
	/**
	 * metoda pro prohledavani do sirky
	 * @param start
	 */
	void BFS(String start){
		int iStart = index(start);
		
		Fronta f = new Fronta(pocetVrcholu*2);
		
		vrchP[iStart].barva = 'S';
		vrchP[iStart].vzdalenost = 0;
		
		f.vloz(start);
		
		while(!f.jePrazdna()){
			String u = f.vyber();
			int indexU = index(u);
			int pom = 0;
			
			for(int i = 1;i<= vrchP[indexU].pocetSousedu;i++){
				while(matice[indexU][pom] == 0){
					pom++;
					
				}
				int a = pom++;
				
				
				if(vrchP[a].barva =='B'){
					vrchP[a].barva = 'S';
					vrchP[a].vzdalenost = vrchP[indexU].vzdalenost + 1;
					vrchP[a].predchudce = u;
					f.vloz(vrchP[a].klic);
				}
			}
			vrchP[indexU].barva = 'C';
			pole.add(vrchP[indexU].klic);
		}
		
		
	}
	
	/**
	 * porovna velikost pole nalezenych vrcholu
	 * s poctem zadanych vrcholu
	 * pokud se cisla rovnaji byly nalezeny vsechny vrcholy
	 * a graf je souvily
	 * @return vraci true pouze pokud ma velikost pole pocet vrcholu stejnou hodnotu;
	 */
	public boolean souvislost(){
		
		if(pole.size() != pocetVrcholu){
			return false;
		}
		return true;
		
	}
	
	
	/**
	 * vrati String retezec s cestou pro nakresleni grafu jednim tahem;
	 * @return
	 */
	public String vystiskniCestu(){
		
		String vypis = "Euleruv tah : "  + cesta.get(cesta.size()-1);
		for(int i = cesta.size()-2 ; i >= 0;i--){
			vypis += " -> " + cesta.get(i);
		}
		
		return vypis;
	}
	
	
	String vytiskniBFS(){
		
		int min = 0;
		String vypis  = "BFS(" + pole.get(min)+ "): " + pole.get(min);
		
		for(int i = 1;i<pole.size();i++){
			vypis +=", " + pole.get(i);
			
		}
		return vypis;
	}
	
	String vytiskniDFS(){
		
		int max = pole.size()-1;
		String vypis = "DFS(" + pole.get(max)+"): " + pole.get(max);
		
		for(int i = max-1;i>=0;i--){
			vypis += ", " + pole.get(i) ;
			
		}
		return vypis;
	}
	
	
}