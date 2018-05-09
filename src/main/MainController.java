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

package main;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.text.Text;
import javafx.util.converter.IntegerStringConverter;
import main.algorithme.Algorithme;
import main.donnees.Livre;

import java.io.IOException;

/**
 * Controlle toute l'interface graphique
 */
class MainController {
    //DE FXML
    @FXML
    private ChoiceBox<Algorithme> choiceBoxOptionsRecherche;
    @FXML
    private TextField fieldReference;
    @FXML
    private Text txtResultat;
    @FXML
    private Button bouttonTrouver;
    @FXML
    private Text txtDescription;

    /**
     * La liste d'algorithme
     */
    private final ObservableList<Algorithme> algorithmes;

    /**
     * Le formatter pour l'entrée de texte de la référence.
     * Empêche les lettres autre que des chiffres
     */
    private final TextFormatter<Integer> textFormatter = new TextFormatter<>(
            new IntegerStringConverter(),
            null,
            change -> change.isAdded() && change.getText().matches("[^0-9]") ? null : change
    );

    MainController(ObservableList<Algorithme> algorithmes) {
        this.algorithmes = algorithmes;
    }

    @FXML
    private void initialize() {
        //Ajouter les options à la liste d'options
        choiceBoxOptionsRecherche.setItems(algorithmes);

        choiceBoxOptionsRecherche.getSelectionModel().select(0);

        //Attacher le formatter
        fieldReference.setTextFormatter(textFormatter);

        //Faire que le button soit "disabled" si les cases ne sont pas toutes remplies
        bouttonTrouver.disableProperty().bind(
                choiceBoxOptionsRecherche.getSelectionModel().selectedItemProperty().isNull().or(
                        fieldReference.textProperty().isEmpty()
                )
        );
    }

    @FXML
    private void handleInfo() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/info.fxml"));
            fxmlLoader.setController(new InfoController(choiceBoxOptionsRecherche.getValue().getDescription()));

            Dialog dialog = new Dialog();
            dialog.setTitle("Informations");
            dialog.setDialogPane(fxmlLoader.load());
            dialog.showAndWait();
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    /**
     * Appelé quand le boutton est appuyé
     * Calcule le résultat
     */
    @FXML
    private void handleTrouver() {
        Livre livre = choiceBoxOptionsRecherche.getValue().rechercher(textFormatter.getValue());

        if (livre != null) {
            txtResultat.setText("Livre trouvé!\nNom: " + livre.getNom());
        } else {
            txtResultat.setText("Ce numéro de référence n'existe pas");
        }
    }
}
