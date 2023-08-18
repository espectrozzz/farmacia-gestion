package com.farmacia.uth.views.inventario;

import com.farmacia.uth.data.entity.Farmacia;
import com.farmacia.uth.data.entity.Medicamento;
import com.farmacia.uth.data.controller.InventoryInteractor;
import com.farmacia.uth.data.controller.InventoryInteractorImpl;
import com.farmacia.uth.data.service.ReportGenerator;
import com.farmacia.uth.data.entity.Inventario;
import com.farmacia.uth.data.entity.InventoryDataReport;
import com.farmacia.uth.views.MainLayout;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.radiobutton.RadioButtonGroup;
import com.vaadin.flow.component.radiobutton.RadioGroupVariant;
import com.vaadin.flow.component.html.Anchor;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.router.AfterNavigationEvent;
import com.vaadin.flow.router.AfterNavigationObserver;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

import net.sf.jasperreports.engine.JRDataSource;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.ArrayList;

import org.vaadin.lineawesome.LineAwesomeIcon;

@PageTitle("Inventario")
@Route(value = "inventario", layout = MainLayout.class)
public class InventarioView extends Div implements InventarioViewModel {

	private Grid<Inventario> grid = new Grid<>(Inventario.class, false);
    private Button search = new Button("Generar Reporte");
    private Button reset = new Button("Reiniciar");
    private TextField idMed = new TextField("ID del Medicamento");
    private TextField idFarm = new TextField("ID de la Farmacia");
    private List<Inventario> inventario = new ArrayList<>();
    private ComboBox<Farmacia> cboFarmacia = new ComboBox<>("Farmacias");
    private List<Farmacia> farmacias;
    private ComboBox<Medicamento> cboMedicamentos = new ComboBox<>("Medicamentos");
    private List<Medicamento> medicamentos;
    private RadioButtonGroup<String> parametersReports = new RadioButtonGroup<>();
    private List<Inventario> dataInventory =  new ArrayList<>();
    
    private InventoryInteractor controlador;
    public InventarioView() {
        addClassName("inventario-view");
        setSizeFull();
        this.controlador = new InventoryInteractorImpl(this);
        this.controlador.consultarInventario();
        this.controlador.consultarFarmacias();
        this.controlador.consultarMedicamentos();;
        grid.setHeight("100%");
        grid.addThemeVariants(GridVariant.LUMO_NO_BORDER, GridVariant.LUMO_NO_ROW_BORDERS);
        grid.addComponentColumn(producto -> createCard(producto));
        add(createBody(), grid);
    }

    private Div createBody(){
    	Div containerBody = new Div();
    	VerticalLayout bodyLayout = new VerticalLayout();
    	HorizontalLayout formLayout = new HorizontalLayout();
    	cboMedicamentos.setPrefixComponent(LineAwesomeIcon.TABLE_SOLID.create()); cboMedicamentos.setItems(this.medicamentos); cboMedicamentos.setItemLabelGenerator(Medicamento::getNombre_med);
    	cboFarmacia.setPrefixComponent(LineAwesomeIcon.HOSPITAL_SOLID.create()); cboFarmacia.setItems(this.farmacias); cboFarmacia.setItemLabelGenerator(Farmacia::getNombre_farm);
    	formLayout.add(cboFarmacia, cboMedicamentos);
    	HorizontalLayout filtersRadioButton = new HorizontalLayout();
    	parametersReports.addThemeVariants(RadioGroupVariant.LUMO_HELPER_ABOVE_FIELD);
    	parametersReports.setLabel("Filtros");
    	parametersReports.setHelperText("Seleccione una de las opciones para parametrizar los reportes:");
    	parametersReports.setItems("Farmacia","Medicamento", "None");
    	parametersReports.setValue("None");
    	filtersRadioButton.add(parametersReports);
    	HorizontalLayout buttonLayout = new HorizontalLayout();
    	search.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
    	search.addClickListener(event -> {
    		generarReporte();
    	});
    	reset.addClickListener(event -> {
    		this.cboFarmacia.clear();
    		this.cboMedicamentos.clear();
    		parametersReports.setValue("None");
    	});
    	reset.addThemeVariants(ButtonVariant.LUMO_TERTIARY_INLINE);
    	buttonLayout.add(search, reset);
    	bodyLayout.add(formLayout, filtersRadioButton, buttonLayout);
    	containerBody.add(bodyLayout);
    	return containerBody;
    }
    
