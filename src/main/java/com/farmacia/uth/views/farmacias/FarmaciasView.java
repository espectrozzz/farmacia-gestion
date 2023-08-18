package com.farmacia.uth.views.farmacias;

import com.farmacia.uth.data.controller.FarmaciaInteractor;
import com.farmacia.uth.data.controller.FarmaciaInteractorImpl;
import com.farmacia.uth.data.entity.Farmacia;
import com.farmacia.uth.data.entity.FarmaciasDataReport;
import com.farmacia.uth.data.entity.InventoryDataReport;
import com.farmacia.uth.data.service.ReportGenerator;
import com.farmacia.uth.views.MainLayout;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.html.Anchor;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.splitlayout.SplitLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.data.renderer.ComponentRenderer;
import com.vaadin.flow.component.confirmdialog.ConfirmDialog;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.vaadin.lineawesome.LineAwesomeIcon;

@PageTitle("Farmacias")
@Route(value = "farmacias/:farmaciaID?/:action?(edit)", layout = MainLayout.class)
public class FarmaciasView extends Div implements BeforeEnterObserver, FarmaciasViewModel {

    private final String FARMACIA_ID = "farmaciaID";
    private final String FARMACIA_EDIT_ROUTE_TEMPLATE = "farmacias/%s/edit";

    private final Grid<Farmacia> grid = new Grid<>(Farmacia.class, false);

    private TextField nombre;
    private TextArea descripcion;
    private TextField direccion;
    private TextField correo;
    private TextField telefono;
    private TextField user;
    private DatePicker fechaReg;
    private List<Farmacia> farmacias;
    

    private final Button cancel = new Button("Cancelar");
    private final Button save = new Button("Guardar");
    private final Button report = new Button("Generar Reporte");
    
    private Farmacia farmacia;
    private FarmaciaInteractor controlador;

    public FarmaciasView() {
        addClassNames("farmacias-view");
        farmacias = new ArrayList<>();
        this.controlador = new FarmaciaInteractorImpl(this);
        // Create UI
        SplitLayout splitLayout = new SplitLayout();
        createGridLayout(splitLayout);
        createEditorLayout(splitLayout);
        add(splitLayout);
        this.controlador.consultarFarmacias();
        cancel.addClickListener(e -> {
            clearForm();
            refreshGrid();
        });
        save.addClickListener(e -> {
        	createFarm();
        });
        report.addClickListener(e -> {
        	generarReporte();
        });
    }

    private void generarReporte() {
    	ReportGenerator generador = new ReportGenerator();
    	Map<String, Object> parametros = new HashMap<>();
    	
    	parametros.put("LOGO_DIR", "img/icons-farmacia.png");
    	
    	FarmaciasDataReport datasourse = new FarmaciasDataReport();
    	datasourse.setData(this.farmacias);
		boolean generado = generador.reportGeneratorPDF("farmacias-report", parametros, datasourse);
		if(generado) {
			String ubicacion = generador.getRute();
			Anchor url = new Anchor(ubicacion, "Abrir reporte PDF");
			url.setTarget("_blank");
			Notification notificacion = new Notification(url);
			notificacion.addThemeVariants(NotificationVariant.LUMO_SUCCESS);
			notificacion.setDuration(20000);
			notificacion.open();
		}else {
			Notification notificacion = new Notification("Error al generar reporte");
			notificacion.addThemeVariants(NotificationVariant.LUMO_ERROR);
			notificacion.setDuration(10000);
			notificacion.open();
		}
		this.farmacias.clear();
		
	}

