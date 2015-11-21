/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.Dao;

import com.entidad.Usuarios;
import com.interfaces.UsuariosInterface;
import com.util.HibernateUtil;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

/**
 *
 * @author KIVANDY
 */
public class UsuariosDao implements UsuariosInterface{
    
    private Session sesion;
    private Transaction tx;

    @Override
    public boolean registrar(Usuarios usuario) throws Exception {
        sesion = null;
        tx = null;
        try {
            iniciaOperacion();
            sesion.save(usuario);
            this.tx.commit();
             FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "correcto:", "El registro se ha guardado correctamente"));
            return true;
        } catch (HibernateException he) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "error:", "No se ha podido guardar el registro" + he));           
            manejaExcepcion(he);
            throw he;
        } finally {
            sesion.close();
        }
    }

    @Override
    public List<Usuarios> verTodo() throws Exception {
        sesion = null;
        tx = null;
        try {
            iniciaOperacion();
            String hql = "from Usuarios";
            Query query = sesion.createQuery(hql);
            List<Usuarios> listaUsuarios = (List<Usuarios>) query.list();
            return listaUsuarios;
        } catch (HibernateException he) {
            manejaExcepcion(he);
            throw he;
        } finally {
            sesion.close();
        }
    }

    @Override
    public boolean editar(Usuarios usuario) throws Exception {
        sesion = null;
        tx = null;
        try {
            iniciaOperacion();
            sesion.update(usuario);
            this.tx.commit();
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "correcto:", "El registro se ha guardado correctamente"));
            return true;
        } catch (HibernateException he) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "error:", "No se ha podido guardar el registro" + he));
            manejaExcepcion(he);
            throw he;
        } finally {
            sesion.close();
        }
    }
    
    @Override
    public Usuarios verPorUsuario(int idUsuarios) throws Exception {
        sesion = null;
        tx = null;
        try {
            iniciaOperacion();
            String hql = "from Usuarios where idUsuarios=:idUsuarios";
            Query query = sesion.createQuery(hql);
            query.setParameter("idUsuarios", idUsuarios);
            Usuarios usuario = (Usuarios) query.uniqueResult();
            return usuario;
        } catch (HibernateException he) {
            manejaExcepcion(he);
            throw he;
        } finally {
            sesion.close();
        }
    }
    
    @Override
    public Usuarios verPorEmail(String nick) throws Exception {
        sesion = null;
        tx = null;
        try {
            iniciaOperacion();
            String hql = "from Usuarios u where u.usuarios=:usuarios";
            Query query = sesion.createQuery(hql);
            query.setParameter("usuarios", nick);
            Usuarios usuario = (Usuarios) query.uniqueResult();
            return usuario;
        } catch (HibernateException he) {
            manejaExcepcion(he);
            throw he;
        } finally {
            sesion.close();
        }
    }
    
    private void iniciaOperacion() throws HibernateException {
        sesion = HibernateUtil.getSessionFactory().openSession();
        tx = sesion.beginTransaction();
    }

    private void manejaExcepcion(HibernateException he) throws HibernateException {
        tx.rollback();
        throw new HibernateException("Ocurrió un error en la capa de acceso a datos", he);
    }

}
