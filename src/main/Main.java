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
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
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

    private static final int HAUTEUR_FENETRE = 600;
    private static final int LARGUEUR_FENETRE = 800;
    private static final int PADDING_FENETRE = 30;

    private static final int ESPACE_VBOX = 60;
    private static final int ESPACE_HBOX = 20;

    private final SwitchPane switchPane = new SwitchPane();

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(@NotNull Stage primaryStage) {
        primaryStage.setTitle(TITRE);

        //Titre
        Text txtTitre = new Text(TITRE);
        txtTitre.setFont(Constantes.FONT_TITRE);

        //Création du layout principal
        VBox layout = new VBox(
                txtTitre,
                creeZoneEntree(),
                new Separator(Orientation.HORIZONTAL),
                switchPane
        );

        VBox.setVgrow(switchPane, Priority.ALWAYS);
        switchPane.montrerMessage(MSG_ENTREE_VIDE);

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
        boiteMontant.setFont(Constantes.FONT_NORMAL);
        boiteMontant.setPromptText(SUGGESTION_BOITE_DE_TEXTE);
        boiteMontant.setAlignment(Pos.BASELINE_RIGHT);

        //Ajouter les listeners
        boiteMontant.textProperty().addListener(
                (observable, oldValue, newValue) -> montrerReponse(newValue)
        );


        Text text = new Text(DESCRIPTION_ENTREE);
        text.setFont(Constantes.FONT_NORMAL);

        HBox hBox = new HBox(ESPACE_HBOX, text, boiteMontant);
        hBox.setAlignment(Pos.CENTER);

        return hBox;
    }

    private void montrerReponse(@NotNull String montant) {
        if (montant.isEmpty()) {
            //TODO
            return;
        }

        Float numero1 = parseEntree(montant);

        if (numero1 == null){
            //TODO
            return;
        }

        switchPane.montrerTableau(numero1);
    }

    @Nullable
    private Float parseEntree(@NotNull String entree) {
        try {
            return Float.parseFloat(entree);
        } catch (NumberFormatException e) {
            switchPane.montrerMessage(String.format(MSG_ENTREE_INVALIDE, entree));
            return null;
        }
    }
}