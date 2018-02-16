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
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class Main extends Application {
    private static final String TITRE_DE_FENETRE = "Div et Mod";

    private static final String SUGGESTION_POUR_FIELD = "Entrez un nombre ici";
    private static final String DESCRIPTION_ENTREE_1 = "Premier numéro";
    private static final String DESCRIPTION_ENTREE_2 = "Deuxième numéro";
    private static final String ERREUR_INPUT_INVALIDE = "'%s' n'est pas un numéro valide\n"; //New line pour que la hauteur ne change pas
    private static final String TXT_PROMT = "Entrez les numéros\n"; //New line pour que la hauteur ne change pas

    private static final Font FONT_NORMAL = Font.font(15);
    private static final Font FONT_BOLD = Font.font(null, FontWeight.BOLD, 15);
    private static final int LINE_SPACING = 10;

    private static final int PADDING = 15;
    private static final int SPACING_VBOX = 20;
    private static final int ESPACE_H_TABLEAU = 20;
    private static final int ESPACE_V_TABLEAU = 10;
    private static final int HAUTEUR_FENETRE = 300;
    private static final int LARGUEUR_FENETRE = 800;
    private Text reponse;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(@NotNull Stage primaryStage) {
        primaryStage.setTitle(TITRE_DE_FENETRE);

        //Zone de text où la réponse est montrée
        reponse = new Text(TXT_PROMT);
        reponse.setLineSpacing(LINE_SPACING);
        reponse.setFont(FONT_BOLD);

        //Création du layout principal
        VBox layout = new VBox(creeZoneEntree(), new Separator(Orientation.HORIZONTAL), reponse);
        layout.setSpacing(SPACING_VBOX);
        layout.setPadding(new Insets(PADDING));
        layout.setAlignment(Pos.CENTER);

        Scene scene = new Scene(layout, LARGUEUR_FENETRE, HAUTEUR_FENETRE);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    @NotNull
    private GridPane creeZoneEntree() {
        //Création des deux boites d'entrées de texte
        final TextField premierTxtField = creeBoiteEntreeDeTexte();
        final TextField deuxiemeTxtField = creeBoiteEntreeDeTexte();

        //Ajouter les listeners
        premierTxtField.textProperty().addListener((observable, oldValue, newValue) -> montrerReponse(newValue, deuxiemeTxtField.getText()));
        deuxiemeTxtField.textProperty().addListener((observable, oldValue, newValue) -> montrerReponse(premierTxtField.getText(), newValue));

        //Création du grid
        GridPane inputPane = new GridPane();
        inputPane.addColumn(0, creeDescription(DESCRIPTION_ENTREE_1), creeDescription(DESCRIPTION_ENTREE_2));
        inputPane.addColumn(1, premierTxtField, deuxiemeTxtField);
        inputPane.setVgap(ESPACE_V_TABLEAU);
        inputPane.setHgap(ESPACE_H_TABLEAU);
        inputPane.setAlignment(Pos.CENTER);

        return inputPane;
    }

    @NotNull
    private static TextField creeBoiteEntreeDeTexte() {
        TextField boiteInput = new TextField();
        boiteInput.setFont(FONT_NORMAL);
        boiteInput.setPromptText(SUGGESTION_POUR_FIELD);
        boiteInput.setAlignment(Pos.BASELINE_RIGHT);
        return boiteInput;
    }

    @NotNull
    private static Text creeDescription(String message) {
        Text text = new Text(message);
        text.setFont(FONT_NORMAL);
        return text;
    }

    private void montrerReponse(@NotNull String entree1, @NotNull String entree2) {
        if (entree1.isEmpty() || entree2.isEmpty()) {
            setReponseBold(TXT_PROMT);
            return;
        }

        Long numero1 = parseEntree(entree1);
        Long numero2 = parseEntree(entree2);

        if (numero1 == null || numero2 == null) {
            return;
        }

        setReponseNormal(creeReponse(numero1, numero2)
                + "\n"
                + creeReponse(numero2, numero1)
        );
    }

    @Nullable
    private Long parseEntree(@NotNull String entree) {
        try {
            return Long.parseLong(entree);
        } catch (NumberFormatException e) {
            setReponseBold(String.format(ERREUR_INPUT_INVALIDE, entree));
            return null;
        }
    }

    private void setReponseBold(@NotNull String text) {
        reponse.setFont(FONT_BOLD);
        reponse.setText(text);
    }

    private void setReponseNormal(@NotNull String text) {
        reponse.setFont(FONT_NORMAL);
        reponse.setText(text);
    }

    @NotNull
    @Contract(pure = true)
    private static String creeReponse(long dividende, long diviseur) {
        if (diviseur == 0) {
            return "Impossible de diviser par zéro";
        }

        return dividende
                + " / "
                + diviseur
                + "  =  "
                + dividende / diviseur
                + " avec reste de "
                + dividende % diviseur;
    }
}