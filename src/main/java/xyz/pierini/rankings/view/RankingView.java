package xyz.pierini.rankings.view;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.Grid.SelectionMode;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.provider.CallbackDataProvider;
import com.vaadin.flow.data.provider.DataProvider;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouteAlias;

import xyz.pierini.rankings.enumerator.GenderEnum;
import xyz.pierini.rankings.enumerator.PlayerRankingEnum;
import xyz.pierini.rankings.enumerator.YearEnum;
import xyz.pierini.rankings.model.Player;
import xyz.pierini.rankings.service.PlayerService;
import xyz.pierini.rankings.view.layout.MainLayout;

@Route(value = "ranking", layout = MainLayout.class)
@RouteAlias(value = "", layout = MainLayout.class)
@PageTitle("Beach Tennis Ranking")
public class RankingView extends HorizontalLayout {

	@Autowired
	private PlayerService playerService;
	
	private static final String FILTER_PLACEHOLDER = "Filtra";

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

		var filter = new Player();

		CallbackDataProvider<Player, Void> provider = DataProvider.fromCallbacks(query -> playerService
				.fetchForGrid(query.getOffset(), query.getLimit(), query.getSortOrders(), filter).stream(),
				query -> playerService.countForGrid(filter));
		grid.setDataProvider(provider);
		
		grid.removeAllColumns();
		Grid.Column<Player> indexCol = grid.addColumn(item -> "").setKey("rowIndex");

		grid.addAttachListener(event -> {
			grid.getColumnByKey("rowIndex").getElement().executeJs(
					"this.renderer = function(root, column, rowData) {root.textContent = rowData.index + 1}");
			
			setPlayerTotalCounter(filter, indexCol);
		});
		Grid.Column<Player> fullNameCol = grid.addColumn(Player::getFullName, "fullName").setHeader("Nome");
		Grid.Column<Player> cityCodeCol = grid.addColumn(Player::getCityCode, "cityCode").setHeader("Provincia");
		Grid.Column<Player> prevRankingCol = grid.addColumn(p -> p.getPreviousRanking().getValue(), "previousRanking").setHeader("2020");
		Grid.Column<Player> currRankingCol = grid.addColumn(p -> p.getCurrentRanking().getValue(), "currentRanking").setHeader("2021");
		Grid.Column<Player> noteCol = grid.addColumn(Player::getNote).setHeader("Note");
		Grid.Column<Player> pointsCol = grid.addColumn(Player::getPoints, "points").setHeader("Punti");

		provider.addDataProviderListener(o -> {
			if (playerService != null) {
				setPlayerTotalCounter(filter, indexCol);
			} else {
				indexCol.setHeader(new Label());
			}
		});
		
		var filterRow = grid.appendHeaderRow();

		var fullNameFilter = new TextField();
		fullNameFilter.addValueChangeListener(event -> {
			if (StringUtils.isEmpty(event.getValue())) {
				filter.setFullName(null);
			} else {
				filter.setFullName(event.getValue());
			}
			provider.refreshAll();
		});
		fullNameFilter.setValueChangeMode(ValueChangeMode.EAGER);
		filterRow.getCell(fullNameCol).setComponent(fullNameFilter);
		fullNameFilter.setSizeFull();
		fullNameFilter.setPlaceholder(FILTER_PLACEHOLDER);
		fullNameFilter.getElement().setAttribute("focus-target", "");
		
		var cityCodeFilter = new TextField();
		cityCodeFilter.addValueChangeListener(event -> {
			if (StringUtils.isEmpty(event.getValue())) {
				filter.setCityCode(null);
			} else {
				filter.setCityCode(event.getValue());
			}
			provider.refreshAll();
		});
		cityCodeFilter.setValueChangeMode(ValueChangeMode.EAGER);
		filterRow.getCell(cityCodeCol).setComponent(cityCodeFilter);
		cityCodeFilter.setSizeFull();
		cityCodeFilter.setPlaceholder(FILTER_PLACEHOLDER);
		cityCodeFilter.getElement().setAttribute("focus-target", "");
		
