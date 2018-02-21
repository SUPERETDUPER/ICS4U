/*
 * MIT License
 *
 * Copyright (c) [2018] [Martin Staadecker]
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

package main;

import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Separator;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class Main extends Application implements ChangeListener<String> {
    private static final String TITRE = "Monnaie";

    private static final String SUGGESTION_BOITE_DE_TEXTE = "Entrez votre montant ici";
    private static final String DESCRIPTION_ENTREE = "Montant";

    private static final String MSG_ENTREE_INVALIDE = "'%s' n'est pas un montant valide";
    private static final String MSG_ENTREE_VIDE = "Entrez votre montant";
    private static final String MSG_MONTANT_TROP_LARGE = "Montant trop grand";
    private static final String MSG_INFERIEUR_A_ZERO = "Montant doit être supérieur à zéro";

    private static final Font FONT_TITRE = Font.font(null, FontWeight.EXTRA_BOLD, 34);
    static final Font FONT_NORMAL = Font.font(16);
    static final Font FONT_BOLD = Font.font(null, FontWeight.BOLD, 16);
    static final Font FONT_BOLD_LARGE = Font.font(null, FontWeight.BOLD, 20);

    private static final int HAUTEUR_FENETRE = 600;
    private static final int LARGUEUR_FENETRE = 800;
    private static final int PADDING_FENETRE = 30;

    private static final int ESPACE_VBOX = 50;
    private static final int ESPACE_HBOX = 20;
    private static final String UNITE_DE_MONNAIE = "$";

    private final SwitchPane switchPane = new SwitchPane(); // Zone qui montre soit le tableau de résultat ou un message

    public static void main(String[] args) {
        launch(args);
    }

    /*
    Appelé au début du programme
     */
    @Override
    public void start(@NotNull Stage primaryStage) {
        primaryStage.setTitle(TITRE);

        //Création du titre
        Text txtTitre = new Text(TITRE);
        txtTitre.setFont(FONT_TITRE);

        //Montrer le message "Entrez votre montant"
        switchPane.montrerMessage(MSG_ENTREE_VIDE);

        //Création du layout (vertical) principal
        VBox layout = new VBox(
                txtTitre,
                creeZoneEntree(),
                new Separator(),
                switchPane
        );

        //Formatter
        VBox.setVgrow(switchPane, Priority.SOMETIMES);
        layout.setSpacing(ESPACE_VBOX);
        layout.setPadding(new Insets(PADDING_FENETRE));
        layout.setAlignment(Pos.CENTER);

        //Commencer l'interface
        Scene scene = new Scene(layout, LARGUEUR_FENETRE, HAUTEUR_FENETRE);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    /*
    Crée la boite d'entrée de texte et sa description
     */
    @NotNull
    private HBox creeZoneEntree() {
        //Création de la description de la boite d'entrée de texte
        Text description = new Text(DESCRIPTION_ENTREE);
        description.setFont(FONT_NORMAL);

        //Création de la boite d'entrée de texte
        TextField boiteMontant = new TextField();
        boiteMontant.setFont(FONT_NORMAL);
        boiteMontant.setPromptText(SUGGESTION_BOITE_DE_TEXTE);
        boiteMontant.setAlignment(Pos.BASELINE_RIGHT);

        //Ajouter le listener
        boiteMontant.textProperty().addListener(this);

        //Création du txt pour l'unité
        Text unite = new Text(UNITE_DE_MONNAIE);
        unite.setFont(FONT_NORMAL);

        //Création du layout
        HBox hBox = new HBox(ESPACE_HBOX, description, boiteMontant, unite);
        hBox.setAlignment(Pos.CENTER);

        return hBox;
    }

    /**
     * Appelé quand la valeur de la boite d'entrée de texte est modifié
     *
     * @param newValue utilisé pour recalculer les résultats
     */
    @Override
    public void changed(ObservableValue observable, String oldValue, String newValue) {
        if (newValue.isEmpty()) { // Si aucune valeur quitter
            switchPane.montrerMessage(MSG_ENTREE_VIDE);
            return;
        }

        Float numero1 = parseEntree(newValue);

        if (numero1 == null) return; // Quitter si parseEntree n'a pas réussi à parse la valeur

        if (numero1.intValue() == Integer.MAX_VALUE) { // Vérifier que la valeur n'est pas trop large pour les calculs à venir
            switchPane.montrerMessage(MSG_MONTANT_TROP_LARGE);
            return;
        }

        if (numero1 < 0) { //Vérifier que c'est un nombre positif
            switchPane.montrerMessage(MSG_INFERIEUR_A_ZERO);
            return;
        }

        switchPane.montrerTableau(numero1); // Montrer le tableau de résultat
    }

    /**
     * Converti du texte en valeur numérique
     * Si c'est impossible montre une erreur sur l'interface
     *
     * @param entree le string à parser
     * @return la valeur résultante ou null si échec
     */
    @Nullable
    private Float parseEntree(@NotNull String entree) {
        try {
            entree = entree.replaceAll(",", "."); // Pour que les virgules soient accéptées
            return Float.parseFloat(entree);
        } catch (NumberFormatException e) {
            switchPane.montrerMessage(String.format(MSG_ENTREE_INVALIDE, entree));
            return null;
        }
    }
}