package xyz.pierini.rankings.utility;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.domain.Sort.Order;

import com.vaadin.flow.data.provider.QuerySortOrder;
import com.vaadin.flow.data.provider.SortDirection;

public class SortUtility {
	
	private SortUtility() {}

	public static Sort fromGrid(List<QuerySortOrder> sorterList) {
		if (sorterList == null) {
			return null;
		}
		List<Order> orders = new ArrayList<>();
		for (QuerySortOrder qso : sorterList) {
			orders.add(new Order(fromSortDirection(qso.getDirection()), qso.getSorted()));
		}
		return Sort.by(orders);
	}
	
	public static Direction fromSortDirection(SortDirection sd) {
		switch (sd) {
		case ASCENDING:
			return Direction.ASC;
		case DESCENDING:
			return Direction.DESC;
		default:
			return null;
		}
	}
	
}
