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
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Separator;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.List;

public class Main extends Application {
    private static final String TITRE = "Vérification pour manège russe";

    private static final String MSG_ENTREE_VIDE = "Remplissez toutes les cases";

    private static final int HAUTEUR_FENETRE = 600;
    private static final int LARGUEUR_FENETRE = 800;
    private static final int PADDING_FENETRE = 30;

    private static final int ESPACE_VBOX = 50;

    private static final Critere[] criteres = {
            new CritereOuiNon("Mal de dos?", false),
            new CritereOuiNon("Malaise cardiaque", false),
            new CritereMinMax("Hauteur", 122, 188, "Rentrez votre hauteur ici")
    };

    private static final Text reponse = Utils.creeTextBoldGrand(null); // Zone qui montre soit le message
    private static final GridPane tableaudeCriteres = Critere.creeTableauDeCritere(criteres);

    public static void main(String[] args) {
        launch(args);
    }

    /**
     * Appelé au début du programme
     */
    @Override
    public void start(@NotNull Stage primaryStage) {
        primaryStage.setTitle(TITRE);

        //Montrer le message "Entrez votre montant"
        reponse.setText(MSG_ENTREE_VIDE);

        //Création du layout (vertical) principal
        VBox layout = new VBox(
                Utils.creeTextTitre(TITRE),
                tableaudeCriteres,
                new Separator(),
                reponse
        );

        //Formatter
        tableaudeCriteres.setAlignment(Pos.CENTER);
        VBox.setVgrow(reponse, Priority.SOMETIMES);
        layout.setSpacing(ESPACE_VBOX);
        layout.setPadding(new Insets(PADDING_FENETRE));
        layout.setAlignment(Pos.CENTER);

        //Commencer l'interface
        Scene scene = new Scene(layout, LARGUEUR_FENETRE, HAUTEUR_FENETRE);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    static void notifierChangement(){
        boolean isPass = true;

        for (Critere critere : criteres) {
            try {
                isPass = isPass && critere.isPass();
            } catch (Exception e) {
                reponse.setText(e.getMessage());
                return;
            }
        }

        reponse.setText(isPass ? "Peut passer" : "Peut pas passer");
    }
}