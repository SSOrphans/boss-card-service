/**
 * 
 */
package org.ssor.boss.card.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.ssor.boss.card.entity.CardEntity;
import org.ssor.boss.card.entity.CardTypeEntity;
import org.ssor.boss.card.repositiory.CardRepository;
import org.ssor.boss.card.repositiory.CardTypeRepository;

/**
 * @author Derrian Harris
 *
 */
@DataJpaTest
public class CardRepositoryTest {
	@Autowired
	public CardRepository cardRepository;
	@Autowired
	public CardTypeRepository cardTypeRepository;

	public CardEntity cardA;
	public CardTypeEntity cardType;
	@BeforeEach
	public void setup() {
		LocalDateTime currTime = LocalDateTime.now();
		cardType = new CardTypeEntity(1, "Debit");
		cardA = new CardEntity();

		cardA.setId(1);
		cardA.setCardType(cardType);
		cardA.setNumberHash("1234123412341234");
		cardA.setAccountId(1);
		cardA.setCreated(currTime);
		cardA.setExpirationDate(LocalDate.of(2025, 1, 1));
		cardA.setPin(1111);
		cardA.setCvv(111);
		cardA.setActive(false);
		cardA.setConfirmed(false);
		cardA.setStolen(false);

	}
	
	@Test
	public void test_CanFindAllEmpty() {
		List<CardEntity> result = cardRepository.findAll();
		assertThat(result).isNotNull().isEmpty();
		;
	}
	@Test
	public void test_CanFindById() {
		cardTypeRepository.save(cardType);
		cardRepository.save(cardA);
		CardEntity result = cardRepository.findById(1).get();
		assertThat(result).isNotNull().isEqualTo(cardA);
	}
}
