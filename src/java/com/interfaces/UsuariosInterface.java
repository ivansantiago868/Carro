package com.interfaces;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import com.entidad.Usuarios;
import java.util.List;
/**
 *
 * @author KIVANDY
 */
public interface UsuariosInterface {
    
    public boolean registrar(Usuarios usuario) throws Exception;
    public List<Usuarios> verTodo()throws Exception;
    public Usuarios verPorUsuario (int idUsuarios)throws Exception; 
    public boolean editar(Usuarios usuario)throws Exception; 
    public Usuarios verPorEmail(String usuarios)throws Exception;
}
