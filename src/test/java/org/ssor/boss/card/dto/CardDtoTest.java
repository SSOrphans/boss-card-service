/**
 * 
 */
package org.ssor.boss.card.dto;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;
import org.ssor.boss.card.entity.CardEntity;
import org.ssor.boss.card.entity.CardTypeEntity;

/**
 * @author Derrian Harris
 *
 */
public class CardDtoTest {
	@Test
	  void test_CanCreateEmptyCardDto(){
		CardDto cardDto = new CardDto();
	    assertThat(cardDto).isNotNull();
	  }
	
	@Test
	  void test_CanEvalEqual(){
		LocalDateTime currTime = LocalDateTime.now();
		CardDto cardDtoA = new CardDto();
		CardDto cardDtoB = new CardDto();
		CardDto cardDtoC = new CardDto();
		
		cardDtoA.setId(1);
		cardDtoA.setTypeId(1);
		cardDtoA.setNumberHash("1234123412341234");
		cardDtoA.setAccountId(1);
		cardDtoA.setCreated(currTime);
		cardDtoA.setExpirationDate(LocalDate.of(2025, 1, 1));
		cardDtoA.setPin(1111);
		cardDtoA.setCvv(111);
		cardDtoA.setActive(false);
		cardDtoA.setConfirmed(false);
		cardDtoA.setStolen(false);
		
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
		
		cardDtoC.setId(2);
		cardDtoC.setTypeId(2);
		cardDtoC.setNumberHash("1234123412341234");
		cardDtoC.setAccountId(2);
		cardDtoC.setCreated(LocalDateTime.of(2021, 1, 1, 0, 0));
		cardDtoC.setExpirationDate(LocalDate.of(2026, 1, 1));
		cardDtoC.setPin(2222);
		cardDtoC.setCvv(222);
		cardDtoC.setActive(true);
		cardDtoC.setConfirmed(true);
		cardDtoC.setStolen(true);
		
		
	    assertThat(cardDtoA).isEqualTo(cardDtoB);
	    assertThat(cardDtoA).isNotEqualTo(cardDtoC);
	  }
	
	@Test
	  void test_CanConvertToCardEntity(){
		CardDto cardDtoA = new CardDto();
		LocalDateTime currTime = LocalDateTime.now();
		
		cardDtoA.setId(1);
		cardDtoA.setTypeId(1);
		cardDtoA.setNumberHash("1234123412341234");
		cardDtoA.setAccountId(1);
		cardDtoA.setCreated(currTime);
		cardDtoA.setExpirationDate(LocalDate.of(2025, 1, 1));
		cardDtoA.setPin(1111);
		cardDtoA.setCvv(111);
		cardDtoA.setActive(false);
		cardDtoA.setConfirmed(false);
		cardDtoA.setStolen(false);
		
		CardEntity cardB = new CardEntity();
		CardTypeEntity cardType = new CardTypeEntity(1,"");
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
		
	    assertThat(cardDtoA.convertToCardEntity()).isEqualTo(cardB);
	  }
}
