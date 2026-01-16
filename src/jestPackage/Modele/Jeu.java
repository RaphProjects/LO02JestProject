package jestPackage.Modele;

import java.util.*;
import jestPackage.Vue.*;
import jestPackage.Controleur.*;
import java.io.*;

/**
 * Classe principale du jeu Jest qui gère l'ensemble de la logique du jeu.
 * Implémente le pattern Singleton pour garantir une instance unique du jeu.
 * Gère les joueurs, les trophées, la pioche, les tours et la sauvegarde/chargement.
 */
public class Jeu implements Serializable {
    private static final long serialVersionUID = 1L;
    
    /** Indique si le jeu est en mode graphique ou console. */
    public static boolean modeGraphique = true;
    
    /** Liste des joueurs participant à la partie. */
    private ArrayList<Joueur> joueurs = new ArrayList<Joueur>();
    
    /** Liste des cartes trophées de la partie. */
    private ArrayList<Carte> trophees = new ArrayList<Carte>();
    
    /** Liste des numéros de joueurs ayant remporté chaque trophée. */
    private ArrayList<Integer> joueurPourTrophee = new ArrayList<Integer>();
    
    /** Vue du jeu (transient car non sérialisable). */
    public static transient IVue vue;
    
    /** Contrôleur du jeu (transient car non sérialisable). */
    public static transient IControleur controleur;

    /** Pioche de cartes du jeu. */
    private Pioche pioche;
    
    /** Tour actuel du jeu. */
    private Tour tour;

    /** Extension choisie pour la partie. */
    private int extension;
    
    /** Variante choisie pour la partie. */
    private int variante;

    /** Répertoire de sauvegarde des parties. */
    private static final String SAVE_DIRECTORY = "saves/";
    
    /** Extension des fichiers de sauvegarde. */
    private static final String SAVE_EXTENSION = ".jest";

    /** Instance unique du jeu (pattern Singleton). */
    private static Jeu instance = null;

    /**
     * Constructeur privé du jeu (pattern Singleton).
     * Initialise la pioche et réinitialise la vue et le contrôleur.
     */
    private Jeu() {
        this.pioche = new Pioche(this);
        reinitialiserVueEtControleur();
    }

    /**
     * Retourne l'instance unique du jeu (pattern Singleton).
     * Crée l'instance si elle n'existe pas encore.
     * 
     * @return l'instance unique du jeu
     */
    public static Jeu getInstance() {
        if (instance == null) {
            instance = new Jeu();
        }
        return instance;
    }

    /**
     * Réinitialise l'instance du jeu en créant une nouvelle instance.
     */
    private static void resetInstance() {
        instance = new Jeu();
    }

    /**
     * Réinitialise la vue et le contrôleur après chargement ou création.
     * Instancie les objets appropriés selon le mode (graphique ou console).
     */
    public void reinitialiserVueEtControleur() {
        if (!modeGraphique) {
            if (vue == null) {
                vue = new VueConsole();
            }
            if (controleur == null) {
                controleur = new ControleurConsole((VueConsole) vue);
            }
        }
        else {
            if (vue == null) {
                vue = new VueGraphique();
            }
            if (controleur == null) {
                controleur = new ControleurGraphique((VueGraphique) vue);
            }
        }
    }

    // ==================== Getters ====================
    
    /**
     * Retourne la liste des joueurs de la partie.
     * 
     * @return la liste des joueurs
     */
    public ArrayList<Joueur> getJoueurs() {
        return this.joueurs;
    }

    /**
     * Retourne la pioche du jeu.
     * 
     * @return la pioche
     */
    public Pioche getPioche() {
        return this.pioche;
    }

    /**
     * Retourne l'extension choisie pour la partie.
     * 
     * @return le numéro de l'extension
     */
    public int getExtension() {
        return this.extension;
    }

    /**
     * Retourne la vue du jeu.
     * 
     * @return la vue
     */
    public IVue getVue() {
        return vue;
    }

    /**
     * Retourne le contrôleur du jeu.
     * 
     * @return le contrôleur
     */
    public IControleur getControleur() {
        return controleur;
    }

