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
public class CChats {

    private int idchat, idsuario;
    private String fechamensaje, horamensaje, mensaje, usuario;

    public int getIdchat() {
        return idchat;
    }

    public void setIdchat(int idchat) {
        this.idchat = idchat;
    }

    public String getFechamensaje() {
        return fechamensaje;
    }

    public void setFechamensaje(String fechamensaje) {
        this.fechamensaje = fechamensaje;
    }

    public String getHoramensaje() {
        return horamensaje;
    }

    public void setHoramensaje(String horamensaje) {
        this.horamensaje = horamensaje;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public int getIdsuario() {
        return idsuario;
    }

    public void setIdsuario(int idsuario) {
        this.idsuario = idsuario;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

}
