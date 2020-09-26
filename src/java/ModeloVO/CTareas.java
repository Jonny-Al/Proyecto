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
public class CTareas {
    //======= FECHA EN QUE SE APRUEBA LA TAREA PARA ENVIAR LA FECHA DE APROBACION A LA TABLA HISTORIAL DE TAREAS

    private String fechaaprobacion;

    public String getFechaaprobacion() {
        return fechaaprobacion;
    }

    public void setFechaaprobacion(String fechaaprobacion) {
        this.fechaaprobacion = fechaaprobacion;
    }

    //========== VARIABLES PARA LA ENTIDAD TAREAS
    private int idtarea, progreso, iddesarrolla, idusasigna, idcomentario;

    private String nombre, anotacion, caracteristicas, comentarios, fechaasignada, fechainicio, fechafinal, estado, usuario;

    public int getIdtarea() {
        return idtarea;
    }

    public void setIdtarea(int idtarea) {
        this.idtarea = idtarea;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getAnotacion() {
        return anotacion;
    }

    public void setAnotacion(String anotacion) {
        this.anotacion = anotacion;
    }

    public int getIdcomentario() {
        return idcomentario;
    }

    public void setIdcomentario(int idcomentario) {
        this.idcomentario = idcomentario;
    }

    public String getCaracteristicas() {
        return caracteristicas;
    }

    public void setCaracteristicas(String caracteristicas) {
        this.caracteristicas = caracteristicas;
    }

    public String getComentarios() {
        return comentarios;
    }

    public void setComentarios(String comentariordir) {
        this.comentarios = comentariordir;
    }

    public String getFechaasignada() {
        return fechaasignada;
    }

    public void setFechaasignada(String fechaasignada) {
        this.fechaasignada = fechaasignada;
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

    public int getProgreso() {
        return progreso;
    }

    public void setProgreso(int progreso) {
        this.progreso = progreso;
    }

    public int getIddesarrolla() {
        return iddesarrolla;
    }

    public void setIddesarrolla(int iddesarrolla) {
        this.iddesarrolla = iddesarrolla;
    }

    public int getIdusasigna() {
        return idusasigna;
    }

    public void setIdusasigna(int idusasigna) {
        this.idusasigna = idusasigna;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

}
