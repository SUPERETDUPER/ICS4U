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
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Separator;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class Main extends Application {
    private static final String TITRE = "Monnaie";

    private static final String SUGGESTION_BOITE_DE_TEXTE = "Entrez votre montant ici";
    private static final String DESCRIPTION_ENTREE = "Montant";

    private static final String MSG_ENTREE_INVALIDE = "'%s' n'est pas un montant valide\n"; //New line pour que la hauteur ne change pas
    private static final String MSG_ENTREE_VIDE = "Entrez votre montant\n"; //New line pour que la hauteur ne change pas

    private static final Font FONT_TITRE = Font.font(null, FontWeight.EXTRA_BOLD, 35);
    private static final Font FONT_NORMAL = Font.font(15);
    private static final Font FONT_BOLD = Font.font(null, FontWeight.BOLD, 15);
    private static final int INTERLINE = 10;

    private static final int HAUTEUR_FENETRE = 300;
    private static final int LARGUEUR_FENETRE = 800;
    private static final int PADDING_FENETRE = 15;

    private static final int ESPACE_VBOX = 30;
    private static final int ESPACE_HBOX = 20;

    private Text txtReponse;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(@NotNull Stage primaryStage) {
        primaryStage.setTitle(TITRE);

        //Titre
        Text txtTitre = new Text(TITRE);
        txtTitre.setFont(FONT_TITRE);

        //Zone de text où la réponse est montrée
        txtReponse = new Text(MSG_ENTREE_VIDE);
        txtReponse.setLineSpacing(INTERLINE);
        txtReponse.setFont(FONT_BOLD);

        //Création du layout principal
        VBox layout = new VBox(
                txtTitre,
                creeZoneEntree(),
                new Separator(Orientation.HORIZONTAL),
                txtReponse
        );

        layout.setSpacing(ESPACE_VBOX);
        layout.setPadding(new Insets(PADDING_FENETRE));
        layout.setAlignment(Pos.CENTER);

        Scene scene = new Scene(layout, LARGUEUR_FENETRE, HAUTEUR_FENETRE);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    @NotNull
    private HBox creeZoneEntree() {
        //Création des deux boites d'entrées de texte
        TextField boiteMontant = new TextField();
        boiteMontant.setFont(FONT_NORMAL);
        boiteMontant.setPromptText(SUGGESTION_BOITE_DE_TEXTE);
        boiteMontant.setAlignment(Pos.BASELINE_RIGHT);

        //Ajouter les listeners
        boiteMontant.textProperty().addListener(
                (observable, oldValue, newValue) -> montrerReponse(newValue)
        );


        Text text = new Text(DESCRIPTION_ENTREE);
        text.setFont(FONT_NORMAL);

        HBox hBox = new HBox(ESPACE_HBOX, text, boiteMontant);
        hBox.setAlignment(Pos.CENTER);

        return hBox;
    }

    private void montrerReponse(@NotNull String montant) {
        if (montant.isEmpty()) {
            setReponseBold(MSG_ENTREE_VIDE);
            return;
        }

        Long numero1 = parseEntree(montant);

        setReponseNormal("Valeur : " + numero1);
    }

    @Nullable
    private Long parseEntree(@NotNull String entree) {
        try {
            return Long.parseLong(entree);
        } catch (NumberFormatException e) {
            setReponseBold(String.format(MSG_ENTREE_INVALIDE, entree));
            return null;
        }
    }

    private void setReponseBold(@NotNull String text) {
        txtReponse.setFont(FONT_BOLD);
        txtReponse.setText(text);
    }

    private void setReponseNormal(@NotNull String text) {
        txtReponse.setFont(FONT_NORMAL);
        txtReponse.setText(text);
    }
}