/**
 *
 */
package org.ssor.boss.card.service;

import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.ssor.boss.core.entity.Card;
import org.ssor.boss.core.repository.CardRepository;
import org.ssor.boss.core.transfer.CardDto;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * @author Derrian Harris
 *
 */
@Service
public class CardService {
	@Autowired
	CardRepository cardDao;
	
	private static final String RESOURCE_NOT_FOUND_WITH_STR = "Resource not found with id: ";

    public Card add(CardDto cardDto) throws IllegalArgumentException {

        Card card = cardDto.convertToCardEntity();
        card.setCreated(LocalDateTime.now());
        return cardDao.save(card);
    }

	public Card update(CardDto cardDto) throws IllegalArgumentException, NotFoundException {
		if (!cardDao.existsById(cardDto.getId()))
			throw new NotFoundException(RESOURCE_NOT_FOUND_WITH_STR + cardDto.getId());

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

	public Card findById(Integer id) throws IllegalArgumentException, NotFoundException {
		Optional<Card> result = cardDao.findById(id);
		if (result.isEmpty()) {
			throw new NotFoundException(RESOURCE_NOT_FOUND_WITH_STR + id);
		}
		return result.get();
	}

    public Card findById(Integer id) throws IllegalArgumentException, NotFoundException {
        Optional<Card> result = cardDao.findById(id);
        if (result.isEmpty()) {
            throw new NotFoundException("Resource not found with id: " + id);
        }
        return result.get();
    }

		if (!cardDao.existsById(id)) {
			throw new NotFoundException(RESOURCE_NOT_FOUND_WITH_STR + id);
		}
		cardDao.deleteById(id);
	}

        if (!cardDao.existsById(id)) {
            throw new NotFoundException("Resource not found with id: " + id);
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
