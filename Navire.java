package batailleNavale;

public class Navire {

	
	private Coordonnee debut;
	private Coordonnee fin;
	private Coordonnee[] partiesTouchees;
	private int nbTouchees;

	/**
     * Construit un navire à partir d'une coordonnée de début
     * d'une longueur et d'une orientation.
     */
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

	/**
     * Retourne une représentation textuelle du navire
     * avec sa position, sa longueur et son orientation.
     */
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
		
	/**
     * Retourne la coordonnée de début du navire.
     */
	public Coordonnee getDebut() {
		return debut;
	}
	
	/**
     * Retourne la coordonnée de début du navire.
     */
	public Coordonnee getFin() {
		return fin;
	}
	
	 /**
     * Indique si le navire est orienté verticalement.
     */
	public boolean estVertical() {
		return debut.getColonne() == fin.getColonne();
	}

	/**
    * Vérifie si une coordonnée appartient au navire.
    */
	public boolean contient(Coordonnee c) {
		return c.getLigne() >= debut.getLigne() && c.getLigne() <= fin.getLigne() &&
	               c.getColonne() >= debut.getColonne() && c.getColonne() <= fin.getColonne();
	}
	
	
	/**
     * Vérifie si ce navire touche un autre navire.
     */
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

	/**
     * Vérifie si ce navire chevauche un autre navire
     * en parcourant les coordonnées de celui-ci.
     */
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
	
	/**
     * Vérifie si ce navire chevauche un autre navire.
     */
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
	       
	 /**
     * Reçoit un tir sur le navire et marque la partie touchée si nécessaire.
     */
	public boolean recoitTir(Coordonnee c) {
       if (this.contient(c)) {
            int indice = estVertical() ? c.getLigne() - debut.getLigne() : c.getColonne() - debut.getColonne();
            if (partiesTouchees[indice] == null) {
            partiesTouchees[indice] = c;
            this.nbTouchees ++;
            }
            return true;
        }return false;
    }
	
	
	/**
     * Vérifie si une case précise du navire a été touchée.
     */
	public boolean estTouche(Coordonnee c) {
		 if (!contient(c)) {
		        return false;
		    }

		    int index;
		    if (debut.getLigne() == fin.getLigne()) { // horizontal
		        index = c.getColonne() - debut.getColonne();
		    } else { // vertical
		        index = c.getLigne() - debut.getLigne();
		    }

		    return partiesTouchees[index] != null;
	}
	
	/**
     * Indique si le navire a été touché au moins une fois.
     */
	public boolean estTouche() {
		return nbTouchees > 0;
	}
	
	
	/**
     * Indique si toutes les parties du navire ont été touchées.
     */
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


