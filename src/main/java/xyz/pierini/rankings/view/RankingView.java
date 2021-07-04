package xyz.pierini.rankings.view;

import org.springframework.beans.factory.annotation.Autowired;

import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.Grid.SelectionMode;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.data.provider.CallbackDataProvider;
import com.vaadin.flow.data.provider.DataProvider;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouteAlias;

import xyz.pierini.rankings.model.Player;
import xyz.pierini.rankings.service.PlayerService;
import xyz.pierini.rankings.view.layout.MainLayout;

@Route(value = "ranking", layout = MainLayout.class)
@RouteAlias(value = "", layout = MainLayout.class)
@PageTitle("Beach Tennis Ranking")
public class RankingView extends HorizontalLayout {

	@Autowired
	private PlayerService playerService;

	private Grid<Player> grid = new Grid<>(Player.class);

	public RankingView() {
		addClassName("ranking-view");

		setSizeFull();
		configureGrid();

		add(grid);
	}

	private void configureGrid() {
		grid.addClassName("ranking-grid");
		grid.setHeightFull();
		grid.addThemeVariants(GridVariant.LUMO_NO_BORDER, GridVariant.LUMO_NO_ROW_BORDERS,
				GridVariant.LUMO_ROW_STRIPES);
		grid.setSelectionMode(SelectionMode.NONE);
		grid.setMultiSort(true);

		CallbackDataProvider<Player, Void> provider = DataProvider.fromCallbacks(query -> {
			return playerService.fetchForGrid(query.getOffset(), query.getLimit(), query.getSortOrders()).stream();
		}, query -> playerService.countForGrid());
		grid.setDataProvider(provider);

		grid.removeAllColumns();
		grid.addColumn(item -> "").setKey("rowIndex");

		grid.addAttachListener(event -> {
			grid.getColumnByKey("rowIndex").getElement().executeJs(
					"this.renderer = function(root, column, rowData) {root.textContent = rowData.index + 1}");
		});
		grid.addColumn(Player::getFullName, "fullName").setHeader("Nome");
		grid.addColumn(Player::getCityCode, "cityCode").setHeader("Provincia");
		grid.addColumn(p -> p.getPreviousRanking().getValue(), "previousRanking").setHeader("2020");
		grid.addColumn(p -> p.getCurrentRanking().getValue(), "currentRanking").setHeader("2021");
		grid.addColumn(Player::getNote).setHeader("Note");
		grid.addColumn(Player::getPoints, "points").setHeader("Punti");

	}

}
