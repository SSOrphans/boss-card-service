/**
 * 
 */
package org.ssor.boss.card.service;

import java.time.LocalDateTime;
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

	public CardEntity add(CardDto cardDto) throws IllegalArgumentException, NotFoundException {

		CardEntity card = cardDto.convertToCardEntity();
		Optional<CardTypeEntity> cardTypeOpt = cardTypeDao.findById(cardDto.getTypeId());
		if (cardTypeOpt.isEmpty())
			throw new NotFoundException("Resource not found with id: " + cardDto.getTypeId());

		card.setCardType(cardTypeOpt.get());
		card.setCreated(LocalDateTime.now());
		return cardDao.save(card);
	}
	
	public CardEntity update(CardDto cardDto) throws IllegalArgumentException, NotFoundException {

		if (cardDto == null) {
			throw new IllegalArgumentException();
		}

		if (!cardDao.existsById(cardDto.getId()))
			throw new NotFoundException("Resource not found with id: " + cardDto.getId());

		CardEntity card = cardDao.getOne(cardDto.getId());
		
		if(cardDto.getNumberHash() != null) {
			card.setNumberHash(cardDto.getNumberHash());
		}
		if(cardDto.getAccountId() != null) {
			card.setAccountId(cardDto.getAccountId());
		}
		if(cardDto.getCreated() != null) {
			card.setCreated(cardDto.getCreated());
		}
		if(cardDto.getActiveSince() != null) {
			card.setActiveSince(cardDto.getActiveSince());
		}
		if(cardDto.getExpirationDate() != null) {
			card.setExpirationDate(cardDto.getExpirationDate());
		}
		if(cardDto.getPin() != null) {
			card.setPin(cardDto.getPin());
		}
		if(cardDto.getCvv() != null) {
			card.setCvv(cardDto.getCvv());
		}
		if(cardDto.getConfirmed() != null) {
			card.setConfirmed(cardDto.getConfirmed());
		}
		if(cardDto.getActive() != null) {
			card.setActive(cardDto.getActive());
		}
		if(cardDto.getStolen() != null) {
			card.setStolen(cardDto.getStolen());
		}
		
		if (cardDto.getTypeId() != null) {
			Optional<CardTypeEntity> cardTypeOpt = cardTypeDao.findById(cardDto.getTypeId());
			if (cardTypeOpt.isEmpty())
				throw new NotFoundException("Resource not found with id: " + cardDto.getTypeId());
			card.setCardType(cardTypeOpt.get());
		}

		return cardDao.save(card);
	}

	public CardEntity findById(Integer id) throws IllegalArgumentException, NotFoundException {
		Optional<CardEntity> result = cardDao.findById(id);
		if (result.isEmpty()) {
			throw new NotFoundException("Resource not found with id: " + id);
		}
		return result.get();
	}
	
	public void deleteById(Integer id) throws IllegalArgumentException, NotFoundException {

		if (!cardDao.existsById(id)) {
			throw new NotFoundException("Resource not found with id: " + id);
		}
		cardDao.deleteById(id);
	}

}
