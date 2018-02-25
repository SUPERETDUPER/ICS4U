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

import javafx.scene.layout.GridPane;

import java.util.Arrays;
import java.util.List;

class Verificateur {
    private static final GridPane tableauDeCriteres;

    private static final List<Critere> criteres = Arrays.asList(
            new CritereOuiNon("Mal de dos?", false),
            new CritereOuiNon("Malaise cardiaque", false),
            new CritereMinMax("Hauteur", 122, 188, "Rentrez votre hauteur ici")
    );

    static {
        tableauDeCriteres = new GridPane();

        for (int i = 0; i < criteres.size(); i++) {
            tableauDeCriteres.addRow(i,
                    Utils.creeTextNormal(criteres.get(i).getNom()),
                    criteres.get(i).getNode()
            );
        }

        tableauDeCriteres.setHgap(20);
        tableauDeCriteres.setVgap(10);
    }

    static GridPane getTableauDeCriteres() {
        return tableauDeCriteres;
    }

    static String getResultatDesCriteres() {
        boolean isPass = true;

        for (Critere critere : criteres) {
            try {
                isPass = isPass && critere.isPass();
            } catch (Exception e) {
                return e.getMessage();
            }
        }

        return isPass ? "Peut passer" : "Peut pas passer";
    }
}