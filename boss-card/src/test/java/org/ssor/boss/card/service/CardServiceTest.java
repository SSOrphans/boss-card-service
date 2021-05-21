/**
 *
 */
package org.ssor.boss.card.service;

import javassist.NotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.ssor.boss.core.entity.Card;
import org.ssor.boss.core.repository.CardRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.ssor.boss.core.entity.CardType.CARD_PLAIN;

/**
 * @author Derrian Harris
 *
 */
@ExtendWith(MockitoExtension.class)
public class CardServiceTest {
    @Mock
    CardRepository cardRepository;
    Card cardA;
    Card cardE;
    @InjectMocks
    private CardService cardService;

    @BeforeEach
    public void setup() {

        cardA = new Card();
        cardA.setId(1);
        cardA.setCardType(CARD_PLAIN);
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
        cardE.setCardType(CARD_PLAIN);
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
        when(cardRepository.save(any(Card.class))).thenReturn(cardA);
        Card result = cardService.add(cardA.convertToCardDto());
        assertThat(result).isNotNull().isEqualTo(cardE);
    }

    public void test_CanUpdateCard() throws IllegalArgumentException, NotFoundException {
        when(cardRepository.save(any(Card.class))).thenReturn(cardA);
        Card result = cardService.update(cardA.convertToCardDto());
        assertThat(result).isNotNull().isEqualTo(cardE);
    }

    public void test_CanDeleteCard() throws IllegalArgumentException, NotFoundException {
        doNothing().when(cardRepository).deleteById(any(Integer.class));
        cardService.deleteById(1);
        verify(cardRepository, atLeast(1)).deleteById(any(Integer.class));
    }
}
