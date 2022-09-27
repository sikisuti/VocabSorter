package org.vocabsorter.service;

import org.jsoup.Jsoup;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.vocabsorter.util.TestHelper.loadFile;

class SubTopicsParserTest {
  private final SubTopicsParser subTopicsParser = new SubTopicsParser();

  @Test
  void parse() {
    var response = loadFile("responses/a1SubTopics1.html");
    var doc = Jsoup.parse(response);
    var subTopics = subTopicsParser.parse(doc);

    assertThat(subTopics).hasSize(2);
  }
}
