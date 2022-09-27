package org.vocabsorter;

import org.vocabsorter.client.VocabClient;
import org.vocabsorter.service.SubTopicsParser;
import org.vocabsorter.service.TopicsParser;
import org.vocabsorter.service.WordService;
import org.vocabsorter.service.WordsParser;

import static org.vocabsorter.constant.Level.A1;

/** Hello world! */
public class App {
  public static void main(String[] args) {
    var vocabClient =
        new VocabClient(
            "https://www.angolszokincs.com",
            new TopicsParser(),
            new SubTopicsParser(),
            new WordsParser());
    var wordService = new WordService(vocabClient);
    wordService.getWordsForLevel(A1);
  }
}
