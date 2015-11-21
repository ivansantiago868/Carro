/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bean.view;


import com.Dao.UsuariosDao;

import com.entidad.Usuarios;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import javax.servlet.ServletContext;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.primefaces.model.UploadedFile;

/**
 *
 * @author KIVANDY
 */
@ManagedBean
@ViewScoped
public class UsuariosView implements Serializable{

    private Usuarios usuario;
    private List<Usuarios> listaUsuario;
    private List<SelectItem> ListaPerfilesSelect;
    private Session session;
    private Transaction trans;
    private UploadedFile imagen;
    /**
     * Creates a new instance of UsuariosView
     */
    public UsuariosView() {
        ListaPerfilesSelect = new ArrayList<SelectItem>();
        this.usuario = new Usuarios();
        
    }
    
    public String registrar() throws Exception {
        UsuariosDao daoUsuarios = new UsuariosDao();
        this.usuario.setClave(encriptado(this.usuario.getClave()));
        daoUsuarios.registrar(this.usuario);
        this.usuario = new Usuarios();
        return "/admin/usuario";
    }

    public List<Usuarios> getListarUsuarios() throws Exception {
        UsuariosDao daoUsuarios = new UsuariosDao();
        this.listaUsuario = daoUsuarios.verTodo();
        return this.listaUsuario;
    }



    public void actualizar() throws Exception {
        UsuariosDao daoUsuarios = new UsuariosDao();
        this.usuario.setClave(encriptado(this.usuario.getClave()));
        daoUsuarios.editar(this.usuario);
    }
    
    public String encriptado(String args1) throws Exception {
        String[] args=new String[100];
        for(int i=0; i<args1.length(); i++)
        {
            args[i]=String.valueOf(args1.charAt(i));
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
    
     public void actualizarMapa() throws IOException {
        InputStream inputStream = null;
        OutputStream outputStream = null;

        try {
            if (imagen.getSize() <= 0) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERROR:", "Tiene que seleccionar un archivo"));
                return;
            }

            if (!imagen.getFileName().endsWith(".png")) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERROR:", "El archivo debe ser en formato png"));
                return;
            }

            if (imagen.getSize() > 2097152) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERROR:", "Tiene que seleccionar un archivo"));
                return;
            }

            ServletContext servletContext = (ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();
            String carpetaCarreraMapa = (String) servletContext.getRealPath("/ImagenUsuario");

            outputStream = new FileOutputStream(new File(carpetaCarreraMapa + "/" + usuario.getIdUsuarios()+ ".png"));
            inputStream = this.imagen.getInputstream();

            int read = 0;
            byte[] bytes = new byte[1024];
            while ((read = inputStream.read(bytes)) != -1) {
                outputStream.write(bytes, 0, read);
            }
        
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Correcto:", "La imagen se guardo correctamente"));
            
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERROR FATAL:", "existe un error" + e));
        } finally {
            if (inputStream != null) {
                inputStream.close();
            }
            if (outputStream != null) {
                outputStream.close();
            }
        }
    }
    
    
    public Usuarios getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuarios usuario) {
        this.usuario = usuario;
    }

    public List<Usuarios> getListaUsuario() {
        return listaUsuario;
    }

    public void setListaUsuario(List<Usuarios> listaUsuario) {
        this.listaUsuario = listaUsuario;
    }
    public List<SelectItem> getListaPerfilesSelect() {
        return ListaPerfilesSelect;
    }

    public void setListaPerfilesSelect(List<SelectItem> ListaPerfilesSelect) {
        this.ListaPerfilesSelect = ListaPerfilesSelect;
    }

    public Session getSession() {
        return session;
    }

    public void setSession(Session session) {
        this.session = session;
    }

    public Transaction getTrans() {
        return trans;
    }

    public void setTrans(Transaction trans) {
        this.trans = trans;
    }

    public UploadedFile getImagen() {
        return imagen;
    }

    public void setImagen(UploadedFile imagen) {
        this.imagen = imagen;
    }
}
