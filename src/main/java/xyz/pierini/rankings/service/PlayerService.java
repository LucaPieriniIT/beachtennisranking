package xyz.pierini.rankings.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.vaadin.flow.data.provider.QuerySortOrder;

import xyz.pierini.rankings.model.Player;
import xyz.pierini.rankings.paging.OffsetBasedPageRequest;
import xyz.pierini.rankings.repository.PlayerRepository;
import xyz.pierini.rankings.utility.SortUtility;

@Service
public class PlayerService {
	
	@Autowired
	private PlayerRepository playerRepository;
	
	/*
	https://vaadin.com/docs/v14/flow/binding-data/tutorial-flow-data-provider
		
	https://vaadin.com/components/vaadin-grid/java-examples
	*/

	public Page<Player> fetchForGrid(int offset, int limit, List<QuerySortOrder> sorterList) {
        Pageable pageable = new OffsetBasedPageRequest(offset, limit, SortUtility.fromGrid(sorterList));
        Page<Player> p = playerRepository.findAll(pageable);
		return p;
	}

	public int countForGrid() {
		return Math.toIntExact(playerRepository.count());
	}
}