	public void createFarm() {
    	if(this.farmacia == null) {
    		this.farmacia = new Farmacia();
    		this.farmacia.setNombre_farm(nombre.getValue().toString());
    		this.farmacia.setDescripcion_farm(descripcion.getValue().toString());
    		this.farmacia.setDireccion_farm(direccion.getValue().toString());
    		this.farmacia.setCorreo_farm(correo.getValue().toString());
    		this.farmacia.setTelefono_farm(telefono.getValue().toString());
    		this.farmacia.setUsuario(user.getValue().toString());
    		this.controlador.crearFarmacia(farmacia);
    		refreshGrid();
    		clearForm();
    	}else {
    		this.farmacia.setNombre_farm(nombre.getValue().toString());
    		this.farmacia.setDescripcion_farm(descripcion.getValue().toString());
    		this.farmacia.setDireccion_farm(direccion.getValue().toString());
    		this.farmacia.setCorreo_farm(correo.getValue().toString());
    		this.farmacia.setTelefono_farm(telefono.getValue().toString());
    		this.farmacia.setUsuario(user.getValue().toString());
    		this.controlador.updateFarmacia(this.farmacia);
    		refreshGrid();
    		clearForm();
    	}
    }
    
    public void selectedDataGrid(Farmacia farmacia) {
    	if(farmacia != null) {
    		populateForm(farmacia);
    		nombre.setValue(this.farmacia.getNombre_farm()+"");
    	    descripcion.setValue(this.farmacia.getDescripcion_farm()+"");
    	    direccion.setValue(this.farmacia.getDireccion_farm()+"");
    	    correo.setValue(this.farmacia.getCorreo_farm()+"");
    	    telefono.setValue(this.farmacia.getTelefono_farm()+"");
    	    user.setValue(this.farmacia.getUsuario()+"");
    	    DateFormat formatDate = new SimpleDateFormat("yyyy-MM-dd");
    	    String fecha = formatDate.format(farmacia.getFecha_creacion());
    	    LocalDate fechaRegistro = LocalDate.parse(fecha);
    	    fechaReg.setValue(fechaRegistro);
    	}else {
    		clearForm();
    	}
    }

    @Override
    public void beforeEnter(BeforeEnterEvent event) {
        Optional<Long> farmaciaId = event.getRouteParameters().get(FARMACIA_ID).map(Long::parseLong);
        if (farmaciaId.isPresent()) {
        }
    }

    private void createEditorLayout(SplitLayout splitLayout) {
        Div editorLayoutDiv = new Div();
        editorLayoutDiv.setClassName("editor-layout");

        Div editorDiv = new Div();
        editorDiv.setClassName("editor");
        editorLayoutDiv.add(editorDiv);

        H3 header = new H3("Informacion de Farmacia"); header.addClassName("text-align-center");
        FormLayout formLayout = new FormLayout();
        nombre = new TextField("Nombre"); nombre.setPrefixComponent(LineAwesomeIcon.HOSPITAL_ALT_SOLID.create());
        descripcion = new TextArea("Descripcion"); descripcion.setPrefixComponent(LineAwesomeIcon.STREET_VIEW_SOLID.create()); descripcion.setHelperText("Ingrese la descripcion visual del establecimiento");
        direccion = new TextField("Direccion"); direccion.setPrefixComponent(LineAwesomeIcon.MAP_MARKED_ALT_SOLID.create()); direccion.setHelperText("Ingrese la direccion del establecimiento");
        correo = new TextField("Correo"); correo.setPrefixComponent(LineAwesomeIcon.AT_SOLID.create()); correo.setPlaceholder("example@gmail.com");
        Div prefixNumber = new Div(); prefixNumber.setText("+504");
        telefono = new TextField("Telefono"); telefono.setPrefixComponent(prefixNumber); telefono.setSuffixComponent(LineAwesomeIcon.PHONE_SOLID.create()); telefono.setPlaceholder("9598-4316");
        user = new TextField("Usuario"); user.setPrefixComponent(LineAwesomeIcon.USER_CIRCLE_SOLID.create());
        fechaReg = new DatePicker("Fecha de Registro"); fechaReg.setReadOnly(true); fechaReg.setValue(LocalDate.now()); fechaReg.setPrefixComponent(LineAwesomeIcon.CALENDAR_CHECK_SOLID.create());
        formLayout.add(nombre, descripcion, direccion, correo, telefono, user, fechaReg);

        editorDiv.add(header, formLayout);
        createButtonLayout(editorLayoutDiv);
        splitLayout.addToSecondary(editorLayoutDiv);
    }

