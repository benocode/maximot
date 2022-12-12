package fr.eni.maximot;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Random;
import java.util.Scanner;

public class Jeu_Maximot {

	public static final String FICHIER_MOTS = "./dictionnaire.txt";
	public static final int NB_MOTS = 22506;
	public static Random r = new Random();
	public static final char VIDE = '_';
	
	public static void main(String[] args) {
		try {
			char[] mot = tirerMotAleatoirement();
			System.out.println("Le mot a trouvé est :");
			for (char m : mot) {
				System.out.print(m);
				}
			System.out.println();
			
			char[] tirage = melanger(mot);
			
			afficher(tirage);
			
			// saisie du mot dans la console par le joueur
			Scanner console = new Scanner(System.in);
			System.out.println("Quel est le mot caché dans ce tirage ?");
			char[] motPropose = console.nextLine().toUpperCase().toCharArray();
			
			// vérification des lettres par rapport au tirage
			boolean validation = bonnesLettres(tirage,motPropose);
			
			// vérification de l'existence du mot si ce sont les bonnes lettres
			if (validation)			
				validation = dansLeDico(tirage);
			
			if (validation == true)
				System.out.println("Félicitations le mot est correct");
			else
				System.out.println("Mot erroné");
			
			console.close();
			
		} catch (IOException e) {
			System.err.println("Problème de lecture du fichier dictionnaire");
		}
	}

	private static char[] tirerMotAleatoirement() throws IOException {
		int numMot = r.nextInt(NB_MOTS);
		try (FileInputStream fichier = new FileInputStream(FICHIER_MOTS);
				Scanner s = new Scanner(fichier)) {
			for (int i = 1; i <= numMot; i++)
				s.nextLine();
			return s.nextLine().toUpperCase().toCharArray();
		}
	}
	
	private static char[] melanger(char[] mot) {
		// clonage du tableau
		char[] mel = new char[mot.length];
		for (int i = 0; i < mot.length; i++) {
			mel[i] = mot[i];
		}
		
		// échanges de position de caractères
		for (int i = 0; i < mel.length * 4; i++) {
			int p1 = r.nextInt(mel.length);
			int p2 = r.nextInt(mel.length);
			char tmp = mel[p1];
			mel[p1] = mel[p2];
			mel[p2] = tmp;
		}
		return mel;
	}
	
	private static void afficher(char[] tirage) {
		System.out.println("Voici le tirage :");
		for (char t : tirage) {
			System.out.print(t);
			}
		System.out.println();
	}
	
	private static boolean bonnesLettres(char[] tirage,char[] motPropose) {
		// clonage du tableau des lettres à utiliser
		char[] copie = new char[tirage.length];
		for (int i = 0; i < copie.length; i++) {
			copie[i] = tirage [i];
		}
		// vérification de chaque lettre de la proposition
		int j = 0;
		boolean validation = true;
		while (validation && j < motPropose.length) {
			int k = 0;
			while (k < copie.length && motPropose[j] != copie[k]) {
				k++;
			}
			if (k == copie.length)
				validation = false;
			else {
				copie[k] = VIDE;
				j++;
			}
		}
		return validation;
	}
	
	private static boolean dansLeDico(char[] motPropose) throws IOException {
		boolean trouve = false;
		String mot1 = new String(motPropose);
		try (FileInputStream fichier = new FileInputStream(FICHIER_MOTS);
				Scanner s = new Scanner(fichier)) {
			char[] motDuDico;
			while (!trouve && s.hasNext()) {
				motDuDico = s.nextLine().toUpperCase().toCharArray();
				//trouve = Jeu_Maximot.sontIdentiques(motPropose, motDuDico);
				String mot2 = new String(motDuDico);
				trouve = mot1.equals(mot2);
			}
		}
		return trouve;
	}
/**	
	private static boolean sontIdentiques(char[] mot1, char[] mot2) {
		boolean ok = mot1.length == mot2.length;
		if (ok) {
			int i = 0;
			while (ok && i < mot1.length) {
				ok = mot1[i] == mot2[i];
				i++;
			}
		}
		return ok;
	}*/
}
