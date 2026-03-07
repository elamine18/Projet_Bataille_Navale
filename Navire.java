package batailleNavale;

public class Navire {

	
	private Coordonnee debut;
	private Coordonnee fin;
	private Coordonnee[] partiesTouchees;
	private int nbTouchees;


	public Navire(Coordonnee debut, int longueur, boolean estVertical) {
		if (longueur < 1) {
            throw new IllegalArgumentException("La longueur du navire doit être au moins 1");
        }
		this.debut = debut;
	    if (estVertical) 
	    	this.fin = new Coordonnee (debut.getLigne() + longueur -1, debut.getColonne());
	    else 
	    	this.fin = new Coordonnee (debut.getLigne(), debut.getColonne() + longueur -1);
	    this.partiesTouchees = new Coordonnee[longueur];
	    this.nbTouchees = 0;
		}


	public String toString() {
		String s= "Navire(";
		s+= debut.toString();
		if(estVertical()) {
			int longueur= fin.getLigne()- debut.getLigne()+1;
			s+=", "+ longueur + ", vertical)";
		}else {
			int longueur= fin.getColonne()- debut.getColonne()+1;
			s+=", "+ longueur + ", horizontal)";
		}
		return s;
	}
		
	
	public Coordonnee getDebut() {
		return debut;
	}
	
	public Coordonnee getFin() {
		return fin;
	}
	

	public boolean estVertical() {
		return debut.getColonne() == fin.getColonne();
	}


	public boolean contient(Coordonnee c) {
		return c.getLigne() >= debut.getLigne() && c.getLigne() <= fin.getLigne() &&
	               c.getColonne() >= debut.getColonne() && c.getColonne() <= fin.getColonne();
	}
	
	
	
	public boolean touche(Navire n) {
	    int debutLigneThis = debut.getLigne();
	    int finLigneThis = fin.getLigne();
	    int debutColonneThis = debut.getColonne();
	    int finColonneThis = fin.getColonne();
	
	    
	    int debutLigneN = n.debut.getLigne();
	    int finLigneN = n.fin.getLigne();
	    int debutColonneN = n.debut.getColonne();
	    int finColonneN = n.fin.getColonne();
	
	 
	    boolean adjacenceVerticale = (finLigneThis >= debutLigneN && debutLigneThis <= finLigneN) &&
	                                   (debutColonneThis == finColonneN + 1 || finColonneThis == debutColonneN - 1 ||
	                                    debutColonneThis == finColonneN || finColonneThis == debutColonneN);
	
	    boolean adjacenceHorizontale = (finColonneThis >= debutColonneN && debutColonneThis <= finColonneN) &&
	                                 (debutLigneThis == finLigneN + 1 || finLigneThis == debutLigneN - 1 ||
	                                  debutLigneThis == finLigneN || finLigneThis == debutLigneN);
	
	    return adjacenceHorizontale || adjacenceVerticale;
	    
	}


	public boolean chevauchebis(Navire n) {
		for (int i=n.getDebut().getLigne();i<=n.getFin().getLigne(); i++ ) {
			Coordonnee c = new Coordonnee(i,n.getDebut().getColonne());
			if (this.contient(c))
				return true;
	    }
		for (int i=n.getDebut().getColonne();i<=n.getFin().getColonne(); i++ ) {
	    	Coordonnee c = new Coordonnee(n.getDebut().getLigne(),i);
	        if (this.contient(c))
	            return true;
	        }
		return false;
	    }
	
	public boolean chevauche(Navire n) {
	    int debutLigneThis = debut.getLigne();
	    int finLigneThis = fin.getLigne();
	    int debutColonneThis = debut.getColonne();
	    int finColonneThis = fin.getColonne();
	
	    
	    int debutLigneN = n.debut.getLigne();
	    int finLigneN = n.fin.getLigne();
	    int debutColonneN = n.debut.getColonne();
	    int finColonneN = n.fin.getColonne();
	
	 
	    boolean chevauchementVerticale  = (finLigneThis >= debutLigneN && debutLigneThis <= finLigneN) &&
	                                   (debutColonneThis == finColonneN || finColonneThis == debutColonneN);
	
	    boolean chevauchementHorizontale = (finColonneThis >= debutColonneN && debutColonneThis <= finColonneN) &&
	                                 (debutLigneThis == finLigneN || finLigneThis == debutLigneN);
	
	    return chevauchementHorizontale || chevauchementVerticale;
	}
	       

	public boolean recoitTir(Coordonnee c) {
       if (this.contient(c)) {
            int indice = estVertical() ? c.getLigne() - debut.getLigne() : c.getColonne() - debut.getColonne();
            partiesTouchees[indice] = c;
            this.nbTouchees ++;
            return true;
        }return false;
    }
	
	
	
	public boolean estTouche(Coordonnee c) {
		return contient(c) && (partiesTouchees[c.getLigne() - debut.getLigne()] != null);
	}
	
	public boolean estTouche() {
		return nbTouchees > 0;
	}
	
	
	
	public boolean estCoule() {
	    for (int i = estVertical() ? debut.getLigne() : debut.getColonne();
	         i <= (estVertical() ? fin.getLigne() : fin.getColonne());
	         i++) {
	        Coordonnee c = estVertical() ? new Coordonnee(i, debut.getColonne()) : new Coordonnee(debut.getLigne(), i);
	        if (!estTouche(c)) {
	            return false;
	        }
	    }return true;
	}

}


