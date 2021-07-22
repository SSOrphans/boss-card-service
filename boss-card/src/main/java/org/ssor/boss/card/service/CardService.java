package org.ssor.boss.card.service;

import javassist.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.ssor.boss.card.repository.CardPagingRepository;
import org.ssor.boss.card.transfer.CardPageResult;
import org.ssor.boss.core.entity.Card;
import org.ssor.boss.core.entity.CardType;
import org.ssor.boss.core.entity.FilterParams;
import org.ssor.boss.core.repository.CardRepository;
import org.ssor.boss.core.transfer.CardDto;
import org.ssor.boss.core.transfer.SecureCardDetails;
import java.time.Instant;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

/**
 * @author Derrian Harris
 */
@Service
@RequiredArgsConstructor
public class CardService
{
  private static final String RESOURCE_NOT_FOUND_WITH_STR = "Resource not found with id: ";
  private final CardRepository cardDao;
  private final CardPagingRepository cardPagingRepository;

  public Card add(CardDto cardDto) throws IllegalArgumentException
  {
    var card = cardDto.convertToCardEntity();
    card.setCreated(Instant.now().toEpochMilli());
    return cardDao.save(card);
  }

  public Card update(CardDto cardDto) throws IllegalArgumentException, NotFoundException
  {
    if (!cardDao.existsById(cardDto.getId()))
      throw new NotFoundException(RESOURCE_NOT_FOUND_WITH_STR + cardDto.getId());

    var card = cardDao.getOne(cardDto.getId());
    card.setNumberHash(cardDto.getNumberHash());
    card.setAccountId(cardDto.getAccountId());
    card.setCreated(cardDto.getCreated());
    card.setActiveSince(cardDto.getActiveSince());
    card.setExpirationDate(cardDto.getExpirationDate());
    card.setPin(cardDto.getPin());
    card.setCvv(cardDto.getCvv());
    card.setConfirmed(cardDto.isConfirmed());
    card.setActive(cardDto.isActive());
    card.setStolen(cardDto.isStolen());
    card.setType(cardDto.getCardType());

    return cardDao.save(card);
  }

  public Card findById(Integer id) throws IllegalArgumentException, NotFoundException
  {
    Optional<Card> result = cardDao.findById(id);
    if (result.isEmpty())
      throw new NotFoundException(RESOURCE_NOT_FOUND_WITH_STR + id);
    return result.get();
  }

  /**
   * Finds a page result for all cards based on the provided filtering parameters.
   *
   * @param params The parameters used to filter the page results.
   * @return The results of paging the cards stored in the database.
   * @throws NotFoundException If no account with the given ID exists.
   */
  public CardPageResult pageCards(FilterParams params) throws NotFoundException
  {
    final var limit = params.getLimit();
    if (limit == -1)
    {
      final var sortBy = Sort.by(getSortDirection(params.getSortDir()), params.getSortBy().split(","));
      final var cards = cardPagingRepository.findAll(sortBy);
      final var cardStream = StreamSupport.stream(cards.spliterator(), false);
      final var secCards = cardStream.map(SecureCardDetails::new).collect(Collectors.toList());
      return new CardPageResult(secCards, 0, 1, secCards.size());
    }

    final var sortBy = Sort.by(getSortDirection(params.getSortDir()), params.getSortBy().split(","));
    final var pageable = PageRequest.of(params.getPage(), limit, sortBy);
    final var keyword = params.getKeyword();
    final var cards = cardPagingRepository.findAllByLastFourStartsWith(keyword, pageable);
    if (cards.isEmpty())
      throw new NotFoundException("Resource not found");

    // Return them all.
    final var filter = params.getFilter();
    if (filter.isEmpty())
    {
      final var secCards = cards.stream().map(SecureCardDetails::new).collect(Collectors.toList());
      return new CardPageResult(secCards, cards.getNumber(), cards.getTotalPages(), cards.getNumberOfElements());
    }

    // Return the filtered version.
    final var type = CardType.valueOf(filter);
    final Function<Card, Card> typesThatEqual = c ->
    {
      if (c.getType().equals(type))
        return c;
      return null;
    };

    final var secCards = cards.stream().map(typesThatEqual).filter(Objects::nonNull)
                              .map(SecureCardDetails::new).collect(Collectors.toList());
    return new CardPageResult(secCards, cards.getNumber(), cards.getTotalPages(), cards.getNumberOfElements());
  }

