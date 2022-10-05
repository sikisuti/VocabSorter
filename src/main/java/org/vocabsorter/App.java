package org.vocabsorter;

import com.opencsv.bean.CsvToBeanBuilder;
import lombok.extern.java.Log;
import org.vocabsorter.client.VocabClient;
import org.vocabsorter.constant.Level;
import org.vocabsorter.model.Word;
import org.vocabsorter.service.SubTopicsParser;
import org.vocabsorter.service.TopicsParser;
import org.vocabsorter.service.WordService;
import org.vocabsorter.service.WordsGroupParser;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.nio.charset.StandardCharsets.UTF_8;
import static org.vocabsorter.constant.Level.C2;

@Log
public class App {
  private static final Level LEVEL = C2;

  public static void main(String[] args) throws IOException {
    var vocabClient =
        new VocabClient(
            "https://www.angolszokincs.com",
            new TopicsParser(),
            new SubTopicsParser(),
            new WordsGroupParser());
    var wordService = new WordService(vocabClient);
    log.info("Gather started...");
    wordService.getWordsForLevel(LEVEL);
    constructSQL();
  }

  static void constructSQL() throws IOException {
    var words = new ArrayList<Word>();

    var files =
        Optional.ofNullable(new File("csv").listFiles())
            .map(
                csvFiles ->
                    Stream.of(csvFiles)
                        .filter(file -> !file.isDirectory())
                        .collect(Collectors.toList()))
            .orElse(List.of());

    for (var file : files) {
      try (var reader = Files.newBufferedReader(file.toPath())) {
        var cb = new CsvToBeanBuilder<Word>(reader).withType(Word.class).build().parse();
        words.addAll(cb);
      }
    }

    String query =
        "USE LearnWords;\nINSERT INTO words (foreignWord, native, audioFile, levelID) VALUES\n"
            + words.stream().map(Word::toString).collect(Collectors.joining(",\n"))
            + ";";

    Files.write(Paths.get("insertWords" + LEVEL.name() + ".sql"), query.getBytes(UTF_8));
  }
}
