package jestPackage;

import java.util.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

public class Jeu implements Serializable{
	private static final long serialVersionUID = 1L;
	private ArrayList<Joueur> joueurs = new ArrayList<Joueur>();
	private ArrayList<Carte> trophees = new ArrayList<Carte>();
	private ArrayList<Integer> joueurPourTrophee = new ArrayList<Integer>();// Le trophée i est attribué au joueur
																			// joueurPourTrophee.get(i)
	private Pioche pioche;
	private Tour tour;
	
	private int extension;
	private int variante; 
	
	private static final String SAVE_DIRECTORY = "saves/";
    private static final String SAVE_EXTENSION = ".jest";
	
	private static Jeu instance = null;

	private Jeu() {
		this.pioche = new Pioche(this);
	}
	
	public static Jeu getInstance(){
		if (instance == null) {
			instance = new Jeu();
		}        
		return instance;
	}

	public ArrayList<Joueur> getJoueurs() {
		return this.joueurs;
	}

	public Pioche getPioche() {
		return this.pioche;
	}
	
	public int getExtension() {
        return this.extension;
    }
		
	public void setTropheeJeu() {
		if (this.joueurs.size() == 3) {
			for (int i = 0; i < 2; i++) {
				this.trophees.add(pioche.piocher());
			}
		} else {
			this.trophees.add(pioche.piocher());
		}
		// afficher quels sont les trophées du jeu
		System.out.println("Les trophées du jeu sont : ");
		for (Carte carte : this.trophees) {
			System.out.println("- " + carte.getNom());
			// Afficher la condition du trophée
			System.out.println("  Condition : " + carte.getBandeauTrophee().toString());
		}
	}

	public ArrayList<Carte> getTrophees() {
		return this.trophees;
	}

	public void initialiserJoueurs() {
		Scanner scanner = new Scanner(System.in);
		System.out.println("Bienvenue dans le jeu Jest !");
		int nombreJoueurs = 0;
		while (nombreJoueurs < 3 || nombreJoueurs > 4) {
			System.out.println("Entrez le nombre de joueurs (3 ou 4) : ");
			nombreJoueurs = scanner.nextInt();
			if (nombreJoueurs < 3 || nombreJoueurs > 4) {
				System.out.println("Nombre de joueurs invalide. Le jeu nécessite 3 ou 4 joueurs.");
			}
		}
		int compteurJoueur = 1;
		while (compteurJoueur <= nombreJoueurs) {
			System.out.println("Est-ce que le joueur " + compteurJoueur
					+ " est un reel ou virtuel ? (0 pour reel et 1 pour virtuel) : ");
			int joueurIsVirtuel = scanner.nextInt();
			if (joueurIsVirtuel == 0) {
				System.out.println("Creation du joueur reel \"Joueur " + compteurJoueur + "\".");
				this.joueurs.add(new JoueurReel("Joueur " + compteurJoueur, compteurJoueur));
				compteurJoueur++;
			} else if (joueurIsVirtuel == 1) {
				System.out.println("Choisissez la stratégie de prise pour le joueur " + compteurJoueur
						+ " (1 pour prudente, 2 pour aléatoire) : ");
				int numStrategy = scanner.nextInt();
				System.out.println("Creation du joueur virtuel \"Joueur " + compteurJoueur + "\".");
				this.joueurs.add(new JoueurVirtuel("Joueur " + compteurJoueur + " (IA)", compteurJoueur, numStrategy));
				compteurJoueur++;
			} else {
				System.out.println("Valeur invalide. Veuillez entrer 0 pour reel ou 1 pour virtuel.");
			}
		}
	}

