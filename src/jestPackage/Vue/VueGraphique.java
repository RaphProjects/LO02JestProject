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
 * <p>
 * Cette classe s'appuie sur Swing pour afficher :
 * </p>
 * <ul>
 *   <li>une fenêtre principale (table de jeu) ;</li>
 *   <li>un historique (zone de log) ;</li>
 *   <li>un panneau persistant des trophées ;</li>
 *   <li>un popup de fin de partie (classement).</li>
 * </ul>
 * <p>
 * Les mises à jour de l'interface sont effectuées via {@link SwingUtilities#invokeLater(Runnable)}
 * afin de respecter le thread EDT de Swing.
 * </p>
 * <p>
 * Cette vue stocke également des données temporaires (options de prise) permettant au contrôleur
 * de reconstruire la liste des choix disponibles.
 * </p>
 */
public class VueGraphique implements IVue {

    // Composants principaux de l'interface
    private JFrame frame;
    private JTextArea logArea;           // Zone de texte pour l'historique des actions
    private JScrollPane scrollPane;       // Conteneur scrollable pour le log
    private JLabel statusLabel;           // Barre de statut en bas de la fenetre
    private JPanel centerPanel;           // Panel central (fond vert table de jeu)
    private JPanel tropheePanel;          // Panel d'affichage des trophees

    // Cache des images pour eviter les rechargements
    private Map<String, ImageIcon> imageCache = new HashMap<>();
    private static final String IMG_FOLDER = "LO02JestImg" + File.separator;

    // Donnees temporaires pour la phase de prise
    private ArrayList<String> optionsPriseEnCours = new ArrayList<>();
    private String joueurOffreEnCours = "";

    // Stockage des trophees pour affichage permanent
    private ArrayList<String> tropheesNoms = new ArrayList<>();
    private ArrayList<String> tropheesConditions = new ArrayList<>();

    // Stockage des scores pour le popup final
    private ArrayList<String> scoresFinaux = new ArrayList<>();
    private Timer timerClassement = null;  // Timer pour declencher le popup apres tous les scores

    /**
     * Construit et initialise la vue graphique (fenêtre + composants).
     */
    public VueGraphique() {
        initializeFrame();
        initializeComponents();
    }