    /**
     * Retourne la liste des trophées de la partie.
     * 
     * @return la liste des trophées
     */
    public ArrayList<Carte> getTrophees() {
        return this.trophees;
    }

    /**
     * Retourne le numéro du tour actuel.
     * 
     * @return le numéro du tour actuel
     */
    public int getNumeroTourActuel() {
        return tour.getNumeroTour();
    }

    /**
     * Retourne le numéro du tour actuel.
     * 
     * @return le numéro du tour
     */
    public int getNumTour() {
        return this.tour.getNumeroTour();
    }

    // ==================== Setters ====================
    
    /**
     * Définit la vue du jeu.
     * 
     * @param vue la vue console à définir
     */
    public void setVue(VueConsole vue) {
        Jeu.vue = vue;
    }

    /**
     * Définit le contrôleur du jeu.
     * 
     * @param controleur le contrôleur à définir
     */
    public void setControleur(IControleur controleur) {
        Jeu.controleur = controleur;
    }

    /**
     * Définit le numéro du tour actuel.
     * 
     * @param numero le numéro du tour à définir
     */
    public void setNumeroTourActuel(int numero) {
        this.tour.setNumeroTour(numero);
    }

    // ==================== Initialisation ====================

    /**
     * Initialise les trophées du jeu en piochant les cartes appropriées.
     * Le nombre de trophées dépend du nombre de joueurs (2 pour 3 joueurs, 1 sinon).
     */
    public void setTropheeJeu() {
        if (this.joueurs.size() == 3) {
            for (int i = 0; i < 2; i++) {
                this.trophees.add(pioche.piocher());
            }
        } else {
            this.trophees.add(pioche.piocher());
        }
        vue.annonceTrophees();
        for (Carte carte : this.trophees) {
            vue.afficherInfosTrophee(carte);
        }
    }

    /**
     * Initialise les joueurs de la partie en demandant leur configuration.
     * Permet de créer des joueurs réels ou virtuels avec différentes stratégies.
     */
    public void initialiserJoueurs() {
        vue.afficherBienvenue();
        
        int nombreJoueurs = controleur.demanderNombreJoueurs();
        
        for (int compteurJoueur = 1; compteurJoueur <= nombreJoueurs; compteurJoueur++) {
            int joueurIsVirtuel = controleur.demanderTypeJoueur(compteurJoueur);
            
            if (joueurIsVirtuel == 0) {
                vue.afficherCreationJoueurReel(compteurJoueur);
                this.joueurs.add(new JoueurReel("Joueur " + compteurJoueur, compteurJoueur));
            } else {
                int numStrategy = controleur.demanderStrategieJoueurVirtuel(compteurJoueur);
                vue.afficherCreationJoueurVirtuel(compteurJoueur);
                this.joueurs.add(new JoueurVirtuel("Joueur " + compteurJoueur + " (IA)", compteurJoueur, numStrategy));
            }
        }
    }

    /**
     * Initialise la pioche du jeu en créant et mélangeant les cartes.
     */
    public void initialiserPioche() {
        this.pioche.initialiserCartes();
        this.pioche.melangerCartes();
    }

    /**
     * Initialise l'extension choisie pour la partie.
     */
    public void initialiserExtension() {
        int extensionChoisie = controleur.demanderExtension();
        this.extension = extensionChoisie;
        vue.afficherExtensionChoisie(extensionChoisie);
    }

    /**
     * Initialise la variante choisie pour la partie.
     */
    public void initialiserVariante() {
        int varianteChoisie = controleur.demanderVariante();
        this.variante = varianteChoisie;
        vue.afficherVarianteChoisie(varianteChoisie);
    }

    // ==================== Trophées et score ====================
    
