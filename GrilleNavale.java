package batailleNavale;

public class GrilleNavale {
	
	 	private Navire[] navires;
	    private int nbNavires;
	    private int taille;
	    private Coordonnee[] tirsRecus;
	    private int nbTirsRecus;
	    
	    /**
	      Construit une grille navale de taille donnée
	      et y place automatiquement les navires indiqués.
	     */
	    public GrilleNavale(int taille, int[] taillesNavires) {
	        this.taille = taille;
	        this.navires = new Navire[taillesNavires.length];
	        placementauto(taillesNavires);
	        this.tirsRecus = new Coordonnee[taille * taille];
	        this.nbTirsRecus = 0;
	        
	    }
	    
	    /**
	     * Construit une grille vide de taille donnée
	     * pouvant contenir un certain nombre de navires.
	     */
	    public GrilleNavale(int taille, int nbNavires) {
	        this.taille = taille;
	        this.navires = new Navire[nbNavires];
	        this.tirsRecus = new Coordonnee[taille * taille];
	        this.nbTirsRecus = 0;
	        this.nbNavires = 0;
	    }
	    	  
	    /**
	     * Retourne une représentation textuelle de la grille
	     * avec les navires, les tirs touchés et les tirs à l’eau.
	     */
	    public String toString() {
	        StringBuilder grid = new StringBuilder();
	        grid.append(" ");
	        for (int i = 0; i < taille; i++) {
	            grid.append((char) ('A' + i)).append(" ");
	        }
	        grid.append("\n");
	        for (int i = 0; i < taille; i++) {
	            grid.append(i + 1).append(" ");
	            for (int j = 0; j < taille; j++) {
	            	Coordonnee c = new Coordonnee(i, j);
	            	 if (estTouche(c)) {
	                     grid.append("X ");
	                 }
	            	 else if (estALEau(c)) {
	                     grid.append("O ");
	            	 } else {
	                         boolean navirePresent = false;
	                         for (Navire n : navires) {
	                             if (n != null && n.contient(c)) {
	                                 navirePresent = true;
	                                 break;
	                             }
	                         }
	                         if (navirePresent) {
	                             grid.append("# ");
	                         } else {
	                             grid.append(". ");
	                         }
	                     }
	                 }
	            grid.append("\n");
	        }
	        return grid.toString();
	    }
	    
	    /**
	     * Retourne la taille de la grille.
	     */
	    public int getTaille() {
	    return taille;
	  }
	    
	    /**
	     * Ajoute un navire dans la grille si celui-ci
	     * est dans les limites et ne touche aucun autre navire.
	     */
	    public boolean ajoutNavire(Navire n) {
	    	for(int i=0; i<navires.length; i++) {
	    		if(navires[i] != null) {
	    			if( navires[i].touche(n) || navires[i].chevauche(n)) return false;
	    		}
	    	}
	    	if(n.getDebut().getLigne() < 0 || n.getDebut().getLigne() >= taille ||
	    	   n.getDebut().getColonne() < 0 || n.getDebut().getColonne() >= taille ||
	    	   n.getFin().getLigne() < 0 || n.getFin().getLigne() >= taille ||
	    	   n.getFin().getColonne() < 0 || n.getFin().getColonne() >= taille) {
	    		return false;
	    	}
	    	for(int i=0; i<navires.length; i++) {
	    		if(navires[i]==null) {
	    			navires[i]=n;
	    			nbNavires++;
	    			return true;
	    		}
	    	}
	    	return false;
	    }
	    
	    /**
	     * Place automatiquement les navires dans la grille
	     * à des positions aléatoires valides.
	     */
	    public void placementauto(int [] taillesNavires) {
	    	Coordonnee c;
	    	boolean v;
	    	Navire n;
	    	 for (int i = 0; i < taillesNavires.length; i++) {
	    		 	do {
		        	c = new Coordonnee((int) (Math.random() * taille), (int) (Math.random() * taille));
		        	v = Math.random() > 0.5 ? true : false;
		            n = new Navire(c, taillesNavires[i], v); 
	    		 	}
		             while(!ajoutNavire(n));
	    		 
	    	 }
	    }
	    
	    /**
	     * Vérifie si une coordonnée appartient à la grille.
	     */
	    private boolean estDansGrille(Coordonnee c) {
	    	return 0<= c.getLigne() && c.getLigne() < this.taille && 0<=c.getColonne() && c.getColonne()<this.taille; //pas sur est ce que est juste
	    }
	    
	    /**
	     * Vérifie si une coordonnée correspond à un tir déjà reçu.
	     */
	    private boolean estDansTirsRecus(Coordonnee c) {
	    	for(int i=0; i<tirsRecus.length; i++) {
	    		if(tirsRecus[i]!= null && tirsRecus[i].equals(c)) return true;
	    	}
	    	return false;
	    }
	    
	    /**
	     * Ajoute une coordonnée dans la liste des tirs reçus
	     * si elle est valide et pas déjà présente.
	     */
	    private boolean ajouteDansTirsRecus(Coordonnee c) {
	    	if(estDansGrille(c) && !estDansTirsRecus(c) && nbTirsRecus < tirsRecus.length) {
	    		tirsRecus[nbTirsRecus]=c;
	    		nbTirsRecus++;
	    		return true;
	    	}
	    	return false;
	    }
	    
	    /**
	     * Traite un tir reçu sur la grille
	     * et indique s’il touche un navire.
	     */
	    public boolean recoitTir(Coordonnee c) {
	    	if(!estDansTirsRecus(c) && estDansGrille(c)) {
	    		tirsRecus[nbTirsRecus]=c;
				nbTirsRecus++;
	    		for(int i=0; i<navires.length; i++) {
	    			if(navires[i] != null && navires[i].recoitTir(c)) return true;
	    		}
	    	}
	    	return false;
	    }
	    
	    /**
	     * Indique si une case de la grille correspond
	     * à une partie de navire touchée.
	     */
	    public boolean estTouche(Coordonnee c) {
	    	if(!estDansGrille(c)) return false;
	    	for(int i=0; i<navires.length; i++) {
	    		if(navires[i]!= null && navires[i].estTouche(c)) return true;
	    	}
	    	return false;
	    }
	    
	    /**
	     * Indique si une case correspond à un tir dans l’eau.
	     */
	    public boolean estALEau(Coordonnee c) {
	    	if(estDansGrille(c) && estDansTirsRecus(c) && !estTouche(c)) return true;
	    	 return false;
	    }
	    
	    /**
	     * Indique si la case donnée appartient
	     * à un navire complètement coulé.
	     */
	    public boolean estCoule(Coordonnee c) {
	    	if(estDansGrille(c) && estDansTirsRecus(c) && estTouche(c)) {
	    	for(int i=0; i<navires.length; i++) {
	    		if(navires[i]!=null && navires[i].contient(c) && navires[i].estCoule()) return true;
	    		}
	    	}
	    	return false;
	    }
	    
	    /**
	     * Indique si la case donnée appartient
	     * à un navire complètement coulé.
	     */
	    public boolean perdu() {
	    	for(Navire n : navires) {
	    		if(n!= null && !n.estCoule()) return false;
	    	}
	    	return true;
	    }
	    
}
