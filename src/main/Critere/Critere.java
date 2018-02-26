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

import javafx.scene.Node;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import main.Main;
import main.Utils;
import org.jetbrains.annotations.NotNull;

/**
 * Class abstraite qui represente un critère pour le manège
 * <p>
 * Chaque critère contient un
 * <p>
 * nomDuCritere
 * indicateur de statusDuCritere (rouge, jaune ou vert)
 * message de resultat (txtMessageDeResultat)
 */
public abstract class Critere {
    //Different statusDuCritere possible pour un critère
    public enum Status {
        PASSE,
        INCOMPLET,
        REFUSE
    }

    private static final String MSG_INCOMPLET = "Remplir critère";
    private static final String MSG_PASSE = "Passe";
    private static final String MSG_REFUSE = "Refusé";

    //Nom du critère
    private final String nomDuCritere;

    //Status du critère
    private Status statusDuCritere;

    //Message associé au critère (ex. Passe ou refusé)
    private final Text txtMessageDeResultat = Utils.creeTextNormal(null);

    //Indicateur affichant le statusDuCritere du critère
    private final IndicateurStatus rectIndicateurDeStatus = new IndicateurStatus(15);

    /**
     * Creer un nouveau critère
     *
     * @param nomDuCritere nomDuCritere du critère
     */
    Critere(String nomDuCritere) {
        this.nomDuCritere = nomDuCritere;

        statusDuCritere = Status.INCOMPLET;
        txtMessageDeResultat.setText(MSG_INCOMPLET);
        rectIndicateurDeStatus.mettreAJour(Status.INCOMPLET);
    }

    public String getNomDuCritere() {
        return this.nomDuCritere;
    }

    @NotNull
    public Rectangle getRectIndicateurDeStatus() {
        return this.rectIndicateurDeStatus;
    }

    @NotNull
    public Text getTxtMessageDeResultat() {
        return this.txtMessageDeResultat;
    }

    public Status getStatusDuCritere() {
        return this.statusDuCritere;
    }

    /**
     * Mets à jour le statusDuCritere utilisant le message par défaut
     * @param statusDuCritere le statusDuCritere
     */
    void setStatusDuCritere(@NotNull Status statusDuCritere) {
        switch (statusDuCritere) {
            case PASSE:
                setStatus(statusDuCritere, MSG_PASSE);
                break;
            case INCOMPLET:
                setStatus(statusDuCritere, MSG_INCOMPLET);
                break;
            default:
                setStatus(statusDuCritere, MSG_REFUSE);
                break;
        }
    }

    /**
     * Mets à jour le statusDuCritere du critère. Mets à jour l'indicateur, le statusDuCritere et le message
     *
     * @param nouveauStatus  si le critère passe ou ne passe pas
     * @param message message associé avec le critère
     */
    void setStatus(Status nouveauStatus, String message) {
        this.statusDuCritere = nouveauStatus;
        this.txtMessageDeResultat.setText(message);
        this.rectIndicateurDeStatus.mettreAJour(nouveauStatus);

        Main.calculerResultat();
    }

    /**
     * Methode abstracte qui retourne un objet de l'interface qui est utilisé par l'utilisateur pour rentrer le critère
     *
     * @return objet à mettre sur l'interface graphique
     */
    public abstract Node getObjetEntree();
}
