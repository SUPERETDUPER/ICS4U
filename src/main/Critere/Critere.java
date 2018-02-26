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

package main.Critere;

import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import main.Main;
import main.Utils;

/**
 * Class abstraite qui represente un critère pour le manège
 * <p>
 * Chaque critère contient un
 * <p>
 * nom
 * indicateur de status (rouge, jaune ou vert)
 * message de resultat (txtResultat)
 */
public abstract class Critere {
    //Different status possible pour un critère
    public enum Status {
        PASSE,
        INCOMPLET,
        REFUSE
    }

    private static final String MSG_INCOMPLET = "Remplir critère";
    private static final String MSG_PASSE = "Passe";
    private static final String MSG_REFUSE = "Refusé";

    //Nom du critère
    private final String nom;

    //Status du critère
    private Status status;

    //Message associé au critère (ex. Passe ou refusé)
    private final Text txtResultat = Utils.creeTextNormal(null);

    //Indicateur affichant le status du critère
    private final IndicateurStatus rectIndicateurDeStatus = new IndicateurStatus(15);

    /**
     * Creer un nouveau critère
     *
     * @param nom nom du critère
     */
    Critere(String nom) {
        this.nom = nom;

        status = Status.INCOMPLET;
        txtResultat.setText(MSG_INCOMPLET);
        rectIndicateurDeStatus.mettreAJour(Status.INCOMPLET);
    }

    /**
     * Retourne le nom du critère
     *
     * @return nom du critère
     */
    private String getNom() {
        return this.nom;
    }

    /**
     * Retourne un rectangle-indicateur
     *
     * @return le rectangle-indicateur
     */
    private Rectangle getRectIndicateurDeStatus() {
        return this.rectIndicateurDeStatus;
    }

    /**
     * Retourne le message du critère
     *
     * @return le message
     */
    private Text getTxtResultat() {
        return this.txtResultat;
    }

    /**
     * Indique le status du critère
     *
     * @return le status
     */
    public Status getStatus() {
        return this.status;
    }

    /**
     * Mets à jour le status utilisant le message par défaut
     * @param status le status
     */
    void setStatus(Status status) {
        switch (status) {
            case PASSE:
                setStatus(status, MSG_PASSE);
                break;
            case INCOMPLET:
                setStatus(status, MSG_INCOMPLET);
                break;
            default:
                setStatus(status, MSG_REFUSE);
                break;
        }
    }

    /**
     * Mets à jour le status du critère. Mets à jour l'indicateur, le status et le message
     *
     * @param status  si le critère passe ou ne passe pas
     * @param message message associé avec le critère
     */
    void setStatus(Status status, String message) {
        this.status = status;
        this.txtResultat.setText(message);
        this.rectIndicateurDeStatus.mettreAJour(status);

        Main.calculerResultat();
    }

    /**
     * Methode abstracte qui retourne un objet de l'interface qui est utilisé par l'utilisateur pour rentrer le critère
     *
     * @return objet à mettre sur l'interface graphique
     */
    abstract Node getObjetEntree();

    /**
     * Utilisé par "Main" pour construire un tableau contenant les options pour entrer les critères (nom + objet d'entree)
     *
     * @param criteres criteres à utiliser
     * @return tableau
     */
    public static GridPane creeTableauDeCritere(Critere[] criteres) {
        GridPane tableauDeCriteres = new GridPane();

        //Pour chaque rangée ajouter le nom du critère et son objet d'entrée (getObjetEntree())
        for (int i = 0; i < criteres.length; i++) {
            tableauDeCriteres.addRow(i,
                    Utils.creeTextNormal(criteres[i].getNom()),
                    criteres[i].getObjetEntree()
            );
        }

        //Formatter
        tableauDeCriteres.setAlignment(Pos.CENTER);
        tableauDeCriteres.setHgap(20);
        tableauDeCriteres.setVgap(10);
        return tableauDeCriteres;
    }

    /**
     * Utilisé par "Main" pour construire un tableau contenant les résultats des critères (rectIndicateurDeStatus + nom + message)
     *
     * @param criteres critères à utiliser
     * @return tableau de résultat
     */
    public static GridPane creeTableauDeResultat(Critere[] criteres) {
        GridPane tableauDeResultat = new GridPane();

        for (int i = 0; i < criteres.length; i++) {
            tableauDeResultat.addRow(i,
                    criteres[i].getRectIndicateurDeStatus(),
                    Utils.creeTextNormal(criteres[i].getNom()),
                    criteres[i].getTxtResultat()
            );
        }

        tableauDeResultat.setHgap(20);
        tableauDeResultat.setVgap(10);
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
