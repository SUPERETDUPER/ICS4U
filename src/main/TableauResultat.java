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

import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.text.Text;

import java.text.NumberFormat;
import java.util.ArrayList;

class TableauResultat extends GridPane {

    private static final NumberFormat formatter = NumberFormat.getCurrencyInstance();

    private static final int ESPACE_TABLEAU_H = 30;
    private static final int ESPACE_TABLEAU_V = 15;

    private static final float[] typesDePieces = {
            2,
            1,
            0.5F,
            0.25F,
            0.1F,
            0.05F,
            0.01F
    };

    private final ArrayList<Text> reponses = new ArrayList<>(typesDePieces.length);

    TableauResultat() {
        super();

        for (int i = 0; i < typesDePieces.length; i++) {
            reponses.add(new Text());
            reponses.get(i).setFont(Constantes.FONT_NORMAL);

            Text description = new Text(formatter.format(typesDePieces[i]));
            description.setFont(Constantes.FONT_NORMAL);

            this.addRow(i,
                    description,
                    reponses.get(i)
            );
        }

        this.setHgap(ESPACE_TABLEAU_H);
        this.setVgap(ESPACE_TABLEAU_V);

        ColumnConstraints constraints0 = new ColumnConstraints();
        constraints0.setHalignment(HPos.RIGHT);
        constraints0.setHgrow(Priority.ALWAYS);
        constraints0.setPercentWidth(50);

        ColumnConstraints constraints1 = new ColumnConstraints();
        constraints1.setHgrow(Priority.ALWAYS);
        constraints0.setPercentWidth(50);

        this.getColumnConstraints().add(constraints0);
        this.getColumnConstraints().add(constraints1);

        this.setAlignment(Pos.CENTER);
    }

    void mettreAJour(float montant) {
        for (int i = 0 ; i < typesDePieces.length ; i++){

            int nombreDePieces = (int) (montant / typesDePieces[i]);

            reponses.get(i).setText(String.valueOf(nombreDePieces));

            montant -= nombreDePieces * typesDePieces[i];
        }
    }
}
