/**
 * 
 */
package org.ssor.boss.card.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

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
	
	List<CardTypeEntity> cardTypesA;
	List<CardTypeEntity> cardTypesE;

	@BeforeEach
	public void setup() {
		
		cardTypesA = new ArrayList<CardTypeEntity>();
		cardTypesA.add(new CardTypeEntity(1,"Debit"));
		cardTypesA.add(new CardTypeEntity(2,"Credit"));
		
		cardTypesE = new ArrayList<CardTypeEntity>();
		cardTypesE.add(new CardTypeEntity(1,"Debit"));
		cardTypesE.add(new CardTypeEntity(2,"Credit"));
		
		LocalDateTime currTime = LocalDateTime.now();
		cardA = new CardEntity();
		cardE = new CardEntity();
		
		
		cardA.setId(1);
		cardA.setCardType(cardTypesA.get(1));
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
		cardE.setCardType(cardTypesA.get(1));
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
	public void test_CanAddCard() throws Exception {
		when(cardService.add(cardA.convertToCardDto())).thenReturn(cardA);

		mvc.perform(post("/api/cards").contentType(MediaType.APPLICATION_JSON)
				.content(mapper.writeValueAsString(cardA.convertToCardDto()))).andExpect(status().isCreated())
				.andExpect(content().json(mapper.writeValueAsString(cardE)));
	}
	
	@Test
	public void test_CanUpdateCard() throws Exception {
		when(cardService.update(cardA.convertToCardDto())).thenReturn(cardA);

		mvc.perform(put("/api/cards").contentType(MediaType.APPLICATION_JSON)
				.content(mapper.writeValueAsString(cardA.convertToCardDto()))).andExpect(status().isOk())
				.andExpect(content().json(mapper.writeValueAsString(cardE)));
	}
	
	@Test
	public void test_CanDeleteCardById() throws Exception {
		doNothing().when(cardService).deleteById(any(Integer.class));
		

		mvc.perform(delete("/api/cards").contentType(MediaType.APPLICATION_JSON)
				.content(mapper.writeValueAsString(cardA))).andExpect(status().isOk());
	}
	
	@Test
	public void test_CanGetAllCardTypes() throws Exception {
		when(cardService.findAllCardTypes()).thenReturn(cardTypesA);

		mvc.perform(get("/api/cards/types").contentType(MediaType.APPLICATION_JSON)
				.content(mapper.writeValueAsString(cardTypesA))).andExpect(status().isOk())
				.andExpect(content().json(mapper.writeValueAsString(cardTypesE)));
	}
}
