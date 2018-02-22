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

        //Montrer le message "Entrez votre montant"
        switchPane.montrerMessage(MSG_ENTREE_VIDE);

        //Création du layout (vertical) principal
        VBox layout = new VBox(
                Utils.creeTextTitre(TITRE),
                creeZoneEntree(),
                new Separator(),
                switchPane
        );

        //Formatter
        switchPane.setAlignment(Pos.CENTER);
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
        //Création de la boite d'entrée de texte
        TextField boiteMontant = new TextField();
        boiteMontant.setFont(Utils.FONT_NORMAL);
        boiteMontant.setPromptText(SUGGESTION_BOITE_DE_TEXTE);
        boiteMontant.setAlignment(Pos.BASELINE_RIGHT);

        //Ajouter le listener
        boiteMontant.textProperty().addListener(this);

        //Création du layout
        HBox hBox = new HBox(
                Utils.creeTextNormal(DESCRIPTION_ENTREE),  //Text de description
                boiteMontant,
                Utils.creeTextNormal(UNITE_DE_MONNAIE) // Text avec l'unité
        );

        hBox.setSpacing(ESPACE_HBOX);
        hBox.setAlignment(Pos.CENTER);

        return hBox;
    }

    /**
     * Appelé quand la valeur de la boite d'entrée de texte est modifié
     *
     * @param nouvelleValeur utilisé pour recalculer les résultats
     */
    @Override
    public void changed(ObservableValue observable, String oldValue, String nouvelleValeur) {
        if (nouvelleValeur.isEmpty()) { // Si aucune valeur quitter
            switchPane.montrerMessage(MSG_ENTREE_VIDE);
            return;
        }

        Float montant = parseEntree(nouvelleValeur);

        if (montant == null) return; // Quitter si parseEntree n'a pas réussi à parse la valeur

        if (montant.intValue() == Integer.MAX_VALUE) { // Vérifier que la valeur n'est pas trop large pour les calculs à venir
            switchPane.montrerMessage(MSG_MONTANT_TROP_LARGE);
            return;
        }

        if (montant < 0) { //Vérifier que c'est un nombre positif
            switchPane.montrerMessage(MSG_INFERIEUR_A_ZERO);
            return;
        }

        switchPane.montrerTableau(montant); // Montrer le tableau de résultat
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