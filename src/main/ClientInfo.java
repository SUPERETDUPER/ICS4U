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

package main;

import java.util.List;

/**
 * Représente un client
 */
public class ClientInfo {
    private final String prenom;
    private final String nom;
    private final List<Integer> points;

    public ClientInfo(String prenom, String nom, List<Integer> points) {
        this.prenom = prenom;
        this.nom = nom;
        this.points = points;
    }

    public List<Integer> getPoints() {
        return points;
    }

    public String getNom() {
        return nom;
    }

    public String getPrenom() {
        return prenom;
    }

    @Override
    public String toString() {
        StringBuilder string = new StringBuilder();
        string.append(prenom).append(" ").append(nom).append(": ");

        for (Integer point : points) {
            string.append(point).append(", ");
        }

        return string.toString();
    }
}