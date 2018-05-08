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
import org.jetbrains.annotations.Nullable;

import java.util.function.Function;

class AlgorithmeBinaireRecursif implements Function<Integer, Livre> {
    private final BaseDeDonnees<Livre> baseDeDonnees;

    AlgorithmeBinaireRecursif(BaseDeDonnees<Livre> baseDeDonnees) {
        this.baseDeDonnees = baseDeDonnees;
    }

    @Override
    public Livre apply(Integer numeroDeReference) {
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

        //Si le minimum n'est plus plus petit que le max le livre n'existe pas
        return null;
    }

    @Override
    public String toString() {
        return "Algorithm binaire recursif";
    }
}
