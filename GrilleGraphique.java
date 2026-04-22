package batailleNavale;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;


class JButtonCoordonnee extends JButton {
	
	private Coordonnee c;
	public JButtonCoordonnee(Coordonnee c) {
		this.c=c;
	}
	
	public Coordonnee getCoordonnee() {
		return c;
	}
}
/**
 * Classe repr�sentant un composant graphique "Grille". Une grille est compos�e
 * de JButton
 * 
 * @author jerome.david@univ-grenoble-alpes.fr
 * 
 */
public class GrilleGraphique extends JPanel implements ActionListener {

	private static final long serialVersionUID = 8857166149660579225L;

	/**
	 * La matrice des boutons (cases de la grille)
	 */
	private JButton[][] cases;

	/** 
	 * La coordonnee actuellement selectionn�e.
	 * Null si aucune selection en cours
	 */
	private Coordonnee coordonneeSelectionnee;

	/**
	 * Initialise une grille carr�e de taille donn�e
	 * 
	 * @param taille
	 *            la taille de la grille
	 */
	public GrilleGraphique(int taille) {
		try {
			// Certains LookAndFeels ne colorient pas les boutons.
			// on choisi celui le plus simple (et le moins joli)
			UIManager.setLookAndFeel(UIManager
					.getCrossPlatformLookAndFeelClassName());
		} catch (ClassNotFoundException | InstantiationException
				| IllegalAccessException | UnsupportedLookAndFeelException e) {
			e.printStackTrace();
		}

		this.setLayout(new GridLayout(taille + 1, taille + 1));

		this.add(new JLabel());
		for (int i = 0; i < taille; i++) {
			JLabel lbl = new JLabel(String.valueOf((char) ('A' + i)));
			lbl.setHorizontalAlignment(JLabel.CENTER);
			this.add(lbl);
		}
		cases = new JButton[taille][taille];
		for (int i = 0; i < taille; i++) {
			JLabel lbl = new JLabel(String.valueOf(i + 1));
			lbl.setHorizontalAlignment(JLabel.CENTER);
			this.add(lbl);
			for (int j = 0; j < taille; j++) {
				cases[i][j] = new JButtonCoordonnee(new Coordonnee(i,j));
				this.add(cases[i][j]);
				cases[i][j].addActionListener(this);
			}
		}
		coordonneeSelectionnee=null;
	}

	/**
	 * Colorie la case indiqu�e par la coordonn�e
	 * 
	 * @param coord
	 *            la coordonn�e de la case � colorier
	 * @param color
	 *            la couleur de la case
	 */
	public void colorie(Coordonnee cord, Color color) {
		cases[cord.getLigne()][cord.getColonne()].setBackground(color);
	}

	/**
	 * Colorie le rectangle compris entre les deux coordonnees
	 * 
	 * @param debut
	 *            Coordonn�e du d�but de la zone � colorier (haut gauche)
	 * @param fin
	 *            Coordonn�e de la fin de la zone � colorier (bas droit)
	 * @param color
	 *            la couleur de la case
	 */
	public void colorie(Coordonnee debut, Coordonnee fin, Color color) {
		for (int i = debut.getLigne(); i <= fin.getLigne(); i++) {
			for (int j = debut.getColonne(); j <= fin.getColonne(); j++) {
				cases[i][j].setBackground(color);
			}
		}

	}

	@Override
	public Dimension getPreferredSize() {
		Dimension d = super.getPreferredSize();
		d.setSize(d.width, d.width);
		return d;
	}

	public void setClicActive(boolean active) {
		SwingUtilities.invokeLater(() -> {
			this.setEnabled(false);
			for (JButton[] ligne : cases) {
				for (JButton bt : ligne) {
					bt.setEnabled(active);
				}
			}
			this.setEnabled(true);
		});
	}


	/**
	 * Methode appel�e lorsque l'on clique sur une case de la grille.
	 * Elle "reveille" la m�thode getCoordonneeSelectionnee
	 * 
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		this.setClicActive(false);
		coordonneeSelectionnee = ((JButtonCoordonnee) e.getSource()).getCoordonnee();
		 synchronized (this) {
	            this.notifyAll();
	        }
	}
	
	 /**
     * Attend que l'utilisateur selectionne (clic) sur une case de la grille et
     * retourne la coordonnee qui a �t� selectionn�e
     * @return la coordonn�e selectionn�e
     */
    public synchronized Coordonnee getCoordonneeSelectionnee() {
        this.setClicActive(true);
        try {
            this.wait();
        } catch (InterruptedException ex) {
            throw new RuntimeException(ex);
        }
        return coordonneeSelectionnee;
    }
}