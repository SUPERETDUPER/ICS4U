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
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.text.Text;
import javafx.util.converter.IntegerStringConverter;
import main.algorithme.Algorithme;
import main.algorithme.Resultat;
import main.modele.Modele;

import java.io.IOException;

/**
 * Controlle toute l'interface graphique
 */
class MainController {
    private static final String MSG_LIVRE_NULL = "Ce numéro de référence n'existe pas";
    private static final String MSG_LIVRE_TROUVE = "Livre trouvé!\nNom: %s";
    private static final String MSG_DUREE = "Durée de la recherche: %f ms";
    private static final String TITRE_FENETRE_INFO = "Informations";

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
    private final Modele modele;

    /**
     * Le formatter pour l'entrée de texte de la référence.
     * Empêche les lettres autre que des chiffres
     */
    private final TextFormatter<Integer> textFormatter = new TextFormatter<>(
            new IntegerStringConverter(),
            null,
            change -> change.isAdded() && change.getText().matches("[^0-9]") ? null : change
    );

    MainController(Modele modele) {
        this.modele = modele;
    }

    @FXML
    private void initialize() {
        //Ajouter les options à la liste d'options et choisir la première option
        choiceBoxOptionsRecherche.setItems(modele.getAlgorithmes());
        choiceBoxOptionsRecherche.getSelectionModel().select(0);

        //Attacher le formatter à la boite de text
        fieldReference.setTextFormatter(textFormatter);

        //Faire que le button soit "disabled" si le text n'est pas remplie
        bouttonTrouver.disableProperty().bind(fieldReference.textProperty().isEmpty());
    }

    /**
     * Appelé quand le button d'info est appuyé
     */
    @FXML
    private void handleInfo() {
        try {
            //Créer l'interface avec le message
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/info.fxml"));
            fxmlLoader.setController(new InfoController(choiceBoxOptionsRecherche.getValue().getDescription()));

            //Créer le dialog
            Dialog dialog = new Dialog();
            dialog.setTitle(TITRE_FENETRE_INFO);
            dialog.setDialogPane(fxmlLoader.load()); //Attacher l'interface
            dialog.showAndWait(); //Montrer l'interface
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Appelé quand le boutton est appuyé
     * Calcule le résultat
     */
    @FXML
    private void handleTrouver() {
        //Obtenir le resultat de la recherche
        Resultat resultat = choiceBoxOptionsRecherche.getValue().rechercher(modele.getBaseDeDonnees(), textFormatter.getValue());

        //Afficher le résultat

        txtResultat.setText(
                (resultat.getLivre() == null ? MSG_LIVRE_NULL : String.format(MSG_LIVRE_TROUVE, resultat.getLivre().getNom())) +
                        "\n" +
                        String.format(MSG_DUREE, resultat.getTemps())
        );
    }
}
