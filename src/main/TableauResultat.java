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
            2.00F,
            1.00F,
            0.50F,
            0.25F,
            0.10F,
            0.05F,
            0.01F
    };

    private static final String MSG_QUANTITE = "%sx";

    private final ArrayList<Text> quantites = new ArrayList<>(typesDePieces.length);

    TableauResultat() {
        super();

        //Création de l'entête
        Text headerCol1 = new Text("Quantité");
        Text headerCol2 = new Text("Types des pièces");
        headerCol1.setFont(Main.FONT_BOLD);
        headerCol2.setFont(Main.FONT_BOLD);
        this.addRow(0, headerCol1, headerCol2);

        //Ajout des rangées
        for (int i = 0; i < typesDePieces.length; i++) {
            quantites.add(new Text());
            quantites.get(i).setFont(Main.FONT_NORMAL);

            Text txtTypeDePiece = new Text(formatter.format(typesDePieces[i]));
            txtTypeDePiece.setFont(Main.FONT_NORMAL);

            this.addRow(i + 1, // +1 à cause de l'entête
                    quantites.get(i),
                    txtTypeDePiece
            );
        }

        this.setHgap(ESPACE_TABLEAU_H);
        this.setVgap(ESPACE_TABLEAU_V);

        ColumnConstraints constraints0 = new ColumnConstraints();
        constraints0.setHalignment(HPos.RIGHT);
        constraints0.setPercentWidth(50);

        ColumnConstraints constraints1 = new ColumnConstraints();
        constraints1.setHgrow(Priority.ALWAYS);

        this.getColumnConstraints().addAll(
                constraints0,
                constraints1
        );

        this.setAlignment(Pos.TOP_CENTER);
    }

    void mettreAJour(float montant) {
        for (int i = 0; i < typesDePieces.length; i++) {
            int nombreDePieces = (int) (montant / typesDePieces[i]);

            quantites.get(i).setText(String.format(MSG_QUANTITE, nombreDePieces));

            montant -= nombreDePieces * typesDePieces[i];
        }
    }
}
