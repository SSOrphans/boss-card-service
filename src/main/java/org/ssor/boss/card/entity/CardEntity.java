/**
 * 
 */
package org.ssor.boss.card.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedNativeQuery;
import javax.persistence.Table;

import org.ssor.boss.card.dto.CardDto;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * @author Derrian Harris
 *
 */
@Entity
@Getter
@Setter
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Table(name = "card")
public class CardEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	
	@Column(name = "number_hash")
	private String numberHash;
	
	@Column(name = "account_id")
	private Integer accountId;
	
	@Column(name = "created")
	private LocalDateTime created;
	
	@Column(name = "active_since")
	private LocalDate activeSince;
	
	@Column(name = "expiration_date")
	private LocalDate expirationDate;
	
	@Column(name = "pin")
	private Integer pin;
	
	@Column(name = "cvv")
	private Integer cvv;
	
	@Column(name = "confirmed")
	private Boolean confirmed;
	
	@Column(name = "active")
	private Boolean active;
	
	@Column(name = "stolen")
	private Boolean stolen;
	
	@ManyToOne
	@JoinColumn(name="type_id", nullable=false)
	private CardTypeEntity cardType;

	
	public CardDto convertToCardDto() {
		CardDto cardDto = new CardDto();
		
		cardDto.setId(id);
		cardDto.setNumberHash(numberHash);
		cardDto.setPin(pin);
		cardDto.setCvv(cvv);
		cardDto.setAccountId(accountId);
		cardDto.setCreated(created);
		cardDto.setActiveSince(activeSince);
		cardDto.setExpirationDate(expirationDate);
		cardDto.setConfirmed(confirmed);
		cardDto.setActive(active);
		cardDto.setStolen(stolen);
		cardDto.setTypeId(cardType.getId());
		return cardDto;
	}
}
