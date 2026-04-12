package batailleNavale;

import java.util.Scanner;

public class JoueurTexte extends JoueurAvecGrille{
	
	private Scanner sc;
	
	public JoueurTexte(GrilleNavale g, String nom) {
		super(g, nom);
	}

	public JoueurTexte(GrilleNavale g) {
		super(g);
		
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
		this.sc = new Scanner(System.in);
		 while (true) {
		        System.out.print("Attaque en ? : ");
		        String s = this.sc.next();

		        try {
		            Coordonnee c = new Coordonnee(s);

		            if (c.getColonne() < 0 || c.getColonne() >= this.getTailleGrille()
		                    || c.getLigne() < 0 || c.getLigne() >= this.getTailleGrille()) {
		                System.out.println("Attaque hors champ de bataille, [1, " + this.getTailleGrille()
		                        + "] * [1, " + this.getTailleGrille() + "]");
		            } else {
		                return c;
		            }
		        } catch (IllegalArgumentException e) {
		            System.out.println("Coordonnée incorrecte : merci de respecter le format \"charInt\".");
		        }
		    }
	}

}
