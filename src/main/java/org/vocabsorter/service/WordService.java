package org.vocabsorter.service;

import com.opencsv.bean.StatefulBeanToCsvBuilder;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.java.Log;
import org.vocabsorter.client.VocabClient;
import org.vocabsorter.constant.Level;
import org.vocabsorter.model.Topic;
import org.vocabsorter.model.Word;

import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static com.opencsv.ICSVWriter.DEFAULT_SEPARATOR;

@Log
@RequiredArgsConstructor
public class WordService {
  private final VocabClient vocabClient;

  public List<Word> getWordsForLevel(Level level) {
    var words = new ArrayList<Word>();
    var topics = vocabClient.getTopicsOfLevel(level);

    topics.forEach(topic -> words.addAll(getWordsOfTopics(topic, level)));

    return words;
  }

  private List<Word> getWordsOfTopics(Topic topic, Level level) {
    var words = new ArrayList<Word>();
    var subTopics = vocabClient.getSubTopicsOfTopic(topic);
    subTopics.forEach(subTopic -> words.addAll(getWordsOfSubTopic(subTopic)));

    words.forEach(w -> w.setLevel(level));
    writeToCsv(topic.getName(), words);
    log.info("----------- " + topic.getName() + ": " + words.size() + " words");

    return words;
  }

  private List<Word> getWordsOfSubTopic(Topic subTopic) {
    var wordList = vocabClient.getWordsOfSubTopic(subTopic);
    log.info(wordList.stream().map(Word::getForeignWord).collect(Collectors.joining(", ")));
    return wordList;
  }

  @SneakyThrows
  private void writeToCsv(String topicName, List<Word> words) {
    try (var writer = new FileWriter("csv/" + topicName.replaceAll(",|\\s", "") + ".csv")) {
      var sbc = new StatefulBeanToCsvBuilder<Word>(writer).withSeparator(DEFAULT_SEPARATOR).build();
      sbc.write(words);
    }
  }
}
