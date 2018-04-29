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

package main;

import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

public class MainController {
    @FXML
    private TableView<Client> table;

    @FXML
    private TableColumn<Client, String> colPrenom;

    @FXML
    private TableColumn<Client, String> colNom;

    @FXML
    private TableColumn<Client, Integer> colPSem1;
    @FXML
    private TableColumn<Client, Integer> colPSem2;
    @FXML
    private TableColumn<Client, Integer> colPSem3;
    @FXML
    private TableColumn<Client, Integer> colPSem4;
    @FXML
    private TableColumn<Client, Integer> colPSomme;
    @FXML
    private TableColumn<Client, Integer> colPBonus;
    @FXML
    private TableColumn<Client, Integer> colPTotal;

    private final BaseDeDonnees donnees;

    MainController(BaseDeDonnees donnees) {
        this.donnees = donnees;
    }

    @FXML
    private void initialize() {
        table.setItems(donnees.getClients());
        colPrenom.setCellValueFactory(param -> new ReadOnlyStringWrapper(param.getValue().info.getPrenom()));
        colNom.setCellValueFactory(param -> new ReadOnlyStringWrapper(param.getValue().info.getNom()));
        colPSem1.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getValue().info.getPoints().get(0)));
        colPSem2.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getValue().info.getPoints().get(1)));
        colPSem3.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getValue().info.getPoints().get(2)));
        colPSem4.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getValue().info.getPoints().get(3)));
        colPSomme.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getValue().info.calculateSomme()));
        colPBonus.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getValue().info.calculateBonus()));
        colPTotal.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getValue().info.calculateTotal()));
    }
}
