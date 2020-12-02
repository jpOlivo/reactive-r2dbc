package com.jpolivo.reactive.repository;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.jpolivo.reactive.model.Player;

import reactor.test.StepVerifier;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class PlayerRepositoryTests {
	
	@Autowired
	private PlayerRepository playerRepository;
	

	@Test
	@Order(1)
	public void whenDeleteAll_then0IsExpected() {
	    playerRepository.deleteAll()
	      .as(StepVerifier::create)
	      .expectNextCount(0)
	      .verifyComplete();
	}
	
	
	@Test
	@Order(2)
	public void whenInsert6_then6AreExpected() {
		insertPlayers();
	    playerRepository.findAll()
	      .as(StepVerifier::create)
	      .expectNextCount(6)
	      .verifyComplete();
	}
	
	@Test
	@Order(3)
	public void whenSearchForCR7_then1IsExpected() {
	    playerRepository.findByName("CR7")
	      .as(StepVerifier::create)
	      .expectNextCount(1)
	      .verifyComplete();
	}
	
	@Test
	@Order(4)
	public void whenSearchFor32YearsOld_then2AreExpected() {
	    playerRepository.findByAge(32)
	      .as(StepVerifier::create)
	      .expectNextCount(2)
	      .verifyComplete();
	}
	
	@Test
	@Order(5)
	public void whenSearchForMessi_then1IsExpected() {
	    playerRepository.findFirstByName("Messi")
	      .as(StepVerifier::create)
	      .expectNextCount(1)
	      .verifyComplete();
	}
	
	private void insertPlayers() {
        List<Player> players = Arrays.asList(
                new Player(null, "Kaka", 37),
                new Player(null, "Messi", 32),
                new Player(null, "Mbappé", 20),
                new Player(null, "CR7", 34),
                new Player(null, "Lewandowski", 30),
                new Player(null, "Cavani", 32)
        );
        playerRepository.saveAll(players).subscribe();
    }

}
