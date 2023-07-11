package com.farmacia.uth.data.entity;


public class Movimiento{

	private int id_mov;
	private String tipo_mov;
	private int cantidad;
	private String fecha_mov;
	private String usuario;
	private int id_prod;
	public int getId_mov() {
		return id_mov;
	}
	public void setId_mov(int id_mov) {
		this.id_mov = id_mov;
	}
	public String getTipo_mov() {
		return tipo_mov;
	}
	public void setTipo_mov(String tipo_mov) {
		this.tipo_mov = tipo_mov;
	}
	public int getCantidad() {
		return cantidad;
	}
	public void setCantidad(int cantidad) {
		this.cantidad = cantidad;
	}
	public String getFecha_mov() {
		return fecha_mov;
	}
	public void setFecha_mov(String fecha_mov) {
		this.fecha_mov = fecha_mov;
	}
	public String getUsuario() {
		return usuario;
	}
	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}
	public int getId_prod() {
		return id_prod;
	}
	public void setId_prod(int id_prod) {
		this.id_prod = id_prod;
	}
    
	
}
