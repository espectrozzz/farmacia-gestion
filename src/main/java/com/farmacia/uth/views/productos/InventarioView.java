package com.farmacia.uth.views.productos;

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
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.router.AfterNavigationEvent;
import com.vaadin.flow.router.AfterNavigationObserver;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import java.util.Arrays;
import java.util.List;

import org.vaadin.lineawesome.LineAwesomeIcon;

@PageTitle("Inventario")
@Route(value = "inventario", layout = MainLayout.class)
public class InventarioView extends Div implements AfterNavigationObserver {

    Grid<Person> grid = new Grid<>();
    Button search = new Button("Buscar");
    Button reset = new Button("Reiniciar");
    TextField idMed = new TextField("ID del Medicamento");
    TextField idFarm = new TextField("ID de la Farmacia");
    public InventarioView() {
        addClassName("productos-view");
        setSizeFull();
        grid.setHeight("100%");
        grid.addThemeVariants(GridVariant.LUMO_NO_BORDER, GridVariant.LUMO_NO_ROW_BORDERS);
        grid.addComponentColumn(person -> createCard(person));
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
    
    private HorizontalLayout createCard(Person person) {
        HorizontalLayout card = new HorizontalLayout();
        card.addClassName("card");
        card.setSpacing(false);
        card.getThemeList().add("spacing-s");

        Image image = new Image();
        image.setSrc(person.getImage());
        VerticalLayout description = new VerticalLayout();
        description.addClassName("description");
        description.setSpacing(false);
        description.setPadding(false);

        HorizontalLayout header = new HorizontalLayout();
        header.addClassName("header");
        header.setSpacing(false);
        header.getThemeList().add("spacing-s");

        Span name = new Span(person.getName());
        name.addClassName("name");
        Span date = new Span(person.getDate());
        date.addClassName("date");
        header.add(name, date);

        Span post = new Span(person.getPost());
        post.addClassName("post");

        HorizontalLayout actions = new HorizontalLayout();
        actions.addClassName("actions");
        actions.setSpacing(false);
        actions.getThemeList().add("spacing-s");

        Icon likeIcon = VaadinIcon.HEART.create();
        likeIcon.addClassName("icon");
        Span likes = new Span(person.getLikes());
        likes.addClassName("likes");
        Icon commentIcon = VaadinIcon.COMMENT.create();
        commentIcon.addClassName("icon");
        Span comments = new Span(person.getComments());
        comments.addClassName("comments");
        Icon shareIcon = VaadinIcon.CONNECT.create();
        shareIcon.addClassName("icon");
        Span shares = new Span(person.getShares());
        shares.addClassName("shares");

        actions.add(likeIcon, likes, commentIcon, comments, shareIcon, shares);

        description.add(header, post, actions);
        card.add(image, description);
        return card;
    }

    @Override
    public void afterNavigation(AfterNavigationEvent event) {

        // Set some data when this view is displayed.
        List<Person> persons = Arrays.asList( //
                createPerson("https://randomuser.me/api/portraits/men/42.jpg", "John Smith", "May 8",
                        "In publishing and graphic design, Lorem ipsum is a placeholder text commonly used to demonstrate the visual form of a document without relying on meaningful content (also called greeking).",
                        "1K", "500", "20"),
                createPerson("https://randomuser.me/api/portraits/women/42.jpg", "Abagail Libbie", "May 3",
                        "In publishing and graphic design, Lorem ipsum is a placeholder text commonly used to demonstrate the visual form of a document without relying on meaningful content (also called greeking).",
                        "1K", "500", "20"),
                createPerson("https://randomuser.me/api/portraits/men/24.jpg", "Alberto Raya", "May 3",

                        "In publishing and graphic design, Lorem ipsum is a placeholder text commonly used to demonstrate the visual form of a document without relying on meaningful content (also called greeking).",
                        "1K", "500", "20"),
                createPerson("https://randomuser.me/api/portraits/women/24.jpg", "Emmy Elsner", "Apr 22",

                        "In publishing and graphic design, Lorem ipsum is a placeholder text commonly used to demonstrate the visual form of a document without relying on meaningful content (also called greeking).",
                        "1K", "500", "20"),
                createPerson("https://randomuser.me/api/portraits/men/76.jpg", "Alf Huncoot", "Apr 21",

                        "In publishing and graphic design, Lorem ipsum is a placeholder text commonly used to demonstrate the visual form of a document without relying on meaningful content (also called greeking).",
                        "1K", "500", "20"),
                createPerson("https://randomuser.me/api/portraits/women/76.jpg", "Lidmila Vilensky", "Apr 17",

                        "In publishing and graphic design, Lorem ipsum is a placeholder text commonly used to demonstrate the visual form of a document without relying on meaningful content (also called greeking).",
                        "1K", "500", "20"),
                createPerson("https://randomuser.me/api/portraits/men/94.jpg", "Jarrett Cawsey", "Apr 17",
                        "In publishing and graphic design, Lorem ipsum is a placeholder text commonly used to demonstrate the visual form of a document without relying on meaningful content (also called greeking).",
                        "1K", "500", "20"),
                createPerson("https://randomuser.me/api/portraits/women/94.jpg", "Tania Perfilyeva", "Mar 8",

                        "In publishing and graphic design, Lorem ipsum is a placeholder text commonly used to demonstrate the visual form of a document without relying on meaningful content (also called greeking).",
                        "1K", "500", "20"),
                createPerson("https://randomuser.me/api/portraits/men/16.jpg", "Ivan Polo", "Mar 5",

                        "In publishing and graphic design, Lorem ipsum is a placeholder text commonly used to demonstrate the visual form of a document without relying on meaningful content (also called greeking).",
                        "1K", "500", "20"),
                createPerson("https://randomuser.me/api/portraits/women/16.jpg", "Emelda Scandroot", "Mar 5",

                        "In publishing and graphic design, Lorem ipsum is a placeholder text commonly used to demonstrate the visual form of a document without relying on meaningful content (also called greeking).",
                        "1K", "500", "20"),
                createPerson("https://randomuser.me/api/portraits/men/67.jpg", "Marcos Sá", "Mar 4",

                        "In publishing and graphic design, Lorem ipsum is a placeholder text commonly used to demonstrate the visual form of a document without relying on meaningful content (also called greeking).",
                        "1K", "500", "20"),
                createPerson("https://randomuser.me/api/portraits/women/67.jpg", "Jacqueline Asong", "Mar 2",

                        "In publishing and graphic design, Lorem ipsum is a placeholder text commonly used to demonstrate the visual form of a document without relying on meaningful content (also called greeking).",
                        "1K", "500", "20")

        );

        grid.setItems(persons);
    }

    private static Person createPerson(String image, String name, String date, String post, String likes,
            String comments, String shares) {
        Person p = new Person();
        p.setImage(image);
        p.setName(name);
        p.setDate(date);
        p.setPost(post);
        p.setLikes(likes);
        p.setComments(comments);
        p.setShares(shares);

        return p;
    }

}
