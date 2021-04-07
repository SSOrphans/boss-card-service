/**
 * 
 */
package org.ssor.boss.card.entity;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;
import org.ssor.boss.card.dto.CardDto;

/**
 * @author Derrian Harris
 *
 */
public class CardEntityTest {
	@Test
	void test_CanCreateEmptyCardEntity() {
		CardEntity card = new CardEntity();
		assertThat(card).isNotNull();
	}

	@Test
	void test_CanEvalEqual() {
		LocalDateTime currTime = LocalDateTime.now();
		CardTypeEntity cardType = new CardTypeEntity(1, "Debit");
		CardEntity cardA = new CardEntity();
		CardEntity cardB = new CardEntity();
		CardEntity cardC = new CardEntity();

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

		cardB.setId(1);
		cardB.setCardType(cardType);
		cardB.setNumberHash("1234123412341234");
		cardB.setAccountId(1);
		cardB.setCreated(currTime);
		cardB.setExpirationDate(LocalDate.of(2025, 1, 1));
		cardB.setPin(1111);
		cardB.setCvv(111);
		cardB.setActive(false);
		cardB.setConfirmed(false);
		cardB.setStolen(false);

		cardC.setId(2);
		cardC.setCardType(new CardTypeEntity(2, "Credit"));
		cardC.setNumberHash("1234123412341234");
		cardC.setAccountId(2);
		cardC.setCreated(LocalDateTime.of(2021, 1, 1, 0, 0));
		cardC.setExpirationDate(LocalDate.of(2026, 1, 1));
		cardC.setPin(2222);
		cardC.setCvv(222);
		cardC.setActive(true);
		cardC.setConfirmed(true);
		cardC.setStolen(true);

		assertThat(cardA).isEqualTo(cardB);
		assertThat(cardA).isNotEqualTo(cardC);
	}

	@Test
	void test_CanConvertToCardDto() {
		LocalDateTime currTime = LocalDateTime.now();
		CardEntity cardA = new CardEntity();
		CardTypeEntity cardType = new CardTypeEntity(1, "");
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

		CardDto cardDtoB = new CardDto();
		cardDtoB.setId(1);
		cardDtoB.setTypeId(1);
		cardDtoB.setNumberHash("1234123412341234");
		cardDtoB.setAccountId(1);
		cardDtoB.setCreated(currTime);
		cardDtoB.setExpirationDate(LocalDate.of(2025, 1, 1));
		cardDtoB.setPin(1111);
		cardDtoB.setCvv(111);
		cardDtoB.setActive(false);
		cardDtoB.setConfirmed(false);
		cardDtoB.setStolen(false);

		assertThat(cardA.convertToCardDto()).isEqualTo(cardDtoB);
	}
}
