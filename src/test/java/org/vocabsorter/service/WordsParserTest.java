package org.vocabsorter.service;

import org.jsoup.Jsoup;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.vocabsorter.util.TestHelper.loadFile;

class WordsParserTest {
  private final WordsParser wordsParser = new WordsParser();

  @Test
  void parse() {
    var response = loadFile("responses/a1SubTopics1Words.html");
    var doc = Jsoup.parse(response);
    var words = wordsParser.parse(doc);

    assertThat(words).hasSize(15);
  }
}
