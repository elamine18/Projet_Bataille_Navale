package batailleNavale;

public class Coordonnee implements Comparable<Coordonnee>{
	private int ligne;
    private int colonne;
    
    public Coordonnee(int ligne, int colonne) {
        if (ligne>25 || ligne<0 || colonne >25 || colonne <0){
            throw new IllegalArgumentException("les cordonnes sont hors limites");
        }
        this.ligne=ligne;
        this.colonne=colonne;
    }
    
    public Coordonnee(String s) {
        if(s.charAt(0)>='A' && s.charAt(0)<='Z')
            colonne=s.charAt(0)-'A';
        else if (s.charAt(0)>='a' && s.charAt(0)<= 'z')
            colonne=s.charAt(0)-'a';
        else 
            throw new IllegalArgumentException("La colonne est hors limite");
        ligne = Integer.parseInt(s.substring(1))-1;
        if (ligne>25 || ligne<0 || colonne >25 || colonne <0) throw new IllegalArgumentException("les cordonnes sont hors limites");
    }
    
    
        
    public String toString(){ 
        return (char)('A' + colonne) + Integer.toString(ligne + 1);   
    }
    
    public int getLigne() {
        return ligne;
    }
    
    public int getColonne() {
        return colonne;
    }
    
    public boolean equals(Object obj) {
        return (obj instanceof Coordonnee) && (this.compareTo((Coordonnee) obj) ==0);
     }
    
     public boolean voisine(Coordonnee c){
        return( Math.abs(ligne - c.ligne)<=1 && Math.abs(colonne - c.colonne) <=1);
         
    }
     public int compareTo(Coordonnee c) {
	if (this.ligne < c.ligne )
		return -1; 
	if (this.ligne > c.ligne)
		return 1; 
	if (this.colonne < c.colonne)
		return -1; 
	if (this.colonne > c.colonne)
		return 1;
	
	return 0; 
		 
}

}
