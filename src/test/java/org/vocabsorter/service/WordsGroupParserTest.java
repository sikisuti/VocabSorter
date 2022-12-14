package org.vocabsorter.service;

import com.opencsv.bean.CsvToBeanBuilder;
import lombok.extern.java.Log;
import org.jsoup.Jsoup;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.vocabsorter.model.Word;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.nio.charset.StandardCharsets.UTF_8;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.vocabsorter.util.TestHelper.loadFile;

@Log
class WordsGroupParserTest {
  private final WordsGroupParser wordsGroupParser = new WordsGroupParser();

  @ParameterizedTest
  @MethodSource("testInput")
  void parse(String filePath, int resultCount) {
    var response = loadFile(filePath);
    var doc = Jsoup.parse(response);
    var words = wordsGroupParser.parse(doc);

    assertAll(
        () -> {
          assertThat(words).hasSize(resultCount);
          assertTrue(words.stream().noneMatch(w -> w.getNativeWord().isEmpty()));
        });
  }

  @Test
  @Disabled
  void constructSQL() throws IOException {
    var words = new ArrayList<Word>();

    var files =
        Stream.of(new File("csv").listFiles())
            .filter(file -> !file.isDirectory())
            .collect(Collectors.toList());

    for (var file : files) {
      try (var reader = Files.newBufferedReader(file.toPath())) {
        var cb = new CsvToBeanBuilder<Word>(reader).withType(Word.class).build().parse();
        words.addAll(cb);
      }
    }

    var query =
        new StringBuilder(
            "USE LearnWords;\nINSERT INTO words (foreignWord, native, audioFile, levelID) VALUES\n");
    query.append(words.stream().map(Word::toString).collect(Collectors.joining(",\n")));
    query.append(";");

    Files.write(Paths.get("insertWordsB1.sql"), query.toString().getBytes(UTF_8));

    assertThat(words).isNotEmpty();
  }

  private static Stream<Arguments> testInput() {
    return Stream.of(
        Arguments.of("responses/a1SubTopics1Words.html", 16),
        Arguments.of("responses/a1SubTopics2Words.html", 21));
  }
}
