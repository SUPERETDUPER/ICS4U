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
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.converter.NumberStringConverter;
import org.jetbrains.annotations.NotNull;

public class Main extends Application {

    private Text premiereReponse = new Text();
    private Text deuxiemeReponse = new Text();

    private TextField premierTxtField = getInputField();
    private TextField deuxiemeTxtField = getInputField();

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(@NotNull Stage primaryStage) {
        primaryStage.setTitle("Div et Mod");

        GridPane inputPane = new GridPane();
        inputPane.add(new Label("Premier numéro"), 0, 0);
        inputPane.add(new Label("Deuxième numéro"), 0, 1);
        inputPane.add(premierTxtField, 1, 0);
        inputPane.add(deuxiemeTxtField, 1, 1);
        inputPane.setVgap(5);
        inputPane.setHgap(5);

        Button btnCalcul = new Button("Calculez");
        btnCalcul.setOnMouseClicked(event -> {
            update();
        });




        VBox layout = new VBox(inputPane, btnCalcul, premiereReponse, deuxiemeReponse);
        layout.setSpacing(5);
        layout.setPadding(new Insets(10));
        layout.setAlignment(Pos.CENTER);

        Scene scene = new Scene(layout);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void update() {
        premiereReponse.setText(premierTxtField.getText());
    }

    @NotNull
    private static TextField getInputField() {
        TextField boiteInput = new TextField();
        boiteInput.setPromptText("Entrez un nombre ici");
        boiteInput.setAlignment(Pos.CENTER_RIGHT);
        boiteInput.setTextFormatter(new TextFormatter<>(new NumberStringConverter()));
        return boiteInput;
    }
}
