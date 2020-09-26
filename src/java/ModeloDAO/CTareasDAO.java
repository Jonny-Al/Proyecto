/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ModeloDAO;

import ModeloVO.CTareas;
import Conexion.CConexion;
import ModeloVO.CUsuario;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 *
 * @author ALEJANDRO
 */
public class CTareasDAO {

    private boolean sentencia = false;

    Connection conn = null;
    CConexion cn = new CConexion();
    ResultSet rs = null;

    //=== METODO PARA LISTAR TAREAS DEL USUARIO SELECCIONADO EN TAREASGRUPO.JSP Y MISTAREAS.JSP
    public List listarTareas(CTareas tarea, String accion, int opcion) {

        List<CTareas> listtareasfil = new ArrayList<>();
        // OPCION = PARA VER TAREAS PERSONALES O ASIGNADAS
        // PARA VER LAS ASIGNADAS ES CON OPCION 1 Y LAS PERSONALES OPCION 2

        try {
            conn = cn.getConexion();

            switch (accion) {

                case "1": // Lista todaa las tareas del usuario

                    CallableStatement proctareastodas = conn.prepareCall("CALL Tr_ListarTareas (?,?,?)");
                    proctareastodas.setInt(1, tarea.getIdusasigna());
                    proctareastodas.setInt(2, tarea.getIddesarrolla());
                    proctareastodas.setInt(3, opcion);

                    rs = proctareastodas.executeQuery();

                    break;

                case "2": // Lista las tareas que estan en proceso

                    CallableStatement proctaresactuales = conn.prepareCall("CALL Tr_ListarActuales (?,?,?)");
                    proctaresactuales.setInt(1, tarea.getIdusasigna());
                    proctaresactuales.setInt(2, tarea.getIddesarrolla());
                    proctaresactuales.setInt(3, opcion);

                    rs = proctaresactuales.executeQuery();

                    break;

                case "3": // Lista las tareas a futuro = > a fechaactual

                    CallableStatement proctareasfuturo = conn.prepareCall("CALL Tr_ListarAFuturo (?,?,?)");
                    proctareasfuturo.setInt(1, tarea.getIdusasigna());
                    proctareasfuturo.setInt(2, tarea.getIddesarrolla());
                    proctareasfuturo.setInt(3, opcion);

                    rs = proctareasfuturo.executeQuery();

                    break;

                case "4": // Lista las tareas que estan terminadas en 100%

                    CallableStatement proctareasterminadas = conn.prepareCall("CALL Tr_ListarTerminadas (?,?,?)");
                    proctareasterminadas.setInt(1, tarea.getIdusasigna());
                    proctareasterminadas.setInt(2, tarea.getIddesarrolla());
                    proctareasterminadas.setInt(3, opcion);

                    rs = proctareasterminadas.executeQuery();

                    break;

                case "5": // Lista tareas que fueron desaprobadas

                    CallableStatement proctareasdesaprobadas = conn.prepareCall("CALL Tr_ListarDesaprobadas (?,?,?)");
                    proctareasdesaprobadas.setInt(1, tarea.getIdusasigna());
                    proctareasdesaprobadas.setInt(2, tarea.getIddesarrolla());
                    proctareasdesaprobadas.setInt(3, opcion);

                    rs = proctareasdesaprobadas.executeQuery();

                    break;

                case "6": // Lista las tareas que fueron restauradas desde el historial de 

                    CallableStatement proctareasrestauradas = conn.prepareCall("CALL Tr_ListarRestauradas  (?,?,?)");
                    proctareasrestauradas.setInt(1, tarea.getIdusasigna());
                    proctareasrestauradas.setInt(2, tarea.getIddesarrolla());
                    proctareasrestauradas.setInt(3, opcion);

                    rs = proctareasrestauradas.executeQuery();

                    break;

                default:

            }

            while (rs.next()) {

                CTareas tareasfil = new CTareas();

                tareasfil.setIdtarea(rs.getInt(1));
                tareasfil.setNombre(rs.getString(2));
                tareasfil.setFechainicio(rs.getString(3));
                tareasfil.setFechafinal(rs.getString(4));
                // ProgresoTarea = Metodo para calcular el progreso % de la tarea
                progresoTarea(tareasfil);

                listtareasfil.add(tareasfil);
            }

        } catch (SQLException e) {
            System.out.println("Error DAO en listar tareas " + e);
            Logger.getLogger(CTareasDAO.class.getName()).log(Level.SEVERE, null, e);
        } finally {
            conn = cn.closeConn();
        }
        return listtareasfil;
    }

