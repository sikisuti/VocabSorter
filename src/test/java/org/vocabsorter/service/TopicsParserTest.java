package org.vocabsorter.service;

import org.jsoup.Jsoup;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.vocabsorter.util.TestHelper.loadFile;

class TopicsParserTest {
  private final TopicsParser topicsParser = new TopicsParser();

  @Test
  void parse() {
    var response = loadFile("responses/a1Topics.html");
    var doc = Jsoup.parse(response);
    var topics = topicsParser.parse(doc);

    assertThat(topics).hasSize(40);
  }
}
