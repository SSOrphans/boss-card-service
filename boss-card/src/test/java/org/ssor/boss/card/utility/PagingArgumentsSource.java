package org.ssor.boss.card.utility;

import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;
import org.ssor.boss.core.entity.FilterParams;
import java.util.stream.Stream;

public class PagingArgumentsSource
{
  private static final FilterParams allElements = new FilterParams("", "", 0, -1, "id", "asc");
  private static final FilterParams noFilterPage = new FilterParams("", "", 0, 5, "id", "asc");
  private static final FilterParams filteredPage = new FilterParams("", "CARD_PLAIN", 0, 5, "id", "asc");
  private static final FilterParams keywordPage = new FilterParams("4", "", 0, 5, "id", "asc");

  public static class FilterOnly implements ArgumentsProvider
  {
    @Override
    public Stream<? extends Arguments> provideArguments(ExtensionContext extensionContext) throws Exception
    {
      return Stream.of(Arguments.of(allElements), Arguments.of(noFilterPage), Arguments.of(filteredPage),
                       Arguments.of(keywordPage));
    }
  }

  public static class IdsAndFilter implements ArgumentsProvider
  {
    @Override
    public Stream<? extends Arguments> provideArguments(ExtensionContext extensionContext) throws Exception
    {
      // The first one is designed to fail.
      return Stream.of(Arguments.of(0, allElements), Arguments.of(1, noFilterPage), Arguments.of(1, filteredPage),
                       Arguments.of(1, keywordPage));
    }
  }
}
