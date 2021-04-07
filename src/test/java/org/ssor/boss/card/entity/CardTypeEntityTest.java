/**
 * 
 */
package org.ssor.boss.card.entity;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

/**
 * @author Derrian Harris
 *
 */
public class CardTypeEntityTest {
	@Test
	  void test_CanCreateEmptyCardType(){
		CardTypeEntity cardType = new CardTypeEntity();
	    assertThat(cardType).isNotNull();
	  }
	
	
	@Test
	  void test_CanCreateAllArgsCardType(){
		CardTypeEntity cardType = new CardTypeEntity(1,"Debit");
	    assertThat(cardType).isNotNull();
	    
	  }
	
	@Test
	  void test_CanEvalEqual(){
		CardTypeEntity cardTypeA = new CardTypeEntity(1,"Debit");
		CardTypeEntity cardTypeB = new CardTypeEntity(1,"Debit");
		CardTypeEntity cardTypeC = new CardTypeEntity(2, "Credit");
		assertThat(cardTypeA).isEqualTo(cardTypeB);
		assertThat(cardTypeA).isNotEqualTo(cardTypeC);
	  }
}
