package org.vocabsorter.service;

import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.vocabsorter.model.Topic;

import java.util.ArrayList;
import java.util.List;

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
          var subTopicLinks = subTopicElement.select("a");
          subTopicLinks.forEach(
              subTopicLink -> {
                topics.add(
                    Topic.builder()
                        .name(subTopicLink.wholeOwnText().replace("*", ""))
                        .link(subTopicLink.attr("href"))
                        .build());
              });
        });
  }
}
