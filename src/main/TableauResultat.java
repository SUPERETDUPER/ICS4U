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
import java.util.ArrayList;
import java.util.List;

/**
 * Un tableau qui affiche la monnaie pour un certain montant
 */
class TableauResultat extends GridPane {
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

    private static final String MSG_QUANTITE = "%sx"; //Utilisé pour formatter les quantité ex. 15x ou 25x

    private static final String ENTETE_COL1 = "Quantité";
    private static final String ENTETE_COL2 = "Types des pièces";

    //Chaque type de pieces (clé) est associé avec un object "Text" (valeur)
    private final List<Pair<Float, Text>> quantites = new ArrayList<>(typesDePieces.length);

    TableauResultat() {
        super();

        //Initialize la liste quantites avec des pairs (typesDePieces, Text)
        for (float type : typesDePieces) {
            quantites.add(new Pair<>(
                    type,
                    Utils.creeTextNormal(null)
            ));
        }

        NumberFormat formatter = NumberFormat.getCurrencyInstance(); //Utilisé pour afficher les types de pièces

        //Ajouter l'en-tête
        this.addRow(0,
                Utils.creeTextBold(ENTETE_COL1),
                Utils.creeTextBold(ENTETE_COL2)
        );

        //Ajout d'une rangée pour chaque type de pièce
        for (int i = 0; i < quantites.size(); i++) {
            this.addRow(i + 1, // +1 à cause de l'entête
                    quantites.get(i).getValue(),
                    Utils.creeTextNormal(formatter.format(quantites.get(i).getKey()))
            );
        }

        //Formatter
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


    /**
     * Appelé quand un nouveau montant est entrée pour mettre à jour le tableau
     *
     * @param montant nouveau montant
     */
    void mettreAJour(float montant) {
        for (Pair<Float, Text> pair : quantites) {
            montant = arrondir(montant); //Arrondir pour résoudre des problèmes de manque de précision

            int nombreDePieces = (int) (montant / pair.getKey());  //Calculer le nombre de pieces de ce type

            pair.getValue().setText(String.format(MSG_QUANTITE, nombreDePieces)); //Mettre à jour la quantité pour ce type de pièces

            montant -= nombreDePieces * pair.getKey(); //Soustraire l'argent utilisé au montant actuel
        }
    }

    /**
     * Arrondi au centième près
     *
     * @param montant montant à arrondir
     * @return montant arrondit
     */
    @Contract(pure = true)
    private static float arrondir(float montant) {
        return Math.round(montant * 100) / 100.0F;
    }
}
