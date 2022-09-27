package org.vocabsorter.service;

import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.vocabsorter.model.Topic;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class SubTopicsParser {

  public List<Topic> parse(Document doc) {
    var subTopics = new ArrayList<Topic>();

    var subTopicElements = doc.select("dd");
    parseSubTopics(subTopicElements, subTopics);

    return subTopics;
  }

  private void parseSubTopics(Elements subTopicElements, List<Topic> topics) {
    subTopicElements.forEach(
        subTopicElement -> {
          var subTopicLink = subTopicElement.select("a").first();
          Optional.ofNullable(subTopicLink)
              .ifPresent(
                  link -> {
                      topics.add(
                          Topic.builder()
                              .name(subTopicLink.wholeOwnText().replace("*", ""))
                              .link(subTopicLink.attr("href"))
                              .build());
                  });
        });
  }
}
