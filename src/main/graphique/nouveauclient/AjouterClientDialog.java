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

package main.graphique.nouveauclient;

import javafx.fxml.FXMLLoader;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.Dialog;
import main.ClientInfo;

import java.io.IOException;

public class AjouterClientDialog extends Dialog<ClientInfo> {
    public AjouterClientDialog() {
        final FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/clientEntree.fxml"));

        this.setHeaderText("Ajouter un client");

        try {
            this.setDialogPane(fxmlLoader.load());
        } catch (IOException e) {
            e.printStackTrace();
        }

        this.setResultConverter(param -> {
            if (param.getButtonData() == ButtonBar.ButtonData.APPLY) {
                return ((ClientEntreeController) fxmlLoader.getController()).creerClient();
            } else {
                return null;
            }
        });
    }
}
