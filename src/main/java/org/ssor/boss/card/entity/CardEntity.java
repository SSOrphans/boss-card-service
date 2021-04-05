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
import javax.persistence.NamedNativeQuery;
import javax.persistence.Table;

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
@Table(name = "loan")
public class CardEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private String id;

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
	
	@Column(name = "active")
	private Boolean active;
	
	@Column(name = "stolen")
	private Boolean stolen;

}