	public void initialiserPioche() {
		this.pioche.initialiserCartes();
		this.pioche.melangerCartes();
	}
	
	
	public void attribuerTropheePlusGrandJest(ArrayList<Joueur> joueurs , ArrayList<Integer> scoresAvantTrophees, Carte carteTrophee) { // Méthode pour simplifier le code, on peut l'appeler pour plus grand jest et plus grand jest sans joker
		// Le joueur ayant le plus grand score remporte le trophée
		int maxScore = Collections.max(scoresAvantTrophees);
		ArrayList<Integer> joueursGagnantsTrophee = new ArrayList<Integer>();
		for (int i = 0; i < scoresAvantTrophees.size(); i++) {
			if (scoresAvantTrophees.get(i) == maxScore) {
				joueursGagnantsTrophee.add(i);
			}
		}
		if (joueursGagnantsTrophee.size() == 1) {
			Joueur gagnantTrophee = this.joueurs.get(joueursGagnantsTrophee.get(0));
			this.joueurPourTrophee.add(gagnantTrophee.getNumJoueur());
			//gagnantTrophee.ajouterAsonJest(carteTrophee); Legacy
			System.out.println("Le trophée " + carteTrophee.getNom() + " est remporté par Joueur "
					+ gagnantTrophee.getNumJoueur() + " !");
		} else {
			// Gérer les égalités si nécessaire
			System.out.println("Égalité pour le trophée " + carteTrophee.getNom()
					+ ". On regarde qui a la carte de plus haute valeur d'abord");
			// Pas faire la même erreur deux fois : on utilise la méthode plus haute valeur dans Jest
			ArrayList<Integer> valeurMaxPourEgalite = new ArrayList<Integer>();
			for (Integer indiceJoueur : joueursGagnantsTrophee) {
				Joueur joueur = this.joueurs.get(indiceJoueur);
				int valeurMax = joueur.getJest().plusHauteValeur();
				valeurMaxPourEgalite.add(valeurMax);
			}
			// Ici encore on peut avoir une égalité, dans quel cas il faut trouver le joueur 
			// dont la carte la plus haute a la valeur de couleur la plus haute
			int maxValeurEgalite = Collections.max(valeurMaxPourEgalite);
			ArrayList<Integer> finalistes = new ArrayList<Integer>();
			
			for (int i = 0; i < valeurMaxPourEgalite.size(); i++) { 
			    if (valeurMaxPourEgalite.get(i) == maxValeurEgalite) {
			        finalistes.add(joueursGagnantsTrophee.get(i));
			    }
			}
			
			
			if(finalistes.size()==1) {
				Joueur gagnantTrophee = this.joueurs.get(finalistes.get(0));
				this.joueurPourTrophee.add(gagnantTrophee.getNumJoueur());
				//gagnantTrophee.ajouterAsonJest(carteTrophee); Legacy
				System.out.println("Le trophée " + carteTrophee.getNom() + " est remporté par Joueur "
						+ gagnantTrophee.getNumJoueur() + " !");
			} else {
				// il faut donc trouver le joueur dont la carte la plus haute à la valeur de couleur la plus haute
				// on utilise la méthode valeurDeCouleurDePlusHauteValeur dans Jest
				ArrayList<Integer> valeurCouleurMaxPourEgalite = new ArrayList<Integer>();
				for (Integer indiceJoueur : finalistes) {
					Joueur joueur = this.joueurs.get(indiceJoueur);
					int valeurCouleurMax = joueur.getJest().valeurDeCouleurDePlusHauteValeur();
					valeurCouleurMaxPourEgalite.add(valeurCouleurMax);
				}
				int maxValeurCouleurEgalite = Collections.max(valeurCouleurMaxPourEgalite);
				ArrayList<Integer> finalistes2 = new ArrayList<Integer>();
				for (int i = 0; i < valeurCouleurMaxPourEgalite.size(); i++) {
					if (valeurCouleurMaxPourEgalite.get(i) == maxValeurCouleurEgalite) {
						finalistes2.add(finalistes.get(i));
					}
				}
				if (finalistes2.size() == 1) {
					Joueur gagnantTrophee = this.joueurs.get(finalistes2.get(0));
					this.joueurPourTrophee.add(gagnantTrophee.getNumJoueur());
					//gagnantTrophee.ajouterAsonJest(carteTrophee); Legacy
					System.out.println("Le trophée " + carteTrophee.getNom() + " est remporté par Joueur "
							+ gagnantTrophee.getNumJoueur() + " !");
				} else {
					System.out.println("Égalité parfaite pour le trophée " + carteTrophee.getNom()
							+ ". Cela est impossible, il y a un bug"); // On ne devrait jamais arriver ici
																		// normalement
				}
			}
		} 
	}

