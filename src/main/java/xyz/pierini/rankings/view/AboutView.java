package xyz.pierini.rankings.view;

import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

import xyz.pierini.rankings.view.layout.MainLayout;

@Route(value = "about", layout = MainLayout.class)
@PageTitle("About")
public class AboutView extends Div {

	public AboutView() {
        addClassName("about-view");
        add(new Text("Content placeholder"));
    }

}
