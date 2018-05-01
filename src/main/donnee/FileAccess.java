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

package main.donnee;

import java.io.*;

public class FileAccess {
    private static final String PATHNAME = "res/donneeClient.txt";
    private static final String PATHNAME_DEFAULT = "res/donneeClient_default.txt";

    private final File file = new File(PATHNAME);

    public BaseDeDonnees load() {
        File file;

        if (this.file.exists()){
            file = this.file;
        } else {
            file = new File(PATHNAME_DEFAULT);
            if (!file.exists()) return new BaseDeDonnees();
        }

        try {
            BaseDeDonnees baseDeDonnees = new BaseDeDonnees();
            baseDeDonnees.readObject(new DataInputStream(new FileInputStream(file)));
            return baseDeDonnees;
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Impossible de charger le fichier");
        }
    }

    public void write(BaseDeDonnees donnees) {
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        try {
            donnees.writeObject(new DataOutputStream(new FileOutputStream(file)));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