  /**
   * Finds a page result for cards of a given user based on the provided filtering parameters.
   *
   * @param userId The ID of the user to get the cards for.
   * @param params The parameters used to filter the page results.
   * @return The results of paging the cards by user ID.
   * @throws NotFoundException If no account with the given ID exists.
   */
  public CardPageResult findByUserId(int userId, FilterParams params) throws NotFoundException
  {
    final var limit = params.getLimit();
    if (limit == -1)
    {
      final var sortBy = Sort.by(getSortDirection(params.getSortDir()), params.getSortBy().split(","));
      final var cards = cardPagingRepository.findAllByUserIdAndLastFourStartsWith(userId, params.getKeyword(), sortBy);
      final var cardStream = StreamSupport.stream(cards.spliterator(), false);
      final var secCards = cardStream.map(SecureCardDetails::new).collect(Collectors.toList());
      return new CardPageResult(secCards, 0, 1, secCards.size());
    }

    final var sortBy = Sort.by(getSortDirection(params.getSortDir()), params.getSortBy().split(","));
    final var pageable = PageRequest.of(params.getPage(), limit, sortBy);
    final var keyword = params.getKeyword();
    final var cards = cardPagingRepository.findAllByUserIdAndLastFourStartsWith(userId, keyword, pageable);
    if (cards.isEmpty())
      throw new NotFoundException("Resource not found");

    // Return them all.
    final var filter = params.getFilter();
    if (filter.isEmpty())
    {
      final var secCards = cards.stream().map(SecureCardDetails::new).collect(Collectors.toList());
      return new CardPageResult(secCards, cards.getNumber(), cards.getTotalPages(), cards.getNumberOfElements());
    }

    // Return the filtered version.
    final var type = CardType.valueOf(filter);
    final Function<Card, Card> typesThatEqual = c ->
    {
      if (c.getType().equals(type))
        return c;
      return null;
    };

    final var secCards = cards.stream().map(typesThatEqual).filter(Objects::nonNull)
                              .map(SecureCardDetails::new).collect(Collectors.toList());
    return new CardPageResult(secCards, cards.getNumber(), cards.getTotalPages(), cards.getNumberOfElements());
  }

  /**
   * Finds a page result for cards of a given account based on the provided filtering parameters.
   *
   * @param accountId The ID of the account to get the cards for.
   * @param params    The parameters used to filter the page results.
   * @return The results of paging the cards by account ID.
   * @throws NotFoundException If no account with the given ID exists.
   */
  public CardPageResult findByAccountId(int accountId, FilterParams params) throws NotFoundException
  {
    final var limit = params.getLimit();
    if (limit == -1)
    {
      final var sortBy = Sort.by(getSortDirection(params.getSortDir()), params.getSortBy().split(","));
      final var cards = cardPagingRepository.findAllByAccountIdAndLastFourStartsWith(accountId, params.getKeyword(),
                                                                                     sortBy);
      final var cardStream = StreamSupport.stream(cards.spliterator(), false);
      final var secCards = cardStream.map(SecureCardDetails::new).collect(Collectors.toList());
      return new CardPageResult(secCards, 0, 1, secCards.size());
    }

    final var sortBy = Sort.by(getSortDirection(params.getSortDir()), params.getSortBy().split(","));
    final var pageable = PageRequest.of(params.getPage(), limit, sortBy);
    final var keyword = params.getKeyword();
    final var cards = cardPagingRepository.findAllByAccountIdAndLastFourStartsWith(accountId, keyword, pageable);
    if (cards.isEmpty())
      throw new NotFoundException("Resource not found");

    // Return them all.
    final var filter = params.getFilter();
    if (filter.isEmpty())
    {
      final var secCards = cards.stream().map(SecureCardDetails::new).collect(Collectors.toList());
      return new CardPageResult(secCards, cards.getNumber(), cards.getTotalPages(), cards.getNumberOfElements());
    }

    // Return the filtered version.
    final var type = CardType.valueOf(filter);
    final Function<Card, Card> typesThatEqual = c ->
    {
      if (c.getType().equals(type))
        return c;
      return null;
    };

    final var secCards = cards.stream().map(typesThatEqual).filter(Objects::nonNull)
                              .map(SecureCardDetails::new).collect(Collectors.toList());
    return new CardPageResult(secCards, cards.getNumber(), cards.getTotalPages(), cards.getNumberOfElements());
  }

  public void deleteById(Integer id) throws IllegalArgumentException, NotFoundException
  {
    if (!cardDao.existsById(id))
      throw new NotFoundException(RESOURCE_NOT_FOUND_WITH_STR + id);
    cardDao.deleteById(id);
  }

  private Sort.Direction getSortDirection(String sortDir)
  {
    var dir = Sort.DEFAULT_DIRECTION;
    if ("asc".equals(sortDir))
      dir = Sort.Direction.ASC;
    if ("desc".equals(sortDir))
      dir = Sort.Direction.DESC;
    return dir;
  }
}
