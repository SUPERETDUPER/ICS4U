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

package main.donnees;

import main.MethodeDeRecherche;
import main.Resultat;
import org.jetbrains.annotations.Nullable;

public class Rechercheur {
    private BaseDeDonnees baseDeDonnees;

    public Rechercheur(BaseDeDonnees baseDeDonnees) {
        this.baseDeDonnees = baseDeDonnees;
    }

    public Resultat rechercher(MethodeDeRecherche methode, int reference) {
        Livre livreResultat;

        switch (methode) {
            case LINEAIRE:
                livreResultat = rechercheLineaire(reference);
                break;
            case BINAIRE:
                livreResultat = rechercheBinaire(reference);
                break;
            default:
                throw new RuntimeException("Méthode de recherche inconnue");
        }

        return new Resultat(livreResultat, livreResultat != null, null);
    }

    @Nullable
    private Livre rechercheLineaire(int referenceATrouver) {
        for (int i = 0; i < baseDeDonnees.getSize(); i++) {
            Livre livre = baseDeDonnees.getLivre(i);
            if (livre.getReference() == referenceATrouver) return livre;
        }

        return null;
    }

    private Livre rechercheBinaire(int referenceATrouver) {
        return rechercheBinaire(0, baseDeDonnees.getSize(), referenceATrouver);
    }

    private Livre rechercheBinaire(int minimum, int maximum, int referenceATrouver) {
        int milieu = (minimum + maximum) / 2;

        int referenceMilieu = baseDeDonnees.getLivre(milieu).getReference();
        if (referenceMilieu == referenceATrouver) return baseDeDonnees.getLivre(milieu);
        else if (minimum == maximum) return null;
        else if (referenceATrouver > referenceMilieu) return rechercheBinaire(milieu + 1, maximum, referenceATrouver);
        else return rechercheBinaire(minimum, milieu - 1, referenceATrouver);
    }
}
