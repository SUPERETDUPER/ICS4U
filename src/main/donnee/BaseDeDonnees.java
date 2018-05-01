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
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.function.Consumer;

/**
 * Donne accès à une liste de clients.
 * Permet d'ajouter ou de supprimer des clients de la liste
 */
public class BaseDeDonnees {
    /**
     * La liste de clients
     */
    private final ObservableList<Client> clients;

    /**
     * La fonction a executer quand les données changent
     */
    private final Consumer<List<Client>> writeFunction;

    /**
     * @param clientsInitials la liste de clients initiales
     * @param writeFunction   la fonction à appeler quand les clients changent
     */
    public BaseDeDonnees(@NotNull List<Client> clientsInitials, Consumer<List<Client>> writeFunction) {
        this.writeFunction = writeFunction;
        this.clients = FXCollections.observableArrayList(clientsInitials);
    }

    public void ajouter(Client client) {
        clients.add(client);
        writeFunction.accept(clients);
    }

    public void supprimer(int index) {
        clients.remove(index);
        writeFunction.accept(clients);
    }

    public ObservableList<Client> getClients() {
        return clients;
    }
}
