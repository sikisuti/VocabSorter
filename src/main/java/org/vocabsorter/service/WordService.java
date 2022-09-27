package org.vocabsorter.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.vocabsorter.client.VocabClient;
import org.vocabsorter.constant.Level;
import org.vocabsorter.model.Word;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Log
@RequiredArgsConstructor
public class WordService {
  private final VocabClient vocabClient;

  public List<Word> getWordsForLevel(Level level) {
    var words = new ArrayList<Word>();

    var topics = vocabClient.getTopicsOfLevel(level);
    topics.forEach(
        topic -> {
          log.info(topic.getName());
          var subTopics = vocabClient.getSubTopicsOfTopic(topic);
          subTopics.forEach(
              subTopic -> {
                log.info(subTopic.getName());
                var wordList = vocabClient.getWordsOfSubTopic(subTopic);
                log.info(
                    wordList.stream().map(Word::getForeignWord).collect(Collectors.joining(", ")));
                words.addAll(wordList);
              });
        });

    return words;
  }
}
