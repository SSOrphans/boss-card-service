/**
 * 
 */
package org.ssor.boss.card.controller;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDate;
import java.time.LocalDateTime;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.ssor.boss.card.entity.CardEntity;
import org.ssor.boss.card.entity.CardTypeEntity;
import org.ssor.boss.card.service.CardService;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * @author Derrian Harris
 *
 */
@WebMvcTest(CardController.class)
public class CardControllerTest {

	@Autowired
	private MockMvc mvc;

	@Autowired
	ObjectMapper mapper;

	@MockBean
	private CardService cardService;

	private CardEntity cardA;
	private CardEntity cardE;

	@BeforeEach
	public void setup() {
		
		CardTypeEntity cardType = new CardTypeEntity(1,"Debit");
		LocalDateTime currTime = LocalDateTime.now();
		cardA = new CardEntity();
		cardE = new CardEntity();
		
		
		cardA.setId(1);
		cardA.setCardType(cardType);
		cardA.setNumberHash("1234123412341234");
		cardA.setAccountId(1);
		cardA.setCreated(currTime);
		cardA.setExpirationDate(LocalDate.of(2025, 1, 1));
		cardA.setPin(1111);
		cardA.setCvv(111);
		cardA.setActive(false);
		cardA.setConfirmed(false);
		cardA.setStolen(false);
		
		cardE.setId(1);
		cardE.setCardType(cardType);
		cardE.setNumberHash("1234123412341234");
		cardE.setAccountId(1);
		cardE.setCreated(currTime);
		cardE.setExpirationDate(LocalDate.of(2025, 1, 1));
		cardE.setPin(1111);
		cardE.setCvv(111);
		cardE.setActive(false);
		cardE.setConfirmed(false);
		cardE.setStolen(false);
	}
	
	
	@Test
	public void test_CanAdd() throws Exception {
		when(cardService.add(cardA.convertToCardDto())).thenReturn(cardA);

		mvc.perform(post("/api/cards").contentType(MediaType.APPLICATION_JSON)
				.content(mapper.writeValueAsString(cardA.convertToCardDto()))).andExpect(status().isCreated())
				.andExpect(content().json(mapper.writeValueAsString(cardE)));
	}
}
