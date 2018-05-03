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

package main.graphique.main;

import javafx.beans.binding.Bindings;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import main.donnee.BaseDeDonnees;
import main.donnee.Client;
import main.graphique.nouveauclient.AjouterClientDialog;

import java.util.Optional;

/**
 * Contrôle l'interface graphique
 */
public class MainController {
    //DU FICHIER FXML

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
    @FXML
    private Button buttonSupprimer;

    /**
     * La base de données de clients
     */
    private final BaseDeDonnees donnees;

    public MainController(BaseDeDonnees donnees) {
        this.donnees = donnees;
    }

    @FXML
    private void initialize() {
        table.setItems(donnees.getClients()); //Attacher la table aux clients

        //Attacher les colonnes aux propriétés
        colPrenom.setCellValueFactory(new PropertyValueFactory<>("prenom"));
        colNom.setCellValueFactory(new PropertyValueFactory<>("nom"));
        colPSem1.setCellValueFactory(new PropertyValueFactory<>("semaineUn"));
        colPSem2.setCellValueFactory(new PropertyValueFactory<>("semaineDeux"));
        colPSem3.setCellValueFactory(new PropertyValueFactory<>("semaineTrois"));
        colPSem4.setCellValueFactory(new PropertyValueFactory<>("semaineQuatre"));
        colPSomme.setCellValueFactory(new PropertyValueFactory<>("somme"));
        colPBonus.setCellValueFactory(new PropertyValueFactory<>("bonus"));
        colPTotal.setCellValueFactory(new PropertyValueFactory<>("total"));

        //Faire que le button supprimer soit gris si aucune rangée n'est sélectionnée
        buttonSupprimer.disableProperty().bind(Bindings.equal(table.getSelectionModel().selectedIndexProperty(), -1));
    }

    /**
     * Ajoute un client aux données
     */
    @FXML
    private void ajouter() {
        //Montrer la fenêtre pour ajouter un client
        Optional<Client> clientInfo = new AjouterClientDialog().showAndWait();

        //Si le resultat n'est pas null ajouter le client aux données
        //Example de "Functional Programming". donnees::ajouter est une référence à une méthode
        clientInfo.ifPresent(donnees::ajouter);
    }

    /**
     * Supprime le client sélectionné
     */
    @FXML
    private void supprimer() {
        //Montrer une alerte pour confirmer que l'utilisateur veut vraiment supprimer
        Optional<ButtonType> resultat = new Alert(Alert.AlertType.CONFIRMATION).showAndWait();

        //Si le resultat est OK et pas null supprimer le client
        if (resultat.isPresent() && resultat.get() == ButtonType.OK) {
            donnees.supprimer(table.getSelectionModel().getSelectedIndex()); //Supprimer le client en fonction de sa position
        }
    }
}
