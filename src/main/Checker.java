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

import javafx.geometry.Pos;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

import java.util.Arrays;
import java.util.List;

public class Checker extends VBox implements UpdateListener {

    private final List<Critere> criteres = Arrays.asList(
            new CritereOuiNon("Mal de dos?", false, this),
            new CritereOuiNon("Malaise cardiaque",false, this),
            new CritereMinMax(122, 188, "Hauteur", "Rentrez votre hauteur ici", this)
    );

    private final Text txtReponse;

    Checker(Text txtReponse) {
        super();

        this.txtReponse = txtReponse;

        for (Critere critere : criteres){
            HBox critereDisplayable = critere.creeDisplayable();
            critereDisplayable.setAlignment(Pos.CENTER);
            this.getChildren().add(critereDisplayable);
        }
    }

    @Override
    public void notifyChange() {
        boolean isPass = true;

        for (Critere critere : criteres){
            try {
                isPass = isPass && critere.isPass();
            } catch (Exception e){
                txtReponse.setText(e.getMessage());
                break;
            }
        }

        txtReponse.setText(isPass ? "Peut passer" : "Peut pas passer");
    }
}