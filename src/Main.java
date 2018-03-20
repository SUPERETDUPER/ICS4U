/*
 * MIT License
 *
 * Copyright (c) 2018 Martin Staadecker
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Separator;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.jetbrains.annotations.NotNull;

public final class Main extends Application implements ChangeListener<String> {
    //CONSTANTES
    private static final String TITRE_APPLICATION = "Puissances";

    private static final String MSG_ENTREE_INVALIDE = "'%s' n'est pas un entier valide\n";
    private static final String MSG_BASE_VIDE = "Indiquer la base";
    private static final String MSG_EXPOSANT_VIDE = "Indiquer l'exposant";

    private static final String INDICE_ENTREE_TEXTE_BASE = "Entrez la base ici";
    private static final String INDICE_ENTREE_TEXT_BASE = "Entrez l'exposant ici";

    private static final String DESCRIPTION_BASE = "Base";
    private static final String DESCRIPTION_EXPOSANT = "Exposant";

    private static final int PADDING_FENETRE = 80;

    private static final int HGAP_TABLEAU = 20;
    private static final int VGAP_TABLEAU = 10;

    private static final int ESPACE_LAYOUT_PRINCIPALE = 50;

    private static final Font FONT_TITRE = Font.font(null, FontWeight.EXTRA_BOLD, 34);
    static final Font FONT_BOLD = Font.font(null, FontWeight.BOLD, 20);
    static final Font FONT_NORMAL = Font.font(16);

    //VARIABLES D'INSTANCES
    private final TextField entreeBase = creeBoiteDeTexte(INDICE_ENTREE_TEXTE_BASE, this);
    private final TextField entreeExposant = creeBoiteDeTexte(INDICE_ENTREE_TEXT_BASE, this);
    private final ZoneReponse zoneReponse = new ZoneReponse();

    public static void main(String[] args) {
        launch(args);
    }

    /**
     * Appelé au début du programme
     *
     * @param primaryStage voir documentation {@link Application}
     */
    @Override
    public void start(@NotNull Stage primaryStage) {
        primaryStage.setTitle(TITRE_APPLICATION);

        //Créer le titre
        Text txtTitre = new Text(TITRE_APPLICATION);
        txtTitre.setFont(FONT_TITRE);

        //Crée la zone où l'utilisateur rentre la base et l'exposant
        GridPane zoneEntree = new GridPane();

        zoneEntree.addRow(0, creeTextNormal(DESCRIPTION_BASE), entreeBase);
        zoneEntree.addRow(1, creeTextNormal(DESCRIPTION_EXPOSANT), entreeExposant);

        zoneEntree.setHgap(HGAP_TABLEAU);
        zoneEntree.setVgap(VGAP_TABLEAU);
        zoneEntree.setAlignment(Pos.CENTER);

        //Créer le layout principale
        VBox layoutPrincipale = new VBox(
                txtTitre,
                zoneEntree,
                new Separator(),
                zoneReponse
        );

        zoneReponse.montrerMessageErreur(MSG_BASE_VIDE); //Montrer le premier message (base vide)

        //Formatter
        VBox.setVgrow(zoneReponse, Priority.ALWAYS);
        layoutPrincipale.setAlignment(Pos.TOP_CENTER);
        layoutPrincipale.setPadding(new Insets(PADDING_FENETRE));
        layoutPrincipale.setSpacing(ESPACE_LAYOUT_PRINCIPALE);

        //Afficher l'interface
        primaryStage.setScene(new Scene(layoutPrincipale));
        primaryStage.setMaximized(true); //Commencer en plein écran
        primaryStage.show();
    }

    /**
     * Appelé quand une valeur dans les entrees de texts est changée
     *
     * @param observable voir documentation {@link ChangeListener}
     * @param oldValue   voir documentation {@link ChangeListener}
     * @param newValue   voir documentation {@link ChangeListener}
     */
    @Override
    public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
        //Lire les entrées
        String txtBase = entreeBase.getText();
        String txtExposant = entreeExposant.getText();

        //Si entrée vide, arrêter et afficher message d'erreur
        if (txtBase.isEmpty()) {
            zoneReponse.montrerMessageErreur(MSG_BASE_VIDE);
            return;
        } else if (txtExposant.isEmpty()) {
            zoneReponse.montrerMessageErreur(MSG_EXPOSANT_VIDE);
            return;
        }

        Long base;
        Long exposant;

        //Essayer de convertir les entrées en numéro
        try {
            base = Long.parseLong(txtBase);
        } catch (NumberFormatException e) {
            zoneReponse.montrerMessageErreur(String.format(MSG_ENTREE_INVALIDE, txtBase));
            return;
        }

        try {
            exposant = Long.parseLong(txtExposant);
        } catch (NumberFormatException e) {
            zoneReponse.montrerMessageErreur(String.format(MSG_ENTREE_INVALIDE, txtExposant));
            return;
        }

        zoneReponse.calculerEtAfficherReponse(base, exposant); //Calculer la réponse et l'afficher
    }

    /**
     * Crée un objet {@link Text} avec font normal
     *
     * @param message message à mettre dans l'objet {@link Text}
     * @return l'objet {@link Text}
     */
    @NotNull
    private static Text creeTextNormal(String message) {
        Text text = new Text(message);
        text.setFont(FONT_NORMAL);
        return text;
    }

    /**
     * Génère une nouvelle boite de texte {@link TextField}
     *
     * @param indice   l'indice à utiliser (texte qui s'affiche dans la boite vide)
     * @param listener un listener qui sera appelé quand la valeur change
     * @return la boite de texte
     */
    private static TextField creeBoiteDeTexte(String indice, ChangeListener<String> listener) {
        TextField textField = new TextField();
        textField.setFont(FONT_NORMAL);
        textField.setPromptText(indice);
        textField.textProperty().addListener(listener);
        return textField;
    }
}