    private void generarReporte() {
    	ReportGenerator generador = new ReportGenerator();
    	Map<String, Object> parametros = new HashMap<>();
    	String filter = parametersReports.getValue();
    	InventoryDataReport datasourse = new InventoryDataReport();
    	if("Farmacia".equalsIgnoreCase(filter)) {
        	parametros.put("TIPO_REPORT", "img/icon-hospital.png");
        	parametros.put("DESCRIPCION_REPORT", "Reporte de inventario por farmacia: "+ cboFarmacia.getValue().getNombre_farm());
        	for(int i=0; i<this.inventario.size(); i++) {
        		if(this.inventario.get(i).getNombre_farmacia().equalsIgnoreCase(cboFarmacia.getValue().getNombre_farm())) {
        			this.dataInventory.add(this.inventario.get(i));
        		}
        	}
    	}else if("Medicamento".equalsIgnoreCase(filter)) {
        	parametros.put("TIPO_REPORT", "img/icon-medicamentos.png");
        	parametros.put("DESCRIPCION_REPORT", "Reporte de inventario por medicamento: "+ cboMedicamentos.getValue().getNombre_med());
        	for(int i=0; i<this.inventario.size(); i++) {
        		if(this.inventario.get(i).getNombre_medicamento().equalsIgnoreCase(cboMedicamentos.getValue().getNombre_med())) {
        			this.dataInventory.add(this.inventario.get(i));
        		}
        	}
    	}else if("None".equalsIgnoreCase(filter)){
        	parametros.put("TIPO_REPORT", "img/icon-all-registers.png");
        	parametros.put("DESCRIPCION_REPORT", "Reporte de inventario");
        	this.dataInventory.addAll(this.inventario);
    	}

    	datasourse.setData(this.dataInventory);
		boolean generado = generador.reportGeneratorPDF("inventory-report", parametros, datasourse);
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
		this.dataInventory.clear();
    }
    
    private HorizontalLayout createCard(Inventario producto) {
        HorizontalLayout card = new HorizontalLayout();
        card.addClassName("card");
        card.setSpacing(false);
        card.getThemeList().add("spacing-s");

        VerticalLayout description = new VerticalLayout();
        description.addClassName("description");
        description.setSpacing(false);
        description.setPadding(false);

        HorizontalLayout header = new HorizontalLayout();
        header.addClassName("header");
        header.setSpacing(false);
        header.getThemeList().add("spacing-s");
        Span name = new Span(producto.getNombre_medicamento()+"");
        name.addClassName("name");
        Span date = new Span(producto.getNombre_farmacia()+"");
        date.addClassName("date");
        header.add(name, date);

        Span post = new Span(producto.getDescripcion_med());
        post.addClassName("post");

        HorizontalLayout actions = new HorizontalLayout();
        actions.addClassName("actions");
        actions.setSpacing(false);
        actions.getThemeList().add("spacing-s");

        Component moneyIcon = LineAwesomeIcon.MONEY_BILL_ALT_SOLID.create();
        moneyIcon.addClassName("icon");
        Span price = new Span(producto.getPrecio_venta()+"");
        price.addClassName("likes");
        Component stockIcon = LineAwesomeIcon.BOX_SOLID.create();
        stockIcon.addClassName("icon");
        Span stock = new Span(producto.getStock_inicial()+"");
        stock.addClassName("comments");
        Component calendarIcon = LineAwesomeIcon.CALENDAR_ALT_SOLID.create();
        calendarIcon.addClassName("icon");
        Span calendar = new Span(producto.getFecha_creacion());
        calendar.addClassName("shares");

        actions.add(moneyIcon, price, stockIcon, stock, calendarIcon, calendar);

        description.add(header, post, actions);
        card.add(description);
        return card;
    }

	@Override
	public void refrescarGridInventario(List<Inventario> inventario) {
	    Collection<Inventario> items  = inventario;
		grid.setItems(items);
		this.inventario = inventario;
	}

	@Override
	public void getFarmacias(List<Farmacia> farmacias) {
		this.farmacias = farmacias;
	}

	@Override
	public void getMedicamentos(List<Medicamento> medicamentos) {
		this.medicamentos = medicamentos;
	}

}
