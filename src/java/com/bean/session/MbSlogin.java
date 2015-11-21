/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bean.session;

import com.Dao.UsuariosDao;
import com.entidad.Usuarios;
import java.io.Serializable;
import java.security.MessageDigest;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

/**
 *
 * @author AnchorPriceGroup
 */
@ManagedBean
@SessionScoped

public class MbSlogin implements Serializable {

    /**
     * Creates a new instance of MbSlogin
     */
    private String nick;
    private String contra;
    private boolean logeado = false;

    public MbSlogin() {
        HttpSession miSession = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
        miSession.setMaxInactiveInterval(3600);
    }

    public String login() throws Exception {
        String clave1 = new String();

        UsuariosDao daoUsuario = new UsuariosDao();
        Usuarios usuario = daoUsuario.verPorEmail(this.nick);
        if (usuario != null) {
            clave1 = encriptado(this.contra);

            if (clave1.equals(usuario.getClave())) {
                HttpSession httpSession = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
                httpSession.setAttribute("usuario", this.nick);
                //httpSession.setAttribute("perfil", usuario.getPerfil());
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Bienvenido;", "Se ha realizado el logueo correctamente " + httpSession.getAttribute("usuario")));
                return "/admin/adminperfil";
            }
        } else {
            this.nick = null;
            this.contra = null;
        }
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error;", "El usuario y/o contraseña son incorrectos"));
        return "/index";
    }

    public String encriptado(String args1) throws Exception {
        String[] args = new String[100];
        for (int i = 0; i < args1.length(); i++) {
            args[i] = String.valueOf(args1.charAt(i));
        }
        String original = args[0];
        MessageDigest md = MessageDigest.getInstance("MD5");
        md.update(original.getBytes());
        byte[] digest = md.digest();
        StringBuffer sb = new StringBuffer();
        for (byte b : digest) {
            sb.append(String.format("%02x", b & 0xff));
        }
        return sb.toString();
    }

    public String cerrarSesion() {
        this.nick = null;
        this.contra = null;
        HttpSession httpSession = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
        httpSession.invalidate();
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Adios:", "se ha cerrado sesion correctamente"));
        return "/index";
    }

    public String getNick() {
        return nick;
    }

    public void setNick(String nick) {
        this.nick = nick;
    }

    public String getContra() {
        return contra;
    }

    public void setContra(String contra) {
        this.contra = contra;
    }

    public boolean isLogeado() {
        return logeado;
    }

    public void setLogeado(boolean logeado) {
        this.logeado = logeado;
    }

}
