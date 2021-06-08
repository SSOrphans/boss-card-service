package org.ssor.boss.card.service;

import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.ssor.boss.core.entity.Card;
import org.ssor.boss.core.repository.CardRepository;
import org.ssor.boss.core.transfer.CardDto;
import java.time.Instant;
import java.util.List;
import java.util.Optional;

/**
 * @author Derrian Harris
 */
@Service
public class CardService
{
  private static final String RESOURCE_NOT_FOUND_WITH_STR = "Resource not found with id: ";
  @Autowired
  CardRepository cardDao;

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
    {
      throw new NotFoundException(RESOURCE_NOT_FOUND_WITH_STR + id);
    }
    return result.get();
  }

  public void deleteById(Integer id) throws IllegalArgumentException, NotFoundException
  {

    if (!cardDao.existsById(id))
    {
      throw new NotFoundException(RESOURCE_NOT_FOUND_WITH_STR + id);
    }
    cardDao.deleteById(id);
  }

  public List<Card> findAllCards() throws NotFoundException
  {
    List<Card> cards = cardDao.findAll();
    if (cards.isEmpty())
    {
      throw new NotFoundException("Resource not found");
    }
    return cards;
  }
}
