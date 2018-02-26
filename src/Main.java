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
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class Main extends Application implements ChangeListener<String> {
    private static final String TITRE_APPLICATION = "Puissances";
    private static final int PADDING_FENETRE = 80;
    private static final Font FONT_TITRE = Font.font(null, FontWeight.EXTRA_BOLD, 34);
    private static final Font FONT_BOLD = Font.font(null, FontWeight.BOLD, 15);
    private static final int HGAP_TABLEAU = 20;
    private static final int VGAP_TABLEAU = 10;
    private static final Font FONT_NORMAL = Font.font(16);
    private static final int ESPACE_LAYOUT_PRINCIPALE = 50;
    private static final String MSG_ENTREE_INVALIDE = "'%s' n'est pas un numéro valide\n";

    private final Text txtReponse = new Text();

    private final TextField entreeBase = creeBoiteDeTexte(this, "Entrez la base ici");
    private final TextField entreeExposant = creeBoiteDeTexte(this, "Entrez l'exposant ici");

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        Text txtTitre = new Text(TITRE_APPLICATION);
        txtTitre.setFont(FONT_TITRE);

        GridPane zoneEntree = new GridPane();

        zoneEntree.addRow(0,
                creeTextNormal("Base"),
                entreeBase
        );

        zoneEntree.addRow(1,
                creeTextNormal("Exposant"),
                entreeExposant
        );

        zoneEntree.setHgap(HGAP_TABLEAU);
        zoneEntree.setVgap(VGAP_TABLEAU);

        VBox layoutPrincipale = new VBox(
                txtTitre,
                zoneEntree,
                new Separator(),
                txtReponse
        );

        layoutPrincipale.setAlignment(Pos.CENTER);
        layoutPrincipale.setPadding(new Insets(PADDING_FENETRE));
        layoutPrincipale.setSpacing(ESPACE_LAYOUT_PRINCIPALE);

        primaryStage.setScene(new Scene(layoutPrincipale));
        primaryStage.show();
    }

    @Override
    public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
        String txtBase = entreeBase.getText();
        String txtExposant = entreeExposant.getText();

        if (txtBase.isEmpty()){
            setReponseBold("Indiquer la base");
            return;
        }

        if(txtExposant.isEmpty()){
            setReponseBold("Indiquer l'exposant");
            return;
        }

        Integer base = parseEntree(txtBase);
        Integer exposant = parseEntree(txtExposant);

        if (base == null || exposant == null){
            return;
        }

        setReponseNormal(calculerReponse(base, exposant));
    }

    private static String calculerReponse(int base, int exposant){
        StringBuilder message = new StringBuilder();

        for (int i = 1; i <= exposant ; i++){
            message.append(String.format("%d à l'exposant %d = %d", base, i, (int) Math.pow(base, i)));
            message.append("\n");
        }

        return message.toString();
    }

    private void setReponseBold(String text) {
        txtReponse.setFont(FONT_BOLD);
        txtReponse.setText(text);
    }

    private void setReponseNormal(String text) {
        txtReponse.setFont(FONT_NORMAL);
        txtReponse.setText(text);
    }

    @Nullable
    private Integer parseEntree(@NotNull String entree) {
        try {
            return Integer.parseInt(entree);
        } catch (NumberFormatException e) {
            setReponseBold(String.format(MSG_ENTREE_INVALIDE, entree));
            return null;
        }
    }

    private static Text creeTextNormal(String message){
        Text text = new Text(message);
        text.setFont(FONT_NORMAL);
        return text;
    }

    private static TextField creeBoiteDeTexte(ChangeListener<String> listener, String indice){
        TextField textField = new TextField();
        textField.setFont(FONT_NORMAL);
        textField.setPromptText(indice);
        textField.textProperty().addListener(listener);
        return textField;
    }
}
