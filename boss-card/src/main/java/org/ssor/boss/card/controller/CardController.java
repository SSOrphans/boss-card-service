/**
 *
 */
package org.ssor.boss.card.controller;

import javassist.NotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.ssor.boss.card.service.CardService;
import org.ssor.boss.core.entity.Card;
import org.ssor.boss.core.entity.CardType;
import org.ssor.boss.core.entity.FilterParams;
import org.ssor.boss.core.transfer.CardDto;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Derrian Harris
 *
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/cards")
public class CardController
{
  private static final String INVALID_REQ_DATA_STR = "Invalid request data.";
  private static final String NO_CARD_FOUND_STR = "No Cards Found.";
  private final CardService cardService;

  @GetMapping(path = "/{card_id}", produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
  public ResponseEntity<Object> getCard(@PathVariable("card_id") @Valid String cardId)
  {
    Card card;
    try
    {
      card = cardService.findById(Integer.parseInt(cardId));
    }
    catch (NotFoundException e)
    {
      return new ResponseEntity<>("Card not found.", HttpStatus.NOT_FOUND);
    }
    return new ResponseEntity<>(card, HttpStatus.OK);
  }

  @GetMapping(path = "", produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
  public ResponseEntity<Object> getAllCards()
  {
    List<Card> cards;
    try
    {
      cards = cardService.findAllCards();
    }
    catch (NotFoundException e)
    {
      log.debug("Are we ending here?");
      return new ResponseEntity<>(NO_CARD_FOUND_STR, HttpStatus.NOT_FOUND);
    }

    return new ResponseEntity<>(cards, HttpStatus.OK);
  }

  @GetMapping(path = "/api/v1/users/{user_id}/cards",
              produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE },
              consumes = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
  public ResponseEntity<Object> getCardsByUserId(@PathVariable("user_id") int userId,
                                                 @RequestParam(name = "keyword", defaultValue = "") String keyword,
                                                 @RequestParam(name = "filter", defaultValue = "CARD_INVALID") CardType filter,
                                                 @RequestParam(name = "page", defaultValue = "0") int page,
                                                 @RequestParam(name = "limit", defaultValue = "5") int limit,
                                                 @RequestParam(name = "sort", defaultValue = "id") String sortBy,
                                                 @RequestParam(name = "sortDir", defaultValue = "asc") String sortDir)
      throws NotFoundException
  {
    final var filterParams = new FilterParams(keyword, filter.index(), page, limit, sortBy, sortDir);
    final var cards = cardService.findByUserId(userId, filterParams);
    return ResponseEntity.ok(cards);
  }

  @GetMapping(path = "/api/v1/accounts/{account_id}/cards",
              produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE },
              consumes = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
  public ResponseEntity<Object> getCardsByAccountId(@PathVariable("account_id") int accountId,
                                                    @RequestParam(name = "keyword", defaultValue = "") String keyword,
                                                    @RequestParam(name = "filter", defaultValue = "CARD_INVALID") CardType filter,
                                                    @RequestParam(name = "page", defaultValue = "0") int page,
                                                    @RequestParam(name = "limit", defaultValue = "5") int limit,
                                                    @RequestParam(name = "sort", defaultValue = "id") String sortBy,
                                                    @RequestParam(name = "sortDir", defaultValue = "asc") String sortDir)
      throws NotFoundException
  {
    final var filterParams = new FilterParams(keyword, filter.index(), page, limit, sortBy, sortDir);
    final var cards = cardService.findByAccountId(accountId, filterParams);
    return ResponseEntity.ok(cards);
  }

  @PostMapping(path = "",
               produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE },
               consumes = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
  public ResponseEntity<Object> addCard(@RequestBody @Valid CardDto cardDto)
  {
    Card card;
    try
    {
      card = cardService.add(cardDto);
    }
    catch (IllegalArgumentException e)
    {
      return new ResponseEntity<>(INVALID_REQ_DATA_STR, HttpStatus.BAD_REQUEST);
    }
    return new ResponseEntity<>(card, HttpStatus.CREATED);
  }

  @PutMapping(path = "/{card_id}",
              produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE },
              consumes = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
  public ResponseEntity<Object> updateCard(@PathVariable("card_id") @Valid String cardId,
                                           @RequestBody @Valid CardDto cardDto)
  {
    Card newCard;
    try
    {
      cardDto.setId(Integer.parseInt(cardId));
      newCard = cardService.update(cardDto);
    }
    catch (IllegalArgumentException e)
    {
      return new ResponseEntity<>(INVALID_REQ_DATA_STR, HttpStatus.BAD_REQUEST);
    }
    catch (NotFoundException e)
    {
      return new ResponseEntity<>(NO_CARD_FOUND_STR, HttpStatus.NOT_FOUND);
    }
    return new ResponseEntity<>(newCard, HttpStatus.OK);

  }

  @DeleteMapping(path = "/{card_id}", produces = { MediaType.TEXT_PLAIN_VALUE })
  public ResponseEntity<Object> deleteCardById(@PathVariable("card_id") @Valid String cardId)
  {
    try
    {
      cardService.deleteById(Integer.parseInt(cardId));
    }
    catch (IllegalArgumentException e)
    {
      return new ResponseEntity<>(INVALID_REQ_DATA_STR, HttpStatus.BAD_REQUEST);
    }
    catch (NotFoundException e)
    {
      return new ResponseEntity<>(NO_CARD_FOUND_STR, HttpStatus.NOT_FOUND);
    }
    return new ResponseEntity<>(HttpStatus.OK);
  }
}
