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
import main.donnee.BaseDeDonnees;
import main.donnee.FileAccess;
import main.graphique.main.MainController;

public class Main extends Application {
    private static final String TITRE = "Gestionnaire de clients";

    private static BaseDeDonnees baseDeDonnees;

    public static void main(String[] args) {
        //Créer la class qui permet d'accéder les fichiers de données
        FileAccess fileAccess = new FileAccess();

        //Créer la base de données à partir des fichiers
        baseDeDonnees = fileAccess.read();

        //Définir la function qu'il faut appeler quand les données changent
        baseDeDonnees.setListener(fileAccess::write);

        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle(TITRE); //Mettre le titre

        //Créer l'interface
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/main.fxml"));
        //Créer un controlleur d'interface avec la base de donnees
        fxmlLoader.setController(new MainController(baseDeDonnees));

        //Afficher l'interface
        primaryStage.setScene(new Scene(fxmlLoader.load()));
        primaryStage.setMaximized(true);
        primaryStage.show();
    }
}
