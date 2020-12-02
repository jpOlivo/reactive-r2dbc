package com.jpolivo.reactive.service;

import java.util.Map;

import org.slf4j.MDC;
import org.springframework.stereotype.Service;

import com.jpolivo.reactive.model.Player;
import com.jpolivo.reactive.repository.PlayerRepository;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;

@Service
@Slf4j
public class PlayerServiceImpl implements PlayerService {

	private PlayerRepository playerRepository;

	public PlayerServiceImpl(PlayerRepository playerRepository) {
		super();
		this.playerRepository = playerRepository;
	}

	@Override
	public Flux<Player> getAllPlayers() {
		//return playerRepository.findAll();
		return playerRepository.findAll().doFirst(wrap(() -> log.info("Searching players...")));
	}
	
	private Runnable wrap(final Runnable r) {
		Map<String, String> copyOfContextMap = MDC.getCopyOfContextMap();
		return () -> {
			Map<String, String> backupOfContextMap = MDC.getCopyOfContextMap();
			MDC.setContextMap(copyOfContextMap);
			try {
				r.run();
			} finally {
				if (backupOfContextMap == null) {
					MDC.clear();
				} else {
					MDC.setContextMap(backupOfContextMap);
				}
			}
		};
	}
}
