package com.entidad;
// Generated 21-nov-2015 9:14:52 by Hibernate Tools 4.3.1



/**
 * Compra generated by hbm2java
 */
public class Compra  implements java.io.Serializable {


     private Integer idCompra;
     private Articulo articulo;
     private Usuarios usuarios;
     private int cantidad;

    public Compra() {
    }

    public Compra(Articulo articulo, Usuarios usuarios, int cantidad) {
       this.articulo = articulo;
       this.usuarios = usuarios;
       this.cantidad = cantidad;
    }
   
    public Integer getIdCompra() {
        return this.idCompra;
    }
    
    public void setIdCompra(Integer idCompra) {
        this.idCompra = idCompra;
    }
    public Articulo getArticulo() {
        return this.articulo;
    }
    
    public void setArticulo(Articulo articulo) {
        this.articulo = articulo;
    }
    public Usuarios getUsuarios() {
        return this.usuarios;
    }
    
    public void setUsuarios(Usuarios usuarios) {
        this.usuarios = usuarios;
    }
    public int getCantidad() {
        return this.cantidad;
    }
    
    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }




}


