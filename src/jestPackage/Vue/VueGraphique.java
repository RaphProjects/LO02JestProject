package jestPackage.Vue;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import javax.swing.*;
import jestPackage.Modele.Carte;
import jestPackage.Modele.Couleur;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;

/**
 * Implémentation graphique de la vue.
 * Gère l'affichage des fenêtres et le chargement des images.
 */
public class VueGraphique implements IVue {
    
    private JFrame frame;
    private JTextArea logArea;
    private JScrollPane scrollPane;
    private JLabel statusLabel;
    
    private Map<String, ImageIcon> imageCache = new HashMap<>();
    private static final String IMG_FOLDER = "LO02JestImg" + File.separator;
    
    private ArrayList<String> optionsPriseEnCours = new ArrayList<>();
    private String joueurOffreEnCours = "";
    
    public VueGraphique() {
        initializeFrame();
        initializeComponents();
    }
    
    private void initializeFrame() {
        frame = new JFrame("Jest - Jeu de cartes");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1200, 800);
        frame.setLocationRelativeTo(null);
        frame.setLayout(new BorderLayout());
    }
    
    private void initializeComponents() {
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
        scrollPane.setPreferredSize(new Dimension(400, 0));
        scrollPane.getViewport().setBackground(new Color(20, 40, 20));
        
        statusLabel = new JLabel("Bienvenue dans Jest !");
        statusLabel.setFont(new Font("Arial", Font.BOLD, 16));
        statusLabel.setForeground(Color.WHITE);
        statusLabel.setHorizontalAlignment(SwingConstants.CENTER);
        statusLabel.setOpaque(true);
        statusLabel.setBackground(new Color(20, 60, 20));
        statusLabel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        JPanel centerPanel = new JPanel();
        centerPanel.setBackground(new Color(34, 139, 34));
        JLabel logoLabel = new JLabel("🃏 JEST 🃏");
        logoLabel.setFont(new Font("Serif", Font.BOLD, 60));
        logoLabel.setForeground(new Color(255, 255, 255, 100));
        centerPanel.setLayout(new GridBagLayout());
        centerPanel.add(logoLabel);

        frame.add(centerPanel, BorderLayout.CENTER);
        frame.add(scrollPane, BorderLayout.EAST);
        frame.add(statusLabel, BorderLayout.SOUTH);
        
        frame.setVisible(true);
    }

    // ==================== GESTION DES OPTIONS DE PRISE ====================
    
    public ArrayList<String> getEtViderOptionsPrise() {
        ArrayList<String> result = new ArrayList<>(optionsPriseEnCours);
        optionsPriseEnCours.clear();
        joueurOffreEnCours = "";
        return result;
    }

    // ==================== GESTION DES IMAGES ====================

    /**
     * Ajoute un texte (ex: nom du joueur) sous l'icône de la carte.
     */
    public ImageIcon ajouterTexteSousIcone(ImageIcon iconOriginal, String texte) {
        if (iconOriginal == null) return null;
        if (texte == null || texte.isEmpty()) return iconOriginal;

        int w = iconOriginal.getIconWidth();
        int h = iconOriginal.getIconHeight();
        int textHeight = 20; // Espace pour le texte

        BufferedImage combined = new BufferedImage(w, h + textHeight, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = combined.createGraphics();

        // Dessiner l'image originale
        g.drawImage(iconOriginal.getImage(), 0, 0, null);

        // Dessiner le fond du texte (optionnel, améliore la lisibilité)
        g.setColor(new Color(255, 255, 255, 200)); // Blanc semi-transparent
        g.fillRect(0, h, w, textHeight);

        // Dessiner le texte centré
        g.setColor(Color.BLACK);
        g.setFont(new Font("Arial", Font.BOLD, 11));
        FontMetrics fm = g.getFontMetrics();
        int textX = (w - fm.stringWidth(texte)) / 2;
        int textY = h + 14; // Un peu de padding vertical

        g.drawString(texte, Math.max(2, textX), textY);
        g.dispose();

        return new ImageIcon(combined);
    }

    public ImageIcon getIconeCarte(String nomCarteBrut) {
        if (nomCarteBrut == null || nomCarteBrut.trim().isEmpty()) {
            return genererIconeTexte("Carte");
        }
        
        String cleCache = nomCarteBrut.trim();

        if (imageCache.containsKey(cleCache)) {
            return imageCache.get(cleCache);
        }

        String nomFichier = determinerNomFichier(cleCache);
        String chemin = IMG_FOLDER + nomFichier;
        File f = new File(chemin);
        
        ImageIcon iconFinale;

        if (f.exists()) {
            ImageIcon icon = new ImageIcon(chemin);
            Image img = icon.getImage();
            Image newImg = img.getScaledInstance(120, 180, java.awt.Image.SCALE_SMOOTH);
            iconFinale = new ImageIcon(newImg);
        } else {
            // Fallback texte si image non trouvée
            String nomAffiche = nettoyerNomPourAffichage(cleCache);
            iconFinale = genererIconeTexte(nomAffiche);
        }

        imageCache.put(cleCache, iconFinale);
        return iconFinale;
    }
    
    private String nettoyerNomPourAffichage(String nom) {
        if (nom.contains("nom=")) {
            int start = nom.indexOf("nom=") + 4;
            int end = nom.indexOf(",", start);
            if (end == -1) end = nom.indexOf("]", start);
            if (end != -1) return nom.substring(start, end);
        }
        return nom;
    }

    private ImageIcon genererIconeTexte(String texte) {
        int w = 120;
        int h = 180;
        BufferedImage img = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = img.createGraphics();

        g2d.setColor(Color.WHITE);
        g2d.fillRect(0, 0, w, h);
        g2d.setColor(Color.BLACK);
        g2d.drawRect(0, 0, w - 1, h - 1);

        g2d.setColor(Color.BLACK);
        g2d.setFont(new Font("Arial", Font.BOLD, 11));
        FontMetrics fm = g2d.getFontMetrics();
        
        String[] mots = texte.split(" ");
        int y = 60;
        for (String mot : mots) {
            if (mot.length() > 12) mot = mot.substring(0, 12) + "..";
            int x = (w - fm.stringWidth(mot)) / 2;
            g2d.drawString(mot, Math.max(2, x), y);
            y += 18;
        }

        g2d.dispose();
        return new ImageIcon(img);
    }

    private String determinerNomFichier(String nom) {
        if (nom.contains("nom=")) {
            try {
                int start = nom.indexOf("nom=") + 4;
                int end = nom.indexOf(",", start);
                if (end == -1) end = nom.indexOf("]", start);
                if (end != -1) nom = nom.substring(start, end);
            } catch (Exception e) {}
        }
        
        String lower = nom.toLowerCase().trim();
        
        if (lower.contains("verso") || lower.contains("cachée") || lower.contains("cache")) {
            return "FaceVerso.png";
        }
        if (lower.contains("joker")) {
            return "Joker.png";
        }

        String valeur = "";
        String couleur = "";

        if (lower.contains("ace") || lower.equals("as") || lower.startsWith("as ") || lower.contains(" as ") || lower.endsWith(" as")) valeur = "As";
        else if (lower.contains("two") || lower.contains("deux") || lower.contains("2")) valeur = "Deux";
        else if (lower.contains("three") || lower.contains("trois") || lower.contains("3")) valeur = "Trois";
        else if (lower.contains("four") || lower.contains("quatre") || lower.contains("4")) valeur = "Quatre";
        else if (lower.contains("five") || lower.contains("cinq") || lower.contains("5")) valeur = "Cinq";

        if (lower.contains("heart") || lower.contains("coeur")) couleur = "DeCoeur";
        else if (lower.contains("diamond") || lower.contains("carreau")) couleur = "DeCarreau";
        else if (lower.contains("spade") || lower.contains("pique")) couleur = "DePique";
        else if (lower.contains("club") || lower.contains("trefle") || lower.contains("trèfle")) couleur = "DeTrefle";

        if (!valeur.isEmpty() && !couleur.isEmpty()) {
            return valeur + couleur + ".png";
        }
        return nom + ".png";
    }

    // ==================== GETTERS ====================
    
    public JFrame getFrame() { return frame; }
    public JTextArea getLogArea() { return logArea; }
    
    // ==================== LOGS ====================

    private void log(String message) {
        SwingUtilities.invokeLater(() -> {
            logArea.append(message + "\n");
            logArea.setCaretPosition(logArea.getDocument().getLength());
        });
    }

    private void updateStatus(String status) {
        SwingUtilities.invokeLater(() -> {
            statusLabel.setText(status);
        });
    }

    @Override public void afficherMessage(String message) { log("💬 " + message); }
    @Override public void afficherErreur(String message) { log("❌ " + message); }

    // ==================== IMPLEMENTATION IVue ====================
    
    @Override public void annonceTrophees() { log("\n🏆 --- TROPHÉES --- 🏆"); }
    @Override public void afficherInfosTrophee(Carte carte) { log("🏆 " + carte.getNom() + " : " + carte.getBandeauTrophee().toString()); }
    @Override public void afficherTropheeRemporte(String nom, int j) { log("🎉 Trophée " + nom + " remporté par Joueur " + j); }
    @Override public void afficherEgaliteTrophee(String nom) { log("⚖️ Égalité pour " + nom); }
    @Override public void afficherEgaliteParfaiteTrophee(String nom) { log("❌ Égalité parfaite " + nom); }
    @Override public void afficherTropheeNonAttribue(String cond) { log("⚠️ Non attribué : " + cond); }
    @Override public void afficherPlusGrandeCarteCouleur(int j, Couleur c, int v) { log("  J" + j + " max " + c + " : " + v); }
    @Override public void afficherPlusPetiteCarteCouleur(int j, Couleur c, int v) { log("  J" + j + " min " + c + " : " + v); }
    
    @Override public void afficherBienvenue() { log("🎮 Nouvelle Partie de Jest"); }
    @Override public void demanderNombreJoueurs() { log("⚙️ Config : Nb Joueurs"); }
    @Override public void afficherNombreJoueursInvalide() { log("❌ Nb Joueurs invalide"); }
    @Override public void demanderTypeJoueur(int n) { log("⚙️ Config : Type J" + n); }
    @Override public void afficherCreationJoueurReel(int n) { log("👤 J" + n + " (Réel) prêt"); }
    @Override public void demanderStrategieJoueurVirtuel(int n) { log("⚙️ Config : Stratégie J" + n); }
    @Override public void afficherCreationJoueurVirtuel(int n) { log("🤖 J" + n + " (IA) prêt"); }
    @Override public void afficherTypeJoueurInvalide() { log("❌ Type invalide"); }
    @Override public void afficherStrategieDefaut() { log("⚙️ Stratégie par défaut"); }
    
    @Override public void demanderExtension() { log("⚙️ Config : Extension"); }
    @Override public void afficherExtensionChoisie(int e) { log("📦 Extension : " + (e==0?"Non":"Oui")); }
    @Override public void afficherExtensionInvalide() { log("❌ Extension invalide"); }
    @Override public void demanderVariante() { log("⚙️ Config : Variante"); }
    @Override public void afficherVarianteChoisie(int v) { log("🎲 Variante : " + (v==0?"Classique":"Inverse")); }
    @Override public void afficherVarianteInvalide() { log("❌ Variante invalide"); }
    @Override public void afficherVarianteInversee() { log("🔄 Mode Inversé actif"); }
    
    @Override public void afficherJestFinalJoueur(int n) { log("\n📚 Jest final J" + n); }
    @Override public void afficherCartesJest(ArrayList<Carte> c) { for(Carte x:c) log("  • " + x.getNom()); }
    @Override public void afficherClassementFinal() { log("\n🏆 CLASSEMENT FINAL"); }
    @Override public void afficherScoreJoueur(int r, int n, int s) { log(r + ". Joueur " + n + " : " + s + " pts"); }
    
    @Override public void afficherSauvegardeReussie(String p) { log("💾 Sauvegardé : " + p); }
    @Override public void afficherErreurSauvegarde(String m) { log("❌ Erreur Save : " + m); }
    @Override public void afficherChargementReussi(String p) { log("📂 Chargé : " + p); }
    @Override public void afficherFichierNonTrouve(String p) { log("❌ Introuvable : " + p); }
    @Override public void afficherErreurLecture(String m) { log("❌ Erreur Lecture : " + m); }
    @Override public void afficherErreurDeserialisation(String m) { log("❌ Erreur Deserialisation : " + m); }
    @Override public void afficherSuppressionReussie(String n) { log("🗑️ Supprimé : " + n); }
    @Override public void afficherErreurSuppression(String n) { log("❌ Erreur Suppression : " + n); }
    @Override public void afficherAucuneSauvegarde() { log("📂 Vide"); }
    @Override public void afficherAucuneSauvegardeASupprimer() { log("📂 Rien à supprimer"); }
    @Override public void afficherNomSauvegardeAuto(String n) { log("💾 Nom auto : " + n); }
    @Override public void afficherListeSauvegardes(ArrayList<String> s) { log("📂 Sauvegardes dispo..."); }
    @Override public void afficherTitreSauvegardes() {}
    @Override public void afficherElementSauvegarde(int i, String n) {}
    @Override public void afficherOptionRetour() {}
    @Override public void demanderChoixSauvegarde() {}
    @Override public void demanderSauvegardeASupprimer() {}
    @Override public void demanderNomSauvegarde() {}
    
    @Override public void afficherMenuPrincipal() { log("\n📋 MENU PRINCIPAL"); }
    @Override public void afficherMenuPause() { log("\n⏸️ MENU PAUSE"); }
    @Override public void demanderChoix() {}
    
    @Override public void afficherDebutTour(int n) { 
        log("\n════ TOUR " + n + " ════"); 
        updateStatus("Tour " + n + " en cours");
    }
    @Override public void afficherNumeroTour(int n) {}
    @Override public void afficherInstructionPause() { log("💡 Entrez 'p' pour pause"); }
    @Override public void afficherFinJeu() { log("🏁 FIN DU JEU"); updateStatus("Partie terminée"); }
    @Override public void afficherPiocheVide() { log("📦 Pioche vide"); }
    @Override public void afficherAuRevoir() { log("👋 Au revoir"); frame.dispose(); }
    @Override public void afficherChoixInvalide() { log("❌ Choix invalide"); }
    
    @Override public void afficherTourJoueur(String n) { 
        log("▶️ Tour de " + n); 
        updateStatus("Tour de " + n);
        optionsPriseEnCours.clear();
    }
    @Override public void afficherCestAuiDeJouer() { log("👉 C'est à lui de jouer"); }
    @Override public void afficherJoueurAvecPlusGrandeValeurVisible(String n, String c) { log("👑 " + n + " commence (" + c + ")"); }
    @Override public void afficherErreurDeterminerJoueurPlusGrandeValeurVisible() { log("❌ Erreur détermination premier joueur"); }
    
    @Override public void afficherOffresDesJoueurs() { log("📋 Les offres sont posées."); }
    @Override public void afficherOffreJoueur(String n, String v, String c) { log("  " + n + " : [👁️" + v + "] [🔒???]"); }
    
    @Override public void afficherOffreDeJoueur(String n) { 
        log("  Offre de " + n);
        joueurOffreEnCours = n;
    }
    
    @Override public void afficherMainJoueur(String n, String m) { log("🃏 Main " + n + " : " + m); }
    @Override public void afficherMainJoueur() { log("🃏 Votre main :"); }
    @Override public void afficherCarteMain(int i, String c) { log("  " + (i+1) + ". " + c); }
    @Override public void afficherJestDeJoueur(String n) {}
    @Override public void afficherChoixOffreJoueur(String n) { log("🤔 " + n + " prépare son offre..."); }
    @Override public void afficherDemandeCarteVisible() {}
    @Override public void afficherCarteChoisiePourOffre(String c) { log("✅ Carte visible : " + c); }
    @Override public void afficherCarteChoisieIA(String c) { log("🤖 IA montre : " + c); }
    
    @Override public void afficherChoixDansPropreOffre() { 
        log("⚠️ Doit prendre dans sa propre offre");
        joueurOffreEnCours = "Votre offre";
    }
    
    @Override public void afficherOptionCarteVisible(String c) {
        log("  1. Visible : " + c);
        optionsPriseEnCours.add("Visible:" + c);
    }
    
    @Override public void afficherOptionCarteCachee() {
        log("  2. Cachée");
        optionsPriseEnCours.add("Cachée:FaceVerso");
    }
    
    @Override public void afficherOptionCarteVisibleOffre(int i, String c) {
        log("  " + i + ". Visible : " + c);
        optionsPriseEnCours.add("Visible:" + c + " (" + joueurOffreEnCours + ")");
    }
    
    @Override public void afficherOptionCarteCacheeOffre(int i) {
        log("  " + i + ". Cachée");
        optionsPriseEnCours.add("Cachée:FaceVerso (" + joueurOffreEnCours + ")");
    }
    
    @Override public void afficherChoixCarteJoueur(String n, int num) { log("👉 " + n + " prend la carte " + num); }
    @Override public void afficherChoixCarteJoueurVirtuel(String n, int c) { log("🤖 " + n + " choisit " + c); }
    @Override public void afficherChoixCarteJoueurVirtuelAdverse(String n, int c) { log("🤖 " + n + " prend " + c); }
    @Override public void afficherJoueurSeFaitPrendreCarte(String n) { log("📤 " + n + " donne une carte"); }
    @Override public void afficherDemandePriseCarte(String n) { log("🤔 " + n + " choisit une carte à prendre..."); }
    @Override public void afficherChoixInvalidePrise() { log("❌ Prise invalide"); }
    @Override public void afficherOptionPrise(int n, String d) {}
    
    @Override public void afficherDemandeCarteARetourner(int n, ArrayList<String> c) {}
    @Override public void afficherDemandeJoueurCible(int n, ArrayList<Integer> j) {}
    @Override public void afficherJoueurNonDisponible() { log("❌ Non dispo"); }
    @Override public void afficherDemandeTypeCarte(int n, String v, boolean c) {}
    @Override public void afficherDemandeConfirmation(String m) {}
    @Override public void afficherErreurNombreCartesNonJouees() { log("❌ Erreur nb cartes"); }
    @Override public void afficherErreurTousJoueursOntJoue() {}
    @Override public void afficherLigneVide() {}
    @Override public void afficherSeparateur() { log("────────────────"); }
    @Override public void afficherErreurSaisie() { log("❌ Erreur saisie"); }
    @Override public void afficherErreurPlage(int min, int max) { log("❌ Hors plage " + min + "-" + max); }
    @Override public void demanderNettoyageConsole() {}
    @Override public void afficherValeurInvalideNettoyageConsole() {}
    @Override public void nettoyerConsole() {}
}

