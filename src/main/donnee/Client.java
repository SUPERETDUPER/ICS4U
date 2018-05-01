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

package main.donnee;

import javafx.beans.binding.Bindings;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

/**
 * Représente un client
 */
public class Client {
    private final SimpleStringProperty prenom;
    private final SimpleStringProperty nom;
    private final SimpleIntegerProperty semaineUn;
    private final SimpleIntegerProperty semaineDeux;
    private final SimpleIntegerProperty semaineTrois;
    private final SimpleIntegerProperty semaineQuatre;
    private final SimpleIntegerProperty somme = new SimpleIntegerProperty();
    private final SimpleIntegerProperty bonus = new SimpleIntegerProperty();
    private final SimpleIntegerProperty total = new SimpleIntegerProperty();

    public Client(String prenom, String nom, int semaineUn, int semaineDeux, int semaineTrois, int semaineQuatre) {
        this.prenom = new SimpleStringProperty(prenom);
        this.nom = new SimpleStringProperty(nom);
        this.semaineUn = new SimpleIntegerProperty(semaineUn);
        this.semaineDeux = new SimpleIntegerProperty(semaineDeux);
        this.semaineTrois = new SimpleIntegerProperty(semaineTrois);
        this.semaineQuatre = new SimpleIntegerProperty(semaineQuatre);
        somme.bind(this.semaineUn.add(this.semaineDeux).add(this.semaineTrois).add(this.semaineQuatre));
        bonus.bind(Bindings.createIntegerBinding(() -> somme.get() > 5000 ? 1000 : 0, somme));
        total.bind(this.somme.add(this.bonus));
    }

    public int getSemaineUn() {
        return semaineUn.get();
    }

    public int getSemaineDeux() {
        return semaineDeux.get();
    }

    public int getSemaineTrois() {
        return semaineTrois.get();
    }

    public int getSemaineQuatre() {
        return semaineQuatre.get();
    }

    public String getNom() {
        return nom.get();
    }

    public String getPrenom() {
        return prenom.get();
    }

    public int getSomme() {
        return somme.get();
    }

    public int getBonus() {
        return bonus.get();
    }

    public int getTotal() {
        return total.get();
    }

    @Override
    public String toString() {

        return prenom +
                " " +
                nom +
                ": " +
                semaineUn +
                " " +
                semaineDeux +
                " " +
                semaineTrois +
                " " +
                semaineQuatre;
    }
}