    private void createButtonLayout(Div editorLayoutDiv) {
        HorizontalLayout buttonLayout = new HorizontalLayout();
        buttonLayout.setClassName("button-layout");
        cancel.addThemeVariants(ButtonVariant.LUMO_TERTIARY);
        save.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        buttonLayout.add(save, cancel);
        editorLayoutDiv.add(buttonLayout);
    }

    private void createGridLayout(SplitLayout splitLayout) {
        Div wrapper = new Div();
        wrapper.setClassName("grid-wrapper");
        // Configure Grid
        grid.addColumn("id_far").setAutoWidth(true).setHeader("ID");
        grid.addColumn("nombre_farm").setAutoWidth(true).setHeader("Farmacia");
        grid.addColumn("descripcion_farm").setAutoWidth(true).setHeader("Descripcion");
        grid.addColumn("direccion_farm").setAutoWidth(true).setHeader("Direccion");
        grid.addColumn("correo_farm").setAutoWidth(true).setHeader("Correo Electronico");
        grid.addColumn("telefono_farm").setAutoWidth(true).setHeader("Telefono");
        grid.addColumn("usuario").setAutoWidth(true).setHeader("Usuario");
        grid.addColumn("fecha_creacion").setAutoWidth(true).setHeader("Fecha Registro");
        grid.addColumn(new ComponentRenderer<>(Button::new, (btn, farmacia) -> {
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
            		this.controlador.deleteFarmacia(farmacia.getId_far());
            		refreshGrid();
        		});
        		dialog.open();
        		});
        	btn.setIcon(new Icon(VaadinIcon.TRASH));
        })).setHeader("Manage");
        grid.addThemeVariants(GridVariant.LUMO_NO_BORDER);

        // when a row is selected or deselected, populate form
        grid.asSingleSelect().addValueChangeListener(event -> {
        	selectedDataGrid(event.getValue());
        });
        HorizontalLayout layoutActions = new HorizontalLayout();
        layoutActions.addClassName("button-layout");
        report.addThemeVariants(ButtonVariant.LUMO_ICON);
        layoutActions.add(report);
        splitLayout.addToPrimary(wrapper);
        wrapper.add(grid, layoutActions);
    }

    private void refreshGrid() {
    	this.controlador.consultarFarmacias();
    }

    private void clearForm() {
	    nombre.setValue("");
	    descripcion.setValue("");
	    direccion.setValue("");
	    correo.setValue("");
	    telefono.setValue("");
	    user.setValue("");
	    fechaReg.setValue(LocalDate.now());
        populateForm(null);
    }

    private void populateForm(Farmacia value) {
        this.farmacia = value;

    }

	@Override
	public void refrescarGridFarmacias(List<Farmacia> farmacia) {
		Collection<Farmacia> items = farmacia;
		grid.setItems(items);
		this.farmacias = farmacia;
		
	}
	@Override
	public void showMessageInsert(boolean value) {
		String msg = "Farmacia creado correctamente :3";
		if(!value) {
			msg = "Error al intentar crear. :c";
		}
		Notification.show(msg);
	}

	@Override
	public void showMessageUpdate(boolean value) {
		String msg = "Registro actualizado correctamente.";
		if(!value) {
			msg = "Error al intentar actualizar. :c";
		}
		Notification.show(msg);		
	}

	@Override
	public void showMessageDelete(boolean value) {
		String msg = "Registro eliminado correctamente";
		if(!value) {
			msg = "Error al intentar eliminar registro. :c";
		}
		Notification.show(msg);
	}
}
