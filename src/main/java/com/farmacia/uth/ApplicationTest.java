package com.farmacia.uth;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Collection;
import java.util.List;
import java.util.ArrayList;

import com.farmacia.uth.data.controller.FarmaciaInteractor;
import com.farmacia.uth.data.controller.FarmaciaInteractorImpl;
import com.farmacia.uth.data.controller.InventoryInteractor;
import com.farmacia.uth.data.controller.InventoryInteractorImpl;
import com.farmacia.uth.data.controller.MedicamentoInteractor;
import com.farmacia.uth.data.controller.MedicamentoInteractorImpl;
import com.farmacia.uth.data.controller.MovimientoInteractor;
import com.farmacia.uth.data.controller.MovimientoInteractorImpl;
import com.farmacia.uth.data.controller.ProveedorInteractor;
import com.farmacia.uth.data.controller.ProveedorInteractorImpl;
import com.farmacia.uth.data.entity.Farmacia;
import com.farmacia.uth.data.entity.Inventario;
import com.farmacia.uth.data.entity.Medicamento;
import com.farmacia.uth.data.entity.Movimiento;
import com.farmacia.uth.data.entity.Proveedor;
import com.farmacia.uth.views.farmacias.FarmaciasViewModel;
import com.farmacia.uth.views.medicamentos.MedicamentosViewModel;
import com.farmacia.uth.views.movimientos.MovimientosViewModel;
import com.farmacia.uth.views.productos.InventarioViewModel;
import com.farmacia.uth.views.proveedores.ProveedorViewModel;
import com.farmacia.uth.views.proveedores.ProveedoresView;

import org.junit.After;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

class ApplicationTest implements ProveedorViewModel, FarmaciasViewModel, MedicamentosViewModel, MovimientosViewModel, InventarioViewModel {

	private List<Proveedor> proveedores = new ArrayList<>();
	private ProveedorInteractor controladorProveedor;
	private List<Farmacia> farmacias = new ArrayList<>();
	private FarmaciaInteractor controladorFarmacia;
	private List<Medicamento> medicamentos = new ArrayList<>();
	private MedicamentoInteractor controladorMedicamento;
	private boolean hasMore;
	private MovimientoInteractor controladorMovimiento;
	private List<Inventario> inventario = new ArrayList<>();
	private InventoryInteractor controladorInventario;
	
	@Before
	public void initConf() {System.out.println("Iniciando Test");}
	@Test
	public void testProveedores() {
        	this.controladorProveedor = new ProveedorInteractorImpl(this);
        	this.controladorProveedor.consultarProveedores();
			assertTrue(proveedores != null);
			System.out.println("Test pasado");
	}
	@Test
	public void testFarmacias() {
		this.controladorFarmacia = new FarmaciaInteractorImpl(this);
		this.controladorFarmacia.consultarFarmacias();
		assertNotNull(farmacias);
	}
	@Test
	public void testMedicamentos() {
		this.controladorMedicamento = new MedicamentoInteractorImpl(this);
		this.controladorMedicamento.consultarMedicamentos();
		assertNull(medicamentos);
	}
	@Test
	public void testMovimientos() {
		this.controladorMovimiento = new MovimientoInteractorImpl(this);
		this.controladorMovimiento.consultarMovimientos();
		assertFalse(hasMore);
	}
	@Test
	public void testInventario() {
		this.controladorInventario = new InventoryInteractorImpl(this);
		this.controladorInventario.consultarInventario();
		assertEquals(inventario.get(1).getNombre_medicamento(), "Luivac");
	}
	@After
	public void endConf() {System.out.println("Finalizando Test");}
	
	
	
	
	@Override
	public void refrescarGridProveedores(List<Proveedor> proveedor) {
		this.proveedores = proveedor;
	}
	@Override
	public void refrescarGridInventario(List<Inventario> inventario) {
		this.inventario = inventario;
	}
	@Override
	public void refrescarGridMovimientos(List<Movimiento> movimiento) {
	}
	@Override
	public void refrescarGridMedicamentos(List<Medicamento> medicamento) {
		this.medicamentos = medicamento;
	}
	@Override
	public void refrescarGridFarmacias(List<Farmacia> farmacia) {
		this.farmacias = farmacia;
	}
	@Override
	public void getHasMore(boolean value) {
		this.hasMore = value;
	}
	
}
