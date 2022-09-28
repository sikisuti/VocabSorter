package org.vocabsorter.service;

import com.opencsv.CSVWriter;
import com.opencsv.bean.StatefulBeanToCsvBuilder;
import lombok.SneakyThrows;
import org.jsoup.Jsoup;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.vocabsorter.model.Word;

import java.io.FileWriter;
import java.util.List;

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

  @Test
  @Disabled
  @SneakyThrows
  void csvWriteTest() {
    var words =
        List.of(
            Word.builder().foreignWord("book").nativeWord("könyv").audioFile("xy.mp3").build(),
            Word.builder().foreignWord("old").nativeWord("öreg, idős").audioFile("xy.mp3").build());
    try (var writer = new FileWriter("words.csv")) {
      var sbc =
          new StatefulBeanToCsvBuilder<Word>(writer)
              .withSeparator(CSVWriter.DEFAULT_SEPARATOR)
              .build();
      sbc.write(words);
    }
  }
}
