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

/**
 * Permet de sauvgarder et charge la base de donnée à un fichier.
 * Si une base de donnée existe déja elle est utilisé. Sinon la base de donnée par défaut est utilisé
 */
public class FileAccess {
    /**
     * Base de données locale
     */
    private static final String PATHNAME = "res/donneeClient.txt";
    /**
     * Base de données par défaut
     */
    private static final String PATHNAME_DEFAULT = "res/donneeClient_default.txt";

    private final File fichierDonneesLocale = new File(PATHNAME);

    /**
     * Lit la base de données du fichier
     *
     * @return la base de données du fichier
     */
    public BaseDeDonnees read() {
        File fichierAUtiliser = fichierDonneesLocale;

        //Si le fichier n'existe pas utiliser le défaut
        if (!fichierAUtiliser.exists()) {
            fichierAUtiliser = new File(PATHNAME_DEFAULT);

            //Si le défaut n'existe pas construire une base de données vide
            if (!fichierAUtiliser.exists()) return new BaseDeDonnees();
        }

        //Si il y a un problème dans le fichier lancer une erreur
        try {
            return new BaseDeDonnees(new DataInputStream(new FileInputStream(fichierAUtiliser)));
        } catch (IOException e) {
            throw new RuntimeException(e.toString());
        }
    }

    /**
     * Sauvegarde la base de données au fichier
     * @param donnees la base de données à sauvegarder
     */
    public void write(BaseDeDonnees donnees) {
        //Si le fichier n'existe pas le créer
        if (!fichierDonneesLocale.exists()) {
            try {
                //noinspection ResultOfMethodCallIgnored
                fichierDonneesLocale.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        try {
            DataOutputStream outputStream = new DataOutputStream(new FileOutputStream(fichierDonneesLocale));
            donnees.writeObject(outputStream);
            outputStream.flush();
            outputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
