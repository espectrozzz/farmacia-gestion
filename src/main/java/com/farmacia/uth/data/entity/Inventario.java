package com.farmacia.uth.data.entity;

public class Inventario {

	private int id_prod;
	private int id_med;
	private int id_farm;
	private int precio_venta;
	private int stock_ini;
	private String usuario;
	private String fecha_creacion;
	
	public int getId_prod() {
		return id_prod;
	}
	public void setId_prod(int id_prod) {
		this.id_prod = id_prod;
	}
	public int getId_med() {
		return id_med;
	}
	public void setId_med(int id_med) {
		this.id_med = id_med;
	}
	public int getId_farm() {
		return id_farm;
	}
	public void setId_farm(int id_farm) {
		this.id_farm = id_farm;
	}
	public int getPrecio_venta() {
		return precio_venta;
	}
	public void setPrecio_venta(int precio_venta) {
		this.precio_venta = precio_venta;
	}
	public int getStock_ini() {
		return stock_ini;
	}
	public void setStock_ini(int stock_ini) {
		this.stock_ini = stock_ini;
	}
	public String getUsuario() {
		return usuario;
	}
	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}
	public String getFecha_creacion() {
		return fecha_creacion;
	}
	public void setFecha_creacion(String fecha_creacion) {
		this.fecha_creacion = fecha_creacion;
	}
 
	
}
