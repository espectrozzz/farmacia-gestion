package com.farmacia.uth.data.entity;

public class Proveedor {

	private int id;
	private String nombre;
    private String direccion;
    private String telefono;
    private String correo;
    private String usuario;
    private String creado;
    
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre_prov) {
		this.nombre = nombre_prov;
	}
	public String getDireccion() {
		return direccion;
	}
	public void setDireccion(String direccion_pro) {
		this.direccion = direccion_pro;
	}
	public String getTelefono() {
		return telefono;
	}
	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}
	public String getCorreo() {
		return correo;
	}
	public void setCorreo(String correo_pro) {
		this.correo = correo_pro;
	}
	public String getUsuario() {
		return usuario;
	}
	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}
	public String getCreado() {
		return creado;
	}
	public void setCreado(String fecha_creacion) {
		this.creado = fecha_creacion;
	}

    
}
