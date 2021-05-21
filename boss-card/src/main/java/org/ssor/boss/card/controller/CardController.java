/**
 *
 */
package org.ssor.boss.card.controller;

import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.ssor.boss.card.service.CardService;
import org.ssor.boss.core.entity.Card;
import org.ssor.boss.core.transfer.CardDto;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Derrian Harris
 *
 */
@CrossOrigin
@RestController
@RequestMapping("/api/cards")
public class CardController {
    @Autowired
    private CardService cardService;


    @GetMapping(path = "", produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE,
            MediaType.TEXT_PLAIN_VALUE})
    public ResponseEntity<Object> getAllCards() {
        List<Card> cards = new ArrayList<Card>();
        try {
            cards = cardService.findAllCards();
        } catch (NotFoundException e) {
            return new ResponseEntity<Object>("No Cards Found.", HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<Object>(cards, HttpStatus.OK);
    }

    @PostMapping(path = "", produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE,
            MediaType.TEXT_PLAIN_VALUE}, consumes = {MediaType.APPLICATION_JSON_VALUE,
            MediaType.APPLICATION_XML_VALUE, MediaType.TEXT_PLAIN_VALUE})
    public ResponseEntity<Object> addCard(@RequestBody @Valid CardDto cardDto) {
        Card card;
        try {
            card = cardService.add(cardDto);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<Object>("Invalid request data.", HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<Object>(card, HttpStatus.CREATED);
    }

    @PutMapping(path = "/{card_id}", produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE,
            MediaType.TEXT_PLAIN_VALUE}, consumes = {MediaType.APPLICATION_JSON_VALUE,
            MediaType.APPLICATION_XML_VALUE, MediaType.TEXT_PLAIN_VALUE})
    public ResponseEntity<Object> updateCard(@PathVariable("card_id") @Valid String cardId, @RequestBody @Valid CardDto cardDto) {
        Card newCard;
        try {
            cardDto.setId(Integer.parseInt(cardId));
            newCard = cardService.update(cardDto);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<Object>("Invalid request data.", HttpStatus.BAD_REQUEST);
        } catch (NotFoundException e) {
            return new ResponseEntity<Object>("No Card Found.", HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<Object>(newCard, HttpStatus.OK);

    }

    @DeleteMapping(path = "/{card_id}", produces = {MediaType.TEXT_PLAIN_VALUE})
    public ResponseEntity<Object> deleteCardById(@PathVariable("card_id") @Valid String cardId) {
        try {
            cardService.deleteById(Integer.parseInt(cardId));
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<Object>("Invalid request data.", HttpStatus.BAD_REQUEST);
        } catch (NotFoundException e) {
            return new ResponseEntity<Object>("No Card Found.", HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<Object>(HttpStatus.OK);
    }
}
