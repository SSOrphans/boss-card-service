/**
 * 
 */
package org.ssor.boss.card.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;

import javax.validation.constraints.NotNull;

import org.ssor.boss.card.entity.CardEntity;
import org.ssor.boss.card.entity.CardTypeEntity;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

/**
 * @author Derrian Harris
 *
 */
@Data
@NoArgsConstructor
public class CardDto {
	@NotNull
	private Integer id;
	@NotNull
	private String numberHash;
	@NotNull
	private Integer pin;
	@NotNull
	private Integer cvv;
	@NotNull
	private Integer accountId;
	@NotNull
	private LocalDateTime created;
	@NotNull
	private LocalDate activeSince;
	@NotNull
	private LocalDate expirationDate;
	@NotNull
	private Boolean confirmed;
	@NotNull
	private Boolean active;
	@NotNull
	private Boolean stolen;
	@NotNull
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
