package xyz.pierini.rankings.view;

import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

import xyz.pierini.rankings.view.layout.MainLayout;

@Route(value = "graph", layout = MainLayout.class)
@PageTitle("Graph")
public class GraphView extends Div {

	public GraphView() {
        addClassName("graph-view");
        add(new Text("Content placeholder"));
    }

}
