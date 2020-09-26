/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Utilidades;

import java.util.logging.Level;
import java.util.logging.Logger;
import java.io.File;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 *
 * @author jonny
 */
public class CAccionesdoc {

    // Ruta de la carpeta donde sea crea un reporte de usuarios para realizar descarga 
    public static String rutaReporteusuarios() {
        File fileusuarios = new File("Reporteusuarios.xlsx");
        return fileusuarios.getAbsolutePath();
    }

    // Ruta de la carpeta de descarga donde se crea el excel con reporte de objetos
    public static String rutaReporteobjetos() {
        File fileobjetos = new File("Reporteobjetos.xlsx");
        return fileobjetos.getAbsolutePath();
    }

    // Ruta donde se envia el excel de usuarios para hacer la carga masiva
    public static String rutaExcelcargamasiva(String nombredocumento) {
        File cargamasiva = new File(nombredocumento);
        return cargamasiva.getAbsolutePath();
    }

    // Ruta de fotos de perfil para eliminar o como para subir a la carpeta
    public static String rutaFotoperfil(String nombrefoto) {
        return "C:\\Payara\\glassfish\\domains\\domain1\\docroot\\" + nombrefoto;
    }

    // Metodo para eliminar documentos de las rutas que se reciben en parametro
    public static void eliminarDocumento(String rutadocumento) {
        try {
            String directorio = rutadocumento;
            File file = new File(directorio);
            if (file.delete()) {
                System.out.println("Se elimino correctamente el documento de la ruta: " + rutadocumento);
            } else {
                System.out.println("No se elimino el documento de la ruta: " + rutadocumento);
            }
        } catch (Exception e) {
            System.out.println("Error al eliminar el documento: " + e);
            Logger.getLogger(CAccionesdoc.class.getName()).log(Level.SEVERE, null, e);
        }
    }

    // Metodo para crear un json con una lista o un array
    public static String crearListaJson(List lista) {
        JSONArray json = new JSONArray(lista);
        return json.toString();
    }

    // Metodo para crear un json con un objeto
    public static String crearObjetoJson(Object objeto) {
        JSONObject json = new JSONObject(objeto);
        return json.toString();
    }

}
