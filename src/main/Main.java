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
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Separator;
import javafx.scene.layout.*;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import main.Critere.Critere;
import main.Critere.CritereMinMax;
import main.Critere.CritereOuiNon;
import main.Critere.IndicateurStatus;
import org.jetbrains.annotations.NotNull;

public class Main extends Application {
    private static final String TITRE_APPLICATION = "Vérification pour manège russe";

    private static final int PADDING_FENETRE = 80;

    private static final int ESPACE_VBOX_PRINCIPALE = 50;
    private static final int ESPACE_HBOX_ZONE_REPONSE = 10;

    private static final int HGAP_TABLEAU = 20;
    private static final int VGAP_TABLEAU = 10;

    private static final int DIMENSION_INDICATEUR_STATUS_FINAL = 30;

    private static final String MSG_FINALE_PASSE = "Accès autorisé";
    private static final String MSG_FINALE_INCOMPLET = "Remplir tous les critères";
    private static final String MSG_FINALE_REFUSE = "Accès refusé";

    //Liste de tous les critères
    private static final Critere[] listeDeCriteres = {
            new CritereOuiNon("Mal de dos?", false),
            new CritereOuiNon("Malaise cardiaque", false),
            new CritereMinMax("Hauteur", 122, 188, "Rentrez votre hauteur ici")
    };



    private static final Text txtReponseFinale = Utils.creeTextBoldGrand(null);
    private static final IndicateurStatus indicateurStatusFinale = new IndicateurStatus(DIMENSION_INDICATEUR_STATUS_FINAL);

    public static void main(String[] args) {
        launch(args);
    }

    /**
     * Appelé au début du programme
     */
    @Override
    public void start(@NotNull Stage primaryStage) {
        primaryStage.setTitle(TITRE_APPLICATION);

        //Création de la zone de réponse (indicateurStatus + réponse)
        HBox zoneReponse = new HBox(indicateurStatusFinale, txtReponseFinale);
        zoneReponse.setAlignment(Pos.CENTER);
        zoneReponse.setSpacing(ESPACE_HBOX_ZONE_REPONSE);

        //Création du layout (vertical) principal
        VBox layoutPrincipal = new VBox(
                Utils.creeTextTitre(TITRE_APPLICATION), //Titre
                creeTableauDeCritere(), //Tableau de critères
                new Separator(),
                creeTableauDeResultat(),  //Tableau de resultat
                zoneReponse  //Zone de réponse
        );

        //Formatter
        layoutPrincipal.setSpacing(ESPACE_VBOX_PRINCIPALE);
        layoutPrincipal.setPadding(new Insets(PADDING_FENETRE));
        layoutPrincipal.setAlignment(Pos.CENTER);

        //Commencer l'interface
        Scene scene = new Scene(layoutPrincipal);
        primaryStage.setScene(scene);
        primaryStage.show();

        calculerResultat(); //Calculer les résultats pour la première fois. Après sera calculer quand il y a un changement de l'utilisateur
    }

    /**
     * Repasse tout les critères pour savoir leur status. Calcule puis affiche le resultat finale
     */
    public static void calculerResultat() {
        Critere.Status statusFinale = Critere.Status.PASSE;

        for (Critere critereAVerifier : listeDeCriteres) {
            //Si le status est bon passer immédiatement au prochain
            if (critereAVerifier.getStatusDuCritere() == Critere.Status.PASSE){
                continue;
            }

            statusFinale = critereAVerifier.getStatusDuCritere();

            //Si il manque des résultats arrêter immédiatement et demander de remplir tous les critères
            if (statusFinale == Critere.Status.INCOMPLET){
                break;
            }
        }

        indicateurStatusFinale.mettreAJour(statusFinale); //Mettre à jour l'indicateur

        //Mettre à jour le message
        switch (statusFinale) {
            case PASSE:
                txtReponseFinale.setText(MSG_FINALE_PASSE);
                break;
            case INCOMPLET:
                txtReponseFinale.setText(MSG_FINALE_INCOMPLET);
                break;
            case REFUSE:
                txtReponseFinale.setText(MSG_FINALE_REFUSE);
                break;
        }
    }

    /**
     * Utilisé pour construire un tableau contenant les options pour entrer les critères (nomDuCritere + objet d'entree)
     *
     * @return tableau
     */
    @NotNull
    private static GridPane creeTableauDeCritere() {
        GridPane tableauDeCriteres = new GridPane();

        //Pour chaque rangée ajouter le nomDuCritere du critère et son objet d'entrée (getObjetEntree())
        for (int i = 0; i < listeDeCriteres.length; i++) {
            tableauDeCriteres.addRow(i,
                    Utils.creeTextNormal(listeDeCriteres[i].getNomDuCritere()),
                    listeDeCriteres[i].getObjetEntree()
            );
        }

        //Formatter
        tableauDeCriteres.setAlignment(Pos.CENTER);
        tableauDeCriteres.setHgap(HGAP_TABLEAU);
        tableauDeCriteres.setVgap(VGAP_TABLEAU);
        return tableauDeCriteres;
    }

    /**
     * Utilisé pour construire un tableau contenant les résultats des critères (rectIndicateurDeStatus + nomDuCritere + message)
     *
     * @return tableau de résultat
     */
    @NotNull
    private static GridPane creeTableauDeResultat() {
        GridPane tableauDeResultat = new GridPane();

        for (int i = 0; i < listeDeCriteres.length; i++) {
            tableauDeResultat.addRow(i,
                    listeDeCriteres[i].getRectIndicateurDeStatus(),
                    Utils.creeTextNormal(listeDeCriteres[i].getNomDuCritere()),
                    listeDeCriteres[i].getTxtMessageDeResultat()
            );
        }

        tableauDeResultat.setHgap(HGAP_TABLEAU);
        tableauDeResultat.setVgap(VGAP_TABLEAU);
        tableauDeResultat.setAlignment(Pos.CENTER);

        ColumnConstraints columnConstraints1 = new ColumnConstraints();
        columnConstraints1.setHgrow(Priority.SOMETIMES);
        columnConstraints1.setHalignment(HPos.RIGHT);

        ColumnConstraints columnConstraints3 = new ColumnConstraints();
        columnConstraints3.setPercentWidth(50);
        columnConstraints3.setHgrow(Priority.SOMETIMES);

        tableauDeResultat.getColumnConstraints().setAll(
                columnConstraints1, new ColumnConstraints(), columnConstraints3
        );

        return tableauDeResultat;
    }
}