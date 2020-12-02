package com.jpolivo.reactive.service;

import com.jpolivo.reactive.model.Player;

import reactor.core.publisher.Flux;

public interface PlayerService {
	Flux<Player> getAllPlayers();
}
