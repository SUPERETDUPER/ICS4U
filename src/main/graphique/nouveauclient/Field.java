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

import javafx.beans.NamedArg;
import javafx.beans.binding.BooleanBinding;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;

import java.io.IOException;

/**
 * Une zone d'entrée de text
 * Est le controller et l'objet en même temps
 */
class Field extends HBox {
    @FXML
    private Text description;

    @FXML
    protected TextField field;

    /**
     * Le nom de la zone de text
     */
    private final String nom;

    /**
     * @param nom le nom qui peut être passé directement dans le FXML
     */
    Field(@NamedArg("nom") String nom) {
        this.nom = nom;

        //Charger du fxml
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/field.fxml"));
        loader.setController(this);
        loader.setRoot(this);

        try {
            loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Inscrit le nom dans la boite de description
     */
    @FXML
    private void initialize() {
        description.setText(nom);
    }

    /**
     * @return vrai Si la boite de text est vide
     */
    BooleanBinding isEmptyProperty() {
        return field.textProperty().isEmpty();
    }
}
