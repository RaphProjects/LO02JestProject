package jestPackage.Vue;

import java.util.ArrayList;
import javax.swing.*;
import jestPackage.Modele.Carte;
import jestPackage.Modele.Couleur;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * Implémentation graphique de la vue.
 * Affiche les informations dans une fenêtre Swing avec zone de log.
 */
public class VueGraphique implements IVue {
    
    private JFrame frame;
    private JTextArea logArea;
    private JScrollPane scrollPane;
    private JPanel mainPanel;
    private JPanel gamePanel;
    private JLabel statusLabel;
    
    public VueGraphique() {
        initializeFrame();
        initializeComponents();
    }
    
    /**
     * Initialise la fenêtre principale
     */
    private void initializeFrame() {
        frame = new JFrame("Jest - Jeu de cartes");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1200, 800);
        frame.setLocationRelativeTo(null);
        frame.setLayout(new BorderLayout());
    }
    
    /**
     * Initialise les composants graphiques
     */
    private void initializeComponents() {
        // Zone de log (historique du jeu)
        logArea = new JTextArea();
        logArea.setEditable(false);
        logArea.setFont(new Font("Monospaced", Font.PLAIN, 13));
        logArea.setBackground(new Color(20, 40, 20));
        logArea.setForeground(new Color(144, 238, 144));
        logArea.setMargin(new Insets(10, 10, 10, 10));
        logArea.setLineWrap(true);
        logArea.setWrapStyleWord(true);
        
        scrollPane = new JScrollPane(logArea);
        scrollPane.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(new Color(139, 69, 19), 2),
            "📜 Historique du jeu",
            0, 0,
            new Font("Arial", Font.BOLD, 12),
            Color.WHITE
        ));
        scrollPane.setPreferredSize(new Dimension(350, 0));
        scrollPane.getViewport().setBackground(new Color(20, 40, 20));
        
        // Label de statut
        statusLabel = new JLabel("Bienvenue dans Jest !");
        statusLabel.setFont(new Font("Arial", Font.BOLD, 16));
        statusLabel.setForeground(Color.WHITE);
        statusLabel.setHorizontalAlignment(SwingConstants.CENTER);
        statusLabel.setOpaque(true);
        statusLabel.setBackground(new Color(20, 60, 20));
        statusLabel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        // Panel principal
        mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(new Color(34, 139, 34));
        
        // Panel de jeu central
        gamePanel = new JPanel(new BorderLayout());
        gamePanel.setBackground(new Color(34, 139, 34));
        
        frame.setVisible(true);
    }
    
    // ==================== MÉTHODES UTILITAIRES ====================
    
    /**
     * Ajoute un message au log avec scroll automatique
     */
    private void log(String message) {
        SwingUtilities.invokeLater(() -> {
            logArea.append(message + "\n");
            logArea.setCaretPosition(logArea.getDocument().getLength());
        });
    }
    
    /**
     * Met à jour le label de statut
     */
    private void updateStatus(String status) {
        SwingUtilities.invokeLater(() -> {
            statusLabel.setText(status);
        });
    }
    
    /**
     * Crée un bouton stylisé pour le menu
     */
    private JButton createMenuButton(String text) {
        JButton button = new JButton(text);
        button.setAlignmentX(Component.CENTER_ALIGNMENT);
        button.setFont(new Font("Arial", Font.BOLD, 20));
        button.setPreferredSize(new Dimension(350, 60));
        button.setMaximumSize(new Dimension(350, 60));
        button.setMinimumSize(new Dimension(350, 60));
        button.setBackground(new Color(139, 69, 19));
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setOpaque(true);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                button.setBackground(new Color(160, 82, 45));
            }
            
            @Override
            public void mouseExited(MouseEvent e) {
                button.setBackground(new Color(139, 69, 19));
            }
        });
        
        return button;
    }
    
    /**
     * Configure l'interface pour le mode jeu
     */
    public void setupGameInterface() {
        frame.getContentPane().removeAll();
        
        JPanel container = new JPanel(new BorderLayout());
        container.setBackground(new Color(34, 139, 34));
        
        // Zone centrale pour le plateau de jeu
        JPanel centerPanel = new JPanel(new GridBagLayout());
        centerPanel.setOpaque(false);
        
        JLabel gameLabel = new JLabel("🃏 Zone de jeu 🃏");
        gameLabel.setFont(new Font("Arial", Font.BOLD, 24));
        gameLabel.setForeground(Color.WHITE);
        centerPanel.add(gameLabel);
        
        // Barre de statut en bas
        JPanel statusPanel = new JPanel(new BorderLayout());
        statusPanel.setBackground(new Color(20, 60, 20));
        statusPanel.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
        statusPanel.add(statusLabel, BorderLayout.CENTER);
        
        container.add(scrollPane, BorderLayout.EAST);
        container.add(centerPanel, BorderLayout.CENTER);
        container.add(statusPanel, BorderLayout.SOUTH);
        
        frame.getContentPane().add(container);
        frame.revalidate();
        frame.repaint();
    }
    
    // ==================== GETTERS ====================
    
    public JFrame getFrame() {
        return frame;
    }
    
    public JTextArea getLogArea() {
        return logArea;
    }
    
    public JScrollPane getScrollPane() {
        return scrollPane;
    }
    
    public JLabel getStatusLabel() {
        return statusLabel;
    }
    
    // ==================== TROPHÉES ====================
    
    @Override
    public void annonceTrophees() {
        log("═══════════════════════════════════");
        log("       🏆 TROPHÉES DU JEU 🏆        ");
        log("═══════════════════════════════════");
    }
    
    @Override
    public void afficherInfosTrophee(Carte carte) {
        log("  🏆 " + carte.getNom());
        log("     Condition : " + carte.getBandeauTrophee().toString());
    }
    
    @Override
    public void afficherTropheeRemporte(String nomTrophee, int numJoueur) {
        log("🎉 Le trophée " + nomTrophee + " est remporté par Joueur " + numJoueur + " !");
    }
    
    @Override
    public void afficherEgaliteTrophee(String nomTrophee) {
        log("⚖️ Égalité pour le trophée " + nomTrophee + ". Départage par la plus haute carte.");
    }
    
    @Override
    public void afficherEgaliteParfaiteTrophee(String nomTrophee) {
        log("❌ Égalité parfaite pour " + nomTrophee + " - Bug détecté !");
    }
    
    @Override
    public void afficherTropheeNonAttribue(String conditionTrophee) {
        log("⚠️ Le trophée " + conditionTrophee + " n'a été attribué à aucun joueur.");
    }
    
    @Override
    public void afficherPlusGrandeCarteCouleur(int numJoueur, Couleur couleur, int valeur) {
        log("  Joueur " + numJoueur + " : plus grande carte " + couleur + " = " + valeur);
    }
    
    @Override
    public void afficherPlusPetiteCarteCouleur(int numJoueur, Couleur couleur, int valeur) {
        log("  Joueur " + numJoueur + " : plus petite carte " + couleur + " = " + valeur);
    }
    
    // ==================== INITIALISATION ====================
    
    @Override
    public void afficherBienvenue() {
        log("🎮 Bienvenue dans le jeu Jest !");
        updateStatus("Bienvenue dans Jest !");
    }
    
    @Override
    public void demanderNombreJoueurs() {
        log("📝 Configuration : nombre de joueurs...");
    }
    
    @Override
    public void afficherNombreJoueursInvalide() {
        log("❌ Nombre de joueurs invalide (3 ou 4 requis).");
    }
    
    @Override
    public void demanderTypeJoueur(int numJoueur) {
        log("📝 Configuration du joueur " + numJoueur + "...");
    }
    
    @Override
    public void afficherCreationJoueurReel(int numJoueur) {
        log("👤 Joueur " + numJoueur + " créé (réel).");
    }
    
    @Override
    public void demanderStrategieJoueurVirtuel(int numJoueur) {
        log("🤖 Configuration IA pour joueur " + numJoueur + "...");
    }
    
    @Override
    public void afficherCreationJoueurVirtuel(int numJoueur) {
        log("🤖 Joueur " + numJoueur + " créé (IA).");
    }
    
    @Override
    public void afficherTypeJoueurInvalide() {
        log("❌ Type de joueur invalide.");
    }
    
    @Override
    public void afficherStrategieDefaut() {
        log("⚙️ Stratégie invalide → stratégie prudente par défaut.");
    }
    
    // ==================== EXTENSION ET VARIANTE ====================
    
    @Override
    public void demanderExtension() {
        log("📝 Configuration de l'extension...");
    }
    
    @Override
    public void afficherExtensionChoisie(int extension) {
        String ext = (extension == 0) ? "Classique" : "Plus de cartes";
        log("📦 Extension : " + ext);
    }
    
    @Override
    public void afficherExtensionInvalide() {
        log("❌ Extension invalide → classique par défaut.");
    }
    
    @Override
    public void demanderVariante() {
        log("📝 Configuration de la variante...");
    }
    
    @Override
    public void afficherVarianteChoisie(int variante) {
        String var = (variante == 0) ? "Classique" : "Inversée";
        log("🎲 Variante : " + var);
    }
    
    @Override
    public void afficherVarianteInvalide() {
        log("❌ Variante invalide → classique par défaut.");
    }
    
    @Override
    public void afficherVarianteInversee() {
        log("🔄 Mode inversé : le score le plus bas gagne !");
    }
    
    // ==================== JEST FINAL ET SCORES ====================
    
    @Override
    public void afficherJestFinalJoueur(int numJoueur) {
        log("");
        log("───────────────────────────────────");
        log("📋 Jest final du Joueur " + numJoueur + " :");
    }
    
    @Override
    public void afficherCartesJest(ArrayList<Carte> cartes) {
        for (Carte carte : cartes) {
            log("   • " + carte.getNom());
        }
    }
    
    @Override
    public void afficherClassementFinal() {
        log("");
        log("═══════════════════════════════════");
        log("      🏆 CLASSEMENT FINAL 🏆       ");
        log("═══════════════════════════════════");
        updateStatus("🏆 Partie terminée - Classement final");
    }
    
    @Override
    public void afficherScoreJoueur(int rang, int numJoueur, int score) {
        String medal;
        switch (rang) {
            case 1: medal = "🥇"; break;
            case 2: medal = "🥈"; break;
            case 3: medal = "🥉"; break;
            default: medal = "  "; break;
        }
        log(medal + " " + rang + "ᵉ : Joueur " + numJoueur + " → " + score + " points");
    }
    
    // ==================== SAUVEGARDE ET CHARGEMENT ====================
    
    @Override
    public void afficherSauvegardeReussie(String chemin) {
        log("💾 Partie sauvegardée : " + chemin);
    }
    
    @Override
    public void afficherErreurSauvegarde(String message) {
        log("❌ Erreur sauvegarde : " + message);
    }
    
    @Override
    public void afficherChargementReussi(String chemin) {
        log("📂 Partie chargée depuis : " + chemin);
    }
    
    @Override
    public void afficherFichierNonTrouve(String chemin) {
        log("❌ Fichier non trouvé : " + chemin);
    }
    
    @Override
    public void afficherErreurLecture(String message) {
        log("❌ Erreur lecture : " + message);
    }
    
    @Override
    public void afficherErreurDeserialisation(String message) {
        log("❌ Erreur chargement : " + message);
    }
    
    @Override
    public void afficherSuppressionReussie(String nomFichier) {
        log("🗑️ Sauvegarde supprimée : " + nomFichier);
    }
    
    @Override
    public void afficherErreurSuppression(String nomFichier) {
        log("❌ Impossible de supprimer : " + nomFichier);
    }
    
    @Override
    public void afficherAucuneSauvegarde() {
        log("📁 Aucune sauvegarde disponible.");
    }
    
    @Override
    public void afficherAucuneSauvegardeASupprimer() {
        log("📁 Aucune sauvegarde à supprimer.");
    }
    
    @Override
    public void afficherNomSauvegardeAuto(String nom) {
        log("💾 Nom automatique : " + nom);
    }
    
    @Override
    public void afficherListeSauvegardes(ArrayList<String> sauvegardes) {
        log("📁 Sauvegardes disponibles :");
        for (int i = 0; i < sauvegardes.size(); i++) {
            log("   " + (i + 1) + ". " + sauvegardes.get(i));
        }
        log("   0. Annuler");
    }
    
    @Override
    public void afficherTitreSauvegardes() {
        log("───── Sauvegardes ─────");
    }
    
    @Override
    public void afficherElementSauvegarde(int index, String nomSauvegarde) {
        log("   " + index + ". " + nomSauvegarde);
    }
    
    @Override
    public void afficherOptionRetour() {
        log("   0. Retour");
    }
    
    @Override
    public void demanderChoixSauvegarde() {
        log("📝 Sélection d'une sauvegarde...");
    }
    
    @Override
    public void demanderSauvegardeASupprimer() {
        log("📝 Sélection de la sauvegarde à supprimer...");
    }
    
    @Override
    public void demanderNomSauvegarde() {
        log("📝 Demande du nom de sauvegarde...");
    }
    
    // ==================== MENUS ====================
    
    @Override
    public void afficherMenuPrincipal() {
        frame.getContentPane().removeAll();
        
        JPanel menuPanel = new JPanel(new BorderLayout());
        menuPanel.setBackground(new Color(34, 139, 34));
        
        // Panel du titre
        JPanel titlePanel = new JPanel();
        titlePanel.setOpaque(false);
        titlePanel.setBorder(BorderFactory.createEmptyBorder(80, 0, 50, 0));
        
        JLabel titleLabel = new JLabel("🃏 BIENVENUE DANS JEST 🃏");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 42));
        titleLabel.setForeground(Color.WHITE);
        titlePanel.add(titleLabel);
        
        // Panel des boutons (décoratif, les vrais boutons sont dans JOptionPane)
        JPanel infoPanel = new JPanel();
        infoPanel.setOpaque(false);
        JLabel infoLabel = new JLabel("Sélectionnez une option dans la fenêtre de dialogue");
        infoLabel.setFont(new Font("Arial", Font.ITALIC, 16));
        infoLabel.setForeground(Color.WHITE);
        infoPanel.add(infoLabel);
        
        menuPanel.add(titlePanel, BorderLayout.NORTH);
        menuPanel.add(infoPanel, BorderLayout.CENTER);
        
        frame.getContentPane().add(menuPanel);
        frame.revalidate();
        frame.repaint();
        
        log("═══════════════════════════════════");
        log("        📋 MENU PRINCIPAL          ");
        log("═══════════════════════════════════");
    }
    
    @Override
    public void afficherMenuPause() {
        log("");
        log("───────────────────────────────────");
        log("         ⏸️ MENU PAUSE ⏸️          ");
        log("───────────────────────────────────");
        updateStatus("⏸️ Partie en pause");
    }
    
    @Override
    public void demanderChoix() {
        log("📝 En attente de votre choix...");
    }
    
    // ==================== DÉROULEMENT DU JEU ====================
    
    @Override
    public void afficherDebutTour(int numeroTour) {
        log("");
        log("═══════════════════════════════════");
        log("          🎯 TOUR " + numeroTour + " 🎯            ");
        log("═══════════════════════════════════");
        updateStatus("Tour " + numeroTour);
    }
    
    @Override
    public void afficherNumeroTour(int numeroTour) {
        log("───── Tour " + numeroTour + " ─────");
        updateStatus("Tour " + numeroTour + " en cours");
    }
    
    @Override
    public void afficherInstructionPause() {
        log("💡 Appuyez sur 'Pause' pour accéder au menu...");
    }
    
    @Override
    public void afficherFinJeu() {
        log("");
        log("═══════════════════════════════════");
        log("       🏁 FIN DE LA PARTIE 🏁      ");
        log("═══════════════════════════════════");
        updateStatus("🏁 Partie terminée");
    }
    
    @Override
    public void afficherPiocheVide() {
        log("📦 La pioche est vide.");
    }
    
    @Override
    public void afficherAuRevoir() {
        log("👋 Au revoir et merci d'avoir joué !");
    }
    
    @Override
    public void afficherChoixInvalide() {
        log("❌ Choix invalide.");
    }
    
    // ==================== AFFICHAGE DES JOUEURS ET CARTES ====================
    
    @Override
    public void afficherTourJoueur(String nomJoueur) {
        log("");
        log("▶️ Tour de " + nomJoueur);
        updateStatus("Tour de " + nomJoueur);
    }
    
    @Override
    public void afficherCestAuiDeJouer() {
        log("   → C'est à lui de jouer.");
    }
    
    @Override
    public void afficherJoueurAvecPlusGrandeValeurVisible(String nomJoueur, String carte) {
        log("👑 " + nomJoueur + " a la plus grande valeur visible : " + carte);
    }
    
    @Override
    public void afficherErreurDeterminerJoueurPlusGrandeValeurVisible() {
        log("❌ Impossible de déterminer le premier joueur.");
    }
    
    @Override
    public void afficherOffresDesJoueurs() {
        log("");
        log("📋 Offres des joueurs :");
    }
    
    @Override
    public void afficherOffreJoueur(String nomJoueur, String carteVisible, String carteCachee) {
        log("   " + nomJoueur + " → 👁️ " + carteVisible + " | 🔒 " + carteCachee);
    }
    
    @Override
    public void afficherOffreDeJoueur(String nomJoueur) {
        log("   📤 Offre de " + nomJoueur + " :");
    }
    
    @Override
    public void afficherMainJoueur(String nomJoueur, String main) {
        log("🃏 Main de " + nomJoueur + " : " + main);
    }
    
    @Override
    public void afficherMainJoueur() {
        log("🃏 Votre main :");
    }
    
    @Override
    public void afficherCarteMain(int index, String carte) {
        log("   " + (index + 1) + ". " + carte);
    }
    
    @Override
    public void afficherJestDeJoueur(String nomJoueur) {
        log("📚 Jest de " + nomJoueur + " :");
    }
    
    @Override
    public void afficherChoixOffreJoueur(String nomJoueur) {
        log("🎯 " + nomJoueur + ", choisissez votre offre...");
    }
    
    @Override
    public void afficherDemandeCarteVisible() {
        log("   Sélectionnez la carte visible...");
    }
    
    @Override
    public void afficherCarteChoisiePourOffre(String carte) {
        log("✅ Carte visible choisie : " + carte);
    }
    
    @Override
    public void afficherCarteChoisieIA(String carte) {
        log("🤖 IA : carte visible = " + carte);
    }
    
    // ==================== OPTIONS DE PRISE ====================
    
    @Override
    public void afficherChoixDansPropreOffre() {
        log("⚠️ Autres offres vides → choisissez dans votre offre.");
    }
    
    @Override
    public void afficherOptionCarteVisible(String carteVisible) {
        log("   1️⃣ Visible : " + carteVisible);
    }
    
    @Override
    public void afficherOptionCarteCachee() {
        log("   2️⃣ Cachée : ???");
    }
    
    @Override
    public void afficherOptionCarteVisibleOffre(int idx, String carteVisible) {
        log("   " + idx + ". Visible : " + carteVisible);
    }
    
    @Override
    public void afficherOptionCarteCacheeOffre(int idx) {
        log("   " + idx + ". Cachée : ???");
    }
    
    @Override
    public void afficherChoixCarteJoueur(String nomJoueur, int numCarte) {
        log("✅ " + nomJoueur + " prend la carte " + numCarte);
    }
    
    @Override
    public void afficherChoixCarteJoueurVirtuel(String nomJoueur, int choix) {
        log("🤖 " + nomJoueur + " (IA) choisit la carte " + choix);
    }
    
    @Override
    public void afficherChoixCarteJoueurVirtuelAdverse(String nomJoueur, int numCarte) {
        log("🤖 " + nomJoueur + " (IA) prend la carte " + numCarte);
    }
    
    @Override
    public void afficherJoueurSeFaitPrendreCarte(String nomJoueur) {
        log("📤 " + nomJoueur + " perd une carte.");
    }
    
    @Override
    public void afficherDemandePriseCarte(String nomJoueur) {
        log("🎯 " + nomJoueur + ", choisissez une carte à prendre...");
    }
    
    @Override
    public void afficherChoixInvalidePrise() {
        log("❌ Choix invalide pour la prise.");
    }
    
    @Override
    public void afficherOptionPrise(int numero, String description) {
        log("   " + numero + ". " + description);
    }
    
    // ==================== DEMANDES AU CONTRÔLEUR ====================
    
    @Override
    public void afficherDemandeCarteARetourner(int numeroJoueur, ArrayList<String> nomsCartes) {
        log("");
        log("🔄 Joueur " + numeroJoueur + ", carte à retourner :");
        for (int i = 0; i < nomsCartes.size(); i++) {
            log("   " + (i + 1) + ". " + nomsCartes.get(i));
        }
    }
    
    @Override
    public void afficherDemandeJoueurCible(int numeroJoueurActuel, ArrayList<Integer> joueursDisponibles) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < joueursDisponibles.size(); i++) {
            if (i > 0) sb.append(", ");
            sb.append("Joueur ").append(joueursDisponibles.get(i));
        }
        log("🎯 Joueur " + numeroJoueurActuel + ", chez qui prendre ?");
        log("   Disponibles : " + sb.toString());
    }
    
    @Override
    public void afficherJoueurNonDisponible() {
        log("❌ Ce joueur n'est pas disponible.");
    }
    
    @Override
    public void afficherDemandeTypeCarte(int numeroJoueurCible, String carteVisibleNom, boolean carteCacheeDisponible) {
        log("🎴 Chez Joueur " + numeroJoueurCible + " :");
        if (carteVisibleNom != null) {
            log("   1. Visible : " + carteVisibleNom);
        }
        if (carteCacheeDisponible) {
            log("   2. Cachée");
        }
    }
    
    @Override
    public void afficherDemandeConfirmation(String message) {
        log("❓ " + message);
    }
    
    // ==================== ERREURS ====================
    
    @Override
    public void afficherErreurNombreCartesNonJouees() {
        log("❌ Erreur : nombre de cartes non jouées incorrect.");
    }
    
    @Override
    public void afficherErreurTousJoueursOntJoue() {
        // Silencieux comme dans VueConsole
    }
    
    // ==================== AFFICHAGES GÉNÉRIQUES ====================
    
    @Override
    public void afficherMessage(String message) {
        log("💬 " + message);
    }
    
    @Override
    public void afficherErreur(String message) {
        log("❌ " + message);
    }
    
    @Override
    public void afficherLigneVide() {
        log("");
    }
    
    @Override
    public void afficherSeparateur() {
        log("───────────────────────────────────");
    }
    
    @Override
    public void afficherErreurSaisie() {
        log("❌ Entrée invalide.");
    }
    
    @Override
    public void afficherErreurPlage(int min, int max) {
        log("❌ Valeur hors plage (" + min + "-" + max + ").");
    }
    
    // ==================== NETTOYAGE ====================
    
    @Override
    public void demanderNettoyageConsole() {
        // Non pertinent pour GUI
        log("(Nettoyage non applicable en mode graphique)");
    }
    
    @Override
    public void afficherValeurInvalideNettoyageConsole() {
        // Non pertinent pour GUI
    }
    
    @Override
    public void nettoyerConsole() {
        // Vide le log
        SwingUtilities.invokeLater(() -> {
            logArea.setText("");
        });
    }
}