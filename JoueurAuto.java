package batailleNavale;

public class JoueurAuto extends JoueurAvecGrille{

	public JoueurAuto(GrilleNavale g) {
		super(g);
	}
	public JoueurAuto(GrilleNavale g, String nom) {
		super(g, nom);

	}

	@Override
	protected void retourAttaque(Coordonnee c, int etat) {
		System.out.println("\nRésultat de l'attaque :");
		if (etat == TOUCHE)
		    System.out.println(this.getName() + " >>> A touché une partie \"" + c + "\" d’un navire ennemi.");
		else if (etat == COULE)
		    System.out.println(this.getName() + " >>> A coulé un navire ennemi à \"" + c + "\".");
		else if (etat == A_L_EAU)
		    System.out.println(this.getName() + " >>> Tir dans l’eau à \"" + c + "\".");
		else
		    System.out.println(this.getName() + " >>> Bravo ! Toute la flotte ennemie est coulée !");	
	}

	@Override
	protected void retourDefence(Coordonnee c, int etat) {
		System.out.println("\nRésultat de la défense :");
		if (etat == TOUCHE)
		    System.out.println(this.getName() + " >>>  Alerte ! Navire touché en \"" + c + "\"");
		else if (etat == COULE)
		    System.out.println(this.getName() + " >>>  Explosion ! Navire coulé en \"" + c + "\"");
		else if (etat == A_L_EAU)
		    System.out.println(this.getName() + " >>>  Tir ennemi raté en \"" + c + "\"");
		else
		    System.out.println(this.getName() + " >>>  GAME OVER ! Flotte détruite !");
	}

	@Override
	public Coordonnee choixAttaque() {
		int ligne = (int) (this.getTailleGrille() * Math.random());
	    int colonne = (int) (this.getTailleGrille() * Math.random());
	    return new Coordonnee(ligne, colonne);
	}

}
