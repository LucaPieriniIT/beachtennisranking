package xyz.pierini.rankings.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher.GenericPropertyMatcher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.query.UntypedExampleMatcher;
import org.springframework.stereotype.Service;

import com.vaadin.flow.data.provider.QuerySortOrder;

import xyz.pierini.rankings.enumerator.PlayerRankingEnum;
import xyz.pierini.rankings.enumerator.YearEnum;
import xyz.pierini.rankings.grid.OffsetBasedPageRequest;
import xyz.pierini.rankings.model.Player;
import xyz.pierini.rankings.repository.PlayerRepository;
import xyz.pierini.rankings.utility.SortUtility;

@Service
public class PlayerService {

	@Autowired
	private PlayerRepository playerRepository;

	/*
	 * https://vaadin.com/docs/v14/flow/binding-data/tutorial-flow-data-provider
	 * 
	 * https://vaadin.com/components/vaadin-grid/java-examples
	 * 
	 * https://stackoverflow.com/questions/48528066/query-by-example-spring-data-in-clause#comment84052029_48528162
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
		/*Function<Optional<String>, Optional<String>> fnc = (o) -> {
			return null;
		};*/
		var matcher = UntypedExampleMatcher.matching().withIgnoreCase().withIgnoreNullValues()
				.withMatcher("fullName", GenericPropertyMatcher::contains)
				.withMatcher("note", GenericPropertyMatcher::contains)
				.withMatcher("points", GenericPropertyMatcher::contains)
				.withTransformer("previousRanking", o -> {
					if (o.isPresent()) {
						String s = (String) o.get();
						var e = PlayerRankingEnum.valueOf(s);
						return Optional.of(e.getValue());
					}
					return o;
				})
				.withTransformer("currentRanking", o -> {
					if (o.isPresent()) {
						String s = (String) o.get();
						var e = PlayerRankingEnum.valueOf(s);
						return Optional.of(e.getValue());
					}
					return o;
				})
				.withTransformer("year", o -> {
					if (o.isPresent()) {
						String s = (String) o.get();
						var e = YearEnum.valueOf(s);
						return Optional.of(e.getValue());
					}
					return o;
				});
		return Example.of(player, matcher);
	}

}
