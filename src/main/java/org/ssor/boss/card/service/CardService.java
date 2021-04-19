/**
 * 
 */
package org.ssor.boss.card.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.ssor.boss.card.dto.CardDto;
import org.ssor.boss.card.entity.CardTypeEntity;
import org.ssor.boss.card.repositiory.CardRepository;
import org.ssor.boss.card.repositiory.CardTypeRepository;
import org.ssor.boss.card.entity.CardEntity;

import javassist.NotFoundException;

/**
 * @author Derrian Harris
 *
 */
@Service
public class CardService {
	@Autowired
	private CardRepository cardDao;

	@Autowired
	private CardTypeRepository cardTypeDao;

	public CardEntity add(CardDto cardDto) throws IllegalArgumentException {

		CardEntity card = cardDto.convertToCardEntity();
		Optional<CardTypeEntity> cardTypeOpt = cardTypeDao.findById(cardDto.getTypeId());
		if (cardTypeOpt.isEmpty())
			throw new IllegalArgumentException("Resource not found with id: " + cardDto.getTypeId());

		card.setCardType(cardTypeOpt.get());
		card.setCreated(LocalDateTime.now());
		return cardDao.save(card);
	}

	public CardEntity update(CardDto cardDto) throws IllegalArgumentException, NotFoundException {
		if (!cardDao.existsById(cardDto.getId()))
			throw new NotFoundException("Resource not found with id: " + cardDto.getId());

		CardEntity card = cardDao.getOne(cardDto.getId());
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
		
		Optional<CardTypeEntity> cardTypeOpt = cardTypeDao.findById(cardDto.getTypeId());
		if (cardTypeOpt.isEmpty())
			throw new NotFoundException("Resource not found with id: " + cardDto.getTypeId());
		card.setCardType(cardTypeOpt.get());

		return cardDao.save(card);
	}

	public CardEntity findById(Integer id) throws IllegalArgumentException, NotFoundException {
		Optional<CardEntity> result = cardDao.findById(id);
		if (result.isEmpty()) {
			throw new NotFoundException("Resource not found with id: " + id);
		}
		return result.get();
	}

	public List<CardTypeEntity> findAllCardTypes() throws IllegalArgumentException, NotFoundException {
		List<CardTypeEntity> cardTypes = cardTypeDao.findAll();
		if (cardTypes.isEmpty()) {
			throw new NotFoundException("Resource not found");
		}
		return cardTypes;
	}

	public void deleteById(Integer id) throws IllegalArgumentException, NotFoundException {

		if (!cardDao.existsById(id)) {
			throw new NotFoundException("Resource not found with id: " + id);
		}
		cardDao.deleteById(id);
	}

	public List<CardEntity> findAllCards() throws NotFoundException {
		List<CardEntity> cards = cardDao.findAll();
		if (cards.isEmpty()) {
			throw new NotFoundException("Resource not found");
		}
		return cards;
	}

}
