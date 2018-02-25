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

import javafx.scene.Node;
import javafx.scene.control.TextField;
import org.jetbrains.annotations.NotNull;

public class CritereMinMax extends Critere {

    private static final String MSG_ENTREE_INVALIDE = "'%s' n'est pas un numéro valide";

    private final Integer min;
    private final Integer max;

    private final TextField textField;

    CritereMinMax(String nom, Integer min, Integer max, String indicePourBoiteDeText) {
        super(nom);

        this.min = min;
        this.max = max;

        textField = Utils.creeBoiteDeTexte(indicePourBoiteDeText);
        textField.textProperty().addListener(this);
    }

    @Override
    public boolean isPass() throws Exception {
        String entree = textField.getText();

        if (entree.isEmpty()){
            throw new Exception("Remplisser critère : " + this.getNom());
        }

        Integer valeur = parseEntree(entree);
        return min <= valeur && valeur <= max;
    }

    @Override
    public Node getNode() {
        return textField;
    }

    /**
     * Converti du texte en valeur numérique
     * Si c'est impossible montre une erreur sur l'interface
     *
     * @param entree le string à parser
     * @return la valeur résultante ou null si échec
     */
    @NotNull
    private static Integer parseEntree(@NotNull String entree) throws Exception{
        try {
            return Integer.parseInt(entree);
        } catch (NumberFormatException e) {
            throw new Exception(String.format(MSG_ENTREE_INVALIDE, entree));
        }
    }
}
