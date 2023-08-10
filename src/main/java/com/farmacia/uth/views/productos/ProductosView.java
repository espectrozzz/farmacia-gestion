package com.farmacia.uth.views.productos;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

import org.vaadin.lineawesome.LineAwesomeIcon;

import com.farmacia.uth.data.controller.ProductosInteractor;
import com.farmacia.uth.data.controller.ProductosInteractorImpl;
import com.farmacia.uth.data.entity.Farmacia;
import com.farmacia.uth.data.entity.Medicamento;
import com.farmacia.uth.data.entity.Productos;
import com.farmacia.uth.views.MainLayout;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.confirmdialog.ConfirmDialog;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.splitlayout.SplitLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.component.textfield.NumberField;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.data.renderer.ComponentRenderer;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.theme.lumo.LumoIcon;

@PageTitle("Productos")
@Route(value = "productos", layout = MainLayout.class)
public class ProductosView extends Div implements ProductosViewInterface{

    private final String PRODUCTO_ID = "productoID";
    private final String PRODUCTO_EDIT_ROUTE_TEMPLATE = "producto/%s/edit";

    public final Grid<Productos> grid = new Grid<>(Productos.class, false);
    private ComboBox<Medicamento> cboMedicamento = new ComboBox<>("Medicamentos:");;
    private ComboBox<Farmacia> cboFarmacia = new ComboBox<>("Farmacias:");
    private NumberField precio = new NumberField("Precio");
    private NumberField stock = new NumberField("Cantidad"); 
    private TextField usuario;
    private DatePicker creado;
    private Productos producto;
    private List<Productos> productos;
    private List<Medicamento> medicamentos;
    private List<Farmacia> farmacias;

    private final Button cancel = new Button("Cancelar");
    private final Button save = new Button("Guardar");

    private ProductosInteractor controlador;
    
	public ProductosView() {
		addClassNames("productos-view");
		this.controlador = new ProductosInteractorImpl(this);
		this.controlador.consultarProductos();
		this.controlador.consultarMedicamentos();
		this.controlador.consultarFarmacias();
		SplitLayout splitLayout = new SplitLayout();
		createGridLayout(splitLayout);
		createEditorLayout(splitLayout);
		add(splitLayout);
		
	}
    private void createEditorLayout(SplitLayout splitLayout) {
        Div editorLayoutDiv = new Div();
        editorLayoutDiv.setClassName("editor-layout");

        Div editorDiv = new Div();
        editorDiv.setClassName("editor");
        editorLayoutDiv.add(editorDiv);

        FormLayout formLayout = new FormLayout();
        H3 headerForm = new H3("Información del Producto"); headerForm.addClassName("text-center");
        cboMedicamento.setItems(this.medicamentos); cboMedicamento.setItemLabelGenerator(Medicamento::getNombre_med); cboMedicamento.setPrefixComponent(LineAwesomeIcon.TABLETS_SOLID.create());
        cboFarmacia.setItems(this.farmacias); cboFarmacia.setItemLabelGenerator(Farmacia::getNombre_farm); cboFarmacia.setPrefixComponent(LineAwesomeIcon.HOSPITAL_SYMBOL_SOLID.create());
        Div lemp = new Div(); lemp.setText("L.");
        precio.setPrefixComponent(lemp);
        Div units = new Div(); units.setText("uds");
        stock.setSuffixComponent(units);
        usuario = new TextField("Usuario"); usuario.setPrefixComponent(VaadinIcon.USERS.create());
        creado = new DatePicker("Fecha de Creacion"); creado.setValue(LocalDate.now()); creado.setReadOnly(true);
        formLayout.add(headerForm, cboMedicamento, cboFarmacia, precio, stock, usuario, creado);

        editorDiv.add(formLayout);
        createButtonLayout(editorLayoutDiv);

        splitLayout.addToSecondary(editorLayoutDiv);
    }

