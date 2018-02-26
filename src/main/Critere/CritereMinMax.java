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
import javafx.scene.Node;
import javafx.scene.control.TextField;
import main.Utils;
import org.jetbrains.annotations.NotNull;

/**
 * Critère qui prends un nombre comme entrée
 */
public class CritereMinMax extends Critere implements ChangeListener<String> {

    private static final String MSG_ENTREE_INVALIDE = "'%s' n'est pas un numéro valide";

    private final Integer min;
    private final Integer max;

    private final TextField textFieldEntree;

    public CritereMinMax(String nom, Integer min, Integer max, String indicePourBoiteDeText) {
        super(nom);

        this.min = min;
        this.max = max;

        textFieldEntree = Utils.creeBoiteDeTexte(indicePourBoiteDeText);
        textFieldEntree.textProperty().addListener(this);
    }

    @Override
    public Node getObjetEntree() {
        return textFieldEntree;
    }

    /**
     * Appelé quand l'entrée dans la boîte de texte change
     *
     * @param observable voir documentation ChangeListener
     * @param oldValue   voir documentation ChangeListener
     * @param newValue   voir documentation ChangeListener
     */
    @Override
    public void changed(ObservableValue<? extends String> observable, String oldValue, @NotNull String newValue) {
        if (newValue.isEmpty()) {
            setStatusDuCritere(Status.INCOMPLET);
            return;
        }

        int valeur;

        try {
            valeur = Integer.parseInt(newValue);
        } catch (NumberFormatException e) {
            setStatus(Status.REFUSE, String.format(MSG_ENTREE_INVALIDE, newValue));
            return;
        }

        if (valeur < min) {
            setStatus(Status.REFUSE, "Valeur trop petite");
        } else if (valeur > max) {
            setStatus(Status.REFUSE, "Valeur trop grande");
        } else {
            setStatusDuCritere(Status.PASSE);
        }
    }
}