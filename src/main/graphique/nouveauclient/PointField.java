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
import javafx.scene.control.TextFormatter;
import javafx.util.converter.IntegerStringConverter;

/**
 * Une zone de text qui n'accepte que des chiffres
 */
public class PointField extends Field {

    /**
     * Définit un formatteur qui :
     * 1. Converti le text en entier
     * 2. A une valeur par défaut de zéro
     * 3. N'accepte que les chiffres
     */
    private final TextFormatter<Integer> textFormatter = new TextFormatter<>(
            new IntegerStringConverter(),
            0,

            //Si c'est un ajout qui n'est pas entre 0 et 9 retourner null
            change -> change.isAdded() && change.getText().matches("[^0-9]") ? null : change
    );
    ;

    public PointField(@NamedArg("nom") String nom) {
        super(nom);

        field.setTextFormatter(textFormatter);
    }

    /**
     * Retourne le nombre de points à l'aide du convertisseur de text en entier
     */
    int getValue() {
        return textFormatter.getValue();
    }
}
