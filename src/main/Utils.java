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
import javafx.scene.control.TextField;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import org.jetbrains.annotations.NotNull;

/**
 * Classe qui crée des Objet Text avec des différents Fonts
 */
class Utils {
    private static final Font FONT_NORMAL = Font.font(16);
    private static final Font FONT_BOLD_GRAND = Font.font(null, FontWeight.BOLD, 20);
    private static final Font FONT_BOLD = Font.font(null, FontWeight.BOLD, 16);
    private static final Font FONT_TITRE = Font.font(null, FontWeight.EXTRA_BOLD, 34);

    @NotNull
    static Text creeTextBold(String message){
        Text text = new Text(message);
        text.setFont(FONT_BOLD);
        return text;
    }

    @NotNull
    static Text creeTextNormal(String message){
        Text text = new Text(message);
        text.setFont(FONT_NORMAL);
        return text;
    }

    @NotNull
    static Text creeTextBoldGrand(String message){
        Text text = new Text(message);
        text.setFont(FONT_BOLD_GRAND);
        return text;
    }

    @NotNull
    static Text creeTextTitre(String message) {
        Text text = new Text(message);
        text.setFont(FONT_TITRE);
        return text;
    }

    static TextField creeBoiteDeTexte(String indice){
        TextField textField = new TextField();
        textField.setFont(FONT_NORMAL);
        textField.setPromptText(indice);
        textField.setAlignment(Pos.BASELINE_RIGHT);
        return textField;
    }
}
