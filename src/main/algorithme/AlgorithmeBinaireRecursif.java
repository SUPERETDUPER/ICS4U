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

package main.algorithme;

import main.donnees.BaseDeDonnees;
import main.donnees.Livre;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Algorithme de recherche binaire recursif
 */
public class AlgorithmeBinaireRecursif implements Algorithme {
    @NotNull
    private final BaseDeDonnees<Livre> baseDeDonnees;

    public AlgorithmeBinaireRecursif(@NotNull BaseDeDonnees<Livre> baseDeDonnees) {
        this.baseDeDonnees = baseDeDonnees;
    }

    @Nullable
    @Override
    public Livre rechercher(int numeroDeReference) {
        return rechercher(0, baseDeDonnees.getSize() - 1, numeroDeReference);
    }

    @Nullable
    private Livre rechercher(int min, int max, int numeroDeReference) {
        if (min <= max) {
            int milieu = (min + max) / 2;

            Livre livre = baseDeDonnees.getLivre(milieu);

            //Si le numéro de référence du livre du milieu est celui que l'on recherche nous le retournons
            if (livre.getReference() == numeroDeReference) return livre;

            //Sinon nous retournons le resultat de l'algoritme avec un différent min/max
            if (numeroDeReference > livre.getReference()) return rechercher(milieu + 1, max, numeroDeReference);
            return rechercher(min, milieu - 1, numeroDeReference);
        }

        //Si le minimum n'est pas plus petit que le max le livre n'existe pas
        return null;
    }

    @Override
    public String getDescription() {
        return "O(log(n))\n" +
                "L'algorithme binaire consiste à diviser la base de données en demi, jusqu'à ce que l'on ait trouver notre objet.\n" +
                "L'algorithme nécessite une base de données déjà en ordre croissant.";
    }

    @NotNull
    @Override
    public String toString() {
        return "Algorithme binaire recursif";
    }
}
