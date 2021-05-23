/**
 *
 */
package org.ssor.boss.card.service;

import javassist.NotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.ssor.boss.core.entity.Card;
import org.ssor.boss.core.repository.CardRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.ssor.boss.core.entity.CardType.CARD_PLAIN;

/**
 * @author Derrian Harris
 * @author Christian Angeles
 *
 */
@ExtendWith(MockitoExtension.class)
class CardServiceTest
{
	@Mock
	CardRepository cardRepository;
	Card cardA;
	Card cardE;
	@InjectMocks
	CardService cardService;

	@BeforeEach
	void setup()
	{
		LocalDateTime datetime = LocalDateTime.now();
		cardA = new Card();
		cardA.setId(1);
		cardA.setCardType(CARD_PLAIN);
		cardA.setNumberHash("1234123412341234");
		cardA.setAccountId(1);
		cardA.setCreated(datetime);
		cardA.setExpirationDate(LocalDate.of(2025, 1, 1));
		cardA.setPin(1111);
		cardA.setCvv(111);
		cardA.setActive(false);
		cardA.setConfirmed(false);
		cardA.setStolen(false);

		cardE = new Card();
		cardE.setId(1);
		cardE.setCardType(CARD_PLAIN);
		cardE.setNumberHash("1234123412341234");
		cardE.setAccountId(1);
		cardE.setCreated(datetime);
		cardE.setExpirationDate(LocalDate.of(2025, 1, 1));
		cardE.setPin(1111);
		cardE.setCvv(111);
		cardE.setActive(false);
		cardE.setConfirmed(false);
		cardE.setStolen(false);
	}

	@Test
	void test_CanAddCard() throws IllegalArgumentException, NotFoundException
	{
		when(cardRepository.save(any(Card.class))).thenReturn(cardA);
		Card result = cardService.add(cardA.convertToCardDto());

		assertThat(result).isNotNull().isEqualTo(cardE);
	}

	@Test
	void test_CanUpdateCard() throws IllegalArgumentException, NotFoundException
	{
		when(cardRepository.existsById(cardA.getId())).thenReturn(true);
		when(cardRepository.getOne(cardA.getId())).thenReturn(cardA);
		when(cardRepository.save(any(Card.class))).thenReturn(cardA);
		Card result = cardService.update(cardA.convertToCardDto());

		assertThat(result).isNotNull().isEqualTo(cardE);
	}

	@Test
	void test_CanDeleteCard() throws IllegalArgumentException, NotFoundException
	{
		when(cardRepository.existsById(cardA.getId())).thenReturn(true);
		doNothing().when(cardRepository).deleteById(any(Integer.class));
		cardService.deleteById(1);

		verify(cardRepository, atLeast(1)).deleteById(any(Integer.class));
	}

	@Test
	void test_CardExistById_NotFoundException() throws IllegalArgumentException, NotFoundException
	{
		when(cardRepository.existsById(cardA.getId())).thenReturn(false);

		assertThatThrownBy(() -> cardService.update(cardA.convertToCardDto())).isInstanceOf(NotFoundException.class);
		assertThatThrownBy(() -> cardService.deleteById(1)).isInstanceOf(NotFoundException.class);
	}

	@Test
	void test_CanFindAllCards() throws NotFoundException
	{
		List<Card> cardList = new ArrayList<>();
		cardList.add(cardA);
		cardList.add(cardE);

		when(cardRepository.findAll()).thenReturn(cardList);

		assertThat(cardService.findAllCards()).isNotNull().isEqualTo(cardList);
	}

	@Test
	void test_CanFindAllCards_NotFoundException() throws NotFoundException
	{
		when(cardRepository.findAll()).thenReturn(new ArrayList<>());

		assertThatThrownBy(() -> cardService.findAllCards()).isInstanceOf(NotFoundException.class);
	}

	@Test
	void test_CandFindCard() throws IllegalArgumentException, NotFoundException
	{
		when(cardRepository.findById(cardA.getId())).thenReturn(Optional.of(cardA));

		assertThat(cardService.findById(cardA.getId())).isNotNull().isEqualTo(cardE);
	}

	@Test
	void test_CandFindCard_NotFoundException() throws IllegalArgumentException, NotFoundException
	{
		when(cardRepository.findById(cardA.getId())).thenReturn(Optional.empty());

		assertThatThrownBy(() -> cardService.findById(cardA.getId())).isInstanceOf(NotFoundException.class);
	}
}
