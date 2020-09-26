/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ModeloVO;

/**
 *
 * @author ALEJANDRO
 */
public class CReuniones extends CUsuario{

    private int idReunion, idsalajuntas, idUsuarioseparo;
    private String nombrereunion, fechainicio, fechafinal, horainicio, horafin, comentarios;

    public int getIdReunion() {
        return idReunion;
    }

    public void setIdReunion(int idReunion) {
        this.idReunion = idReunion;
    }

    public String getNombrereunion() {
        return nombrereunion;
    }

    public void setNombrereunion(String nombrereunion) {
        this.nombrereunion = nombrereunion;
    }

    public String getFechainicio() {
        return fechainicio;
    }

    public void setFechainicio(String fechainicio) {
        this.fechainicio = fechainicio;
    }

    public String getFechafinal() {
        return fechafinal;
    }

    public void setFechafinal(String fechafinal) {
        this.fechafinal = fechafinal;
    }

    public String getHorainicio() {
        return horainicio;
    }

    public void setHorainicio(String horainicio) {
        this.horainicio = horainicio;
    }

    public String getHorafin() {
        return horafin;
    }

    public void setHorafin(String horafin) {
        this.horafin = horafin;
    }

    public String getComentarios() {
        return comentarios;
    }

    public void setComentarios(String comentarios) {
        this.comentarios = comentarios;
    }

    public int getIdUsuarioseparo() {
        return idUsuarioseparo;
    }

    public void setIdUsuarioseparo(int idUsuarioseparo) {
        this.idUsuarioseparo = idUsuarioseparo;
    }

   

    public int getIdsalajuntas() {
        return idsalajuntas;
    }

    public void setIdsalajuntas(int idsalajuntas) {
        this.idsalajuntas = idsalajuntas;
    }
}