    /**
     * Configure la fenetre principale
     */
    private void initializeFrame() {
        frame = new JFrame("Jest - Jeu de cartes");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1200, 800);
        frame.setLocationRelativeTo(null);  // Centre la fenetre sur l'ecran
        frame.setLayout(new BorderLayout());
    }

    /**
     * Initialise tous les composants graphiques de l'interface
     */
    private void initializeComponents() {
        // Configuration de la zone de log (historique du jeu)
        logArea = new JTextArea();
        logArea.setEditable(false);
        logArea.setFont(new Font("Monospaced", Font.PLAIN, 13));
        logArea.setBackground(new Color(20, 40, 20));       // Fond vert fonce
        logArea.setForeground(new Color(144, 238, 144));    // Texte vert clair
        logArea.setMargin(new Insets(10, 10, 10, 10));
        logArea.setLineWrap(true);
        logArea.setWrapStyleWord(true);

        // Conteneur scrollable pour le log
        scrollPane = new JScrollPane(logArea);
        scrollPane.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(new Color(139, 69, 19), 2),
            "Historique",
            0, 0,
            new Font("Arial", Font.BOLD, 12),
            Color.WHITE
        ));
        scrollPane.setPreferredSize(new Dimension(400, 0));
        scrollPane.getViewport().setBackground(new Color(20, 40, 20));

        // Barre de statut en bas de la fenetre
        statusLabel = new JLabel("Bienvenue dans Jest !");
        statusLabel.setFont(new Font("Arial", Font.BOLD, 16));
        statusLabel.setForeground(Color.WHITE);
        statusLabel.setHorizontalAlignment(SwingConstants.CENTER);
        statusLabel.setOpaque(true);
        statusLabel.setBackground(new Color(20, 60, 20));
        statusLabel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Panel central avec fond vert (simule une table de jeu)
        centerPanel = new JPanel(new BorderLayout());
        centerPanel.setBackground(new Color(34, 139, 34));

        // Logo central decoratif
        JLabel logoLabel = new JLabel("JEST");
        logoLabel.setFont(new Font("Serif", Font.BOLD, 80));
        logoLabel.setForeground(new Color(255, 255, 255, 100));  // Blanc semi-transparent
        logoLabel.setHorizontalAlignment(SwingConstants.CENTER);

        // Panel des trophees (en haut du centre)
        tropheePanel = new JPanel();
        // Utilisation de FlowLayout avec un espacement suffisant
        tropheePanel.setLayout(new FlowLayout(FlowLayout.CENTER, 50, 10));
        tropheePanel.setOpaque(false);  // Transparent pour voir le fond vert
        tropheePanel.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(new Color(255, 215, 0), 3),  // Bordure doree
            "Trophées en jeu",
            javax.swing.border.TitledBorder.CENTER,
            javax.swing.border.TitledBorder.TOP,
            new Font("Arial", Font.BOLD, 18),
            new Color(255, 215, 0)
        ));
        tropheePanel.setVisible(false);  // Cache tant qu'il n'y a pas de trophees

        // Panel pour centrer le logo
        JPanel logoPanel = new JPanel(new GridBagLayout());
        logoPanel.setOpaque(false);
        logoPanel.add(logoLabel);

        // Assemblage du panel central
        centerPanel.add(tropheePanel, BorderLayout.NORTH);
        centerPanel.add(logoPanel, BorderLayout.CENTER);

        // Assemblage de la fenetre principale
        frame.add(centerPanel, BorderLayout.CENTER);
        frame.add(scrollPane, BorderLayout.EAST);
        frame.add(statusLabel, BorderLayout.SOUTH);

        frame.setVisible(true);
    }

    /**
     * Convertit la chaîne brute du modèle (toString) en un affichage HTML compact et lisible
     *
     * @param conditionBrute chaîne brute (ex. {@code TropheeCouleur[...]})
     * @return une chaîne HTML (ou fallback) adaptée à un {@link JLabel}
     */
    private String formaterConditionTrophee(String conditionBrute) {
        if (conditionBrute == null) return "";

        String texte = conditionBrute;
        String couleurTexte = "white"; // Couleur par défaut du texte

        // Cas Trophée Couleur 
        // Ex: TropheeCouleur[ordre=PLUSGRAND, couleurDeCondition=COEUR]
        if (texte.contains("TropheeCouleur")) {
            String ordre = "";
            String symbole = "";

            // Déterminer l'ordre
            if (texte.contains("PLUSGRAND")) ordre = "Plus grand";
            else if (texte.contains("PLUSPETIT")) ordre = "Plus petit";
            else ordre = "Best";

            // Déterminer la couleur et le symbole
            if (texte.contains("COEUR")) { symbole = "♥ Cœur"; couleurTexte = "#ff9999"; } // Rouge clair
            else if (texte.contains("CARREAU")) { symbole = "♦ Carreau"; couleurTexte = "#ff9999"; }
            else if (texte.contains("PIQUE")) { symbole = "♠ Pique"; couleurTexte = "#ccccff"; } // Bleu clair
            else if (texte.contains("TREFLE")) { symbole = "♣ Trèfle"; couleurTexte = "#ccccff"; }

            return "<html><center>" + ordre + "<br><span style='color:" + couleurTexte + "; font-size:14px;'>" + symbole + "</span></center></html>";
        }

        //  Cas Trophée Incolore 
        // Ex: TropheeIncolore[condition=MAJORITÉ, valeurAssociée=4]
        else if (texte.contains("TropheeIncolore")) {
            String condition = "";

            if (texte.contains("JOKER")) condition = "Joker";
            else if (texte.contains("PLUSGRANDJEST_SANSJOKER")) condition = "Meilleur Jest<br>(Sans Joker)";
            else if (texte.contains("PLUSGRANDJEST")) condition = "Meilleur Jest";
            else if (texte.contains("MAJORITÉ")) {
                condition = "Majorité";
                // Extraction de la valeur associée si présente
                if (texte.contains("valeurAssociée=")) {
                    try {
                        // Cherche le chiffre après "valeurAssociée="
                        String valStr = texte.substring(texte.indexOf("valeurAssociée=") + 15);
                        // Nettoyage basique (prend le premier caractère ou jusqu'au crochet)
                        valStr = valStr.replace("]", "").trim();
                        // Si c'est un chiffre simple
                        if(valStr.length() > 0) condition += " de " + valStr.charAt(0);
                    } catch (Exception e) {
                        // Ignore parsing error
                    }
                }
            } else {
                condition = "Spécial";
            }

            return "<html><center>" + condition + "</center></html>";
        }

        // Fallback si format inconnu
        return texte;
    }

    /**
     * Met a jour l'affichage des trophees dans le panneau central.
     * <p>
     * Construit un composant par trophée avec : image + condition formatée.
     * </p>
     */
    private void rafraichirAffichageTrophees() {
        SwingUtilities.invokeLater(() -> {
            tropheePanel.removeAll();

            if (tropheesNoms.isEmpty()) {
                tropheePanel.setVisible(false);
            } else {
                tropheePanel.setVisible(true);

                // Creer un panneau pour chaque trophee
                for (int i = 0; i < tropheesNoms.size(); i++) {
                    String nomTrophee = tropheesNoms.get(i);
                    // Récupère la condition brute et la formate
                    String conditionBrute = (i < tropheesConditions.size()) ? tropheesConditions.get(i) : "";
                    String conditionFormattee = formaterConditionTrophee(conditionBrute);

                    JPanel unTrophee = new JPanel();
                    unTrophee.setLayout(new BoxLayout(unTrophee, BoxLayout.Y_AXIS));
                    unTrophee.setOpaque(false);
                    // Bordure invisible pour espacer les trophées
                    unTrophee.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 10));

                    // Image du trophee
                    ImageIcon iconTrophee = getIconeCarte(nomTrophee);
                    // Redimensionner un peu plus petit pour le panel info
                    Image img = iconTrophee.getImage().getScaledInstance(80, 120, Image.SCALE_SMOOTH);
                    JLabel imageLabel = new JLabel(new ImageIcon(img));
                    imageLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

                    // Condition du trophee en dessous (Texte formaté)
                    JLabel conditionLabel = new JLabel(conditionFormattee);
                    conditionLabel.setFont(new Font("Arial", Font.BOLD, 12));
                    conditionLabel.setForeground(Color.WHITE);
                    conditionLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
                    conditionLabel.setHorizontalAlignment(SwingConstants.CENTER);

                    unTrophee.add(imageLabel);
                    unTrophee.add(Box.createVerticalStrut(5));
                    unTrophee.add(conditionLabel);

                    tropheePanel.add(unTrophee);
                }
            }

            // Forcer le rafraichissement de l'affichage
            tropheePanel.revalidate();
            tropheePanel.repaint();
        });
    }

    /**
     * Affiche une fenetre popup avec le classement final.
     */
    private void afficherPopupClassement() {
        if (scoresFinaux.isEmpty()) return;

        // Construction du message
        StringBuilder sb = new StringBuilder();
        sb.append("Classement final :\n\n");
        for (String s : scoresFinaux) {
            sb.append("  ").append(s).append("\n");
        }
        sb.append("\nMerci d'avoir joue !");

        // Affichage du popup
        JOptionPane.showMessageDialog(
            frame,
            sb.toString(),
            "Fin de partie",
            JOptionPane.INFORMATION_MESSAGE
        );
    }

    // Gestion des options de prise

    /**
     * Recupere et vide la liste des options de prise stockees.
     * <p>
     * Utilisee par le controleur pour connaitre les cartes disponibles.
     * </p>
     *
     * @return une copie des options actuellement stockées (puis vidées)
     */
    public ArrayList<String> getEtViderOptionsPrise() {
        ArrayList<String> result = new ArrayList<>(optionsPriseEnCours);
        optionsPriseEnCours.clear();
        joueurOffreEnCours = "";
        return result;
    }

    // Gestion des images

    /**
     * Ajoute un texte (nom du proprietaire) sous l'icone de la carte.
     * Retourne une nouvelle icone combinee.
     *
     * @param iconOriginal icône d'origine (carte)
     * @param texte texte à afficher sous la carte
     * @return nouvelle icône combinée, ou {@code null} si {@code iconOriginal} est {@code null}
     */
    public ImageIcon ajouterTexteSousIcone(ImageIcon iconOriginal, String texte) {
        if (iconOriginal == null) return null;
        if (texte == null || texte.isEmpty()) return iconOriginal;

        int w = iconOriginal.getIconWidth();
        int h = iconOriginal.getIconHeight();
        int textHeight = 20;  // Hauteur reservee pour le texte

        // Creer une image combinee (carte + espace pour texte)
        BufferedImage combined = new BufferedImage(w, h + textHeight, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = combined.createGraphics();

        // Dessiner l'image de la carte
        g.drawImage(iconOriginal.getImage(), 0, 0, null);

        // Fond blanc semi-transparent pour le texte
        g.setColor(new Color(255, 255, 255, 200));
        g.fillRect(0, h, w, textHeight);

        // Dessiner le texte centre
        g.setColor(Color.BLACK);
        g.setFont(new Font("Arial", Font.BOLD, 11));
        FontMetrics fm = g.getFontMetrics();
        int textX = (w - fm.stringWidth(texte)) / 2;
        int textY = h + 14;

        g.drawString(texte, Math.max(2, textX), textY);
        g.dispose();

        return new ImageIcon(combined);
    }

    /**
     * Charge l'image d'une carte depuis le cache ou le disque.
     * Genere une image texte de remplacement si le fichier n'existe pas.
     *
     * @param nomCarteBrut nom de carte (peut être un nom simple ou une chaîne issue d'un {@code toString()})
     * @return icône correspondante (image réelle ou image texte de remplacement)
     */
    public ImageIcon getIconeCarte(String nomCarteBrut) {
        if (nomCarteBrut == null || nomCarteBrut.trim().isEmpty()) {
            return genererIconeTexte("Carte");
        }

        String cleCache = nomCarteBrut.trim();

        // Verifier le cache d'abord
        if (imageCache.containsKey(cleCache)) {
            return imageCache.get(cleCache);
        }

        // Determiner le nom du fichier image
        String nomFichier = determinerNomFichier(cleCache);
        String chemin = IMG_FOLDER + nomFichier;
        File f = new File(chemin);

        ImageIcon iconFinale;

        if (f.exists()) {
            // Charger et redimensionner l'image
            ImageIcon icon = new ImageIcon(chemin);
            Image img = icon.getImage();
            Image newImg = img.getScaledInstance(120, 180, java.awt.Image.SCALE_SMOOTH);
            iconFinale = new ImageIcon(newImg);
        } else {
            // Generer une image de remplacement avec le nom de la carte
            String nomAffiche = nettoyerNomPourAffichage(cleCache);
            iconFinale = genererIconeTexte(nomAffiche);
        }

        // Ajouter au cache pour les prochains appels
        imageCache.put(cleCache, iconFinale);
        return iconFinale;
    }

    /**
     * Extrait le nom simple de la carte depuis un format {@code toString()} complexe.
     * <p>
     * Ex: {@code "CarteCouleur[nom=As de coeur, ...]"} -> {@code "As de coeur"}.
     * </p>
     *
     * @param nom chaîne brute
     * @return nom simplifié si possible, sinon la chaîne d'origine
     */
    private String nettoyerNomPourAffichage(String nom) {
        if (nom.contains("nom=")) {
            int start = nom.indexOf("nom=") + 4;
            int end = nom.indexOf(",", start);
            if (end == -1) end = nom.indexOf("]", start);
            if (end != -1) return nom.substring(start, end);
        }
        return nom;
    }

    /**
     * Genere une image de carte avec le nom ecrit dessus.
     * Utilisee quand l'image reelle n'est pas trouvee.
     *
     * @param texte texte à afficher sur la carte générée
     * @return une icône représentant une "carte texte"
     */
    private ImageIcon genererIconeTexte(String texte) {
        int w = 120;
        int h = 180;
        BufferedImage img = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = img.createGraphics();

        // Fond blanc avec bordure noire
        g2d.setColor(Color.WHITE);
        g2d.fillRect(0, 0, w, h);
        g2d.setColor(Color.BLACK);
        g2d.drawRect(0, 0, w - 1, h - 1);

        // Ecrire le texte mot par mot
        g2d.setColor(Color.BLACK);
        g2d.setFont(new Font("Arial", Font.BOLD, 11));
        FontMetrics fm = g2d.getFontMetrics();

        String[] mots = texte.split(" ");
        int y = 60;
        for (String mot : mots) {
            // Tronquer les mots trop longs
            if (mot.length() > 12) mot = mot.substring(0, 12) + "..";
            int x = (w - fm.stringWidth(mot)) / 2;
            g2d.drawString(mot, Math.max(2, x), y);
            y += 18;
        }

        g2d.dispose();
        return new ImageIcon(img);
    }

    /**
     * Traduit le nom d'une carte vers le nom de fichier image correspondant.
     * Gere les formats francais et anglais, ainsi que les {@code toString()} complexes.
     *
     * @param nom nom brut
     * @return nom de fichier image (ex. {@code "AsDeCoeur.png"})
     */
    private String determinerNomFichier(String nom) {
        // Extraction du nom si format "CarteCouleur[nom=..., ...]"
        if (nom.contains("nom=")) {
            try {
                int start = nom.indexOf("nom=") + 4;
                int end = nom.indexOf(",", start);
                if (end == -1) end = nom.indexOf("]", start);
                if (end != -1) nom = nom.substring(start, end);
            } catch (Exception e) {
                // En cas d'erreur, continuer avec le nom original
            }
        }

        String lower = nom.toLowerCase().trim();

        // Cas speciaux
        if (lower.contains("verso") || lower.contains("cachée") || lower.contains("cache")) {
            return "FaceVerso.png";
        }
        if (lower.contains("joker")) {
            return "Joker.png";
        }

        String valeur = "";
        String couleur = "";

        // Detection de la valeur
        if (lower.contains("ace") || lower.equals("as") || lower.startsWith("as ") || lower.contains(" as ") || lower.endsWith(" as")) valeur = "As";
        else if (lower.contains("two") || lower.contains("deux") || lower.contains("2")) valeur = "Deux";
        else if (lower.contains("three") || lower.contains("trois") || lower.contains("3")) valeur = "Trois";
        else if (lower.contains("four") || lower.contains("quatre") || lower.contains("4")) valeur = "Quatre";
        else if (lower.contains("five") || lower.contains("cinq") || lower.contains("5")) valeur = "Cinq";

        // Detection de la couleur
        if (lower.contains("heart") || lower.contains("coeur")) couleur = "DeCoeur";
        else if (lower.contains("diamond") || lower.contains("carreau")) couleur = "DeCarreau";
        else if (lower.contains("spade") || lower.contains("pique")) couleur = "DePique";
        else if (lower.contains("club") || lower.contains("trefle") || lower.contains("trèfle")) couleur = "DeTrefle";

        // Construction du nom de fichier si valeur et couleur trouvees
        if (!valeur.isEmpty() && !couleur.isEmpty()) {
            return valeur + couleur + ".png";
        }

        // Fallback : utiliser le nom tel quel
        return nom + ".png";
    }

    // getters

    /**
     * Retourne la fenêtre principale Swing de la vue.
     *
     * @return la {@link JFrame} principale
     */
    public JFrame getFrame() { return frame; }

    /**
     * Retourne la zone de log (historique) utilisée par la vue.
     *
     * @return la {@link JTextArea} d'historique
     */
    public JTextArea getLogArea() { return logArea; }

    // logs

    /**
     * Ajoute un message dans la zone de log avec scroll automatique.
     *
     * @param message message à afficher dans l'historique
     */
    private void log(String message) {
        SwingUtilities.invokeLater(() -> {
            logArea.append(message + "\n");
            // Scroll automatique vers le bas
            logArea.setCaretPosition(logArea.getDocument().getLength());
        });
    }

    /**
     * Met a jour le texte de la barre de statut.
     *
     * @param status texte à afficher dans la barre de statut
     */
    private void updateStatus(String status) {
        SwingUtilities.invokeLater(() -> {
            statusLabel.setText(status);
        });
    }

    /** {@inheritDoc} */
    @Override public void afficherMessage(String message) { log("[Info] " + message); }

    /** {@inheritDoc} */
    @Override public void afficherErreur(String message) { log("[Erreur] " + message); }

    // Implémentation IVue : trophees

    /** {@inheritDoc} */
    @Override
    public void annonceTrophees() {
        log("\n[Trophees]");
        // Reinitialiser pour une nouvelle partie
        tropheesNoms.clear();
        tropheesConditions.clear();
    }

    /** {@inheritDoc} */
    @Override
    public void afficherInfosTrophee(Carte carte) {
        // On logue la version "Brute" pour le debug
        String conditionBrute = carte.getBandeauTrophee().toString();
        log("  " + carte.getNom() + " : " + conditionBrute);

        // Stocker le trophee pour l'affichage graphique
        tropheesNoms.add(carte.getNom());
        tropheesConditions.add(conditionBrute);

        // La méthode rafraichirAffichageTrophees se chargera de formater le texte
        rafraichirAffichageTrophees();
    }

    /** {@inheritDoc} */
    @Override public void afficherTropheeRemporte(String nom, int j) { log("[Bravo] Trophee " + nom + " remporte par Joueur " + j); }

    /** {@inheritDoc} */
    @Override public void afficherEgaliteTrophee(String nom) { log("[Egalite] pour " + nom); }

    /** {@inheritDoc} */
    @Override public void afficherEgaliteParfaiteTrophee(String nom) { log("[Erreur] Egalite parfaite " + nom); }

    /** {@inheritDoc} */
    @Override public void afficherTropheeNonAttribue(String cond) { log("[Attention] Non attribue : " + cond); }

    /** {@inheritDoc} */
    @Override public void afficherPlusGrandeCarteCouleur(int j, Couleur c, int v) { log("  J" + j + " max " + c + " : " + v); }

    /** {@inheritDoc} */
    @Override public void afficherPlusPetiteCarteCouleur(int j, Couleur c, int v) { log("  J" + j + " min " + c + " : " + v); }

    // Implémentation IVue : config

    /** {@inheritDoc} */
    @Override public void afficherBienvenue() { log("[Jeu] Nouvelle partie de Jest"); }

    /** {@inheritDoc} */
    @Override public void demanderNombreJoueurs() { log("[Config] Nb joueurs"); }

    /** {@inheritDoc} */
    @Override public void afficherNombreJoueursInvalide() { log("[Erreur] Nb joueurs invalide"); }

    /** {@inheritDoc} */
    @Override public void demanderTypeJoueur(int n) { log("[Config] Type J" + n); }

    /** {@inheritDoc} */
    @Override public void afficherCreationJoueurReel(int n) { log("[Joueur] J" + n + " (Reel) pret"); }

    /** {@inheritDoc} */
    @Override public void demanderStrategieJoueurVirtuel(int n) { log("[Config] Strategie J" + n); }

    /** {@inheritDoc} */
    @Override public void afficherCreationJoueurVirtuel(int n) { log("[IA] Joueur " + n + " (IA) pret"); }

    /** {@inheritDoc} */
    @Override public void afficherTypeJoueurInvalide() { log("[Erreur] Type invalide"); }

    /** {@inheritDoc} */
    @Override public void afficherStrategieDefaut() { log("[Config] Strategie par defaut"); }

    /** {@inheritDoc} */
    @Override public void demanderExtension() { log("[Config] Extension"); }

    /** {@inheritDoc} */
    @Override public void afficherExtensionChoisie(int e) { log("[Extension] " + (e==0?"Non":"Oui")); }

    /** {@inheritDoc} */
    @Override public void afficherExtensionInvalide() { log("[Erreur] Extension invalide"); }

    /** {@inheritDoc} */
    @Override public void demanderVariante() { log("[Config] Variante"); }

    /** {@inheritDoc} */
    @Override public void afficherVarianteChoisie(int v) { log("[Variante] " + (v==0?"Classique":"Inverse")); }

    /** {@inheritDoc} */
    @Override public void afficherVarianteInvalide() { log("[Erreur] Variante invalide"); }

    /** {@inheritDoc} */
    @Override public void afficherVarianteInversee() { log("[Mode] Inverse actif"); }

    // Implémentation IVue : scores

    /** {@inheritDoc} */
    @Override public void afficherJestFinalJoueur(int n) { log("\n[Jest final] Joueur " + n); }

    /** {@inheritDoc} */
    @Override public void afficherCartesJest(ArrayList<Carte> c) { for(Carte x:c) log("  - " + x.getNom()); }

    /** {@inheritDoc} */
    @Override
    public void afficherClassementFinal() {
        log("\n[Classement final]");
        // Preparer la liste pour le popup
        scoresFinaux.clear();
    }

    /** {@inheritDoc} */
    @Override
    public void afficherScoreJoueur(int r, int n, int s) {
        String ligne = r + ". Joueur " + n + " : " + s + " pts";
        log("  " + ligne);
        scoresFinaux.add(ligne);

        // Annuler le timer precedent si existant
        if (timerClassement != null) {
            timerClassement.stop();
        }

        // Declencher le popup 300ms apres le dernier score recu
        timerClassement = new Timer(300, e -> {
            afficherPopupClassement();
        });
        timerClassement.setRepeats(false);
        timerClassement.start();
    }

    // Implémentation IVUE : sauvegarde

    /** {@inheritDoc} */
    @Override public void afficherSauvegardeReussie(String p) { log("[Save] OK : " + p); }

    /** {@inheritDoc} */
    @Override public void afficherErreurSauvegarde(String m) { log("[Erreur] Save : " + m); }

    /** {@inheritDoc} */
    @Override public void afficherChargementReussi(String p) { log("[Charge] OK : " + p); }

    /** {@inheritDoc} */
    @Override public void afficherFichierNonTrouve(String p) { log("[Erreur] Introuvable : " + p); }

    /** {@inheritDoc} */
    @Override public void afficherErreurLecture(String m) { log("[Erreur] Lecture : " + m); }

    /** {@inheritDoc} */
    @Override public void afficherErreurDeserialisation(String m) { log("[Erreur] Deserialisation : " + m); }

    /** {@inheritDoc} */
    @Override public void afficherSuppressionReussie(String n) { log("[Suppr] OK : " + n); }

    /** {@inheritDoc} */
    @Override public void afficherErreurSuppression(String n) { log("[Erreur] Suppression : " + n); }

    /** {@inheritDoc} */
    @Override public void afficherAucuneSauvegarde() { log("[Fichier] Vide"); }

    /** {@inheritDoc} */
    @Override public void afficherAucuneSauvegardeASupprimer() { log("[Fichier] Rien a supprimer"); }

    /** {@inheritDoc} */
    @Override public void afficherNomSauvegardeAuto(String n) { log("[Save] Nom auto : " + n); }

    /** {@inheritDoc} */
    @Override public void afficherListeSauvegardes(ArrayList<String> s) { log("[Fichier] Sauvegardes dispo..."); }

    /** {@inheritDoc} */
    @Override public void afficherTitreSauvegardes() {}

    /** {@inheritDoc} */
    @Override public void afficherElementSauvegarde(int i, String n) {}

    /** {@inheritDoc} */
    @Override public void afficherOptionRetour() {}

    /** {@inheritDoc} */
    @Override public void demanderChoixSauvegarde() {}

    /** {@inheritDoc} */
    @Override public void demanderSauvegardeASupprimer() {}

    /** {@inheritDoc} */
    @Override public void demanderNomSauvegarde() {}

    // Implémentation IVue : menus

    /** {@inheritDoc} */
    @Override public void afficherMenuPrincipal() {
        log("\n[Menu principal]");
        // Cacher les trophees quand on revient au menu
        tropheesNoms.clear();
        tropheesConditions.clear();
        rafraichirAffichageTrophees();
    }

    /** {@inheritDoc} */
    @Override public void afficherMenuPause() { log("\n[Menu pause]"); }

    /** {@inheritDoc} */
    @Override public void demanderChoix() {}

    // Implémentation Ivue : déroulement du jeu

    /** {@inheritDoc} */
    @Override public void afficherDebutTour(int n) {
        log("\n[Tour " + n + "]");
        updateStatus("Tour " + n + " en cours");
    }

    /** {@inheritDoc} */
    @Override public void afficherNumeroTour(int n) {}

    /** {@inheritDoc} */
    @Override public void afficherInstructionPause() { log("[Info] Entrez 'p' pour pause"); }

    /** {@inheritDoc} */
    @Override public void afficherFinJeu() { log("[Fin du jeu]"); updateStatus("Partie terminee"); }

    /** {@inheritDoc} */
    @Override public void afficherPiocheVide() { log("[Paquet] Pioche vide"); }

    /** {@inheritDoc} */
    @Override public void afficherAuRevoir() { log("[Bye] Au revoir"); frame.dispose(); }

    /** {@inheritDoc} */
    @Override public void afficherChoixInvalide() { log("[Erreur] Choix invalide"); }

    /** {@inheritDoc} */
    @Override public void afficherTourJoueur(String n) {
        log("> Tour de " + n);
        updateStatus("Tour de " + n);
        // Reinitialiser les options de prise pour ce joueur
        optionsPriseEnCours.clear();
    }

    /** {@inheritDoc} */
    @Override public void afficherCestAuiDeJouer() { log("  C'est a lui de jouer"); }

    /** {@inheritDoc} */
    @Override public void afficherJoueurAvecPlusGrandeValeurVisible(String n, String c) { log("[Premier] " + n + " commence (" + c + ")"); }

    /** {@inheritDoc} */
    @Override public void afficherErreurDeterminerJoueurPlusGrandeValeurVisible() { log("[Erreur] Determination premier joueur"); }

    // Implémentation IVue : offres

    /** {@inheritDoc} */
    @Override public void afficherOffresDesJoueurs() { log("[Jeu] Les offres sont posees."); }

    /** {@inheritDoc} */
    @Override public void afficherOffreJoueur(String n, String v, String c) { log("  " + n + " : [Visible " + v + "] [Cachee]"); }

    /** {@inheritDoc} */
    @Override public void afficherOffreDeJoueur(String n) {
        log("  Offre de " + n);
        // Memoriser le joueur courant pour les options de prise
        joueurOffreEnCours = n;
    }

    /** {@inheritDoc} */
    @Override public void afficherMainJoueur(String n, String m) { log("[Main] " + n + " : " + m); }

    /** {@inheritDoc} */
    @Override public void afficherMainJoueur() { log("[Main] Votre main :"); }

    /** {@inheritDoc} */
    @Override public void afficherCarteMain(int i, String c) { log("  " + (i+1) + ". " + c); }

    /** {@inheritDoc} */
    @Override public void afficherJestDeJoueur(String n) {}

    /** {@inheritDoc} */
    @Override public void afficherChoixOffreJoueur(String n) { log("[Action] " + n + " prepare son offre..."); }

    /** {@inheritDoc} */
    @Override public void afficherDemandeCarteVisible() {}

    /** {@inheritDoc} */
    @Override public void afficherCarteChoisiePourOffre(String c) { log("[Choix] Carte visible : " + c); }

    /** {@inheritDoc} */
    @Override public void afficherCarteChoisieIA(String c) { log("[IA] Montre : " + c); }

    // Implémentation IVue : prises

    /** {@inheritDoc} */
    @Override public void afficherChoixDansPropreOffre() {
        log("[Attention] Doit prendre dans sa propre offre");
        joueurOffreEnCours = "Votre offre";
    }

    // Les options de prise sont stockees pour que le controleur puisse les recuperer

    /** {@inheritDoc} */
    @Override public void afficherOptionCarteVisible(String c) {
        log("  1. Visible : " + c);
        optionsPriseEnCours.add("Visible:" + c);
    }

    /** {@inheritDoc} */
    @Override public void afficherOptionCarteCachee() {
        log("  2. Cachee");
        optionsPriseEnCours.add("Cachée:FaceVerso");
    }

    // Format avec crochets [] pour le proprietaire (evite conflit avec parentheses dans "Joueur (IA)")

    /** {@inheritDoc} */
    @Override public void afficherOptionCarteVisibleOffre(int i, String c) {
        log("  " + i + ". Visible : " + c);
        optionsPriseEnCours.add("Visible:" + c + " [" + joueurOffreEnCours + "]");
    }

    /** {@inheritDoc} */
    @Override public void afficherOptionCarteCacheeOffre(int i) {
        log("  " + i + ". Cachee");
        optionsPriseEnCours.add("Cachée:FaceVerso [" + joueurOffreEnCours + "]");
    }

    /** {@inheritDoc} */
    @Override public void afficherChoixCarteJoueur(String n, int num) { log("  " + n + " prend la carte " + num); }

    /** {@inheritDoc} */
    @Override public void afficherChoixCarteJoueurVirtuel(String n, int c) { log("[IA] " + n + " choisit " + c); }

    /** {@inheritDoc} */
    @Override public void afficherChoixCarteJoueurVirtuelAdverse(String n, int c) { log("[IA] " + n + " prend " + c); }

    /** {@inheritDoc} */
    @Override public void afficherJoueurSeFaitPrendreCarte(String n) { log("  " + n + " donne une carte"); }

    /** {@inheritDoc} */
    @Override public void afficherDemandePriseCarte(String n) { log("[Reflexion] " + n + " choisit une carte a prendre..."); }

    /** {@inheritDoc} */
    @Override public void afficherChoixInvalidePrise() { log("[Erreur] Prise invalide"); }

    /** {@inheritDoc} */
    @Override public void afficherOptionPrise(int n, String d) {}

    // Implementation IVUE - Autres

    /** {@inheritDoc} */
    @Override public void afficherDemandeCarteARetourner(int n, ArrayList<String> c) {}

    /** {@inheritDoc} */
    @Override public void afficherDemandeJoueurCible(int n, ArrayList<Integer> j) {}

    /** {@inheritDoc} */
    @Override public void afficherJoueurNonDisponible() { log("[Erreur] Non dispo"); }

    /** {@inheritDoc} */
    @Override public void afficherDemandeTypeCarte(int n, String v, boolean c) {}

    /** {@inheritDoc} */
    @Override public void afficherDemandeConfirmation(String m) {}

    /** {@inheritDoc} */
    @Override public void afficherErreurNombreCartesNonJouees() { log("[Erreur] Nb cartes"); }

    /** {@inheritDoc} */
    @Override public void afficherErreurTousJoueursOntJoue() {}

    /** {@inheritDoc} */
    @Override public void afficherLigneVide() {}

    /** {@inheritDoc} */
    @Override public void afficherSeparateur() { log(""); }

    /** {@inheritDoc} */
    @Override public void afficherErreurSaisie() { log("[Erreur] Saisie"); }

    /** {@inheritDoc} */
    @Override public void afficherErreurPlage(int min, int max) { log("[Erreur] Hors plage " + min + "-" + max); }

    /** {@inheritDoc} */
    @Override public void demanderNettoyageConsole() {}

    /** {@inheritDoc} */
    @Override public void afficherValeurInvalideNettoyageConsole() {}

    /** {@inheritDoc} */
    @Override public void nettoyerConsole() {}
}