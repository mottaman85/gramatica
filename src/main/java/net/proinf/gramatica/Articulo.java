/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package net.proinf.gramatica;

/**
 * Tipo de artículo.
 */
public enum Articulo {
    determinado, indeterminado;

    private final static String[][][] articulos = {
            {// determinado
                    {"lo", "los"}, // neutro: singular y plural
                    {"el", "los"}, // masculino: singular y plural
                    {"la", "las"} // femenino: singular y plural
            },
            {// indeterminado
                    {"uno", "unos"}, // neutro: singular y plural
                    {"un", "unos"}, // masculino: singular y plural
                    {"una", "unas"} // femenino: singular y plural
            }
    };

    /**
     * Indica si se trata de un artículo
     */
    public static boolean es(Palabra palabra) {
        for (String[][] lista : articulos)
            for (String[] sublista : lista)
                for (String elemento : sublista)
                    if (palabra.es(elemento)) return true;
        return false;
    }

    public boolean esDeterminado() {
        return this == determinado;
    }

    public boolean esIndeterminado() {
        return this == indeterminado;
    }

    /**
     * Ej: Genero.femenino,Numero.plural &rarr; "las"
     */
    public String obtener(Genero genero, Numero numero) {
        return articulos[ordinal()][genero.ordinal()][numero.ordinal()];
    }

    /**
     * Ej: determinado("facturas") &rarr; "las"
     */
    public String segunPalabra(Palabra palabra) {
        return obtener(palabra.generoAntepuesto(), palabra.numero());
    }

    /**
     * Ej: determinado("facturas") &rarr; "las facturas"
     */
    public String agregarPalabra(Palabra palabra) {
        return segunPalabra(palabra) + " " + palabra;
    }

    /**
     * Con la preposición <q>a</q> o <q>de</q>. Ej: "al", "del"
     */
    public boolean casoArticuloContracto(Palabra palabra) {
        return esDeterminado()
                && palabra.generoAntepuesto().esMasculino()
                && palabra.numero().esSingular();
    }
}