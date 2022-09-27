package org.vocabsorter.model;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class Word {
  String nativeWord;
  String foreignWord;
  String audioFile;
}
