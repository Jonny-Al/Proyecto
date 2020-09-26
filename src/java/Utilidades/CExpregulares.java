/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Utilidades;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author jonny
 */
public class CExpregulares {

    public static boolean formatoCorreo(String correo) {
        boolean valido = false;
        try {
            Pattern pattern = Pattern.compile("^[_a-z0-9-]+(\\.[_a-z0-9-]+)*@[a-z0-9-]+(\\.[a-z0-9-]+)*(\\.[a-z]{2,4})$");

            Matcher matcher = pattern.matcher(correo);
            if (matcher.matches()) {
                valido = true;
            }

        } catch (Exception e) {
            System.out.println("Error en comprobar formato de correo " + e);
        }
        return valido;
    }

    public static boolean formatoNumero(String numero) {
        boolean valido = false;
        try {
            Pattern pattern = Pattern.compile("[0-9]*");

            Matcher matcher = pattern.matcher(numero);
            if (matcher.matches()) {
                valido = true;
            }
        } catch (Exception e) {
            System.out.println("Error en comprobar formato de numero " + e);
        }

        return valido;
    }

    public static boolean formatoLetras(String cadena) {
        boolean valido = false;
        try {
            Pattern pattern = Pattern.compile("[A-Za-z]*");

            Matcher matcher = pattern.matcher(cadena);
            if (matcher.matches()) {
                valido = true;
            }
        } catch (Exception e) {
            System.out.println("Error en comprobar formato de letras " + e);
        }
        return valido;
    }
}