    //========= VALIDAR SI TAREA SOLICITA PERTENECE A UN USUARIO QUE LA SOLITICA VER
    public CTareas validaTarea(CTareas tr) {
        try {
            conn = cn.getConexion();

            CallableStatement valida = conn.prepareCall("CALL Tr_ValidarUsuario (?)");
            valida.setInt(1, tr.getIdtarea());

            rs = valida.executeQuery();

            if (rs.next()) {
                tr.setIddesarrolla(rs.getInt(2));
                tr.setIdusasigna(rs.getInt(3));
            }

        } catch (SQLException e) {
            System.out.println("Error DAO al validar tarea " + e);
            Logger.getLogger(CTareasDAO.class.getName()).log(Level.SEVERE, null, e);
        } finally {
            conn = cn.closeConn();
        }
        return tr;
    }

    //========== BUSQUEDA DE CARACTERISTICAS DE LA TAREA SELECCIONADA EN TAREASMIAS.JSP Y TAREASGRUPO.JSP
    public CTareas verTarea(CTareas idTarea) {

        CTareas tarea = new CTareas();
        try {
            conn = cn.getConexion();

            CallableStatement procvertarea = conn.prepareCall("CALL Tr_VerTarea(?)");
            procvertarea.setInt(1, idTarea.getIdtarea());

            rs = procvertarea.executeQuery();

            while (rs.next()) {

                tarea.setIdtarea(rs.getInt(1));
                tarea.setNombre(rs.getString(2));
                tarea.setAnotacion(rs.getString(3));
                tarea.setCaracteristicas(rs.getString(4));
                tarea.setFechaasignada(rs.getString(5));
                tarea.setFechainicio(rs.getString(6));
                tarea.setFechafinal(rs.getString(7));
                tarea.setEstado(rs.getString(8));
                tarea.setIddesarrolla(rs.getInt(9));
                tarea.setIdusasigna(rs.getInt(10));

               // progresoTarea(tarea);
            }
        } catch (SQLException e) {
            System.out.println("Error DAO al ver tarea " + e);
            Logger.getLogger(CTareasDAO.class.getName()).log(Level.SEVERE, null, e);
        } finally {
            conn = cn.closeConn();
        }
        return tarea;
    }

    //===  METODO PARA CALCULAR Y VALIDAR EL PROGRESO DE CADA TAREA.
    private static void progresoTarea(CTareas tarea) {

        Date fechactual = new Date(System.currentTimeMillis());
        SimpleDateFormat date = new SimpleDateFormat("yyyy-MM-dd");

        try {
            double diastotal, diasfaltan;

            if (tarea.getFechainicio() != null && tarea.getFechafinal() != null) {

                Date dateinicio = date.parse(tarea.getFechainicio());
                Date datefin = date.parse(tarea.getFechafinal());
                Date dateactual = date.parse(date.format(fechactual));

                if (dateinicio.after(dateactual)) {
                    tarea.setProgreso(0);
                } else if (datefin.equals(dateactual) || datefin.before(dateactual)) {
                    tarea.setProgreso(100);
                } else if (dateinicio.before(dateactual) || dateinicio.equals(dateactual)) {

                    diastotal = (int) ((datefin.getTime() - dateinicio.getTime()) / 86400000); // Total de dias para terminar la tarea
                    diasfaltan = (int) ((datefin.getTime() - dateactual.getTime()) / 86400000); // Dias que faltan para terminar la tarea

                    int progreso = (int) (100 - (diasfaltan / diastotal) * 100);

                    if (progreso <= 100) {
                        tarea.setProgreso(progreso);
                    } else if (progreso >= 100) {
                        tarea.setProgreso(100);
                    } else if (dateinicio.equals(datefin)) {
                        tarea.setProgreso(100);
                    }
                }
            } else {
                tarea.setProgreso(0);
            }

        } catch (ParseException e) {
            System.out.println("Error al calcular progreso de la tarea" + e);
            Logger.getLogger(CTareasDAO.class.getName()).log(Level.SEVERE, null, e);
        }
    }

