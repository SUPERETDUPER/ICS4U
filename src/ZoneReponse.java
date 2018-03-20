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

import javafx.scene.control.ScrollPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;

/**
 * Zone de reponse où les résultats sont affichés
 */
class ZoneReponse extends ScrollPane {
    private static final String MSG_RESULTAT = "%d à l'exposant %s = %s";
    private static final String MSG_RESULTAT_FRACTION = "%d à l'exposant %d = %d \\ %d";

    private static final String MSG_MAXIMUM = "Maximum atteint";
    private static final String INDETERMINE = "Indéterminé";

    private static final int INTERLINE_REPONSE = 10;

    private final Text txtReponse = new Text();

    ZoneReponse() {
        super();

        this.setContent(new StackPane(txtReponse)); //Dans StackPane pour que le Text soit centré

        //Formatter
        this.setBackground(new Background(new BackgroundFill(Color.TRANSPARENT, null, null))); //Pour enlever la bordure
        this.setFitToWidth(true);
        txtReponse.setLineSpacing(INTERLINE_REPONSE);
        txtReponse.setTextAlignment(TextAlignment.CENTER);
    }

    /**
     * Génère le message de résultat
     *
     * @param base     base à utiliser
     * @param exposant exposant à utiliser
     */
    void calculerEtAfficherReponse(long base, long exposant) {
        StringBuilder message = new StringBuilder();

        //Si la base est zéro montrer des messages différents
        if (base == 0) {
            message.append(String.format(MSG_RESULTAT, base, 0, INDETERMINE));
            //Si plus d'exposant que juste 0 ajouter un autre message
            if (exposant != 0) {
                message.append("\n");
                message.append(String.format(MSG_RESULTAT, base, "n", 0));
            }
        } else {
            //Ajouter une ligne pour chaque exposant
            for (int i = 0; i <= Math.abs(exposant); i++) {
                long resultat = (long) Math.pow(base, i);

                //Vérifier que le maximum n'a pas été atteint
                if (resultat == Long.MAX_VALUE) {
                    message.append(MSG_MAXIMUM);
                    break;
                }

                int vraiExposant = i * (int) Math.signum(exposant);

                //Si exposant négatif, montrer une fraction
                if (vraiExposant >= 0) {
                    message.append(String.format(MSG_RESULTAT, base, vraiExposant, resultat));
                } else {
                    message.append(String.format(MSG_RESULTAT_FRACTION, base, vraiExposant, (int) Math.signum(resultat), Math.abs(resultat))); //signum et abs deplacent le sign du résultat devant la fraction
                }

                message.append("\n");
            }
        }

        txtReponse.setFont(Main.FONT_NORMAL);
        txtReponse.setText(message.toString());
    }

    /**
     * Mets à jour la réponse en gras
     *
     * @param reponse nouvelle réponse
     */
    void montrerMessageErreur(String reponse) {
        txtReponse.setFont(Main.FONT_BOLD);
        txtReponse.setText(reponse);
    }
}
