package com.farmacia.uth.views.movimientos;

import com.farmacia.uth.data.controller.MovimientoInteractor;
import com.farmacia.uth.data.controller.MovimientoInteractorImpl;
import com.farmacia.uth.data.entity.Movimiento;
import com.farmacia.uth.data.entity.Productos;
import com.farmacia.uth.views.MainLayout;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.checkbox.CheckboxGroup;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.textfield.NumberField;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.dependency.Uses;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.FlexLayout;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.spring.data.VaadinSpringDataHelpers;
import com.vaadin.flow.theme.lumo.LumoUtility;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Expression;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.vaadin.lineawesome.LineAwesomeIcon;

@PageTitle("Movimientos")
@Route(value = "movimientos", layout = MainLayout.class)
@Uses(Icon.class)
public class MovimientosView extends Div implements MovimientosViewModel{

    private Grid<Movimiento> grid = new Grid<>(Movimiento.class, false);
    private NumberField cantidad = new NumberField("Cantidad");
    private TextField usuario = new TextField("Usuario");
    private DatePicker startDate = new DatePicker("Fecha");
    private DatePicker endDate = new DatePicker();
    private ComboBox<String> cboMovimiento = new ComboBox<>("Movimiento");
    private ComboBox<Productos> cboProductos = new ComboBox<>("Productos"); 
    
    private MovimientoInteractor controlador;
    private List<Productos> productos;
    private List<Movimiento> movimientos;
    private Movimiento movimiento;

    public MovimientosView() {
        setSizeFull();
        addClassNames("movimientos-view");
        this.controlador = new MovimientoInteractorImpl(this);
        this.controlador.consultarMovimientos();
        this.controlador.consultarProductos();
        VerticalLayout layout = new VerticalLayout(createMobileFilters(), createForm(), createGrid());
        layout.setSizeFull();
        layout.setPadding(false);
        layout.setSpacing(false);

        add(layout);
    }

    private HorizontalLayout createMobileFilters() {
        // Mobile version
        HorizontalLayout mobileFilters = new HorizontalLayout();
        mobileFilters.setWidthFull();
        mobileFilters.addClassNames(LumoUtility.Padding.MEDIUM, LumoUtility.BoxSizing.BORDER,
                LumoUtility.AlignItems.CENTER);
        mobileFilters.addClassName("mobile-filters");

        Icon mobileIcon = new Icon("lumo", "plus");
        Span filtersHeading = new Span("Filters");
        mobileFilters.add(mobileIcon, filtersHeading);
        mobileFilters.setFlexGrow(1, filtersHeading);
        return mobileFilters;
    }

    public Div createForm() {
    	Div container = new Div();
        setWidthFull();
        addClassName("filter-layout");
        addClassNames(LumoUtility.Padding.Horizontal.LARGE, LumoUtility.Padding.Vertical.MEDIUM,
                LumoUtility.BoxSizing.BORDER);
        cantidad.setPlaceholder("1"); cantidad.setPrefixComponent(LineAwesomeIcon.BOXES_SOLID.create());
        usuario.setPlaceholder("Grupo 4"); usuario.setPrefixComponent(LineAwesomeIcon.USER_ALT_SOLID.create());
        cboMovimiento.setItems("ingreso", "egreso"); cboMovimiento.setPrefixComponent(LineAwesomeIcon.TRUCK_MOVING_SOLID.create());
        cboProductos.setItems(this.productos); cboProductos.setItemLabelGenerator(Productos::getNombre_med);
        startDate.setValue(LocalDate.now()); startDate.setReadOnly(true);
        // Action buttons
        Button resetBtn = new Button("Reiniciar");
        resetBtn.addThemeVariants(ButtonVariant.LUMO_TERTIARY);
        resetBtn.addClickListener(e -> {
            usuario.clear();
            cantidad.clear();
            startDate.clear();
            cboMovimiento.clear();
        });
        Button searchBtn = new Button("Guardar");
        searchBtn.addClickListener(event -> {
            usuario.clear();
            cantidad.clear();
            startDate.clear();
            cboMovimiento.clear();
        	createMovimiento();
        });
        searchBtn.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        Button reportBtn = new Button("Generar Reporte");
        reportBtn.addThemeVariants(ButtonVariant.MATERIAL_CONTAINED);
        
        Div actions = new Div(resetBtn, searchBtn, reportBtn);
        actions.addClassName(LumoUtility.Gap.SMALL);
        actions.addClassName("actions");

        add(cboMovimiento, cboProductos, cantidad, usuario, startDate, actions);
        return container;
    }
    
    private Component createGrid() {
        grid.addColumn("id_mov").setAutoWidth(true).setHeader("Id Movimiento");
        grid.addColumn("tipo_mov").setAutoWidth(true).setHeader("Tipo Movimiento");
        grid.addColumn("cantidad").setAutoWidth(true).setHeader("Cantidad");
        grid.addColumn("fecha_mov").setAutoWidth(true).setHeader("Fecha Movimiento");
        grid.addColumn("usuario").setAutoWidth(true).setHeader("Usuario");
        grid.addColumn("id_prod").setAutoWidth(true).setHeader("Id Producto");
        grid.addThemeVariants(GridVariant.LUMO_NO_BORDER);
        grid.addClassNames(LumoUtility.Border.TOP, LumoUtility.BorderColor.CONTRAST_10);

        return grid;
    }

    private void refreshGrid() {
        grid.getDataProvider().refreshAll();
    }
    
    private void createMovimiento() {
    	if(this.movimiento == null) {
    		this.movimiento = new Movimiento();
    		this.movimiento.setTipo_mov(this.cboMovimiento.getValue());
    		this.movimiento.setId_prod(this.cboProductos.getValue().getId_prod());
    		this.movimiento.setCantidad(this.cantidad.getValue().intValue());
    		this.movimiento.setUsuario(this.usuario.getValue());
    		this.controlador.insertMovimiento(this.movimiento);
    		this.controlador.consultarMovimientos();
    	}
    }

    public void populateForm(Movimiento value) {
    	this.movimiento = value;
    }
    
	@Override
	public void refrescarGridMovimientos(List<Movimiento> movimiento) {
		this.movimientos = movimiento;
		grid.setItems(this.movimientos);
	}

	@Override
	public void getHasMore(boolean value) {
	}

	@Override
	public void consultarProductos(List<Productos> producto) {
		this.productos = producto;
	}

	@Override
	public void showMsgInsert(boolean value) {
		String msg = "Registro creado correctamente";
		if(!value) {
			msg = "Error al intentar crear registro";
		}
		Notification.show(msg);
		
	}

}
