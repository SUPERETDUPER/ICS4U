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

import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.IOException;
import java.util.function.Consumer;

/**
 * Contient la liste de clients
 * Est capable de
 */
public class BaseDeDonnees {
    /**
     * La liste de clients
     */
    private final ObservableList<Client> clients = FXCollections.observableArrayList();

    BaseDeDonnees() {
    }

    BaseDeDonnees(DataInputStream dataInputStream) throws IOException{
        readObject(dataInputStream);
    }

    /**
     * Notifie un listener quand la liste change
     * @param listener le listener qu'il faut notifier
     */
    public void setListener(Consumer<BaseDeDonnees> listener) {
        clients.addListener((ListChangeListener<? super Client>) c -> listener.accept(this));
    }

    /**
     * Ajoute un client à la base de données
     */
    public void ajouter(Client client) {
        clients.add(client);
    }

    /**
     * Supprime un client de la liste
     * @param index la position du client à supprimer
     */
    public void supprimer(int index) {
        clients.remove(index);
    }

    public ObservableList<Client> getClients() {
        return clients;
    }

    //METHODES POUR ECRIRE L'OBJET A UN FICHIER

    void writeObject(DataOutputStream outputStream) throws IOException {
        for (Client client : clients) {
            client.writeObject(outputStream); //Ecrit chaque client
        }
    }

    private void readObject(DataInputStream inputStream) throws IOException {
        //Ajoute un client pour chaque client dans le fichier
        try {
            //noinspection InfiniteLoopStatement
            while (true) {
                clients.add(new Client(inputStream));
            }
        } catch (EOFException e) {
            System.out.println("Fini de charger le fichier");
        }
    }
}
