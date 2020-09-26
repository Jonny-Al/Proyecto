/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Conexion;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author ALEJANDRO
 */
public class CConexion {

    Connection conn = null;

    public Connection getConexion() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/wooclic?serverTimezone=UTC", "root", null);

        } catch (ClassNotFoundException | SQLException e) {
            System.out.println("Error de conexion en wc get " + e);
            Logger.getLogger(CConexion.class.getName()).log(Level.SEVERE, null, e);
        }
        return conn;
    }

    public Connection closeConn() {
        try {
            conn.close();
            conn = null;
        } catch (SQLException | NullPointerException e) {
            System.out.println("Error al cerrar la conexion wc en close " + e);
            Logger.getLogger(CConexion.class.getName()).log(Level.SEVERE, null, e);
        }
        return conn;
    }
}
