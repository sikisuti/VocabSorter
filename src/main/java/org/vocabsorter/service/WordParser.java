package org.vocabsorter.service;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.vocabsorter.model.Word;

import java.util.ArrayList;
import java.util.List;

// For later use if required
public class WordParser {

  public List<Word> parse(Document doc) {
    var words = new ArrayList<Word>();

    var wordBoxElements = doc.select("h2").parents();
    wordBoxElements.forEach(wordBoxElement -> parseWordBox(wordBoxElement, words));

    return words;
  }

  private void parseWordBox(Element wordBox, List<Word> words) {
    var titleElement = wordBox.select("h2");
    var titleParts = titleElement.select("h2 > span");
    var foreignWord = titleParts.first().text();
    var lexicalCategory = titleParts.get(1).select("span").first().text();
    var pronunciation = titleParts.get(1).ownText();
  }
}
