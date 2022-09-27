package org.vocabsorter.model;

import lombok.Builder;
import lombok.Singular;
import lombok.Value;

import java.util.List;

@Value
@Builder
public class Topic {
    String name;
    String link;
    @Singular List<Word> words;
}