    /**
     * Attribue le trophée "Plus grand Jest" au joueur ayant le score le plus élevé.
     * Gère les égalités en comparant d'abord la plus haute valeur de carte,
     * puis la valeur de couleur en cas d'égalité persistante.
     * 
     * @param joueurs la liste des joueurs candidats au trophée
     * @param scoresAvantTrophees les scores des joueurs avant attribution des trophées
     * @param carteTrophee la carte trophée à attribuer
     */
    public void attribuerTropheePlusGrandJest(ArrayList<Joueur> joueurs, 
                                               ArrayList<Integer> scoresAvantTrophees, 
                                               Carte carteTrophee) {
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
            vue.afficherTropheeRemporte(carteTrophee.getNom(), gagnantTrophee.getNumJoueur());
        } else {
            vue.afficherEgaliteTrophee(carteTrophee.getNom());
            // ... reste de la logique inchangée
            ArrayList<Integer> valeurMaxPourEgalite = new ArrayList<Integer>();
            for (Integer indiceJoueur : joueursGagnantsTrophee) {
                Joueur joueur = this.joueurs.get(indiceJoueur);
                int valeurMax = joueur.getJest().plusHauteValeur();
                valeurMaxPourEgalite.add(valeurMax);
            }
            int maxValeurEgalite = Collections.max(valeurMaxPourEgalite);
            ArrayList<Integer> finalistes = new ArrayList<Integer>();

            for (int i = 0; i < valeurMaxPourEgalite.size(); i++) {
                if (valeurMaxPourEgalite.get(i) == maxValeurEgalite) {
                    finalistes.add(joueursGagnantsTrophee.get(i));
                }
            }

            if (finalistes.size() == 1) {
                Joueur gagnantTrophee = this.joueurs.get(finalistes.get(0));
                this.joueurPourTrophee.add(gagnantTrophee.getNumJoueur());
                vue.afficherTropheeRemporte(carteTrophee.getNom(), gagnantTrophee.getNumJoueur());
            } else {
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
                    vue.afficherTropheeRemporte(carteTrophee.getNom(), gagnantTrophee.getNumJoueur());
                } else {
                    vue.afficherEgaliteParfaiteTrophee(carteTrophee.getNom());
                }
            }
        }
    }

    /**
     * Détermine les gagnants de la partie en attribuant les trophées et calculant les scores finaux.
     * Affiche le classement final des joueurs selon la variante choisie.
     * 
     * @param calculateur le calculateur de score à utiliser
     */
    public void determinerGagnants(CalculateurDeScore calculateur) {
        // ... méthode inchangée, elle utilise déjà vue
        ArrayList<Integer> scoresAvantTrophees = new ArrayList<Integer>();
        for (Joueur joueur : this.joueurs) {
            scoresAvantTrophees.add(calculateur.getScore(joueur.getJest()));
        }

        for (Carte carteTrophee : this.trophees) {
            if (carteTrophee.getBandeauTrophee().estTropheeCouleur()) {
                if (((TropheeCouleur) carteTrophee.getBandeauTrophee()).getOrdre() == OrdreTropheeCouleur.PLUSGRAND) {
                    ArrayList<Integer> plusGrandeValeurPourJoueur = new ArrayList<Integer>();
                    for (Joueur joueur : this.joueurs) {
                        plusGrandeValeurPourJoueur.add(joueur.getJest().plusHauteValeurDeLaCouleur(
                                ((TropheeCouleur) carteTrophee.getBandeauTrophee()).getCouleurDeCondition()));
                    }
                    int MaxValeur = Collections.max(plusGrandeValeurPourJoueur);
                    ArrayList<Integer> joueursGagnantsTrophee = new ArrayList<Integer>();
                    for (int i = 0; i < plusGrandeValeurPourJoueur.size(); i++) {
                        if (plusGrandeValeurPourJoueur.get(i) == MaxValeur) {
                            Joueur gagnantTrophee = this.joueurs.get(i);
                            this.joueurPourTrophee.add(gagnantTrophee.getNumJoueur());
                            vue.afficherPlusGrandeCarteCouleur(gagnantTrophee.getNumJoueur(),
                                    ((TropheeCouleur) carteTrophee.getBandeauTrophee()).getCouleurDeCondition(),
                                    MaxValeur);
                        }
                    }
                } else if (((TropheeCouleur) carteTrophee.getBandeauTrophee()).getOrdre() == OrdreTropheeCouleur.PLUSPETIT) {
                    ArrayList<Integer> plusPetiteValeurPourJoueur = new ArrayList<Integer>();
                    for (Joueur joueur : this.joueurs) {
                        plusPetiteValeurPourJoueur.add(joueur.getJest().plusPetiteValeurDeLaCouleur(
                                ((TropheeCouleur) carteTrophee.getBandeauTrophee()).getCouleurDeCondition()));
                    }
                    int MinValeur = plusPetiteValeurPourJoueur.getFirst();
                    for (int valeur : plusPetiteValeurPourJoueur) {
                        if (valeur > 0 && valeur < MinValeur) {
                            MinValeur = valeur;
                        }
                    }
                    ArrayList<Integer> joueursGagnantsTrophee = new ArrayList<Integer>();
                    for (int i = 0; i < plusPetiteValeurPourJoueur.size(); i++) {
                        if (plusPetiteValeurPourJoueur.get(i) == MinValeur) {
                            Joueur gagnantTrophee = this.joueurs.get(i);
                            this.joueurPourTrophee.add(gagnantTrophee.getNumJoueur());
                            vue.afficherPlusPetiteCarteCouleur(gagnantTrophee.getNumJoueur(),
                                    ((TropheeCouleur) carteTrophee.getBandeauTrophee()).getCouleurDeCondition(),
                                    MinValeur);
                        }
                    }
                }
            } else {
                if (((TropheeIncolore) carteTrophee.getBandeauTrophee()).getCondition() == ConditionIncolore.MAJORITÉ) {
                    ArrayList<Integer> nombreCartesValeurPourJoueur = new ArrayList<Integer>();
                    for (Joueur joueur : this.joueurs) {
                        nombreCartesValeurPourJoueur.add(joueur.getJest().nbCarteDeLaValeur(
                                ((TropheeIncolore) carteTrophee.getBandeauTrophee()).getValeurAssociée()));
                    }
                    int MaxCartes = Collections.max(nombreCartesValeurPourJoueur);
                    ArrayList<Integer> joueursGagnantsTrophee = new ArrayList<Integer>();
                    for (int i = 0; i < nombreCartesValeurPourJoueur.size(); i++) {
                        if (nombreCartesValeurPourJoueur.get(i) == MaxCartes && MaxCartes > 0) {
                            joueursGagnantsTrophee.add(i);
                        }
                    }
                    if (joueursGagnantsTrophee.size() == 1) {
                        Joueur gagnantTrophee = this.joueurs.get(joueursGagnantsTrophee.get(0));
                        this.joueurPourTrophee.add(gagnantTrophee.getNumJoueur());
                        vue.afficherTropheeRemporte(carteTrophee.getNom(), gagnantTrophee.getNumJoueur());
                    } else {
                        ArrayList<Integer> valeurDeCouleurPourGagnant = new ArrayList<Integer>();
                        for (Integer indiceJoueur : joueursGagnantsTrophee) {
                            Joueur joueur = this.joueurs.get(indiceJoueur);
                            int valeurCouleurMax = 0;
                            for (Carte carte : joueur.getJest().getCartes()) {
                                if (carte.getValeurBase() == ((TropheeIncolore) carteTrophee.getBandeauTrophee()).getValeurAssociée()) {
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
                            vue.afficherTropheeRemporte(carteTrophee.getNom(), gagnantTrophee.getNumJoueur());
                        } else {
                            vue.afficherEgaliteParfaiteTrophee(carteTrophee.getNom());
                        }
                    }
                } else if (((TropheeIncolore) carteTrophee.getBandeauTrophee()).getCondition() == ConditionIncolore.JOKER) {
                    boolean jokerTrouve = false;

                    for (Joueur joueur : this.joueurs) {
                        for (Carte carte : joueur.getJest().getCartes()) {
                            if (carte.getNom().equals("Joker")) {
                                this.joueurPourTrophee.add(joueur.getNumJoueur());
                                vue.afficherTropheeRemporte(carteTrophee.getNom(), joueur.getNumJoueur());
                                jokerTrouve = true;
                                break;
                            }
                        }
                        if (jokerTrouve) {
                            break;
                        }
                    }

                    if (!jokerTrouve) {
                        this.joueurPourTrophee.add(-1);
                    }
                } else if (((TropheeIncolore) carteTrophee.getBandeauTrophee()).getCondition() == ConditionIncolore.PLUSGRANDJEST) {
                    attribuerTropheePlusGrandJest(this.joueurs, scoresAvantTrophees, carteTrophee);
                } else if (((TropheeIncolore) carteTrophee.getBandeauTrophee()).getCondition() == ConditionIncolore.PLUSGRANDJEST_SANSJOKER) {
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
                }
            }
        }

        for (Carte carteTrophee : this.trophees) {
            if (this.joueurPourTrophee.get(this.trophees.indexOf(carteTrophee)) == -1) {
                vue.afficherTropheeNonAttribue(carteTrophee.bandeauTrophee.toString());
                continue;
            } else {
                int indiceJoueurGagnant = this.joueurPourTrophee.get(this.trophees.indexOf(carteTrophee)) - 1;
                Joueur joueurGagnantTrophee = this.joueurs.get(indiceJoueurGagnant);
                joueurGagnantTrophee.ajouterAsonJest(carteTrophee);
            }
        }

        for (Joueur joueur : this.joueurs) {
            vue.afficherJestFinalJoueur(joueur.getNumJoueur());
            joueur.getJest().afficherJest();
        }

        ArrayList<Integer> scoresFinaux = new ArrayList<Integer>();
        for (Joueur joueur : this.joueurs) {
            scoresFinaux.add(calculateur.getScore(joueur.getJest()));
        }

        ArrayList<Integer> ordreGagnants = new ArrayList<Integer>();
        while (ordreGagnants.size() < this.joueurs.size()) {
            int maxScoreFinal = Collections.max(scoresFinaux);
            int indiceGagnant = scoresFinaux.indexOf(maxScoreFinal);
            ordreGagnants.add(indiceGagnant);
            scoresFinaux.set(indiceGagnant, Integer.MIN_VALUE);
        }
        vue.afficherClassementFinal();

        if (variante == 0) {
            for (int i = 0; i < ordreGagnants.size(); i++) {
                int indiceJoueur = ordreGagnants.get(i);
                Joueur joueurGagnant = this.joueurs.get(indiceJoueur);
                int scoreFinal = calculateur.getScore(joueurGagnant.getJest());
                vue.afficherScoreJoueur(i + 1, joueurGagnant.getNumJoueur(), scoreFinal);
            }
        }
        if (variante == 1) {
            vue.afficherVarianteInversee();

            for (int i = 0; i < ordreGagnants.size(); i++) {
                int indexInversé = ordreGagnants.size() - 1 - i;
                int indiceJoueur = ordreGagnants.get(indexInversé);

                Joueur joueurGagnant = this.joueurs.get(indiceJoueur);
                int scoreFinal = calculateur.getScore(joueurGagnant.getJest());

                vue.afficherScoreJoueur(i + 1, joueurGagnant.getNumJoueur(), scoreFinal);
            }
        }
    }

    // ==================== Sauvegarde et chargement ====================

    /**
     * Sauvegarde la partie actuelle dans un fichier.
     * 
     * @param nomFichier le nom du fichier de sauvegarde (sans extension)
     * @return true si la sauvegarde a réussi, false sinon
     */
    public boolean sauvegarderPartie(String nomFichier) {
        File directory = new File(SAVE_DIRECTORY);
        if (!directory.exists()) {
            directory.mkdirs();
        }

        String cheminComplet = SAVE_DIRECTORY + nomFichier + SAVE_EXTENSION;

        try (FileOutputStream fos = new FileOutputStream(cheminComplet);
             ObjectOutputStream oos = new ObjectOutputStream(fos)) {

            oos.writeObject(this);
            vue.afficherSauvegardeReussie(cheminComplet);
            return true;

        } catch (IOException e) {
            vue.afficherErreurSauvegarde(e.getMessage());
            return false;
        }
    }

    /**
     * Charge une partie à partir d'un fichier de sauvegarde.
     * 
     * @param nomFichier le nom du fichier de sauvegarde (sans extension)
     * @return le jeu chargé, ou null en cas d'erreur
     */
    public static Jeu chargerPartie(String nomFichier) {
        String cheminComplet = SAVE_DIRECTORY + nomFichier + SAVE_EXTENSION;
        VueConsole vueTemp = new VueConsole();

        try (FileInputStream fis = new FileInputStream(cheminComplet);
             ObjectInputStream ois = new ObjectInputStream(fis)) {

            Jeu jeuCharge = (Jeu) ois.readObject();
            jeuCharge.reinitialiserVueEtControleur();
            instance = jeuCharge;
            vue.afficherChargementReussi(cheminComplet);
            return jeuCharge;

        } catch (FileNotFoundException e) {
            vueTemp.afficherFichierNonTrouve(cheminComplet);
            return null;
        } catch (IOException e) {
            vueTemp.afficherErreurLecture(e.getMessage());
            return null;
        } catch (ClassNotFoundException e) {
            vueTemp.afficherErreurDeserialisation(e.getMessage());
            return null;
        }
    }

    /**
     * Liste toutes les sauvegardes disponibles dans le répertoire de sauvegarde.
     * 
     * @return la liste des noms de sauvegardes (sans extension)
     */
    public static ArrayList<String> listerSauvegardes() {
        ArrayList<String> sauvegardes = new ArrayList<>();
        File directory = new File(SAVE_DIRECTORY);

        if (directory.exists() && directory.isDirectory()) {
            File[] fichiers = directory.listFiles((dir, name) -> name.endsWith(SAVE_EXTENSION));
            if (fichiers != null) {
                for (File fichier : fichiers) {
                    String nom = fichier.getName();
                    sauvegardes.add(nom.substring(0, nom.length() - SAVE_EXTENSION.length()));
                }
            }
        }
        return sauvegardes;
    }

    /**
     * Supprime un fichier de sauvegarde.
     * 
     * @param nomFichier le nom du fichier à supprimer (sans extension)
     * @return true si la suppression a réussi, false sinon
     */
    public static boolean supprimerSauvegarde(String nomFichier) {
        String cheminComplet = SAVE_DIRECTORY + nomFichier + SAVE_EXTENSION;
        File fichier = new File(cheminComplet);

        if (fichier.exists() && fichier.delete()) {
            vue.afficherSuppressionReussie(nomFichier);
            return true;
        } else {
            vue.afficherErreurSuppression(nomFichier);
            return false;
        }
    }

    // ==================== Gestionnaire de partie ====================

    /**
     * Classe interne gérant les interfaces utilisateur pour les opérations de partie.
     * Fournit des méthodes pour afficher les menus et gérer les sauvegardes.
     */
    public static class GestionnairePartie {

        /**
         * Affiche le menu principal et retourne le choix de l'utilisateur.
         * 
         * @return le numéro du choix effectué par l'utilisateur
         */
        public static int afficherMenuPrincipal() {
            return controleur.afficherMenuPrincipal();
        }

        /**
         * Affiche le menu pause et retourne le choix de l'utilisateur.
         * 
         * @return le numéro du choix effectué par l'utilisateur
         */
        public static int afficherMenuPause() {
            return controleur.afficherMenuPause();
        }

        /**
         * Affiche l'interface de sauvegarde et sauvegarde la partie.
         * 
         * @param jeu le jeu à sauvegarder
         */
        public static void interfaceSauvegarde(Jeu jeu) {
            String nomSauvegarde = controleur.demanderNomSauvegarde();
            jeu.sauvegarderPartie(nomSauvegarde);
        }

        /**
         * Affiche l'interface de chargement et charge la partie sélectionnée.
         * 
         * @return le jeu chargé, ou null si aucune sauvegarde sélectionnée
         */
        public static Jeu interfaceChargement() {
            ArrayList<String> sauvegardes = Jeu.listerSauvegardes();
            int choix = controleur.demanderChoixSauvegarde(sauvegardes);

            if (choix > 0 && choix <= sauvegardes.size()) {
                return Jeu.chargerPartie(sauvegardes.get(choix - 1));
            }
            return null;
        }

        /**
         * Affiche l'interface de suppression et supprime la sauvegarde sélectionnée.
         */
        public static void interfaceSuppression() {
            ArrayList<String> sauvegardes = Jeu.listerSauvegardes();
            int choix = controleur.demanderSauvegardeASupprimer(sauvegardes);

            if (choix > 0 && choix <= sauvegardes.size()) {
                Jeu.supprimerSauvegarde(sauvegardes.get(choix - 1));
            }
        }
    }

    // ==================== Boucle de jeu ====================

    /**
     * Lance la boucle principale du jeu.
     * Gère les tours de jeu, le menu pause et la fin de partie.
     * 
     * @param jeuCourant le jeu à exécuter
     */
    private static void lancerBoucleJeu(Jeu jeuCourant) {
        CalculateurDeScore calculateur = new CalculateurDeScore(jeuCourant);
        boolean continuerPartie = true;

        while (continuerPartie && jeuCourant.pioche.estPiochable()) {

            vue.afficherDebutTour(jeuCourant.tour.getNumeroTour());
            String input = controleur.attendreEntreePause();

            if (input.equalsIgnoreCase("P")) {
                int choixPause = GestionnairePartie.afficherMenuPause();

                switch (choixPause) {
                    case 1: // Reprendre
                        break;
                    case 2: // Sauvegarder
                        GestionnairePartie.interfaceSauvegarde(jeuCourant);
                        break;
                    case 3: // Sauvegarder et quitter
                        GestionnairePartie.interfaceSauvegarde(jeuCourant);
                        continuerPartie = false;
                        break;
                    case 4: // Quitter sans sauvegarder
                        continuerPartie = false;
                        break;
                }
            }

            if (continuerPartie) {
                jeuCourant.tour.afficherNumeroTour();
                jeuCourant.tour.distribuerCartes();
                jeuCourant.tour.gererOffres();
                jeuCourant.tour.gererPrises();
                jeuCourant.tour.passerAuTourSuivant();
            }
        }

        if (continuerPartie && !jeuCourant.pioche.estPiochable()) {
            vue.afficherFinJeu();
            jeuCourant.determinerGagnants(calculateur);
        }
    }

    // ==================== Main ====================

    /**
     * Point d'entrée principal du programme.
     * Initialise l'interface utilisateur et gère le menu principal de l'application.
     * 
     * @param args les arguments de ligne de commande (non utilisés)
     */
    public static void main(String[] args) {
        boolean quitterApplication = false;

        // Initialisation statique
        if (!modeGraphique) {
            vue = new VueConsole();
            controleur = new ControleurConsole((VueConsole) vue);
        }
        else {
            vue = new VueGraphique();
            controleur = new ControleurGraphique((VueGraphique) vue);
            System.out.println("Interface Graphique instanciée");
        }
        while (!quitterApplication) {
            int choixPrincipal = GestionnairePartie.afficherMenuPrincipal();

            switch (choixPrincipal) {
                case 1: // Nouvelle partie
                    Jeu.resetInstance();
                    Jeu jeuCourant = Jeu.getInstance();
                    jeuCourant.initialiserExtension();
                    jeuCourant.initialiserVariante();
                    jeuCourant.initialiserJoueurs();
                    jeuCourant.initialiserPioche();
                    jeuCourant.setTropheeJeu();
                    jeuCourant.tour = new Tour(jeuCourant);

                    lancerBoucleJeu(jeuCourant);
                    break;

                case 2: // Charger partie
                    Jeu jeuCharge = GestionnairePartie.interfaceChargement();
                    if (jeuCharge != null) {
                        lancerBoucleJeu(jeuCharge);
                    }
                    break;

                case 3: // Supprimer sauvegarde
                    GestionnairePartie.interfaceSuppression();
                    break;

                case 4: // Quitter
                    vue.afficherAuRevoir();
                    quitterApplication = true;
                    break;

                default:
                    vue.afficherChoixInvalide();
                    break;
            }
        }
    }
}