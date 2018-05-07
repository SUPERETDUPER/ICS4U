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

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import main.donnees.BaseDeDonnees;
import main.donnees.Livre;
import main.donnees.Rechercheur;

import java.util.Arrays;
import java.util.List;

public class Main extends Application {
    private final static List<Livre> livres = Arrays.asList(
            new Livre(2, "Pinocchio"),
            new Livre(5, "Maria Chapdelaine"),
            new Livre(7, "L'Homme Invisible"),
            new Livre(10, "Contes fantastiques"),
            new Livre(13, "L'Assassin habite au 21"),
            new Livre(17, "L'agent secret"),
            new Livre(22, "Robinson Crusoe"),
            new Livre(25,"Les aventures d'Alice au pays des merveilles"),
            new Livre(29,"Voyage au centre de la terre"),
            new Livre(30,"Famille suisse Robinson"),
            new Livre(31,"Trois mousquetaires"),
            new Livre(35,"Aurélia"),
            new Livre(36,"Les deux orphelines"),
            new Livre(40,"Le joueur")
    );

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/main.fxml"));
        fxmlLoader.setController(new MainController(new Rechercheur(new BaseDeDonnees(livres))));

        primaryStage.setScene(new Scene(fxmlLoader.load()));
        primaryStage.show();
    }
}
