package org.vocabsorter.client;

import lombok.RequiredArgsConstructor;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.vocabsorter.constant.Level;
import org.vocabsorter.model.Topic;
import org.vocabsorter.model.Word;
import org.vocabsorter.service.SubTopicsParser;
import org.vocabsorter.service.TopicsParser;
import org.vocabsorter.service.WordsGroupParser;

import java.util.List;

@RequiredArgsConstructor
public class VocabClient {
  private final String host;
  private final TopicsParser topicsParser;
  private final SubTopicsParser subTopicsParser;
  private final WordsGroupParser wordsGroupParser;

  public List<Topic> getTopicsOfLevel(Level level) {
    var topicsDoc = call(level.getTopicsPath());
    return topicsParser.parse(topicsDoc);
  }

  public List<Topic> getSubTopicsOfTopic(Topic topic) {
    var subTopicsDoc = call(topic.getLink());
    return subTopicsParser.parse(subTopicsDoc);
  }

  public List<Word> getWordsOfSubTopic(Topic subTopic) {
    var wordsDoc = call(subTopic.getLink());
    return wordsGroupParser.parse(wordsDoc);
  }

  private Document call(String path) {
    var url = host + path;

    try {
      Thread.sleep(5000);
      return Jsoup.connect(url).get();
    } catch (Exception e) {
      throw new RuntimeException();
    }
  }
}
