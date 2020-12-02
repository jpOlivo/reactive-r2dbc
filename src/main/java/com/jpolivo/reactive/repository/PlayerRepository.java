package com.jpolivo.reactive.repository;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;

import com.jpolivo.reactive.model.Player;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface PlayerRepository extends ReactiveCrudRepository<Player, Integer> {

	Flux<Player> findByName(String name);
	
	Mono<Player> findFirstByName(String name); 

	Flux<Player> findByAge(int age);
}
