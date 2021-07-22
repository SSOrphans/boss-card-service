package org.ssor.boss.card.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import org.ssor.boss.core.entity.Card;

/**
 * Repository capable of paging card specific data from the database.
 *
 * @author John Christman
 */
@Repository
public interface CardPagingRepository extends PagingAndSortingRepository<Card, Integer>
{
  Page<Card> findAllByLastFourStartsWith(String lastFour, Pageable pageable);
  Page<Card> findAllByUserIdAndLastFourStartsWith(int userId, String lastFour, Pageable pageable);
  Page<Card> findAllByAccountIdAndLastFourStartsWith(int accountId, String lastFour, Pageable pageable);
}
