package jestPackage.Controleur;

import java.util.ArrayList;

/**
 * Interface définissant toutes les interactions utilisateur.
 * Permet de basculer facilement entre Console et Interface Graphique.
 */
public interface IControleur {
    
    // ==================== INITIALISATION ====================
    
    /**
     * Demande le nombre de joueurs (3 ou 4)
     * @return le nombre de joueurs choisi
     */
    int demanderNombreJoueurs();
    
    /**
     * Demande si le joueur est réel ou virtuel
     * @param numeroJoueur numéro du joueur concerné
     * @return 0 pour réel, 1 pour virtuel
     */
    int demanderTypeJoueur(int numeroJoueur);
    
    /**
     * Demande la stratégie pour un joueur virtuel
     * @param numeroJoueur numéro du joueur concerné
     * @return numéro de la stratégie choisie
     */
    int demanderStrategieJoueurVirtuel(int numeroJoueur);
    
    /**
     * Demande l'extension à utiliser
     * @return 0 pour classique, 1 pour extension
     */
    int demanderExtension();
    
    /**
     * Demande la variante à utiliser
     * @return 0 pour classique, 1 pour inversée
     */
    int demanderVariante();
    
    // ==================== MENUS ====================
    
    /**
     * Affiche le menu principal et récupère le choix
     * @return le choix du joueur (1-4)
     */
    int afficherMenuPrincipal();
    
    /**
     * Affiche le menu pause et récupère le choix
     * @return le choix du joueur
     */
    int afficherMenuPause();
    
    // ==================== SAUVEGARDE/CHARGEMENT ====================
    
    /**
     * Demande le nom pour la sauvegarde
     * @return le nom de la sauvegarde
     */
    String demanderNomSauvegarde();
    
    /**
     * Demande quelle sauvegarde charger
     * @param sauvegardes liste des sauvegardes disponibles
     * @return l'index choisi (1-based) ou 0 pour annuler
     */
    int demanderChoixSauvegarde(ArrayList<String> sauvegardes);
    
    /**
     * Demande quelle sauvegarde supprimer
     * @param sauvegardes liste des sauvegardes disponibles
     * @return l'index choisi (1-based) ou 0 pour annuler
     */
    int demanderSauvegardeASupprimer(ArrayList<String> sauvegardes);
    
    // ==================== GAMEPLAY ====================
    
    /**
     * Attend une entrée pour la pause ou continuer
     * @return la chaîne entrée par l'utilisateur
     */
    String attendreEntreePause();
    
    /**
     * Demande quelle carte retourner face visible
     * @param numeroJoueur numéro du joueur
     * @param nomsCarte noms des cartes disponibles
     * @return l'index de la carte choisie (0-based)
     */
    int demanderCarteARetourner(int numeroJoueur, ArrayList<String> nomsCarte);
    
    /**
     * Demande chez quel joueur prendre une carte
     * @param numeroJoueurActuel numéro du joueur qui prend
     * @param joueursDisponibles liste des numéros de joueurs disponibles
     * @return le numéro du joueur cible
     */
    int demanderJoueurCible(int numeroJoueurActuel, ArrayList<Integer> joueursDisponibles);
    
    /**
     * Demande quelle carte prendre (visible ou cachée)
     * @param numeroJoueurCible numéro du joueur cible
     * @param carteVisibleNom nom de la carte visible (null si non disponible)
     * @param carteCacheeDisponible true si carte cachée disponible
     * @return 1 pour visible, 2 pour cachée
     */
    int demanderTypeCarte(int numeroJoueurCible, String carteVisibleNom, boolean carteCacheeDisponible);
    
    /**
     * Confirmation avant une action importante
     * @param message le message de confirmation
     * @return true si confirmé
     */
    boolean demanderConfirmation(String message);
  

    /**
     * Demande quelle carte mettre face visible dans l'offre
     * @param nomJoueur nom du joueur
     * @param cartes liste des descriptions des cartes en main
     * @return l'index de la carte choisie (1-based)
     */
    int demanderCarteVisibleOffre(String nomJoueur, ArrayList<String> cartes);

    /**
     * Demande si le joueur veut nettoyer la console (cacher ses cartes)
     * @return true si le joueur veut nettoyer
     */
    boolean demanderNettoyageConsole();

    /**
     * Demande au joueur quelle prise effectuer
     * @param nomJoueur nom du joueur qui doit choisir
     * @param nbPossibilites nombre de choix possibles
     * @param descriptionsChoix descriptions des choix disponibles
     * @return le numéro du choix (1-based)
     */
    int demanderChoixPrise(String nomJoueur, int nbPossibilites, ArrayList<String> descriptionsChoix);

    /**
     * Demande au joueur quelle prise effectuer (version simple)
     * @param nomJoueur nom du joueur qui doit choisir
     * @param nbPossibilites nombre de choix possibles
     * @return le numéro du choix (1-based)
     */
    int demanderChoixPrise(String nomJoueur, int nbPossibilites);
}