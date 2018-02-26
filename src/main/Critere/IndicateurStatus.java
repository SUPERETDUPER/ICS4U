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


import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import org.jetbrains.annotations.NotNull;

/**
 * Un rectangle qui est soit vert, jaune ou rouge dépendant du status
 */
public class IndicateurStatus extends Rectangle {

    private static final Paint REFUSE = Color.RED;
    private static final Paint INCOMPLET = Color.YELLOW;
    private static final Paint PASSE = Color.GREEN;

    public IndicateurStatus(int dimension){
        super(dimension, dimension);
    }

    /**
     * Appelé quand le status change
     * @param status le nouveau status
     */
    public void mettreAJour(@NotNull Critere.Status status){
        switch (status) {
            case INCOMPLET:
                this.setFill(INCOMPLET);
                break;
            case PASSE:
                this.setFill(PASSE);
                break;
            case REFUSE:
                this.setFill(REFUSE);
                break;
        }
    }
}
