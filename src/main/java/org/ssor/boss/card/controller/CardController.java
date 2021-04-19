/**
 * 
 */
package org.ssor.boss.card.controller;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
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
import org.springframework.web.bind.annotation.RestController;
import org.ssor.boss.card.dto.CardDto;
import org.ssor.boss.card.entity.CardEntity;
import org.ssor.boss.card.entity.CardTypeEntity;
import org.ssor.boss.card.service.CardService;
import javassist.NotFoundException;

/**
 * @author Derrian Harris
 *
 */
@RestController
@RequestMapping("/api/cards")
public class CardController {
	@Autowired
	private CardService cardService;

	@GetMapping(path = "/types", produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE,
			MediaType.TEXT_PLAIN_VALUE })
	public ResponseEntity<Object> getCardTypes() {
		List<CardTypeEntity> cardTypes = new ArrayList<CardTypeEntity>();
		try {
			cardTypes = cardService.findAllCardTypes();
		} catch (NotFoundException e) {
			return new ResponseEntity<Object>("No Card Types Found.", HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<Object>(cardTypes, HttpStatus.OK);
	}
	
	@GetMapping(path = "", produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE,
			MediaType.TEXT_PLAIN_VALUE })
	public ResponseEntity<Object> getAllCards() {
		List<CardEntity> cards = new ArrayList<CardEntity>();
		try {
			cards = cardService.findAllCards();
		} catch (NotFoundException e) {
			return new ResponseEntity<Object>("No Cards Found.", HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<Object>(cards, HttpStatus.OK);
	}

	@PostMapping(path = "", produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE,
			MediaType.TEXT_PLAIN_VALUE }, consumes = { MediaType.APPLICATION_JSON_VALUE,
					MediaType.APPLICATION_XML_VALUE, MediaType.TEXT_PLAIN_VALUE })
	public ResponseEntity<Object> addCard(@RequestBody @Valid CardDto cardDto) {
		CardEntity card;
		try {
			card = cardService.add(cardDto);
		} catch (IllegalArgumentException e) {
			return new ResponseEntity<Object>("Invalid request data.", HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<Object>(card, HttpStatus.CREATED);
	}

	@PutMapping(path = "/{card_id}", produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE,
			MediaType.TEXT_PLAIN_VALUE }, consumes = { MediaType.APPLICATION_JSON_VALUE,
					MediaType.APPLICATION_XML_VALUE, MediaType.TEXT_PLAIN_VALUE })
	public ResponseEntity<Object> updateCard(@PathVariable("card_id") @Valid String cardId, @RequestBody @Valid CardDto cardDto) {
		CardEntity newCard;
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

	@DeleteMapping(path = "/{card_id}",produces = { MediaType.TEXT_PLAIN_VALUE })
	public ResponseEntity<Object> deleteCardById( @PathVariable("card_id") @Valid String cardId) {
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
