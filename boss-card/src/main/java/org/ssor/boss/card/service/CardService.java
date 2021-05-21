/**
 *
 */
package org.ssor.boss.card.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.ssor.boss.core.entity.Card;
import org.ssor.boss.core.repository.CardRepository;
import org.ssor.boss.core.transfer.CardDto;

import javassist.NotFoundException;

/**
 * @author Derrian Harris
 *
 */
@Service
public class CardService {
	@Autowired
	CardRepository cardDao;
	
	private final String resourceNotFoundStr = "Resource not found with id: ";

	public Card add(CardDto cardDto) throws IllegalArgumentException {

		Card card = cardDto.convertToCardEntity();
		card.setCreated(LocalDateTime.now());
		return cardDao.save(card);
	}

	public Card update(CardDto cardDto) throws IllegalArgumentException, NotFoundException {
		if (!cardDao.existsById(cardDto.getId()))
			throw new NotFoundException(resourceNotFoundStr + cardDto.getId());

		Card card = cardDao.getOne(cardDto.getId());
		card.setNumberHash(cardDto.getNumberHash());
		card.setAccountId(cardDto.getAccountId());
		card.setCreated(cardDto.getCreated());
		card.setActiveSince(cardDto.getActiveSince());
		card.setExpirationDate(cardDto.getExpirationDate());
		card.setPin(cardDto.getPin());
		card.setCvv(cardDto.getCvv());
		card.setConfirmed(cardDto.getConfirmed());
		card.setActive(cardDto.getActive());
		card.setStolen(cardDto.getStolen());

		return cardDao.save(card);
	}

	public Card findById(Integer id) throws IllegalArgumentException, NotFoundException {
		Optional<Card> result = cardDao.findById(id);
		if (result.isEmpty()) {
			throw new NotFoundException(resourceNotFoundStr + id);
		}
		return result.get();
	}

	public void deleteById(Integer id) throws IllegalArgumentException, NotFoundException {

		if (!cardDao.existsById(id)) {
			throw new NotFoundException(resourceNotFoundStr + id);
		}
		cardDao.deleteById(id);
	}

	public List<Card> findAllCards() throws NotFoundException {
		List<Card> cards = cardDao.findAll();
		if (cards.isEmpty()) {
			throw new NotFoundException("Resource not found");
		}
		return cards;
	}

}
