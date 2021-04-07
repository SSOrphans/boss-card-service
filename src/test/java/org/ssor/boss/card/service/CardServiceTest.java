/**
 * 
 */
package org.ssor.boss.card.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.time.LocalDateTime;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.ssor.boss.card.entity.CardEntity;
import org.ssor.boss.card.entity.CardTypeEntity;
import org.ssor.boss.card.repositiory.CardRepository;
import javassist.NotFoundException;

/**
 * @author Derrian Harris
 *
 */
@ExtendWith(MockitoExtension.class)
public class CardServiceTest {
	@InjectMocks
	private CardService cardService;

	@Mock
	CardRepository cardRepository;
	
	CardEntity cardA;
	CardEntity cardE;
	
	@BeforeEach
	public void setup() {
		cardA = new CardEntity();
		CardTypeEntity cardType = new CardTypeEntity(1,"Debit");
		cardA.setId(1);
		cardA.setCardType(cardType);
		cardA.setNumberHash("1234123412341234");
		cardA.setAccountId(1);
		cardA.setCreated(LocalDateTime.now());
		cardA.setExpirationDate(LocalDate.of(2025, 1, 1));
		cardA.setPin(1111);
		cardA.setCvv(111);
		cardA.setActive(false);
		cardA.setConfirmed(false);
		cardA.setStolen(false);
		
		cardE.setId(1);
		cardE.setCardType(cardType);
		cardE.setNumberHash("1234123412341234");
		cardE.setAccountId(1);
		cardE.setCreated(LocalDateTime.now());
		cardE.setExpirationDate(LocalDate.of(2025, 1, 1));
		cardE.setPin(1111);
		cardE.setCvv(111);
		cardE.setActive(false);
		cardE.setConfirmed(false);
		cardE.setStolen(false);
	}
	
	public void test_CanAddCard() throws IllegalArgumentException, NotFoundException {
		when(cardRepository.save(cardA)).thenReturn(cardA);
		CardEntity result = cardService.add(cardA.convertToCardDto());
		assertThat(result).isNotNull().isEqualTo(cardE);
	}
}
