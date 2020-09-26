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
public class CNotas {

    private int idnota,idusuario;
    private String nombrenota,textonota;

    public int getIdnota() {
        return idnota;
    }

    public void setIdnota(int idnota) {
        this.idnota = idnota;
    }

    public int getIdusuario() {
        return idusuario;
    }

    public void setIdusuario(int idusuario) {
        this.idusuario = idusuario;
    }

    public String getNombrenota() {
        return nombrenota;
    }

    public void setNombrenota(String nombrenota) {
        this.nombrenota = nombrenota;
    }

    public String getTextonota() {
        return textonota;
    }

    public void setTextonota(String textonota) {
        this.textonota = textonota;
    }
}
