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

import javafx.scene.layout.*;
import javafx.scene.text.Text;

/**
 * Pane qui montre soit du texte ou le tableau de résultat
 */
class SwitchPane extends StackPane {
    private final TableauResultat tableauResultat = new TableauResultat();
    private final Text message = Utils.creeTextBoldGrand(null);

    /**
     * Montrer le message dans le pane
     * @param message message à afficher
     */
    void montrerMessage(String message) {
        this.message.setText(message);
        this.getChildren().setAll(this.message);
    }

    /**
     * Montrer le tableau de résultat avec le montant
     * @param montant montant que le tableau de résultat utilise
     */
    void montrerTableau(float montant) {
        this.tableauResultat.mettreAJour(montant);
        this.getChildren().setAll(tableauResultat);
    }
}