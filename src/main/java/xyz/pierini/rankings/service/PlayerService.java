package xyz.pierini.rankings.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.query.UntypedExampleMatcher;
import org.springframework.stereotype.Service;

import com.vaadin.flow.data.provider.QuerySortOrder;

import xyz.pierini.rankings.grid.OffsetBasedPageRequest;
import xyz.pierini.rankings.model.Player;
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

	public Page<Player> fetchForGrid(int offset, int limit, List<QuerySortOrder> sorterList, Player player) {
        Pageable pageable = new OffsetBasedPageRequest(offset, limit, SortUtility.fromGrid(sorterList));
        Page<Player> p = playerRepository.findAll(exampleFromFilter(player), pageable);
		return p;
	}

	public int countForGrid(Player player) {
		return Math.toIntExact(playerRepository.count(exampleFromFilter(player)));
	}
	
	private Example<Player> exampleFromFilter(Player player) {
		var matcher = UntypedExampleMatcher.matching()
				.withIgnoreCase()
				.withIgnoreNullValues()
				.withMatcher("currentRanking", m -> m.contains())
				.withMatcher("fullName", m -> m.contains())
				.withMatcher("note", m -> m.contains())
				.withMatcher("points", m -> m.startsWith())
				.withMatcher("previousRanking", m -> m.contains())
				;
		return Example.of(player, matcher);
	}
	
}
