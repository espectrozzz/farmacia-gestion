package com.farmacia.uth.data.entity;

public class Proveedor {

	private int id;
	private String nombre_prov;
    private String direccion_pro;
    private String telefono;
    private String correo_pro;
    private String usuario;
    private String fecha_creacion;
    
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getNombre_prov() {
		return nombre_prov;
	}
	public void setNombre_prov(String nombre_prov) {
		this.nombre_prov = nombre_prov;
	}
	public String getDireccion_pro() {
		return direccion_pro;
	}
	public void setDireccion_pro(String direccion_pro) {
		this.direccion_pro = direccion_pro;
	}
	public String getTelefono() {
		return telefono;
	}
	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}
	public String getCorreo_pro() {
		return correo_pro;
	}
	public void setCorreo_pro(String correo_pro) {
		this.correo_pro = correo_pro;
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
