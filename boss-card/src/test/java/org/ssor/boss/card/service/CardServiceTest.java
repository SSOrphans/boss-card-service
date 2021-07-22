package org.ssor.boss.card.service;

import javassist.NotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ArgumentsSource;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.ssor.boss.card.repository.CardPagingRepository;
import org.ssor.boss.card.utility.PagingArgumentsSource;
import org.ssor.boss.core.entity.Card;
import org.ssor.boss.core.entity.FilterParams;
import org.ssor.boss.core.repository.CardRepository;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;
import java.util.Optional;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.ssor.boss.core.entity.CardType.CARD_PLAIN;

/**
 * @author Derrian Harris
 * @author Christian Angeles
 */
@SpringBootTest
@ExtendWith(MockitoExtension.class)
class CardServiceTest
{
  @MockBean
  CardRepository cardRepository;
  @MockBean
  CardPagingRepository cardPagingRepository;
  Card cardA;
  Card cardE;

  @Autowired
  CardService cardService;

  @BeforeEach
  void setup()
  {
    final var datetime = Instant.now().toEpochMilli();
    final var expiration = LocalDate.of(2025, 1, 1).atStartOfDay(ZoneId.systemDefault()).toInstant().toEpochMilli();
    cardA = new Card();
    cardA.setId(1);
    cardA.setType(CARD_PLAIN);
    cardA.setNumberHash("1234123412341234");
    cardA.setLastFour("1234");
    cardA.setAccountId(1);
    cardA.setCreated(datetime);
    cardA.setExpirationDate(expiration);
    cardA.setPin("1111");
    cardA.setCvv("111");
    cardA.setActive(false);
    cardA.setConfirmed(false);
    cardA.setStolen(false);

    cardE = new Card();
    cardE.setId(1);
    cardE.setType(CARD_PLAIN);
    cardE.setNumberHash("1234123412344321");
    cardE.setLastFour("4321");
    cardE.setAccountId(1);
    cardE.setCreated(datetime);
    cardE.setExpirationDate(expiration);
    cardE.setPin("1111");
    cardE.setCvv("111");
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

//  @ParameterizedTest
//  @ArgumentsSource(PagingArgumentsSource.FilterOnly.class)
//  void test_CanPageCards(FilterParams params)
//  {
//    when(cardPagingRepository.findAll(any(Sort.class))).thenReturn(List.of(cardA, cardE));
//    when(cardPagingRepository.findAllByLastFourStartsWith(eq(""), any(Pageable.class)))
//        .thenReturn(new PageImpl<>(List.of(cardA, cardE)));
//    when(cardPagingRepository.findAllByLastFourStartsWith(eq("4"), any(Pageable.class)))
//        .thenReturn(new PageImpl<>(List.of(cardE)));
//  }
//
//  @ParameterizedTest
//  @ArgumentsSource(PagingArgumentsSource.IdsAndFilter.class)
//  void test_CanFindBy_UserId(int userId, FilterParams params)
//  {
//    when(cardPagingRepository.findAllByUserIdAndLastFourStartsWith(eq(0), any(String.class), any(Sort.class)))
//        .thenReturn(List.of());
//    when(cardPagingRepository.findAllByUserIdAndLastFourStartsWith(eq(0), any(String.class), any(Pageable.class)))
//        .thenReturn(Page.empty());
//    when(cardPagingRepository.findAllByUserIdAndLastFourStartsWith(eq(1), eq(""), any(Pageable.class)))
//        .thenReturn(Page.empty());
//    when(cardPagingRepository.findAllByUserIdAndLastFourStartsWith(eq(1), eq("4"), any(Pageable.class)))
//        .thenReturn(Page.empty());
//  }
//
//  @ParameterizedTest
//  @ArgumentsSource(PagingArgumentsSource.IdsAndFilter.class)
//  void test_CanFindBy_AccountId(int accountId, FilterParams params)
//  {
//    when(cardPagingRepository.findAllByAccountIdAndLastFourStartsWith(eq(0), any(String.class), any(Sort.class)))
//        .thenReturn(List.of());
//    when(cardPagingRepository.findAllByAccountIdAndLastFourStartsWith(eq(0), any(String.class), any(Pageable.class)))
//        .thenReturn(Page.empty());
//    when(cardPagingRepository.findAllByAccountIdAndLastFourStartsWith(eq(1), eq(""), any(Pageable.class)))
//        .thenReturn(Page.empty());
//    when(cardPagingRepository.findAllByAccountIdAndLastFourStartsWith(eq(1), eq("4"), any(Pageable.class)))
//        .thenReturn(Page.empty());
//  }
}
