/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CargasMasivas;

import Utilidades.CAccionesdoc;
import java.io.FileInputStream;
import java.util.Iterator;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author ALEJANDRO
 */
public class CLeerExcel {

    // ==== METODO LEE EXCEL PARA CARGAR USUARIOS A UNA CARGA MASIVA EN LA DB
    public static List leerExcelUsuarios(String nombredocumento) {

        CCargaUsuarios enviarusuario = new CCargaUsuarios();
        // Lista para llenar con correos que no extan disponibles ya que estos no se agregaran
        List listanodisponibles = new ArrayList<>();

        try {

            int caso = 1;

            FileInputStream inputStream = new FileInputStream(new File(CAccionesdoc.rutaExcelcargamasiva(nombredocumento)));
            Workbook workbook = new XSSFWorkbook(inputStream);
            Sheet firstSheet = workbook.getSheetAt(0);
            Iterator iterator = firstSheet.iterator();

            DataFormatter formatter = new DataFormatter();
            int contador = 0;

            while (iterator.hasNext()) {

                Row nextRow = (Row) iterator.next();
                Iterator cellIterator = nextRow.cellIterator();
                while (cellIterator.hasNext()) {
                    Cell cell = (Cell) cellIterator.next();
                    String contenidocelda = formatter.formatCellValue(cell);
                    if (contador == 6) {

                        switch (caso) {
                            case 1: // Nombres
                                enviarusuario.cargarUsuario(contenidocelda, caso);
                                caso++;

                                break;

                            case 2: // Apellidos
                                enviarusuario.cargarUsuario(contenidocelda, caso);
                                caso++;
                                break;

                            case 3: // Telefono
                                enviarusuario.cargarUsuario(contenidocelda, caso);
                                caso++;
                                break;

                            case 4: // Correo

                                if (enviarusuario.cargarUsuario(contenidocelda, caso) == 1) {
                                    listanodisponibles.add(contenidocelda);
                                }

                                caso++;
                                break;

                            case 5: // Area
                                enviarusuario.cargarUsuario(contenidocelda, caso);
                                caso++;
                                break;

                            case 6: // Rol
                                enviarusuario.cargarUsuario(contenidocelda, caso);
                                caso = 1;
                                break;
                            default:
                                caso = 1;
                                throw new AssertionError();
                        }
                        contador = 6;

                    } else {
                        contador++;
                    }
                }
            }
            CAccionesdoc.eliminarDocumento(CAccionesdoc.rutaExcelcargamasiva(nombredocumento));

        } catch (IOException e) {
            System.out.println("Error al leer el documento excel de usuarios" + e);
            Logger.getLogger(CLeerExcel.class.getName()).log(Level.SEVERE, null, e);
        }

        return listanodisponibles;
    }

    // ====== METODO PARA ENVIAR DATOS A LOS SET PARA ENVIAR AL METODO ADDUSUARIO EN DAOUSUARIO
    public List leerExcelObjetos(String nombredocumento) {
        
        // Lista para guardar los datos que no se cargaron para mostrar al usuario.
        List listserialenuso = new ArrayList<>();
        CCargaObjetos enviarobjeto = new CCargaObjetos();

        try {

            int caso = 1;

            FileInputStream inputStream = new FileInputStream(new File(CAccionesdoc.rutaExcelcargamasiva(nombredocumento)));
            Workbook workbook = new XSSFWorkbook(inputStream);
            Sheet firstSheet = workbook.getSheetAt(0);
            Iterator iterator = firstSheet.iterator();

            DataFormatter formatter = new DataFormatter();
            int contador = 0;
            while (iterator.hasNext()) {

                Row nextRow = (Row) iterator.next();
                Iterator cellIterator = nextRow.cellIterator();
                while (cellIterator.hasNext()) {
                    Cell cell = (Cell) cellIterator.next();
                    String contenidocelda = formatter.formatCellValue(cell);

                    if (contador == 5) {
                        // Iniciar enviando los datos encontrados al objeto enviardatos

                        switch (caso) {
                            case 1: // Nombre del objeto
                                enviarobjeto.cargarObjetos(contenidocelda, caso);
                                caso++;

                                break;

                            case 2: // Marca del objeto
                                enviarobjeto.cargarObjetos(contenidocelda, caso);
                                caso++;
                                break;

                            case 3: // Serial del objeto
                                if (enviarobjeto.cargarObjetos(contenidocelda, caso) > 0) {
                                    listserialenuso.add(contenidocelda);
                                }
                                caso++;
                                break;

                            case 4: // Caracateristicas del objeto
                                enviarobjeto.cargarObjetos(contenidocelda, caso);
                                caso++;
                                break;

                            case 5: // Estado del objeto
                                enviarobjeto.cargarObjetos(contenidocelda, caso);
                                caso = 1;
                                break;

                            default:
                                caso = 1;
                                throw new AssertionError();
                        }

                    } else {
                        contador++;
                    }
                }
            }

            // Luego de haber echo la carga se elimina el documento del servidor.
            CAccionesdoc.eliminarDocumento(CAccionesdoc.rutaExcelcargamasiva(nombredocumento));

        } catch (IOException e) {
            System.out.println("Error al leer el documento excel de objetos " + e);
            Logger.getLogger(CLeerExcel.class.getName()).log(Level.SEVERE, null, e);
        }

        return listserialenuso;
    }
}
