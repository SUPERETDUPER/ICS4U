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

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.text.Text;
import javafx.util.converter.IntegerStringConverter;
import main.donnees.Rechercheur;

/**
 * Controlle toute l'interface graphique
 */
class MainController {
    @FXML
    private ChoiceBox<MethodeDeRecherche> choiceBoxOptionsRecherche;
    @FXML
    private TextField fieldReference;
    @FXML
    private Text txtResultat;
    @FXML
    private Button bouttonTrouver;

    /**
     * Ce qui permet d'acceder aux données et d'obtenir des résultats
     */
    private final Rechercheur rechercheur;

    /**
     * Le formatter pour l'entrée de texte de la référence
     */
    private TextFormatter<Integer> textFormatter = new TextFormatter<>(
            new IntegerStringConverter(),
            null,
            change -> !change.isAdded() || change.getText().matches("[0-9]") ? change : null
    );

    MainController(Rechercheur rechercheur) {
        this.rechercheur = rechercheur;
    }

    @FXML
    private void initialize() {
        //Ajouter les options à la liste d'options
        for (MethodeDeRecherche methodeDeRecherche : MethodeDeRecherche.values()) {
            choiceBoxOptionsRecherche.getItems().add(methodeDeRecherche);
        }

        choiceBoxOptionsRecherche.getSelectionModel().select(MethodeDeRecherche.BINAIRE_RECURSIVE);

        //Attacher le formatter
        fieldReference.setTextFormatter(textFormatter);

        //Faire que le button soit "disabled" si les cases ne sont pas toutes remplies
        bouttonTrouver.disableProperty().bind(
                choiceBoxOptionsRecherche.getSelectionModel().selectedItemProperty().isNull().or(
                        fieldReference.textProperty().isEmpty()
                )
        );
    }

    /**
     * Appelé quand le boutton est appuyé
     * Calcule le résultat
     */
    @FXML
    private void handleTrouver() {
        Resultat resultat = rechercheur.rechercher(choiceBoxOptionsRecherche.getValue(), textFormatter.getValue());

        if (resultat.isSuccess()) {
            txtResultat.setText("Livre trouvé!\nNom: " + resultat.getLivre().getNom());
        } else {
            txtResultat.setText("Ce numéro de référence n'existe pas");
        }
    }
}
