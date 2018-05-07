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

public class AlgorithmeBinaireRecursif implements Function<Integer, Livre> {
    private final BaseDeDonnees baseDeDonnees;

    AlgorithmeBinaireRecursif(BaseDeDonnees baseDeDonnees) {
        this.baseDeDonnees = baseDeDonnees;
    }

    @Nullable
    private Livre rechercher(int minimum, int maximum, int numeroDeReference) {
        if (minimum >= maximum) return null;

        int milieu = (int) Math.floor((minimum + maximum) / 2F);

        Livre livre = baseDeDonnees.getLivre(milieu);
//        System.out.println(minimum + " " + milieu + " " + maximum + " " + livre.getReference());
        if (livre.getReference() == numeroDeReference) return livre;
        else if (numeroDeReference > livre.getReference())
            return rechercher(milieu + 1, maximum, numeroDeReference);
        else return rechercher(minimum, milieu - 1, numeroDeReference);
    }

    @Override
    public Livre apply(Integer numeroDeReference) {
        return rechercher(0, baseDeDonnees.getSize() - 1, numeroDeReference);
    }

    @Override
    public String toString() {
        return "Algorithm binaire recursif";
    }
}
