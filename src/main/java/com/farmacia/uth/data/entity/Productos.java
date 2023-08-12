package com.farmacia.uth.data.entity;

import java.util.Date;

public class Productos {
	int id_prod;
	int id_med;
	String nombre_med;
	int id_farm;
	String nombre_farm;
	double precio_venta;
	double stock_ini;
	String usuario;
	Date fecha_creacion;
	
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
	public String getNombre_med() {
		return nombre_med;
	}
	public void setNombre_med(String nombre_med) {
		this.nombre_med = nombre_med;
	}
	public int getId_farm() {
		return id_farm;
	}
	public void setId_farm(int id_farm) {
		this.id_farm = id_farm;
	}
	public String getNombre_farm() {
		return nombre_farm;
	}
	public void setNombre_farm(String nombre_farm) {
		this.nombre_farm = nombre_farm;
	}
	public double getPrecio_venta() {
		return precio_venta;
	}
	public void setPrecio_venta(double precio_venta) {
		this.precio_venta = precio_venta;
	}
	public double getStock_ini() {
		return stock_ini;
	}
	public void setStock_ini(double stock_ini) {
		this.stock_ini = stock_ini;
	}
	
	public String getUsuario() {
		return usuario;
	}
	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}
	public Date getFecha_creacion() {
		return fecha_creacion;
	}
	public void setFecha_creacion(Date fecha_creacion) {
		this.fecha_creacion = fecha_creacion;
	}
	
	
}
