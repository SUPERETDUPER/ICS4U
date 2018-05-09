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

/**
 * Algorithme de recherche linéaire. Vérifie tous les livres dans la liste pour le numéro de référence
 */
public class AlgorithmeLineaire implements Algorithme {
    private final BaseDeDonnees<Livre> baseDeDonnees;

    public AlgorithmeLineaire(BaseDeDonnees<Livre> baseDeDonnees) {
        this.baseDeDonnees = baseDeDonnees;
    }

    @Nullable
    @Override
    public Livre rechercher(int numeroDeReference) {
        for (Livre livre : baseDeDonnees) {
            if (livre.getReference() == numeroDeReference) return livre;
        }

        return null;
    }

    @Override
    public String getDescription() {
        return this.toString();
    }

    @Override
    public String toString() {
        return "Algorithm linéaire";
    }
}
