package batailleNavale;

public abstract class Joueur {
	public final static int TOUCHE=1;
	public final static int COULE=2;
	public final static int A_L_EAU=3;
	public final static int GAMEOVER=4;
	
	private Joueur adversaire;
	private int tailleGrille;
	private String nom;
	
	public Joueur(int tailleGrille, String nom ) {
		this.nom=nom;
		this.tailleGrille = tailleGrille;
	}
	
	public Joueur(int tailleGrille) {
		this.tailleGrille=tailleGrille;
	}
	
	public int getTailleGrille() {
		return tailleGrille;
	}
	
	public String getName() {
		return nom;
	}
	
	public void jouerAvec(Joueur j) {
		this.adversaire=j;
		j.adversaire= this;
		deroulementJeu(this, j);
	}
	
	public static void deroulementJeu(Joueur attaquant, Joueur defenseur) {
		int res=0;
		while(res != GAMEOVER) {
			Coordonnee c = attaquant.choixAttaque();
			res = defenseur.defendre(c);
			attaquant.retourAttaque(c, res);
			defenseur.retourDefence(c, res);
			Joueur x = attaquant;
			attaquant = defenseur;
			defenseur = x;
			}
	}
	protected abstract void retourAttaque(Coordonnee c, int etat);
	protected abstract void retourDefence(Coordonnee c, int etat);
	public abstract Coordonnee choixAttaque();
	public abstract int defendre(Coordonnee c);
}
