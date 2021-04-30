/**
 *
 */
package org.ssor.boss.card.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.ssor.boss.card.service.CardService;
import org.ssor.boss.core.entity.Card;
import org.ssor.boss.core.entity.CardType;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * @author Derrian Harris
 *
 */
@SpringBootTest
@AutoConfigureMockMvc
//@WebMvcTest(CardController.class)
public class CardControllerTest {

    @Autowired
    ObjectMapper mapper;
    @Autowired
    private MockMvc mvc;

    @MockBean
    private CardService cardService;


    private Card cardA;
    private Card cardE;

    @BeforeEach
    public void setup() {

        LocalDateTime created = LocalDateTime.now();
        LocalDate expirationDate = LocalDate.of(2025, 1, 1);
        LocalDate activeSince = created.toLocalDate();
        cardA = new Card();
        cardE = new Card();


        cardA.setId(1);
        cardA.setCardType(CardType.CARD_PLAIN);
        cardA.setNumberHash("1234123412341234");
        cardA.setAccountId(1);
        cardA.setCreated(created);
        cardA.setActiveSince(activeSince);
        cardA.setExpirationDate(expirationDate);
        cardA.setPin(1111);
        cardA.setCvv(111);
        cardA.setActive(false);

        cardA.setConfirmed(false);
        cardA.setStolen(false);

        cardE.setId(1);
        cardE.setCardType(CardType.CARD_PLAIN);
        cardE.setNumberHash("1234123412341234");
        cardE.setAccountId(1);
        cardE.setCreated(created);
        cardE.setActiveSince(activeSince);
        cardE.setExpirationDate(expirationDate);
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

        mvc.perform(put("/api/cards/1").contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(cardA.convertToCardDto()))).andExpect(status().isOk())
                .andExpect(content().json(mapper.writeValueAsString(cardE)));
    }

    @Test
    public void test_CanDeleteCardById() throws Exception {
        doNothing().when(cardService).deleteById(any(Integer.class));
        mvc.perform(delete("/api/cards/1").contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(cardA))).andExpect(status().isOk());
    }
}
