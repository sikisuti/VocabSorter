package org.vocabsorter.service;

import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.vocabsorter.model.Topic;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;

public class TopicsParser {

  public List<Topic> parse(Document doc) {
    var topics = new ArrayList<Topic>();

    var tableElements = doc.select(".teml");
    parseTables(tableElements, topics);

    return topics;
  }

  private void parseTables(Elements tableElements, List<Topic> topics) {
    tableElements.forEach(
        tableElement -> {
          var topicElements = tableElement.select("li");
          parseTopics(topicElements, topics);
        });
  }

  private void parseTopics(Elements topicElements, List<Topic> topics) {
    topicElements.forEach(
        topicElement -> {
          var topicLink = topicElement.select("a").first();
          Optional.ofNullable(topicLink)
              .ifPresent(
                  linkTag ->
                      topics.add(
                          Topic.builder()
                              .name(linkTag.wholeOwnText().replaceAll("^\\d{1,2}\\.\\s", ""))
                              .link(linkTag.attr("href"))
                              .build()));
        });
  }
}
