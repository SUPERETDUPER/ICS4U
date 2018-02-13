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
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

public class Main extends Application {
    private static final String SUGGESTION_POUR_FIELD = "Entrez un nombre ici";
    private static final String DESCRIPTION_ENTREE_1 = "Premier numéro";
    private static final String DESCRIPTION_ENTREE_2 = "Deuxième numéro";

    private static final String TITRE_DE_FENETRE = "Div et Mod";

    private static final String ERREUR_INPUT_INVALIDE = "'%s' n'est pas un numéro valide";

    private static final int FONT_SIZE = 15;
    private static final Font FONT_DEFAUT = Font.font(FONT_SIZE);
    private static final Font FONT_ERREUR = Font.font(null, FontWeight.BOLD, FONT_SIZE);
    private static final int LINE_SPACING = 2;

    private static final int PADDING = 10;
    private static final int SPACING_VBOX = 5;
    private static final int SPACING_TABLEAU = 5;
    private static final int DIMENSION_FENETRE = 400;

    @NotNull
    private Text reponse = new Text();

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(@NotNull Stage primaryStage) {
        primaryStage.setTitle(TITRE_DE_FENETRE);

        reponse.setLineSpacing(LINE_SPACING);

        VBox layout = new VBox(creeZoneEntree(), reponse);
        layout.setSpacing(SPACING_VBOX);
        layout.setPadding(new Insets(PADDING));
        layout.setAlignment(Pos.CENTER);

        Scene scene = new Scene(layout, DIMENSION_FENETRE, DIMENSION_FENETRE);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    @NotNull
    private GridPane creeZoneEntree() {
        //Création des deux boites d'entrées de texte
        final TextField premierTxtField = getNewTextField();
        final TextField deuxiemeTxtField = getNewTextField();

        premierTxtField.textProperty().addListener((observable, oldValue, newValue) -> mettreAJour(newValue, deuxiemeTxtField.getText()));
        deuxiemeTxtField.textProperty().addListener((observable, oldValue, newValue) -> mettreAJour(premierTxtField.getText(), newValue));

        //Création des descriptions
        Text description1 = new Text(DESCRIPTION_ENTREE_1);
        Text description2 = new Text(DESCRIPTION_ENTREE_2);
        description1.setFont(FONT_DEFAUT);
        description2.setFont(FONT_DEFAUT);

        //Création du grid
        GridPane inputPane = new GridPane();
        inputPane.addColumn(0, description1, description2);
        inputPane.addColumn(1, premierTxtField, deuxiemeTxtField);
        inputPane.setVgap(SPACING_TABLEAU);
        inputPane.setHgap(SPACING_TABLEAU);

        return inputPane;
    }

    private void mettreAJour(@NotNull String entree1, @NotNull String entree2) {
        if (entree1.equals("") || entree2.equals("")) {
            reponse.setText("");
            return;
        }

        long numero1;
        long numero2;

        try {
            numero1 = Long.parseLong(entree1);
        } catch (NumberFormatException e) {
            reponse.setFont(FONT_ERREUR);
            reponse.setText(String.format(ERREUR_INPUT_INVALIDE, entree1));
            return;
        }

        try {
            numero2 = Long.parseLong(entree2);
        } catch (NumberFormatException e) {
            reponse.setFont(FONT_ERREUR);
            reponse.setText(String.format(ERREUR_INPUT_INVALIDE, entree2));
            return;
        }

        reponse.setFont(FONT_DEFAUT);
        reponse.setText(calculer(numero1, numero2) + "\n" + calculer(numero2, numero1));
    }

    @NotNull
    @Contract(pure = true)
    private static String calculer(long dividende, long diviseur) {
        if (diviseur == 0) {
            return "Impossible de diviser par zéro";
        }

        return dividende
                + " / "
                + diviseur
                + " = "
                + dividende / diviseur
                + " avec reste de "
                + dividende % diviseur;
    }

    @NotNull
    private static TextField getNewTextField() {
        TextField boiteInput = new TextField();
        boiteInput.setFont(FONT_DEFAUT);
        boiteInput.setPromptText(SUGGESTION_POUR_FIELD);
        boiteInput.setAlignment(Pos.CENTER_RIGHT);
        return boiteInput;
    }
}