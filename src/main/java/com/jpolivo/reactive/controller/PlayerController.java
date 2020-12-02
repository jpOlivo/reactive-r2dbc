package com.jpolivo.reactive.controller;

import org.slf4j.MDC;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import com.jpolivo.reactive.logger.Logger;
import com.jpolivo.reactive.model.Player;
import com.jpolivo.reactive.service.PlayerService;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;


@RestController
@Slf4j
public class PlayerController {

	private PlayerService playerService;
	private Logger logHelper;

	public PlayerController(PlayerService playerService, Logger logHelper) {
		super();
		this.playerService = playerService;
		this.logHelper = logHelper;
	}

	/*@GetMapping("/players")
	public Flux<Player> all(@RequestHeader(required = false, name = "x-correlation-id") String correlationId) {
			return playerService.getAllPlayers()
					// .delayElements(Duration.ofSeconds(1))
					.doOnEach(logHelper.logOnNext(r -> log.info("found player {}", r.getName())))
					.doOnEach(logHelper.logOnError(e -> log.error("failure", e)));

	}*/
	

	@GetMapping("/players")
	public Flux<Player> all(@RequestHeader(required = false, name = "x-correlation-id") String correlationId) {
		MDC.put("x-correlation-id", correlationId);

		try {
			return playerService.getAllPlayers()
					// .delayElements(Duration.ofSeconds(1))
					.doFirst(logHelper.wrap(() -> log.info("Executing handler for /players")))
					.doOnNext(logHelper.wrap(r -> log.info("found player {}", r.getName())))
					.doOnComplete(logHelper.wrap(() -> log.info("done!")))
					.doOnError(logHelper.wrap(e -> log.error("failure", e)));
		} finally {
			MDC.remove("x-correlation-id");
		}
	}

	

	/*
	 * @GetMapping("/test") public Flux<Integer> allAges() { return
	 * repository.findAll().map(p -> p.getAge()).distinct().sort((i, j) ->
	 * j.compareTo(i)); }
	 */

}
