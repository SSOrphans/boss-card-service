/**
 * 
 */
package org.ssor.boss.card.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;

import org.ssor.boss.card.entity.CardEntity;
import org.ssor.boss.card.entity.CardTypeEntity;

import lombok.Data;

/**
 * @author Derrian Harris
 *
 */
@Data
public class CardDto {
	private Integer id;
	private String numberHash;
	private Integer pin;
	private Integer cvv;
	private Integer accountId;
	private LocalDateTime created;
	private LocalDate activeSince;
	private LocalDate expirationDate;
	private Boolean confirmed;
	private Boolean active;
	private Boolean stolen;
	private Integer typeId;
	
	
	public CardEntity convertToCardEntity() {
		CardEntity card = new CardEntity();
		
		card.setId(id);
		card.setNumberHash(numberHash);
		card.setPin(pin);
		card.setCvv(cvv);
		card.setAccountId(accountId);
		card.setCreated(created);
		card.setActiveSince(activeSince);
		card.setExpirationDate(expirationDate);
		card.setConfirmed(confirmed);
		card.setActive(active);
		card.setStolen(stolen);
		card.setCardType(new CardTypeEntity(typeId,""));
		return card;
	}
}
