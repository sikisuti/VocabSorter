package org.vocabsorter;

import lombok.extern.java.Log;
import org.vocabsorter.client.VocabClient;
import org.vocabsorter.service.SubTopicsParser;
import org.vocabsorter.service.TopicsParser;
import org.vocabsorter.service.WordService;
import org.vocabsorter.service.WordsGroupParser;

import static org.vocabsorter.constant.Level.A2;

@Log
public class App {
  public static void main(String[] args) {
    var vocabClient =
        new VocabClient(
            "https://www.angolszokincs.com",
            new TopicsParser(),
            new SubTopicsParser(),
            new WordsGroupParser());
    var wordService = new WordService(vocabClient);
    log.info("Gather started...");
    wordService.getWordsForLevel(A2);
  }
}
