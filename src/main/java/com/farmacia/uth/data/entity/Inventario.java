	package com.farmacia.uth.data.entity;

public class Inventario {

	private int id_prod;
	private String nombre_medicamento;
	private String nombre_farmacia;
	private int precio_venta;
	private int stock_inicial;
	private String usuario;
	private String fecha_creacion;
	public int getId_prod() {
		return id_prod;
	}
	public void setId_prod(int id_prod) {
		this.id_prod = id_prod;
	}
	public String getNombre_medicamento() {
		return nombre_medicamento;
	}
	public void setNombre_medicamento(String nombre_medicamento) {
		this.nombre_medicamento = nombre_medicamento;
	}
	public String getNombre_farmacia() {
		return nombre_farmacia;
	}
	public void setNombre_farmacia(String nombre_farmacia) {
		this.nombre_farmacia = nombre_farmacia;
	}
	public int getPrecio_venta() {
		return precio_venta;
	}
	public void setPrecio_venta(int precio_venta) {
		this.precio_venta = precio_venta;
	}
	public int getStock_inicial() {
		return stock_inicial;
	}
	public void setStock_inicial(int stock_inicial) {
		this.stock_inicial = stock_inicial;
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
