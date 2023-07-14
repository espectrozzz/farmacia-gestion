package com.farmacia.uth.views.movimientos;

import com.farmacia.uth.data.controller.MovimientoInteractor;
import com.farmacia.uth.data.controller.MovimientoInteractorImpl;
import com.farmacia.uth.data.entity.Movimiento;
import com.farmacia.uth.views.MainLayout;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.checkbox.CheckboxGroup;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.dependency.Uses;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.Icon;
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

    private Filters filters;
    private MovimientoInteractor controlador;

    public MovimientosView() {
        setSizeFull();
        addClassNames("movimientos-view");
        this.controlador = new MovimientoInteractorImpl(this);
        filters = new Filters(() -> refreshGrid());
        VerticalLayout layout = new VerticalLayout(createMobileFilters(), filters, createGrid());
        layout.setSizeFull();
        layout.setPadding(false);
        layout.setSpacing(false);
        this.controlador.consultarMovimientos();
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
        mobileFilters.addClickListener(e -> {
            if (filters.getClassNames().contains("visible")) {
                filters.removeClassName("visible");
                mobileIcon.getElement().setAttribute("icon", "lumo:plus");
            } else {
                filters.addClassName("visible");
                mobileIcon.getElement().setAttribute("icon", "lumo:minus");
            }
        });
        return mobileFilters;
    }

    public static class Filters extends Div implements Specification<Movimiento> {

        private final TextField id_movimiento = new TextField("Id Movimiento");
        private final TextField id_producto = new TextField("Id Producto");
        private final DatePicker startDate = new DatePicker("Fecha");
        private final DatePicker endDate = new DatePicker();
        private final ComboBox<String> movimiento = new ComboBox<>("Movimiento");

        public Filters(Runnable onSearch) {

            setWidthFull();
            addClassName("filter-layout");
            addClassNames(LumoUtility.Padding.Horizontal.LARGE, LumoUtility.Padding.Vertical.MEDIUM,
                    LumoUtility.BoxSizing.BORDER);
            id_movimiento.setPlaceholder("1"); id_movimiento.setPrefixComponent(LineAwesomeIcon.FILE_ALT_SOLID.create());
            id_producto.setPlaceholder("1"); id_producto.setPrefixComponent(LineAwesomeIcon.BOXES_SOLID.create());
            movimiento.setItems("ingreso", "egreso"); movimiento.setPrefixComponent(LineAwesomeIcon.TRUCK_MOVING_SOLID.create());

            // Action buttons
            Button resetBtn = new Button("Reiniciar");
            resetBtn.addThemeVariants(ButtonVariant.LUMO_TERTIARY);
            resetBtn.addClickListener(e -> {
                id_movimiento.clear();
                id_producto.clear();
                startDate.clear();
                endDate.clear();
                movimiento.clear();
            });
            Button searchBtn = new Button("Buscar");
            searchBtn.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
            searchBtn.addClickListener(e -> onSearch.run());

            Div actions = new Div(resetBtn, searchBtn);
            actions.addClassName(LumoUtility.Gap.SMALL);
            actions.addClassName("actions");

            add(id_movimiento, id_producto, createDateRangeFilter(), movimiento, actions);
        }

        private Component createDateRangeFilter() {
            startDate.setPlaceholder("Desde");

            endDate.setPlaceholder("Hasta");

            // For screen readers
            setAriaLabel(startDate, "From date");
            setAriaLabel(endDate, "To date");

            FlexLayout dateRangeComponent = new FlexLayout(startDate, new Text(" – "), endDate);
            dateRangeComponent.setAlignItems(FlexComponent.Alignment.BASELINE);
            dateRangeComponent.addClassName(LumoUtility.Gap.XSMALL);

            return dateRangeComponent;
        }

        private void setAriaLabel(DatePicker datePicker, String label) {
            datePicker.getElement().executeJs("const input = this.inputElement;" //
                    + "input.setAttribute('aria-label', $0);" //
                    + "input.removeAttribute('aria-labelledby');", label);
        }

        @Override
        public Predicate toPredicate(Root<Movimiento> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
            List<Predicate> predicates = new ArrayList<>();
            return criteriaBuilder.and(predicates.toArray(Predicate[]::new));
        }

    }

    private Component createGrid() {
        grid.addColumn("id_mov").setAutoWidth(true);
        grid.addColumn("tipo_mov").setAutoWidth(true);
        grid.addColumn("cantidad").setAutoWidth(true);
        grid.addColumn("fecha_mov").setAutoWidth(true);
        grid.addColumn("usuario").setAutoWidth(true);
        grid.addColumn("id_prod").setAutoWidth(true);
        grid.addThemeVariants(GridVariant.LUMO_NO_BORDER);
        grid.addClassNames(LumoUtility.Border.TOP, LumoUtility.BorderColor.CONTRAST_10);

        return grid;
    }

    private void refreshGrid() {
        grid.getDataProvider().refreshAll();
    }

	@Override
	public void refrescarGridMovimientos(List<Movimiento> movimiento) {
		List<Movimiento> items = movimiento;
		grid.setItems(items);
	}

	@Override
	public void getHasMore(boolean value) {
		// TODO Auto-generated method stub
		
	}

}
