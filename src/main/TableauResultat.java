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
import javafx.util.Pair;
import org.jetbrains.annotations.Contract;

import java.text.NumberFormat;
import java.util.Arrays;
import java.util.List;

class TableauResultat extends GridPane {
    private static final int ESPACE_TABLEAU_H = 30;
    private static final int ESPACE_TABLEAU_V = 15;

    private static final List<Pair<Float, Text>> typesDePieces = Arrays.asList(
            new Pair<>(2.00F, Utils.creeTextNormal(null)),
            new Pair<>(1.00F, Utils.creeTextNormal(null)),
            new Pair<>(0.50F, Utils.creeTextNormal(null)),
            new Pair<>(0.25F, Utils.creeTextNormal(null)),
            new Pair<>(0.10F, Utils.creeTextNormal(null)),
            new Pair<>(0.05F, Utils.creeTextNormal(null)),
            new Pair<>(0.01F, Utils.creeTextNormal(null))
    );

    private static final String MSG_QUANTITE = "%sx"; //Avec String.format sera ex. 15x

    private static final String ENTETE_COL1 = "Quantité";
    private static final String ENTETE_COL2 = "Types des pièces";

    TableauResultat() {
        super();

        NumberFormat formatter = NumberFormat.getCurrencyInstance(); //Utilisé pour afficher les types de pièces

        //Ajouter l'en-tête
        this.addRow(0,
                Utils.creeTextBold(ENTETE_COL1),
                Utils.creeTextBold(ENTETE_COL2)
        );

        //Ajout d'une rangée pour chaque type de pièce
        for (int i = 0; i < typesDePieces.size(); i++) {
            this.addRow(i + 1, // +1 à cause de l'entête
                    typesDePieces.get(i).getValue(),
                    Utils.creeTextNormal(formatter.format(typesDePieces.get(i).getKey()))
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
        montant = arrondir(montant);

        for (Pair<Float, Text> pair : typesDePieces) {
            int nombreDePieces = (int) (montant / pair.getKey());

            pair.getValue().setText(String.format(MSG_QUANTITE, nombreDePieces));

            montant = arrondir(montant - nombreDePieces * pair.getKey());
        }
    }

    @Contract(pure = true)
    private static float arrondir(float montant) {
        return Math.round(montant * 100) / 100.0F;
    }
}
