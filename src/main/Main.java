/*
 * MIT License
 *
 * Copyright (c) [2018] [Martin Staadecker]
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
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Separator;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import main.Critere.Critere;
import main.Critere.CritereMinMax;
import main.Critere.CritereOuiNon;
import main.Critere.IndicateurStatus;
import org.jetbrains.annotations.NotNull;

public class Main extends Application {
    private static final String TITRE = "Vérification pour manège russe";

    private static final int PADDING_FENETRE = 80;

    private static final int ESPACE_VBOX = 50;
    private static final int ESPACE_HBOX_ZONE_REPONSE = 10;

    private static final int DIMENSION_INDICATEUR_STATUS_FINAL = 30;

    private static final String MSG_PASSE = "Accès autorisé";
    private static final String MSG_INCOMPLET = "Remplir tous les critères";
    private static final String MSG_REFUSE = "Accès refusé";

    //Liste de tous les critères
    private static final Critere[] criteres = {
            new CritereOuiNon("Mal de dos?", false),
            new CritereOuiNon("Malaise cardiaque", false),
            new CritereMinMax("Hauteur", 122, 188, "Rentrez votre hauteur ici")
    };

    private static final Text reponseFinale = Utils.creeTextBoldGrand(null);
    private static final IndicateurStatus indicateurFinale = new IndicateurStatus(DIMENSION_INDICATEUR_STATUS_FINAL);


    public static void main(String[] args) {
        launch(args);
    }

    /**
     * Appelé au début du programme
     */
    @Override
    public void start(@NotNull Stage primaryStage) {
        primaryStage.setTitle(TITRE);

        //Création de la zone de réponse (indicateurStatus + réponse)
        HBox zoneReponse = new HBox(indicateurFinale, reponseFinale);
        zoneReponse.setAlignment(Pos.CENTER);
        zoneReponse.setSpacing(ESPACE_HBOX_ZONE_REPONSE);

        //Création du layout (vertical) principal
        VBox layout = new VBox(
                Utils.creeTextTitre(TITRE), //Titre
                Critere.creeTableauDeCritere(criteres), //Tableau de critères
                new Separator(),
                Critere.creeTableauDeResultat(criteres),  //Tableau de resultat
                zoneReponse  //Zone de réponse
        );

        //Formatter
        layout.setSpacing(ESPACE_VBOX);
        layout.setPadding(new Insets(PADDING_FENETRE));
        layout.setAlignment(Pos.CENTER);

        //Commencer l'interface
        Scene scene = new Scene(layout);
        primaryStage.setScene(scene);
        primaryStage.show();

        calculerResultat(); //Calculer les résultats pour la première fois. Après sera calculer quand il y a un changement de l'utilisateur
    }

    /**
     * Repasse tout les critères pour savoir leur status. Calcule puis affiche le resultat finale
     */
    public static void calculerResultat() {
        Critere.Status statusFinale = Critere.Status.PASSE;

        for (Critere critere : criteres) {
            //Si le status est bon passer immédiatement au prochain
            if (critere.getStatus() == Critere.Status.PASSE){
                continue;
            }

            statusFinale = critere.getStatus();

            //Si il manque des résultats arrêter immédiatement et demander de remplir tous les critères
            if (statusFinale == Critere.Status.INCOMPLET){
                break;
            }
        }

        indicateurFinale.mettreAJour(statusFinale); //Mettre à jour l'indicateur

        //Mettre à jour le message
        switch (statusFinale) {
            case PASSE:
                reponseFinale.setText(MSG_PASSE);
                break;
            case INCOMPLET:
                reponseFinale.setText(MSG_INCOMPLET);
                break;
            case REFUSE:
                reponseFinale.setText(MSG_REFUSE);
                break;
        }
    }
}