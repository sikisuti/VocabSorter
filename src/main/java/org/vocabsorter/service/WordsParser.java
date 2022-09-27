package org.vocabsorter.service;

import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.vocabsorter.model.Word;

import java.util.ArrayList;
import java.util.List;

public class WordsParser {

  public List<Word> parse(Document doc) {
    var words = new ArrayList<Word>();

    var wordElements = doc.select("table.etable tr");
    parseWordElements(wordElements, words);

    return words;
  }

  private void parseWordElements(Elements wordElements, List<Word> words) {
    wordElements.forEach(
        wordElement ->
            words.add(
                Word.builder()
                    .foreignWord(wordElement.select("span.be").first().wholeOwnText().trim())
                    .nativeWord(
                        wordElement.select("td").get(2).text().replaceAll("\\(\\d+\\)", "").trim())
                    .audioFile(wordElement.select("source").first().attr("src").trim())
                    .build()));
  }
}
