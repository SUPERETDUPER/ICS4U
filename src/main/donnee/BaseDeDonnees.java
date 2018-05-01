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
import javafx.collections.ObservableList;

import java.io.*;
import java.util.function.Consumer;

/**
 * Donne accès à une liste de clients.
 * Permet d'ajouter ou de supprimer des clients de la liste
 */
public class BaseDeDonnees {
    /**
     * La liste de clients
     */
    private final ObservableList<Client> clients = FXCollections.observableArrayList();

    /**
     * La fonction a executer quand les données changent
     */
    private Consumer<BaseDeDonnees> writeFunction;

    public void setWriteFunction(Consumer<BaseDeDonnees> writeFunction) {
        this.writeFunction = writeFunction;
    }

    public void ajouter(Client client) {
        clients.add(client);
        writeFunction.accept(this);
    }

    public void supprimer(int index) {
        clients.remove(index);
        writeFunction.accept(this);
    }

    public ObservableList<Client> getClients() {
        return clients;
    }

    void writeObject(DataOutputStream outputStream) throws IOException {
        outputStream.writeInt(clients.size());
        for (Client client : clients) {
            client.writeObject(outputStream);
        }
    }

    void readObject(DataInputStream inputStream) throws IOException {
        int size = inputStream.readInt();

        for (int i = 0; i < size; i++) {
            Client client = new Client();
            client.readObject(inputStream);
            clients.add(client);
        }
    }
}