	public void determinerGagnants(CalculateurDeScore calculateur) {
		// calculer les scores de chaque joueur avant les trophées (nécéssaire car
		// certains trophées dépendent du score)
		
		ArrayList<Integer> scoresAvantTrophees = new ArrayList<Integer>(); // l'élément i correspond au score du joueur
																			// i+1 (élement i de la liste joueurs)
		for (Joueur joueur : this.joueurs) {
			scoresAvantTrophees.add(calculateur.getScore(joueur.getJest().getCartes()));
		}
		
		
		// attribuer les trophées. On ne peut pas ajouter directement les trophées au jest car les trophées doivent etre attribués simultanément
		// on utilisera la liste joueurPourTrophee pour ajouter les trophées aux jests des joueurs après cette boucle
		
		for (Carte carteTrophee : this.trophees) {
			if (carteTrophee.getBandeauTrophee().estTropheeCouleur()) {
				// Deux cas possibles de trophées de couleur
				
				// cas 1 : plus grande carte de la couleur
				
				if (((TropheeCouleur) carteTrophee.getBandeauTrophee()).getOrdre() == OrdreTropheeCouleur.PLUSGRAND) {
					//	pour chaque joueur, on cherche la plus grande valeur de la couleur
					ArrayList<Integer> plusGrandeValeurPourJoueur = new ArrayList<Integer>();
					for (Joueur joueur : this.joueurs) {
						plusGrandeValeurPourJoueur.add(joueur.getJest().plusHauteValeurDeLaCouleur(
								((TropheeCouleur) carteTrophee.getBandeauTrophee()).getCouleurDeCondition()));
					}
					// On cherche le maximum de cette liste
					int MaxValeur = Collections.max(plusGrandeValeurPourJoueur);
					// On cherche les joueurs qui ont ce maximum
					ArrayList<Integer> joueursGagnantsTrophee = new ArrayList<Integer>();
					for (int i = 0; i < plusGrandeValeurPourJoueur.size(); i++) {
						if (plusGrandeValeurPourJoueur.get(i) == MaxValeur) {
							// Afficher le joueur gagnant le trophée
							Joueur gagnantTrophee = this.joueurs.get(i);
							this.joueurPourTrophee.add(gagnantTrophee.getNumJoueur());
							System.out.println("Joueur " + gagnantTrophee.getNumJoueur()
									+ " a la plus grande carte de la couleur "
									+ ((TropheeCouleur) carteTrophee.getBandeauTrophee()).getCouleurDeCondition()
									+ " avec une valeur de " + MaxValeur + ".");
							
						}
					}

				}

				// cas 2 : plus petite carte de la couleur
				else if (((TropheeCouleur) carteTrophee.getBandeauTrophee())
						.getOrdre() == OrdreTropheeCouleur.PLUSPETIT) {
					//	pour chaque joueur, on cherche la plus petite valeur de la couleur
					ArrayList<Integer> plusPetiteValeurPourJoueur = new ArrayList<Integer>();
					for (Joueur joueur : this.joueurs) {
						plusPetiteValeurPourJoueur.add(joueur.getJest().plusPetiteValeurDeLaCouleur(
								((TropheeCouleur) carteTrophee.getBandeauTrophee()).getCouleurDeCondition()));
					}
					// On cherche le minimum de cette liste
					int MinValeur = Collections.min(plusPetiteValeurPourJoueur);
					// On cherche les joueurs qui ont ce minimum
					ArrayList<Integer> joueursGagnantsTrophee = new ArrayList<Integer>();
					for (int i = 0; i < plusPetiteValeurPourJoueur.size(); i++) {
						if (plusPetiteValeurPourJoueur.get(i) == MinValeur) {
							// Afficher le joueur gagnant le trophée
							Joueur gagnantTrophee = this.joueurs.get(i);
							this.joueurPourTrophee.add(gagnantTrophee.getNumJoueur());
							System.out.println(
									"Joueur " + gagnantTrophee.getNumJoueur() + " a la plus petite carte de la couleur "
											+ ((TropheeCouleur) carteTrophee.getBandeauTrophee())
													.getCouleurDeCondition()
											+ " avec une valeur de " + MinValeur + ".");
						}
					}

				}
			// Fin des trophées de couleur : le else couvre les trophées incolores
			} else {
				// Quatres cas possibles de trophées incolores
				// cas 1 : le plus compliqué : majorité de la valeur du trophee
				if (((TropheeIncolore) carteTrophee.getBandeauTrophee()).getCondition() == ConditionIncolore.MAJORITÉ) {
					// On stocke le nombre de cartes de la valeur que possède chaque joueur
					ArrayList<Integer> nombreCartesValeurPourJoueur = new ArrayList<Integer>();
					for (Joueur joueur : this.joueurs) {
						nombreCartesValeurPourJoueur.add(joueur.getJest().nbCarteDeLaValeur(
								((TropheeIncolore) carteTrophee.getBandeauTrophee()).getValeurAssociée()));
					}
					// On cherche le maximum de cette liste
					int MaxCartes = Collections.max(nombreCartesValeurPourJoueur);
					// On cherche les joueurs qui ont ce maximum
					ArrayList<Integer> joueursGagnantsTrophee = new ArrayList<Integer>();
					for (int i = 0; i < nombreCartesValeurPourJoueur.size(); i++) {
						if (nombreCartesValeurPourJoueur.get(i) == MaxCartes && MaxCartes > 0) {
							joueursGagnantsTrophee.add(i); // on ajoute l'indice du joueur gagnant
						}
					}
					// S'il n'y a qu'un seul joueur gagnant, il remporte le trophée
					if (joueursGagnantsTrophee.size() == 1) {
						Joueur gagnantTrophee = this.joueurs.get(joueursGagnantsTrophee.get(0));
						this.joueurPourTrophee.add(gagnantTrophee.getNumJoueur());
						//gagnantTrophee.ajouterAsonJest(carteTrophee); Legacy
						System.out.println("Le trophée " + carteTrophee.getNom() + " est remporté par Joueur "
								+ gagnantTrophee.getNumJoueur() + " !");
					} 
					else {
						// Gérer les égalités : complexe.
						// On cherche le joueur ayant la carte de la valeur du trophée avec la plus
						// haute valeur de couleur
						ArrayList<Integer> valeurDeCouleurPourGagnant = new ArrayList<Integer>();// la plus haute valeur
																									// de couleur du
																									// joueur
						for (Integer indiceJoueur : joueursGagnantsTrophee) {
							Joueur joueur = this.joueurs.get(indiceJoueur);
							int valeurCouleurMax = 0;
							for (Carte carte : joueur.getJest().getCartes()) {
								if (carte.getValeurBase() == ((TropheeIncolore) carteTrophee.getBandeauTrophee())
										.getValeurAssociée()) {
									if (carte.getValeurCouleur() > valeurCouleurMax) {
										valeurCouleurMax = carte.getValeurCouleur();
									}
								}
							}
							valeurDeCouleurPourGagnant.add(valeurCouleurMax);
						}
						int maxValeurCouleur = Collections.max(valeurDeCouleurPourGagnant);
						ArrayList<Integer> finalistes = new ArrayList<Integer>();
						for (int i = 0; i < valeurDeCouleurPourGagnant.size(); i++) {
							if (valeurDeCouleurPourGagnant.get(i) == maxValeurCouleur) {
								finalistes.add(joueursGagnantsTrophee.get(i));
							}
						}
						if (finalistes.size() == 1) {
							Joueur gagnantTrophee = this.joueurs.get(finalistes.get(0));
							this.joueurPourTrophee.add(gagnantTrophee.getNumJoueur());
							//gagnantTrophee.ajouterAsonJest(carteTrophee);Legacy
							System.out.println("Le trophée " + carteTrophee.getNom() + " est remporté par Joueur "
									+ gagnantTrophee.getNumJoueur() + " !");
						} else {
							System.out.println("Égalité parfaite pour le trophée " + carteTrophee.getNom()
									+ ". Cela est impossible, il y a un bug"); // On ne devrait jamais arriver ici
																				// normalement
						}
						// Pfiou
						// A simplifier en ajoutant une methode dans jest qui retourne la plus haute valeur de couleur d'une valeur donnée
					}
					

				}// Accolade de la fermeture du cas majorité
					// cas 2 : JOKER
				else if (((TropheeIncolore) carteTrophee.getBandeauTrophee()).getCondition() == ConditionIncolore.JOKER) {
					// Le joueur possédant le joker remporte le trophée
					// variable pour verifier si aucun joueur n'a le joker :
					boolean jokerTrouve = false;
					
					for (Joueur joueur : this.joueurs) {
						for (Carte carte : joueur.getJest().getCartes()) {
							if (carte.getNom().equals("Joker")) {
								this.joueurPourTrophee.add(joueur.getNumJoueur());
								//joueur.ajouterAsonJest(carteTrophee); Legacy
								System.out.println("Le trophée " + carteTrophee.getNom() + " est remporté par Joueur "
										+ joueur.getNumJoueur() + " !");
								break;
							}
						}
					}
					if (!jokerTrouve) {
						// On ajoute un -1 pour indiquer qu'aucun joueur n'a remporté ce trophée
						this.joueurPourTrophee.add(-1);
					}
				}
				
				// cas 3 : PLUSGRANDJEST
				else if (((TropheeIncolore) carteTrophee.getBandeauTrophee())
						.getCondition() == ConditionIncolore.PLUSGRANDJEST) {
					attribuerTropheePlusGrandJest(this.joueurs, scoresAvantTrophees, carteTrophee);
					
				} // Accolade de fermeture du cas 3
				
				// cas 4 : PLUSGRANDJEST_SANSJOKER
				else if (((TropheeIncolore) carteTrophee.getBandeauTrophee()).getCondition() == ConditionIncolore.PLUSGRANDJEST_SANSJOKER){
						// Il nous faut une liste des joueurs sans le joueur ayant le joker s'il existe
					    ArrayList<Joueur> joueursSansJoker = new ArrayList<Joueur>();
						for (Joueur joueur : this.joueurs) {
							boolean possedeJoker = false;
							for (Carte carte : joueur.getJest().getCartes()) {
								if (carte.getNom().equals("Joker")) {
									possedeJoker = true;
									break;
								}
							}
							if (!possedeJoker) {
								joueursSansJoker.add(joueur);
							}
						}
						attribuerTropheePlusGrandJest(joueursSansJoker, scoresAvantTrophees, carteTrophee);
					
				} // Accolade de fermeture du cas 4

			}

		}// Fin de la boucle sur tous les trophées
		// Maintenant on peut ajouter les trophées aux jests des joueurs
		
		// On vérifie si un trophée n'a pas été attribué (on aurait un -1 dans la liste)
		for (Carte carteTrophee : this.trophees) {
			if(this.joueurPourTrophee.get(this.trophees.indexOf(carteTrophee))==-1) {
				System.out.println("Le trophée " + carteTrophee.bandeauTrophee.toString() + " n'a été attribué à aucun joueur.");
				continue;
			}
			else{	
				int indiceJoueurGagnant = this.joueurPourTrophee.get(this.trophees.indexOf(carteTrophee)) - 1;// -1 car les numéros de joueurs commencent à 1
				Joueur joueurGagnantTrophee = this.joueurs.get(indiceJoueurGagnant);
				joueurGagnantTrophee.ajouterAsonJest(carteTrophee);	
			}
					
		}

		
		// On affiche tout les jests finaux des joueurs
		for (Joueur joueur : this.joueurs) {
			System.out.println("Jest final du Joueur " + joueur.getNumJoueur() + " : ");
			joueur.getJest().afficherJest();
		}
		
		
		// Il faut maitenant calculer les scores finaux et déterminer l'ordre des gagnants
		ArrayList<Integer> scoresFinaux = new ArrayList<Integer>();
		for (Joueur joueur : this.joueurs) {
			scoresFinaux.add(calculateur.getScore(joueur.getJest().getCartes()));
		}
		
		
		// A FAIRE : GERER LES EGALITES DE SCORE FINAUX
		
		// On détermine l'ordre des gagnants
		ArrayList<Integer> ordreGagnants = new ArrayList<Integer>();
		while (ordreGagnants.size() < this.joueurs.size()) {
			int maxScoreFinal = Collections.max(scoresFinaux);
			int indiceGagnant = scoresFinaux.indexOf(maxScoreFinal);
			ordreGagnants.add(indiceGagnant);
			scoresFinaux.set(indiceGagnant, Integer.MIN_VALUE); // On remplace le score par -1 pour ne pas le reperer à nouveau
		}
		System.out.println("Classement final des joueurs : ");
		
		
		if (variante == 0) {
			for (int i = 0; i < ordreGagnants.size(); i++) {
				int indiceJoueur = ordreGagnants.get(i);
				Joueur joueurGagnant = this.joueurs.get(indiceJoueur);
				int scoreFinal = calculateur.getScore(joueurGagnant.getJest().getCartes());
				System.out
						.println((i + 1) + " : Joueur " + joueurGagnant.getNumJoueur() + " avec un score de " + scoreFinal);
			}
		}
		if (variante == 1) {
			System.out.println("Variante inversée : le joueur avec le score le plus bas gagne !");
			
			for (int i = 0; i < ordreGagnants.size(); i++) {
			    // On récupère l'index en partant de la fin de la liste
			    int indexInversé = ordreGagnants.size() - 1 - i;
			    int indiceJoueur = ordreGagnants.get(indexInversé);
			    
			    Joueur joueurGagnant = this.joueurs.get(indiceJoueur);
			    int scoreFinal = calculateur.getScore(joueurGagnant.getJest().getCartes());
			    
			    // (i + 1) reste le rang d'affichage (1er, 2ème, etc.)
			    System.out.println((i + 1) + " : Joueur " + joueurGagnant.getNumJoueur() + " avec un score de " + scoreFinal);
			}
			
		}
		

	}
	
