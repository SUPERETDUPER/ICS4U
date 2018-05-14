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

import main.modele.Livre;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

/**
 * Un algorithme de recherche binaire non-recursif.
 */
public class AlgorithmeBinaire implements Algorithme {

    @Nullable
    @Override
    public Livre rechercher(List<Livre> baseDeDonnees, int numeroDeReference) {
        int min = 0;
        int max = baseDeDonnees.size() - 1;

        while (min <= max) {
            int milieu = (min + max) / 2;
            Livre livreAuMilieu = baseDeDonnees.get(milieu);

            //Si le numéro de référence du livre du milieu est celui que l'on recherche nous le retournons
            if (livreAuMilieu.getReference() == numeroDeReference) return livreAuMilieu;

            //Sinon nous recommoncons avec un different max/min
            if (numeroDeReference > livreAuMilieu.getReference()) min = milieu + 1;
            else max = milieu - 1;
        }

        return null; //Si Le minimum n'est pas plus petit que le maximum alors l'élément n'existe pas
    }

    @Override
    public String getDescription() {
        return "L'algorithme binaire non-recursif a un \"Big Theta\" de O(log(n)).\n" +
                "L'algorithme binaire consiste à diviser la base de données en demi, jusqu'à ce que on ait trouvé notre objet.\n" +
                "L'algorithme nécessite une base de données déjà en ordre croissant.";
    }

    @NotNull
    @Override
    public String toString() {
        return "Algorithm binaire non-recursif";
    }
}
