/**
 * 
 */
package org.ssor.boss.card.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.ssor.boss.card.entity.CardTypeEntity;
import org.ssor.boss.card.repositiory.CardTypeRepository;

/**
 * @author Derrian Harris
 *
 */
@DataJpaTest
public class CardTypeRepositoryTest {
	
	@Autowired
	public CardTypeRepository cardTypeRepository;

	public CardTypeEntity cardTypeA;
	@BeforeEach
	public void setup() {
		cardTypeA = new CardTypeEntity(1,"Debit");
	}
	
	@Test
	public void test_CanFindAllEmpty() {
		List<CardTypeEntity> result = cardTypeRepository.findAll();
		assertThat(result).isNotNull().isEmpty();
	}
	
	@Disabled
	@Test
	public void test_CanCRUD() {
		cardTypeRepository.save(cardTypeA);
		CardTypeEntity result = cardTypeRepository.findById(1).get();
		assertThat(result).isNotNull().isEqualTo(cardTypeA);
		cardTypeA.setName("Credit");
		result.setName("Credit");
		cardTypeRepository.save(result);
		result = cardTypeRepository.findById(1).get();
		assertThat(result).isNotNull().isEqualTo(cardTypeA);
		cardTypeRepository.deleteById(1);
		assertThat(cardTypeRepository.existsById(1)).isFalse();
	}
}
