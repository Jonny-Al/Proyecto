/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Reportes;

import Utilidades.CAccionesdoc;
import ModeloDAO.CImplementosDAO;
import ModeloVO.CUsuario;
import ModeloDAO.CUsuarioDAO;
import ModeloVO.CImplementos;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.Iterator;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

/**
 *
 * @author ALEJANDRO
 */
public class CReportexcel {

    public static boolean crearExcelusuarios(String opcionusuarios) {
        boolean creado = false;

        try {

            CUsuarioDAO daous = new CUsuarioDAO();

            Workbook book = new XSSFWorkbook();
            Sheet sheet = book.createSheet("Usuarios");

            Row row = sheet.createRow(0);
            // Crea las celdas con los nombres
            row.createCell(0).setCellValue("NOMBRES");
            row.createCell(1).setCellValue("APELLIDOS");
            row.createCell(2).setCellValue("TELEFONO");
            row.createCell(3).setCellValue("CORREO ELECTRONICO");
            row.createCell(4).setCellValue("AREA");
            row.createCell(5).setCellValue("CARGO");

            List lista = daous.listarUsuarios(opcionusuarios);
            CUsuario usuario;
            Iterator iterador = lista.iterator();
            int celda = 1;

            while (iterador.hasNext()) {

                usuario = (CUsuario) iterador.next();
                Row rowdatos = sheet.createRow(celda);

                // Llena las celdas con los datos
                rowdatos.createCell(0).setCellValue(usuario.getNombres());
                rowdatos.createCell(1).setCellValue(usuario.getApellidos());
                rowdatos.createCell(2).setCellValue(usuario.getTelefono());
                rowdatos.createCell(3).setCellValue(usuario.getCorreo());
                rowdatos.createCell(4).setCellValue(usuario.getAreaNombre());
                rowdatos.createCell(5).setCellValue(usuario.getRolNombre());

                // Aumenta la celda para saltar a la siguiente fila y seguir insertando datos
                celda++;
            }
            try {

                try (FileOutputStream fileout = new FileOutputStream(CAccionesdoc.rutaReporteusuarios())) {
                    book.write(fileout);
                    creado = true;
                }

            } catch (IOException e) {
                System.out.println("Error al crear el excel de usuarios " + e);
                Logger.getLogger(CReportexcel.class.getName()).log(Level.SEVERE, null, e);
            }
        } catch (Exception e) {
            System.out.println("Error al crear el excel de usuarios " + e);
            Logger.getLogger(CReportexcel.class.getName()).log(Level.SEVERE, null, e);
        }
        return creado;
    }

    public static boolean crearExcelObjetos() {
        boolean creado = false;

        try {
            CImplementosDAO daoobjetos = new CImplementosDAO();
            Workbook book = new XSSFWorkbook();
            Sheet sheet = book.createSheet("Objetos");

            Row row = sheet.createRow(0);

            // Crea las celdas con los nombres
            row.createCell(0).setCellValue("IMPLEMENTO");
            row.createCell(1).setCellValue("MARCA");
            row.createCell(2).setCellValue("SERIAL S/N");
            row.createCell(3).setCellValue("CARACTERISTICAS");
            row.createCell(4).setCellValue("ESTADO");

            List lista = daoobjetos.listaObjetosReporte();
            CImplementos objetos;

            Iterator iterator = lista.iterator();
            int celda = 1;

            while (iterator.hasNext()) {
                objetos = (CImplementos) iterator.next();
                Row rowdatos = sheet.createRow(celda);

                // Llena las celdas con los datos
                rowdatos.createCell(0).setCellValue(objetos.getObjetonombre());
                rowdatos.createCell(1).setCellValue(objetos.getMarca());
                rowdatos.createCell(2).setCellValue(objetos.getSerial());
                rowdatos.createCell(3).setCellValue(objetos.getCaracteristicas());
                rowdatos.createCell(4).setCellValue(objetos.getEstado());

                // Aumenta la celda para saltar a la siguiente fila y seguir insertando datos
                celda++;
            }

            try {
                try (FileOutputStream fileout = new FileOutputStream(CAccionesdoc.rutaReporteobjetos())) {
                    book.write(fileout);
                    creado = true;
                }
            } catch (IOException e) {
                System.out.println("Error al crear el excel y enviar a la ruta especificada: " + e);
                Logger.getLogger(CReportexcel.class.getName()).log(Level.SEVERE, null, e);
            }

            // creado = true;
        } catch (Exception e) {
            System.out.println("Error al crear el reporte del excel de objetos " + e);
            Logger.getLogger(CReportexcel.class.getName()).log(Level.SEVERE, null, e);
        }
        return creado;
    }

}
