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
import org.jetbrains.annotations.NotNull;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

/**
 * Représente un client
 */
public class Client implements Serializable {
    private static final int LIMITE_POUR_BONUS = 5000;
    private static final int BONUS = 1000;

    private final SimpleStringProperty prenom = new SimpleStringProperty();
    private final SimpleStringProperty nom = new SimpleStringProperty();
    private final SimpleIntegerProperty semaineUn = new SimpleIntegerProperty();
    private final SimpleIntegerProperty semaineDeux = new SimpleIntegerProperty();
    private final SimpleIntegerProperty semaineTrois = new SimpleIntegerProperty();
    private final SimpleIntegerProperty semaineQuatre = new SimpleIntegerProperty();
    private final SimpleIntegerProperty somme = new SimpleIntegerProperty();
    private final SimpleIntegerProperty bonus = new SimpleIntegerProperty();
    private final SimpleIntegerProperty total = new SimpleIntegerProperty();

    public Client(DataInputStream dataInputStream) throws IOException {
        readObject(dataInputStream);
    }

    /**
     * Créer un client avec toutes les charactéristiques
     */
    public Client(String prenom, String nom, int semaineUn, int semaineDeux, int semaineTrois, int semaineQuatre) {
        this.prenom.set(prenom);
        this.nom.set(nom);
        this.semaineUn.set(semaineUn);
        this.semaineDeux.set(semaineDeux);
        this.semaineTrois.set(semaineTrois);
        this.semaineQuatre.set(semaineQuatre);

        //La somme est automatiquement attaché aux points pour chaque semaine
        somme.bind(this.semaineUn.add(this.semaineDeux).add(this.semaineTrois).add(this.semaineQuatre));
        //Le bonus est automatiquement attaché à la somme
        bonus.bind(Bindings.createIntegerBinding(() -> somme.get() < LIMITE_POUR_BONUS ? 0 : BONUS, somme));
        //Le total est l'addition de la somme et du bonus
        total.bind(this.somme.add(this.bonus));
    }

    public SimpleStringProperty prenomProperty() {
        return prenom;
    }

    public String getPrenom() {
        return prenom.get();
    }

    public SimpleStringProperty nomProperty() {
        return nom;
    }

    public String getNom() {
        return nom.get();
    }

    public SimpleIntegerProperty semaineUnProperty() {
        return semaineUn;
    }

    public SimpleIntegerProperty semaineDeuxProperty() {
        return semaineDeux;
    }

    public SimpleIntegerProperty semaineTroisProperty() {
        return semaineTrois;
    }

    public SimpleIntegerProperty semaineQuatreProperty() {
        return semaineQuatre;
    }

    public SimpleIntegerProperty sommeProperty() {
        return somme;
    }

    public SimpleIntegerProperty bonusProperty() {
        return bonus;
    }

    public SimpleIntegerProperty totalProperty() {
        return total;
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

    public void writeObject(@NotNull DataOutputStream outputStream) throws IOException {
        outputStream.writeUTF(prenom.get());
        outputStream.writeUTF(nom.get());
        outputStream.writeInt(semaineUn.get());
        outputStream.writeInt(semaineDeux.get());
        outputStream.writeInt(semaineTrois.get());
        outputStream.writeInt(semaineQuatre.get());
    }

    public void readObject(@NotNull DataInputStream inputStream) throws IOException {
        prenom.set(inputStream.readUTF());
        nom.set(inputStream.readUTF());
        semaineUn.set(inputStream.readInt());
        semaineDeux.set(inputStream.readInt());
        semaineTrois.set(inputStream.readInt());
        semaineQuatre.set(inputStream.readInt());
    }
}