package com.farmacia.uth.views.inventario;

import com.farmacia.uth.data.controller.InventoryInteractor;
import com.farmacia.uth.data.controller.InventoryInteractorImpl;
import com.farmacia.uth.data.entity.Inventario;
import com.farmacia.uth.views.MainLayout;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.router.AfterNavigationEvent;
import com.vaadin.flow.router.AfterNavigationObserver;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.ArrayList;

import org.vaadin.lineawesome.LineAwesomeIcon;

@PageTitle("Inventario")
@Route(value = "inventario", layout = MainLayout.class)
public class InventarioView extends Div implements InventarioViewModel {

	private Grid<Inventario> grid = new Grid<>(Inventario.class, false);
    private Button search = new Button("Buscar");
    private Button reset = new Button("Reiniciar");
    private TextField idMed = new TextField("ID del Medicamento");
    private TextField idFarm = new TextField("ID de la Farmacia");
    private List<Inventario> inventario = new ArrayList<>();
    
    private InventoryInteractor controlador;
    public InventarioView() {
        addClassName("inventario-view");
        setSizeFull();
        this.controlador = new InventoryInteractorImpl(this);
        this.controlador.consultarInventario();
        grid.setHeight("100%");
        grid.addThemeVariants(GridVariant.LUMO_NO_BORDER, GridVariant.LUMO_NO_ROW_BORDERS);
        grid.addComponentColumn(producto -> createCard(producto));
        add(createBody(), grid);
    }

    private Div createBody(){
    	Div containerBody = new Div();
    	VerticalLayout bodyLayout = new VerticalLayout();
    	HorizontalLayout formLayout = new HorizontalLayout();
    	idMed.setPrefixComponent(LineAwesomeIcon.TABLETS_SOLID.create());idMed.setHelperText("Ingrese el id del medicamento"); idMed.setPlaceholder("1");
    	idFarm.setPrefixComponent(LineAwesomeIcon.HOSPITAL_SOLID.create()); idFarm.setPlaceholder("1"); idFarm.setHelperText("Ingrese el id de la farmacia");
    	formLayout.add(idMed, idFarm);
    	HorizontalLayout buttonLayout = new HorizontalLayout();
    	search.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
    	reset.addThemeVariants(ButtonVariant.LUMO_TERTIARY_INLINE);
    	buttonLayout.add(search, reset);
    	bodyLayout.add(formLayout, buttonLayout);
    	containerBody.add(bodyLayout);
    	return containerBody;
    }
    
    private HorizontalLayout createCard(Inventario producto) {
        HorizontalLayout card = new HorizontalLayout();
        card.addClassName("card");
        card.setSpacing(false);
        card.getThemeList().add("spacing-s");

        Image image = new Image();
        //image.setSrc(person.getImage());
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
        card.add(image, description);
        return card;
    }

	@Override
	public void refrescarGridInventario(List<Inventario> inventario) {
	    Collection<Inventario> items  = inventario;
		grid.setItems(items);
		this.inventario = inventario;
	}

}