    // ==== METODO PARA LISTAR LOS COMENTARIOS DE UNA TAREA
    public List listarComentarios(CTareas tarea) {

        List<CTareas> listacomentarios = new ArrayList<>();

        try {

            conn = cn.getConexion();
            CallableStatement comentarios = conn.prepareCall("CALL Tr_ListarComentarios (?)");
            comentarios.setInt(1, tarea.getIdtarea());

            rs = comentarios.executeQuery();
            while (rs.next()) {
                CTareas tr = new CTareas();

                tr.setIdcomentario(rs.getInt(1));
                tr.setIddesarrolla(rs.getInt(3)); // Id del usuario del comentario
                tr.setComentarios(rs.getString(2));
                tr.setUsuario(rs.getString(5));

                listacomentarios.add(tr);
            }

        } catch (SQLException e) {
            System.out.println("Error DAO al listar comentarios de la tarea: " + e);
            Logger.getLogger(CTareasDAO.class.getName()).log(Level.SEVERE, null, e);
        } finally {
            conn = cn.closeConn();
        }
        return listacomentarios;
    }

    // ==== METODO PARA ASIGNAR UNA TAREA A UN USUARIOs
    public boolean asignarTarea(CTareas tarea) {
        sentencia = false;
        try {
            conn = cn.getConexion();

            CallableStatement addtr = conn.prepareCall("CALL Tr_AddTarea  (?,?,?,?,?,?,?,?,?)");

            addtr.setString(1, tarea.getNombre());
            addtr.setString(2, tarea.getAnotacion());
            addtr.setString(3, tarea.getCaracteristicas());
            addtr.setString(4, tarea.getComentarios());
            addtr.setString(5, tarea.getFechaasignada());
            addtr.setString(6, tarea.getFechainicio());
            addtr.setString(7, tarea.getFechafinal());
            addtr.setInt(8, tarea.getIddesarrolla());
            addtr.setInt(9, tarea.getIdusasigna());

            addtr.executeUpdate();

            sentencia = true;

        } catch (SQLException e) {
            System.out.println("Error DAO al agregar tarea" + e);
            Logger.getLogger(CTareasDAO.class.getName()).log(Level.SEVERE, null, e);
        } finally {
            conn = cn.closeConn();
        }
        return sentencia;
    }

    // === METODO PARA CREAR UNA TAREA PERSONAL
    public boolean crearTareaPersonal(CTareas tarea) {
        sentencia = false;
        try {
            conn = cn.getConexion();

            CallableStatement creartarea = conn.prepareCall("CALL Tr_AddTareapersonal (?,?,?,?,?,?,?,?)");
            creartarea.setString(1, tarea.getNombre());
            creartarea.setString(2, tarea.getAnotacion());
            creartarea.setString(3, tarea.getCaracteristicas());
            creartarea.setString(4, tarea.getFechaasignada());
            creartarea.setString(5, tarea.getFechainicio());
            creartarea.setString(6, tarea.getFechafinal());
            creartarea.setInt(7, tarea.getIddesarrolla());
            creartarea.setInt(8, tarea.getIdusasigna());

            creartarea.executeUpdate();
            sentencia = true;

        } catch (SQLException e) {
            System.out.println("Error DAO al agregar tarea personal: " + e);
            Logger.getLogger(CTareasDAO.class.getName()).log(Level.SEVERE, null, e);
        }
        return sentencia;
    }

