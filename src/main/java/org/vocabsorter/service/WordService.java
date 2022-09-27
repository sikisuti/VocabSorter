package org.vocabsorter.service;

import lombok.RequiredArgsConstructor;
import org.vocabsorter.client.VocabClient;
import org.vocabsorter.constant.Level;
import org.vocabsorter.model.Word;

import java.io.IOException;
import java.util.List;

@RequiredArgsConstructor
public class WordService {
  private final VocabClient vocabClient;

  public List<Word> getWordsForLevel(Level level) {
    var topics = vocabClient.getTopicsOfLevel(level);
    topics.forEach(topic -> {
      var subTopics = vocabClient.getSubTopicsOfTopic(topic);
      subTopics.forEach(subTopic -> {

      });
    });

    return null;
  }
}
