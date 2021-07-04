package xyz.pierini.rankings.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import xyz.pierini.rankings.model.Player;

public interface PlayerRepository extends MongoRepository<Player, String> {
	
}
