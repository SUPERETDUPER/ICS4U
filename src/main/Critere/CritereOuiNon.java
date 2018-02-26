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

package main.Critere;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.control.ChoiceBox;

public class CritereOuiNon extends Critere implements ChangeListener<Number> {

    private static final ObservableList<String> OPTIONS = FXCollections.observableArrayList(
            "Oui",
            "Non"
    );

    private final ChoiceBox<String> choiceBox = new ChoiceBox<>(OPTIONS);

    private final boolean passeAvecOui;

    public CritereOuiNon(String nom, boolean passeAvecOui) {
        super(nom);

        this.passeAvecOui = passeAvecOui;

        choiceBox.getSelectionModel().selectedIndexProperty().addListener(this);
    }

    @Override
    public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
        if (newValue == null) {
            setStatus(Status.INCOMPLET);
        } else if (passeAvecOui == choiceBox.getSelectionModel().isSelected(0)) {
            setStatus(Status.PASSE);
        } else {
            setStatus(Status.REFUSE);
        }
    }

    public Node getObjetEntree() {
        return choiceBox;
    }
}