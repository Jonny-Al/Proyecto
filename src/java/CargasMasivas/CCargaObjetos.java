/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CargasMasivas;

import ModeloDAO.CImplementosDAO;
import ModeloVO.CImplementos;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author ALEJANDRO
 */
public class CCargaObjetos {

    CImplementos objetos = new CImplementos();
    CImplementosDAO daoobjetos = new CImplementosDAO();
    private int disponible;

    public int cargarObjetos(String dato, int caso) {
        try {

            switch (caso) {
                case 1: // Nombre del objeto
                    objetos.setObjetonombre(dato);
                    caso++;
                    break;

                case 2: // Marca del objeto
                    objetos.setMarca(dato);
                    caso++;
                    break;

                case 3: // Serial del objeto
                    objetos.setSerial(dato);
                    // Valida si el serial del objeto esta disponible para permitir el 
                    // el registro del objeto o se retorna el serial no disponible
                    if (daoobjetos.comprobarSerial(objetos) == false) {
                        disponible = 0;
                    } else {
                        disponible = 1;
                    }
                    caso++;
                    break;

                case 4: // Caracteristicas del objeto
                    if (disponible == 0) {
                        objetos.setCaracteristicas(dato);
                    }
                    caso++;
                    break;

                case 5: // Estado del objeto

                    if (disponible == 0) {

                        int estado = daoobjetos.comprobarEstado(dato);
                        if (estado > 6 && estado < 10) {
                            dato = Integer.toString(estado);
                        } else {
                            dato = "7";
                        }

                        objetos.setEstado(dato);
                        daoobjetos.registrarObjeto(objetos);
                    }

                    break;

                default:
                  //  throw new AssertionError();
            }

        } catch (NumberFormatException e) {
            System.out.println("Error en la carga de objetos en class" + e);
            Logger.getLogger(CCargaObjetos.class.getName()).log(Level.SEVERE, null, e);
        }
        return disponible;
    }
}
