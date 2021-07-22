/**
 *
 */
package org.ssor.boss.card.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import javassist.NotFoundException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.ssor.boss.card.service.CardService;
import org.ssor.boss.core.entity.Card;
import org.ssor.boss.core.entity.CardType;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;

/**
 * @author Derrian Harris
 *
 */
@SpringBootTest
@AutoConfigureMockMvc
class CardControllerTest
{

  @Autowired
  ObjectMapper mapper;
  @Autowired
  private MockMvc mvc;

  @MockBean
  private CardService cardService;

  private Card cardA;
  private Card cardE;
  private MockHttpServletResponse mockResponse;
  private String expectedContent;

  @BeforeEach
  void setup()
  {
    final var created = Instant.now().toEpochMilli();
    final var expirationDate = LocalDate.of(2025, 1, 1).atStartOfDay(ZoneId.systemDefault()).toInstant().toEpochMilli();
    cardA = new Card();
    cardE = new Card();

    cardA.setId(1);
    cardA.setType(CardType.CARD_PLAIN);
    cardA.setNumberHash("1234123412341234");
    cardA.setAccountId(1);
    cardA.setCreated(created);
    cardA.setActiveSince(created);
    cardA.setExpirationDate(expirationDate);
    cardA.setPin("1111");
    cardA.setCvv("111");
    cardA.setActive(false);

    cardA.setConfirmed(false);
    cardA.setStolen(false);

    cardE.setId(1);
    cardE.setType(CardType.CARD_PLAIN);
    cardE.setNumberHash("1234123412341234");
    cardE.setAccountId(1);
    cardE.setCreated(created);
    cardE.setActiveSince(created);
    cardE.setExpirationDate(expirationDate);
    cardE.setPin("1111");
    cardE.setCvv("111");
    cardE.setActive(false);
    cardE.setConfirmed(false);
    cardE.setStolen(false);
  }

  @AfterEach
  void breakDown()
  {
    mockResponse = null;
    expectedContent = null;
  }

  @Test
  void test_CanGetCardById() throws Exception
  {
    expectedContent = mapper.writeValueAsString(cardE);
    when(cardService.findById(cardA.getId())).thenReturn(cardA);
    mockResponse = mvc.perform(get("/api/cards/1")).andReturn().getResponse();

    assertEquals(HttpStatus.OK.value(), mockResponse.getStatus());
    assertEquals(expectedContent, mockResponse.getContentAsString());
  }

  @Test
  void test_CanGetCardById_NotFoundException() throws Exception
  {
    when(cardService.findById(cardA.getId())).thenThrow(new NotFoundException("test"));
    mockResponse = mvc.perform(get("/api/cards/1")).andReturn().getResponse();

    assertEquals(HttpStatus.NOT_FOUND.value(), mockResponse.getStatus());
  }

  @Test
  void test_CanAddCard() throws Exception
  {
    when(cardService.add(cardA.convertToCardDto())).thenReturn(cardA);

    expectedContent = mapper.writeValueAsString(cardE);

    mockResponse = mvc.perform(post("/api/cards").contentType(MediaType.APPLICATION_JSON)
                                                 .content(mapper.writeValueAsString(cardA.convertToCardDto())))
                      .andReturn().getResponse();

    assertEquals(HttpStatus.CREATED.value(), mockResponse.getStatus());
    assertEquals(expectedContent, mockResponse.getContentAsString());
  }

  @Test
  void test_CanAddCard_BadRequest() throws Exception
  {
    when(cardService.add(cardA.convertToCardDto())).thenThrow(new IllegalArgumentException());

    mockResponse = mvc.perform(post("/api/cards").contentType(MediaType.APPLICATION_JSON)
                                                 .content(mapper.writeValueAsString(cardA.convertToCardDto())))
                      .andReturn().getResponse();

    assertEquals(HttpStatus.BAD_REQUEST.value(), mockResponse.getStatus());
  }

  @Test
  void test_CanUpdateCard() throws Exception
  {
    when(cardService.update(cardA.convertToCardDto())).thenReturn(cardA);

    expectedContent = mapper.writeValueAsString(cardE);

    mockResponse = mvc.perform(put("/api/cards/1").contentType(MediaType.APPLICATION_JSON)
                                                  .content(mapper.writeValueAsString(cardA.convertToCardDto())))
                      .andReturn().getResponse();

    assertEquals(HttpStatus.OK.value(), mockResponse.getStatus());
    assertEquals(expectedContent, mockResponse.getContentAsString());
  }

  @Test
  void test_CanUpdateCard_NotFound() throws Exception
  {
    when(cardService.update(cardA.convertToCardDto())).thenThrow(new NotFoundException("test"));

    mockResponse = mvc.perform(put("/api/cards/1").contentType(MediaType.APPLICATION_JSON)
                                                  .content(mapper.writeValueAsString(cardA.convertToCardDto())))
                      .andReturn().getResponse();

    assertEquals(HttpStatus.NOT_FOUND.value(), mockResponse.getStatus());
  }

  @Test
  void test_CanUpdateCard_BadRequest() throws Exception
  {
    when(cardService.update(cardA.convertToCardDto())).thenThrow(new IllegalArgumentException());

    mockResponse = mvc.perform(put("/api/cards/1").contentType(MediaType.APPLICATION_JSON)
                                                  .content(mapper.writeValueAsString(cardA.convertToCardDto())))
                      .andReturn().getResponse();

    assertEquals(HttpStatus.BAD_REQUEST.value(), mockResponse.getStatus());
  }

  @Test
  void test_CanDeleteCardById() throws Exception
  {
    doNothing().when(cardService).deleteById(any(Integer.class));

    mockResponse = mvc
        .perform(
            delete("/api/cards/1").contentType(MediaType.APPLICATION_JSON).content(mapper.writeValueAsString(cardA)))
        .andReturn().getResponse();

    assertEquals(HttpStatus.OK.value(), mockResponse.getStatus());
  }

  @Test
  void test_CanDeleteCardById_NotFound() throws Exception
  {
    doThrow(new NotFoundException("test")).when(cardService).deleteById(any(Integer.class));

    mockResponse = mvc
        .perform(
            delete("/api/cards/1").contentType(MediaType.APPLICATION_JSON).content(mapper.writeValueAsString(cardA)))
        .andReturn().getResponse();

    assertEquals(HttpStatus.NOT_FOUND.value(), mockResponse.getStatus());
  }

  @Test
  void test_CanDeleteCardById_BadRequest() throws Exception
  {
    doThrow(new IllegalArgumentException()).when(cardService).deleteById(any(Integer.class));

    mockResponse = mvc
        .perform(
            delete("/api/cards/1").contentType(MediaType.APPLICATION_JSON).content(mapper.writeValueAsString(cardA)))
        .andReturn().getResponse();

    assertEquals(HttpStatus.BAD_REQUEST.value(), mockResponse.getStatus());
  }
}