	public void initialiserExtension() {
		Scanner scanner = new Scanner(System.in);
		System.out.println("Choisissez l'extension du jeu (0 pour classique, 1 pour plus de cartes) : ");
		int extensionChoisie = scanner.nextInt();
		if (extensionChoisie == 0 || extensionChoisie == 1) {
			this.extension = extensionChoisie;
			System.out.println("Extension choisie : " + (extensionChoisie == 0 ? "Classique" : "Plus de cartes"));
		} else {
			System.out.println("Extension invalide. L'Extension classique sera utilisée par défaut.");
			this.extension = 0;
		}
	}
	
	public void initialiserVariante() {
		Scanner scanner = new Scanner(System.in);
		System.out.println("Choisissez la Variante du jeu (0 pour classique, 1 pour inversé) : ");
		int varianteChoisie = scanner.nextInt();
		if (varianteChoisie == 0 || varianteChoisie == 1) {
			this.variante = varianteChoisie;
			System.out.println("Variante choisie : " + (varianteChoisie == 0 ? "Classique" : "Inversé"));
		} else {
			System.out.println("Variante invalide. La variante classique sera utilisée par défaut.");
			this.variante = 0;
		}
	}
	
	public boolean sauvegarderPartie(String nomFichier) {
        // Créer le dossier de sauvegardes s'il n'existe pas
        File directory = new File(SAVE_DIRECTORY);
        if (!directory.exists()) {
            directory.mkdirs();
        }
        
        String cheminComplet = SAVE_DIRECTORY + nomFichier + SAVE_EXTENSION;
        
        try (FileOutputStream fos = new FileOutputStream(cheminComplet);
             ObjectOutputStream oos = new ObjectOutputStream(fos)) {
            
            oos.writeObject(this);
            System.out.println("Partie sauvegardée avec succès dans : " + cheminComplet);
            return true;
            
        } catch (IOException e) {
            System.err.println("Erreur lors de la sauvegarde : " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }
	
	public static Jeu chargerPartie(String nomFichier) {
        String cheminComplet = SAVE_DIRECTORY + nomFichier + SAVE_EXTENSION;
        
        try (FileInputStream fis = new FileInputStream(cheminComplet);
             ObjectInputStream ois = new ObjectInputStream(fis)) {
            
            Jeu jeuCharge = (Jeu) ois.readObject();
            instance = jeuCharge; // Mettre à jour le Singleton
            System.out.println("Partie chargée avec succès depuis : " + cheminComplet);
            return jeuCharge;
            
        } catch (FileNotFoundException e) {
            System.err.println("Fichier de sauvegarde non trouvé : " + cheminComplet);
            return null;
        } catch (IOException e) {
            System.err.println("Erreur de lecture : " + e.getMessage());
            return null;
        } catch (ClassNotFoundException e) {
            System.err.println("Erreur de désérialisation : " + e.getMessage());
            return null;
        }
    }
	
	public static ArrayList<String> listerSauvegardes() {
        ArrayList<String> sauvegardes = new ArrayList<>();
        File directory = new File(SAVE_DIRECTORY);
        
        if (directory.exists() && directory.isDirectory()) {
            File[] fichiers = directory.listFiles((dir, name) -> name.endsWith(SAVE_EXTENSION));
            if (fichiers != null) {
                for (File fichier : fichiers) {
                    // Retirer l'extension pour l'affichage
                    String nom = fichier.getName();
                    sauvegardes.add(nom.substring(0, nom.length() - SAVE_EXTENSION.length()));
                }
            }
        }
        return sauvegardes;
    }
	
	public static boolean supprimerSauvegarde(String nomFichier) {
        String cheminComplet = SAVE_DIRECTORY + nomFichier + SAVE_EXTENSION;
        File fichier = new File(cheminComplet);
        
        if (fichier.exists() && fichier.delete()) {
            System.out.println("Sauvegarde supprimée : " + nomFichier);
            return true;
        } else {
            System.err.println("Impossible de supprimer : " + nomFichier);
            return false;
        }
    }
	public int getNumeroTourActuel() {
        return tour.getNumeroTour();
    }
    
    public void setNumeroTourActuel(int numero) {
        this.tour.setNumeroTour(numero);
    }
    
    public int getNumTour() {
        return this.tour.getNumeroTour();
    }
    
    
    public class GestionnairePartie {
        private static Scanner scanner = new Scanner(System.in);

         //Affiche le menu principal et retourne le choix

        public static int afficherMenuPrincipal() {
            System.out.println("\n╔════════════════════════════════════╗");
            System.out.println("║         BIENVENUE DANS JEST        ║");
            System.out.println("╠════════════════════════════════════╣");
            System.out.println("║  1. Nouvelle partie                ║");
            System.out.println("║  2. Charger une partie             ║");
            System.out.println("║  3. Supprimer une sauvegarde       ║");
            System.out.println("║  4. Quitter                        ║");
            System.out.println("╚════════════════════════════════════╝");
            System.out.print("Votre choix : ");
            
            return scanner.nextInt();
        }
        

        //Menu de pause pendant la partie
        public static int afficherMenuPause() {
            System.out.println("\n┌──────────────────────────────┐");
            System.out.println("│         MENU PAUSE           │");
            System.out.println("├──────────────────────────────┤");
            System.out.println("│  1. Continuer la partie      │");
            System.out.println("│  2. Sauvegarder la partie    │");
            System.out.println("│  3. Sauvegarder et quitter   │");
            System.out.println("│  4. Quitter sans sauvegarder │");
            System.out.println("└──────────────────────────────┘");
            System.out.print("Votre choix : ");
            
            return scanner.nextInt();
        }
        

         //Interface pour sauvegarder

        public static void interfaceSauvegarde(Jeu jeu) {
            scanner.nextLine(); // Vider le buffer
            System.out.print("Nom de la sauvegarde : ");
            String nomSauvegarde = scanner.nextLine().trim();
            
            if (nomSauvegarde.isEmpty()) {
                // Générer un nom par défaut avec la date
                nomSauvegarde = "partie_" + System.currentTimeMillis();
            }
            
            jeu.sauvegarderPartie(nomSauvegarde);
        }
        
        
         // Interface pour charger une partie

        public static Jeu interfaceChargement() {
            ArrayList<String> sauvegardes = Jeu.listerSauvegardes();
            
            if (sauvegardes.isEmpty()) {
                System.out.println("Aucune sauvegarde disponible.");
                return null;
            }
            
            System.out.println("\nSauvegardes disponibles");
            for (int i = 0; i < sauvegardes.size(); i++) {
                System.out.println((i + 1) + ". " + sauvegardes.get(i));
            }
            System.out.println("0. Retour");
            System.out.print("Choisissez une sauvegarde : ");
            
            int choix = scanner.nextInt();
            
            if (choix > 0 && choix <= sauvegardes.size()) {
                return Jeu.chargerPartie(sauvegardes.get(choix - 1));
            }
            return null;
        }
        
        
        //Interface pour supprimer une sauvegarde
        
        public static void interfaceSuppression() {
            ArrayList<String> sauvegardes = Jeu.listerSauvegardes();
            
            if (sauvegardes.isEmpty()) {
                System.out.println("Aucune sauvegarde à supprimer.");
                return;
            }
            
            System.out.println("\nSauvegardes disponibles");
            for (int i = 0; i < sauvegardes.size(); i++) {
                System.out.println((i + 1) + ". " + sauvegardes.get(i));
            }
            System.out.println("0. Retour");
            System.out.print("Sauvegarde à supprimer : ");
            
            int choix = scanner.nextInt();
            
            if (choix > 0 && choix <= sauvegardes.size()) {
                Jeu.supprimerSauvegarde(sauvegardes.get(choix - 1));
            }
        }
    }

    
    public static void main(String[] args) {
    	Jeu jeuCourant = Jeu.getInstance();
    	CalculateurDeScore calculateur = new CalculateurDeScore(jeuCourant);
    	jeuCourant.initialiserExtension();
    	jeuCourant.initialiserVariante();
    	jeuCourant.initialiserJoueurs();
    	jeuCourant.initialiserPioche();
    	jeuCourant.setTropheeJeu();

    	Tour tourCourant = new Tour(jeuCourant);

    	// Boucle de jeu principale, desactivée pour l'instant pour tester le reste
    	while (jeuCourant.pioche.estPiochable()) {
    		tourCourant.afficherNumeroTour();
    		tourCourant.distribuerCartes();
    		tourCourant.gererOffres();
    		tourCourant.gererPrises();
    		tourCourant.passerAuTourSuivant();
    	}
    	System.out.println("La pioche est vide. Fin du jeu.");
        jeuCourant.determinerGagnants(calculateur);
        jeuCourant.sauvegarderPartie("PartieSave");
        jeuCourant.chargerPartie("PartieSave");

    }

}
