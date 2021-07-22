package org.ssor.boss.card.service;

import javassist.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
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
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

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

  public Page<Card> findByUserId(int userId, FilterParams params) throws NotFoundException
  {
    final var sortBy = Sort.by(getSortDirection(params.getSortDir()), params.getSortBy().split(","));
    final var pageable = PageRequest.of(params.getPage(), params.getLimit(), sortBy);
    final var cardType = params.getFilter();
    final var keyword = params.getKeyword();
    final var cards = cardPagingRepository.findAllByUserIdAndLastFourStartsWith(userId, keyword, pageable);
    if (cards.isEmpty())
      throw new NotFoundException("Resource not found with User ID: " + userId);
    return cards;
  }

  public Page<Card> findByAccountId(int accountId, FilterParams params) throws NotFoundException
  {
    final var sortBy = Sort.by(getSortDirection(params.getSortDir()), params.getSortBy().split(","));
    final var pageable = PageRequest.of(params.getPage(), params.getLimit(), sortBy);
    final var keyword = params.getKeyword();
    final var cards = cardPagingRepository.findAllByAccountIdAndLastFourStartsWith(accountId, keyword, pageable);
    if (cards.isEmpty())
      throw new NotFoundException("Resource not found with Account ID: " + accountId);
    return cards;
  }

  public void deleteById(Integer id) throws IllegalArgumentException, NotFoundException
  {
    if (!cardDao.existsById(id))
      throw new NotFoundException(RESOURCE_NOT_FOUND_WITH_STR + id);
    cardDao.deleteById(id);
  }

  public List<Card> findAllCards() throws NotFoundException
  {
    List<Card> cards = cardDao.findAll();
    if (cards.isEmpty())
      throw new NotFoundException("Resource not found");
    return cards;
  }

  public CardPageResult findAllCards(FilterParams params) throws NotFoundException
  {
  	final var sortBy = Sort.by(getSortDirection(params.getSortDir()), params.getSortBy().split(","));
  	final var pageable = PageRequest.of(params.getPage(), params.getLimit(), sortBy);
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
		final Function<Card, Card> typesThatEqual = c -> {
      if (c.getType().equals(type))
        return c;
      return null;
    };

		final var secCards = cards.stream().map(typesThatEqual).filter(Objects::nonNull)
                              .map(SecureCardDetails::new).collect(Collectors.toList());
		return new CardPageResult(secCards, cards.getNumber(), cards.getTotalPages(), cards.getNumberOfElements());
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
