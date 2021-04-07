/**
 * 
 */
package org.ssor.boss.card.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
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
	
	@GetMapping(path = "/types",produces = { "application/json" })
	public ResponseEntity<Object> getCardTypes() {
		List<CardTypeEntity> cardTypes = new ArrayList<CardTypeEntity>();
		try {
			cardTypes = cardService.findAllCardTypes();
		}catch (NotFoundException e) {
			return new ResponseEntity<Object>(cardTypes, HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<Object>(cardTypes,HttpStatus.OK);
	}
	
	@PostMapping(path = "",produces = { "application/json" }, consumes = { "application/json","application/xml" })
	public ResponseEntity<Object> addCard(@RequestBody CardDto cardDto) {
		CardEntity card;
		try
	    {
			card = cardService.add(cardDto);
	    }
	    catch (IllegalArgumentException | NotFoundException e)
	    {
	      return new ResponseEntity<Object>(HttpStatus.BAD_REQUEST);
	    }
		return new ResponseEntity<Object>(card, HttpStatus.CREATED);
	}
	
	@PutMapping(path = "",produces = { "application/json" }, consumes = { "application/json","application/xml" })
	public ResponseEntity<Object> updateCard(@RequestBody CardDto cardDto) {
		CardEntity newCard;
		try
	    {
			newCard = cardService.update(cardDto);
	    }
	    catch (IllegalArgumentException e)
	    {
	      return new ResponseEntity<Object>(HttpStatus.BAD_REQUEST);
	    } catch (NotFoundException e) {
	    	return new ResponseEntity<Object>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<Object>(newCard, HttpStatus.OK);
		
	}
	
	@DeleteMapping(path = "", consumes = { "application/json","application/xml" })
	public  ResponseEntity<Object> deleteCardById(@RequestBody CardEntity card) {
		try
	    {
			cardService.deleteById(card.getId());
	    }
		catch (IllegalArgumentException e)
	    {
	      return new ResponseEntity<Object>(HttpStatus.BAD_REQUEST);
	    } catch (NotFoundException e) {
	    	return new ResponseEntity<Object>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<Object>(HttpStatus.OK);
	}
}
