package org.vocabsorter.service;

import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.vocabsorter.model.Word;

import java.util.ArrayList;
import java.util.List;

public class WordsGroupParser {

  public List<Word> parse(Document doc) {
    var words = new ArrayList<Word>();

    var wordElements = doc.select("table.etable tr");
    parseWordElements(wordElements, words);
    var phraseBoxElements = doc.select("p.jelsor").parents();
    parsePhraseElements(phraseBoxElements, words);

    return words;
  }

  private void parseWordElements(Elements wordElements, List<Word> words) {
    wordElements.forEach(
        wordElement ->
            words.add(
                Word.builder()
                    .foreignWord(wordElement.select("span.be").first().wholeOwnText().trim())
                    .nativeWord(
                        wordElement.select("td").get(2).text().replaceAll("\\(.+?\\)", "").trim())
                    .audioFile(wordElement.select("source").first().attr("src").trim())
                    .build()));
  }

  private void parsePhraseElements(Elements phraseBoxElements, List<Word> words) {
    for (var phraseBoxElement : phraseBoxElements) {
      var audioFile = "";
      for (var child : phraseBoxElement.children()) {
        if (child.tagName().equalsIgnoreCase("audio")) {
          audioFile = child.select("source").first().attr("src");
        } else if (child.hasClass("jelsor")) {
          var foreignPhrase = child.select("a").first().text().trim();
          var nativePhraseBuilder = new StringBuilder(child.ownText());
          var nativePhrases = phraseBoxElement.select("p.jelsor > span");
          nativePhrases.forEach(
              nativePhrase -> nativePhraseBuilder.append(" " + nativePhrase.text()));
          words.add(
              Word.builder()
                  .foreignWord(foreignPhrase)
                  .nativeWord(nativePhraseBuilder.toString().replaceAll("\\(.+\\)", "").trim())
                  .audioFile(audioFile)
                  .build());
        }
      }
    }
  }
}