    //=== Actualizacion del director sobre las propiedades de la tarea procedimientos almacenados tareasgrupo.jsp 
    public boolean actualizarTarea(CTareas tarea, String opcion) {
        sentencia = false;
        try {

            conn = cn.getConexion();

            switch (opcion) {
                case "Propiedades": // Actualiza daros sobre la tarea solo lo hace director de area

                    CallableStatement update = conn.prepareCall("CALL Tr_UpdateTarea (?,?,?,?,?,?)");

                    update.setInt(1, tarea.getIdtarea());
                    update.setString(2, tarea.getNombre());
                    update.setString(3, tarea.getAnotacion());
                    update.setString(4, tarea.getCaracteristicas());
                    update.setString(5, tarea.getFechainicio());
                    update.setString(6, tarea.getFechafinal());

                    update.executeUpdate();
                    sentencia = true;

                    break;

                case "Estado": // Actualiza estado para una tarea solo lo hace director de area

                    CallableStatement estado = conn.prepareCall("CALL Tr_UpdateEstado (?,?)");
                    estado.setInt(1, tarea.getIdtarea());
                    estado.setString(2, tarea.getEstado());

                    estado.executeUpdate();
                    sentencia = true;

                    break;

                case "Aprobacion": // Aprueba la tarea y es enviada al historial de tarea lo hace solo director

                    CallableStatement aprobar = conn.prepareCall("CALL Tr_Aprobacion (?,?)");
                    aprobar.setInt(1, tarea.getIdtarea());
                    aprobar.setString(2, tarea.getFechaaprobacion());

                    aprobar.executeUpdate();
                    sentencia = true;

                    break;

                case "Archivar":

                    CallableStatement archivar = conn.prepareCall("CALL Tr_Archivar (?,?)");
                    archivar.setInt(1, tarea.getIdtarea());
                    archivar.setInt(2, tarea.getIdusasigna());

                    archivar.executeUpdate();
                    sentencia = true;

                    break;
            }

        } catch (SQLException e) {
            System.out.println("Error DAO al actualizar tarea: " + e);
            Logger.getLogger(CTareasDAO.class.getName()).log(Level.SEVERE, null, e);
        } finally {
            conn = cn.closeConn();
        }
        return sentencia;
    }

    // === METODO PARA AGREGAR COMENTARIOS SOBRE UNA TAREA
    public boolean crearComentario(CTareas tarea, CUsuario usuario) {
        sentencia = false;
        try {
            conn = cn.getConexion();

            CallableStatement comment = conn.prepareCall("CALL Tr_AgregarComentario (?,?,?) ");

            comment.setString(1, tarea.getComentarios());
            comment.setInt(2, usuario.getID());
            comment.setInt(3, tarea.getIdtarea());

            comment.executeUpdate();

            sentencia = true;

        } catch (SQLException e) {
            System.out.println("Error en DAO al crear comentario sobre la tarea " + e);
            Logger.getLogger(CTareasDAO.class.getName()).log(Level.SEVERE, null, e);
        } finally {
            conn = cn.closeConn();
        }
        return sentencia;
    }

    // ==== METODO PARA VALIDAR SI EL COMENTARIO A EDITAR LE PERTENECE A ESE USUARIO
    public boolean validarComentario(CTareas tarea, CUsuario usuario) {
        sentencia = false;
        try {
            conn = cn.getConexion();
            CallableStatement validar = conn.prepareCall("CALL Cm_ComprobarComentario(?,?)");
            validar.setInt(1, tarea.getIdcomentario());
            validar.setInt(2, usuario.getID());

            rs = validar.executeQuery();

            sentencia = (rs.next()) ? true : sentencia;

        } catch (SQLException e) {
            System.out.println("Error al validar el comentario " + e);
            Logger.getLogger(CTareasDAO.class.getName()).log(Level.SEVERE, null, e);
        } finally {
            conn = cn.closeConn();
        }
        return sentencia;
    }

    //=== METODO PARA ACTUALIZAR COMENTARIO DE UNA TAREA
    public boolean actualizarComentario(CTareas tarea) {
        sentencia = false;
        try {
            conn = cn.getConexion();
            CallableStatement update = conn.prepareCall("CALL Cm_ActualizarComentario (?,?)");
            update.setInt(1, tarea.getIdcomentario());
            update.setString(2, tarea.getComentarios());

            update.executeUpdate();

            sentencia = true;

        } catch (SQLException e) {
            System.out.println("Error DAO al actualizar comentario: " + e);
            Logger.getLogger(CTareasDAO.class.getName()).log(Level.SEVERE, null, e);
        } finally {
            conn = cn.closeConn();
        }
        return sentencia;
    }

    //=== METODO PARA ELIMINAR TAREA
    public boolean eliminarTarea(CTareas idtr) {
        sentencia = false;
        try {
            conn = cn.getConexion();

            CallableStatement deletetr = conn.prepareCall("CALL Tr_Eliminar(?)");
            deletetr.setInt(1, idtr.getIdtarea());

            deletetr.executeUpdate();

            sentencia = true;
        } catch (SQLException e) {
            System.out.println("Error DAO al eliminar tarea: " + e);
            Logger.getLogger(CTareasDAO.class.getName()).log(Level.SEVERE, null, e);
        } finally {
            conn = cn.closeConn();
        }
        return sentencia;
    }
}
