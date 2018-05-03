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

package main.graphique.nouveauclient;

import javafx.beans.binding.Bindings;
import javafx.beans.property.ReadOnlyBooleanProperty;
import javafx.beans.property.ReadOnlyBooleanWrapper;
import javafx.fxml.FXML;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.VBox;
import main.donnee.Client;


class ClientEntreeController {
    @FXML
    private VBox root;

    @FXML
    private ButtonType appliquer;

    @FXML
    private NameField prenom;

    @FXML
    private NameField nom;

    @FXML
    private PointField semaineUn;

    @FXML
    private PointField semaineDeux;

    @FXML
    private PointField semaineTrois;

    @FXML
    private PointField semaineQuatre;

    /**
     * Défini si tous les fields on été remplit
     */
    private final ReadOnlyBooleanWrapper toutRemplit = new ReadOnlyBooleanWrapper(false);

    @FXML
    private void initialize() {
        toutRemplit.bind(
                Bindings.or(prenom.isEmptyProperty(), nom.isEmptyProperty())
                        .or(semaineUn.isEmptyProperty())
                        .or(semaineDeux.isEmptyProperty())
                        .or(semaineTrois.isEmptyProperty())
                        .or(semaineQuatre.isEmptyProperty())
        );
    }

    ReadOnlyBooleanProperty toutRemplitProperty() {
        return toutRemplit.getReadOnlyProperty();
    }

    /**
     * Permet de créer le client avec les valeurs des entrées
     *
     * @return
     */
    Client creerClient() {
        return new Client(
                prenom.getValue(),
                nom.getValue(),
                semaineUn.getValue(),
                semaineDeux.getValue(),
                semaineTrois.getValue(),
                semaineQuatre.getValue()

        );
    }
}