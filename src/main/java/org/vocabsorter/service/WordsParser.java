package org.vocabsorter.service;

import org.jsoup.nodes.Document;
import org.vocabsorter.model.Topic;
import org.vocabsorter.model.Word;

import java.util.ArrayList;
import java.util.List;

public class WordsParser {

    public List<Word> parse(Document doc) {
        var topics = new ArrayList<Topic>();

        var wordElements = doc.select(".teml");
        parseTables(wordElements, topics);

        return topics;
    }
}
