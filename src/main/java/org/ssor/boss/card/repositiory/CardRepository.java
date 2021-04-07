/**
 * 
 */
package org.ssor.boss.card.repositiory;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.ssor.boss.card.entity.CardEntity;

/**
 * @author Derrian Harris
 *
 */
@Repository
public interface CardRepository extends JpaRepository<CardEntity,Integer>{

}