    private void createButtonLayout(Div editorLayoutDiv) {
        HorizontalLayout buttonLayout = new HorizontalLayout();
        buttonLayout.setClassName("button-layout");
        cancel.addThemeVariants(ButtonVariant.LUMO_TERTIARY);
        cancel.addClickListener(event -> {
        	clearForm();
        });
        save.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        save.addClickListener(event -> {
        	try {
				createUpdateProduct();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        });
        buttonLayout.add(save, cancel);
        editorLayoutDiv.add(buttonLayout);
    }

    private void createGridLayout(SplitLayout splitLayout) {
        Div wrapper = new Div();
        wrapper.setClassName("grid-wrapper");
        // Configure Grid
        grid.addColumn("id_prod").setAutoWidth(true).setHeader("Id");
        grid.addColumn("id_med").setAutoWidth(true).setHeader("ID Medicamento");
        grid.addColumn("nombre_med").setAutoWidth(true).setHeader("Medicamento");
        grid.addColumn("id_farm").setAutoWidth(true).setHeader("ID Farmacia");
        grid.addColumn("precio_venta").setAutoWidth(true).setHeader("Precio Venta");
        grid.addColumn("stock_ini").setAutoWidth(true).setHeader("Stock");
        grid.addColumn("usuario").setAutoWidth(true);
        grid.addColumn("fecha_creacion").setAutoWidth(true).setHeader("Fecha Registro");
        grid.addColumn(new ComponentRenderer<>(Button::new, (btn, producto) -> {
        	btn.addThemeVariants(ButtonVariant.LUMO_ICON,
                    ButtonVariant.LUMO_ERROR,
                    ButtonVariant.LUMO_TERTIARY);
        	btn.addClickListener(e -> {
        		ConfirmDialog dialog = new ConfirmDialog();
        		dialog.setHeader("¿Desea eliminar este registro?");
        		dialog.setText("Al aceptar, el registro se eliminará por completo, por lo que no sea podrá recuperar.");
        		dialog.setCancelText("Cancelar");
        		dialog.setCancelable(true);
        		dialog.setConfirmText("Borrar");
        		dialog.setConfirmButtonTheme("error primary");
        		dialog.addConfirmListener(event -> {

        		});
        		dialog.open();
        		});
        	btn.setIcon(new Icon(VaadinIcon.TRASH));
        })).setHeader("Manage");
        grid.asSingleSelect().addValueChangeListener(event ->{
        	if(event.getValue() != null) {
        		populateForm(event.getValue());
        	}else {
        		populateForm(null);
        	}
        });
        grid.addThemeVariants(GridVariant.LUMO_NO_BORDER);
        grid.setItems(productos);
        // when a row is selected or deselected, populate form

        splitLayout.addToPrimary(wrapper);
        wrapper.add(grid);
    }

    private void createUpdateProduct() throws IOException {
    	if(this.producto == null) {
    		this.producto = new Productos();
    		this.producto.setId_med(this.cboMedicamento.getValue().getId_med());
    		this.producto.setId_farm(this.cboFarmacia.getValue().getId_far());
    		this.producto.setPrecio_venta(this.precio.getValue());
    		this.producto.setStock_ini(this.stock.getValue().intValue());
    		this.producto.setUsuario(this.usuario.getValue());
    		this.controlador.crearProducto(producto);
    		this.controlador.consultarProductos();
    		clearForm();
    	}else {
    		this.producto.setId_med(this.cboMedicamento.getValue().getId_med());
    		this.producto.setId_farm(this.cboFarmacia.getValue().getId_far());
    		this.producto.setPrecio_venta(this.precio.getValue());
    		this.producto.setStock_ini(this.stock.getValue().intValue());
    		this.producto.setUsuario(this.usuario.getValue());
    		//this.controlador.updateProducto(producto);
    	}
    }

	private void clearForm() {
    	this.cboFarmacia.clear();
    	this.cboMedicamento.clear();
    	this.precio.setValue(null);
    	this.stock.setValue(null);
    	this.usuario.setValue("");
    	this.creado.setValue(LocalDate.now());
    	populateForm(null);
    }
    private void populateForm(Productos value) {
    	this.producto = value;
    }
	@Override
	public void consultarDataGrid(List<Productos> productos) {
		this.productos = productos;
		grid.setItems(this.productos);
	}

	@Override
	public void showMsgCreate(boolean value) {
		String msg = "Producto registrado correctamente.";
		if(!value) {
			msg = "Error al intentar registrar producto";
		}
		Notification.show(msg);
	}

	@Override
	public void showMsgUpdate(boolean value) {
	}

	@Override
	public void showMsgDelete(boolean value) {
	}
	
	@Override
	public void consultarMedicamentos(List<Medicamento> medicamentos) {
		this.medicamentos = medicamentos;
	}
	
	@Override
	public void consultarFarmcias(List<Farmacia> farmacias) {
		this.farmacias = farmacias;
	}

	
}
