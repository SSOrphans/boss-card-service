package org.ssor.boss.card.transfer;

import lombok.Data;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.ssor.boss.core.transfer.SecureCardDetails;
import java.util.List;

@Data
@RequiredArgsConstructor
public class CardPageResult
{
  @NonNull
  private final List<SecureCardDetails> cards;

  private final int page;
  private final int pages;
  private final int limit;
}
