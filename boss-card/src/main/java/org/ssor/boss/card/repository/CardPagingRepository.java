package org.ssor.boss.card.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import org.ssor.boss.core.entity.Card;
import org.ssor.boss.core.entity.CardType;

/**
 * Repository capable of paging card specific data from the database.
 *
 * @author John Christman
 */
@Repository
public interface CardPagingRepository extends PagingAndSortingRepository<Card, Integer>
{
  Page<Card> findAllByUserIdAndLastFourStartsWithAndTypeIs(int userId, String lastFour, CardType type, Pageable pageable);
  Page<Card> findAllByAccountIdAndLastFourStartsWithAndTypeIs(int accountId, String lastFour, CardType type, Pageable pageable);
}
