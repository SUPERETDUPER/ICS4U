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
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

public class Main extends Application {
    private static final String SUGGESTION_POUR_FIELD = "Entrez un nombre ici";
    private static final String DESCRIPTION_ENTREE_1 = "Premier numéro";
    private static final String DESCRIPTION_ENTREE_2 = "Deuxième numéro";
    private static final String TITRE_DE_FENETRE = "Div et Mod";
    private static final int PADDING = 10;
    private static final int ESPACE_ENTREE_OBJETS = 5;
    private static final int ESPACE_TABLEAU = 5;

    @NotNull
    private Text premiereReponse = new Text();
    @NotNull
    private Text deuxiemeReponse = new Text();

    private String txt1 = "";
    private String txt2 = "";

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(@NotNull Stage primaryStage) {
        primaryStage.setTitle(TITRE_DE_FENETRE);

        //Creation des deux boites d'entrées de texte
        TextField premierTxtField = getInputField();
        TextField deuxiemeTxtField = getInputField();

        premierTxtField.textProperty().addListener((observable, oldValue, newValue) -> {
            txt1 = newValue;
            mettreAJour();
        });

        deuxiemeTxtField.textProperty().addListener((observable, oldValue, newValue) -> {
            txt2 = newValue;
            mettreAJour();
        });


        GridPane inputPane = new GridPane();
        inputPane.add(new Label(DESCRIPTION_ENTREE_1), 0, 0);
        inputPane.add(new Label(DESCRIPTION_ENTREE_2), 0, 1);
        inputPane.add(premierTxtField, 1, 0);
        inputPane.add(deuxiemeTxtField, 1, 1);
        inputPane.setVgap(ESPACE_TABLEAU);
        inputPane.setHgap(ESPACE_TABLEAU);


        VBox layout = new VBox(inputPane, premiereReponse, deuxiemeReponse);
        layout.setSpacing(ESPACE_ENTREE_OBJETS);
        layout.setPadding(new Insets(PADDING));
        layout.setAlignment(Pos.CENTER);

        Scene scene = new Scene(layout);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void mettreAJour() {

        if (txt1.equals("") || txt2.equals("")) {
            premiereReponse.setText("");
            deuxiemeReponse.setText("");
            return;
        }

        long numero1;
        long numero2;

        try {
            numero1 = Long.parseLong(txt1);
        } catch (NumberFormatException e) {
            premiereReponse.setText('"' + txt2 + '"' + " est trop grand ou invalide");
            deuxiemeReponse.setText("");
            return;
        }

        try {
            numero2 = Long.parseLong(txt2);
        } catch (NumberFormatException e) {
            premiereReponse.setText('"' + txt2 + '"' + " est trop grand ou invalide");
            deuxiemeReponse.setText("");
            return;
        }

        premiereReponse.setText(calculer(numero1, numero2));
        deuxiemeReponse.setText(calculer(numero2, numero1));
    }

    @NotNull
    @Contract(pure = true)
    private String calculer(long dividende, long diviseur) {
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
    private static TextField getInputField() {
        TextField boiteInput = new TextField();
        boiteInput.setPromptText(SUGGESTION_POUR_FIELD);
        boiteInput.setAlignment(Pos.CENTER_RIGHT);
        return boiteInput;
    }
}