/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ModeloDAO;

import ModeloVO.CEstados;
import Conexion.CConexion;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.ArrayList;
import java.util.List;
import java.sql.Connection;
import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author ALEJANDRO
 */
public class CEstadosDAO {

    CConexion cn = new CConexion();
    Connection conn = null;
    ResultSet rs = null;

    // ====  LISTA LOS ESTADOS PARA OBJETOS
    public List listarEstados() {

        List<CEstados> listestado = new ArrayList<>();
        try {
            conn = cn.getConexion();

            CallableStatement est = conn.prepareCall("CALL Es_ListEstadosObjetos");

            rs = est.executeQuery();

            while (rs.next()) {
                CEstados estado = new CEstados();

                estado.setIdestado(rs.getInt(1));
                estado.setEstado(rs.getString(2));

                listestado.add(estado);
            }

        } catch (SQLException e) {
            System.out.println("Error en DAO al listar los estados " + e);
           Logger.getLogger(CEstadosDAO.class.getName()).log(Level.SEVERE, null, e);
        } finally {
            conn = cn.closeConn();
        }
        return listestado;
    }
}