		ComboBox<PlayerRankingEnum> prevRankingFilter = new ComboBox<>();
		prevRankingFilter.addValueChangeListener(event -> {
			filter.setPreviousRanking(event.getValue());
			provider.refreshAll();
		});
		prevRankingFilter.setItems(PlayerRankingEnum.values());
		prevRankingFilter.setItemLabelGenerator(PlayerRankingEnum::getValue);
		filterRow.getCell(prevRankingCol).setComponent(prevRankingFilter);
		prevRankingFilter.setSizeFull();
		prevRankingFilter.setPlaceholder(FILTER_PLACEHOLDER);
		prevRankingFilter.setClearButtonVisible(true);
		prevRankingFilter.setAutoOpen(false);
		prevRankingFilter.getElement().setAttribute("focus-target", "");
		
		ComboBox<PlayerRankingEnum> currRankingFilter = new ComboBox<>();
		currRankingFilter.addValueChangeListener(event -> {
			filter.setCurrentRanking(event.getValue());
			provider.refreshAll();
		});
		currRankingFilter.setItems(PlayerRankingEnum.values());
		currRankingFilter.setItemLabelGenerator(PlayerRankingEnum::getValue);
		filterRow.getCell(currRankingCol).setComponent(currRankingFilter);
		currRankingFilter.setSizeFull();
		currRankingFilter.setPlaceholder(FILTER_PLACEHOLDER);
		currRankingFilter.setClearButtonVisible(true);
		currRankingFilter.setAutoOpen(false);
		currRankingFilter.getElement().setAttribute("focus-target", "");
		
		var noteFilter = new TextField();
		noteFilter.addValueChangeListener(event -> {
			if (StringUtils.isEmpty(event.getValue())) {
				filter.setNote(null);
			} else {
				filter.setNote(event.getValue());
			}
			provider.refreshAll();
		});
		noteFilter.setValueChangeMode(ValueChangeMode.EAGER);
		filterRow.getCell(noteCol).setComponent(noteFilter);
		noteFilter.setSizeFull();
		noteFilter.setPlaceholder(FILTER_PLACEHOLDER);
		noteFilter.getElement().setAttribute("focus-target", "");
		
		/*var pointsFilter = new NumberField();
		pointsFilter.addValueChangeListener(event -> {
			if (event.getValue() == null) {
				filter.setPoints(null);
			} else {
				filter.setPoints(BigDecimal.valueOf(event.getValue()));
			}
			provider.refreshAll();
		});
		pointsFilter.setValueChangeMode(ValueChangeMode.EAGER);
		filterRow.getCell(pointsCol).setComponent(pointsFilter);
		pointsFilter.setSizeFull();
		pointsFilter.setPlaceholder(FILTER_PLACEHOLDER);
		pointsFilter.getElement().setAttribute("focus-target", "");*/
		
		ComboBox<GenderEnum> genderFilter = new ComboBox<>();
		genderFilter.addValueChangeListener(event -> {
			GenderEnum e = event.getValue();
			if (event.getValue() == null) {
				e = GenderEnum.M;
				genderFilter.setValue(e);
			}
			filter.setGender(e);
			provider.refreshAll();
		});
		genderFilter.setItems(GenderEnum.values());
		genderFilter.setValue(GenderEnum.M);
		filterRow.getCell(indexCol).setComponent(genderFilter);
		//genderFilter.setSizeFull();
		genderFilter.getElement().setAttribute("focus-target", "");
		
		ComboBox<YearEnum> yearFilter = new ComboBox<>();
		yearFilter.addValueChangeListener(event -> {
			filter.setYear(event.getValue());
			provider.refreshAll();
		});
		yearFilter.setItems(YearEnum.values());
		yearFilter.setItemLabelGenerator(YearEnum::getValue);
		yearFilter.setValue(YearEnum.TWENTYONE);
		yearFilter.setReadOnly(true);
		//genderFilter.setSizeFull();
		yearFilter.getElement().setAttribute("focus-target", "");
		
		var halfheaderRow = grid.prependHeaderRow();
		halfheaderRow.getCell(indexCol).setComponent(yearFilter);

	}

	private void setPlayerTotalCounter(Player filter, Grid.Column<Player> indexCol) {
		int total = playerService.countForGrid(filter); // non ho capito come riutilizzare la query del dataprovider
		var what = "giocatori";
		if (total == 1) {
			what = "giocatore";
		}
		indexCol.setHeader("Totale: " + total + " " + what);
	}

}
