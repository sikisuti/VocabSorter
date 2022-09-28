package org.vocabsorter.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.vocabsorter.constant.Level;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Word {
  String nativeWord;
  String foreignWord;
  String audioFile;
  Level level;

  @Override
  public String toString() {
    return "(\""
        + foreignWord
        + "\",\""
        + nativeWord
        + "\",\"https://www.angolszokincs.com"
        + audioFile
        + "\","
        + level.getNumber()
        + ")";
  }
}
