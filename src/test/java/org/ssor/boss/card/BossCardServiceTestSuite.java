/**
 * 
 */
package org.ssor.boss.card;

import org.junit.platform.runner.JUnitPlatform;
import org.junit.platform.suite.api.SelectClasses;
import org.junit.runner.RunWith;
import org.ssor.boss.card.controller.CardControllerTest;
import org.ssor.boss.card.dto.CardDtoTest;
import org.ssor.boss.card.entity.CardEntityTest;
import org.ssor.boss.card.entity.CardTypeEntityTest;
import org.ssor.boss.card.repository.CardRepositoryTest;
import org.ssor.boss.card.repository.CardTypeRepositoryTest;
import org.ssor.boss.card.service.CardServiceTest;

/**
 * @author Derrian Harris
 *
 */
@RunWith(JUnitPlatform.class)
@SelectClasses({CardRepositoryTest.class,CardTypeRepositoryTest.class,CardControllerTest.class,CardServiceTest.class,BossCardServiceApplicationTests.class,CardTypeEntityTest.class,CardEntityTest.class,CardDtoTest.class})
public class BossCardServiceTestSuite {

}
