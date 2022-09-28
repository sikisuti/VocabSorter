package org.vocabsorter.constant;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum Level {
  A1("/temak/a1.html", 1),
  A2("/temak/a2.html", 2),
  B1("/temak/b1.html", 3),
  B2("/temak/b2.html", 4),
  C1("/temak/c1.html", 5),
  C2("/temak/c2.html", 6);

    @Getter
    private final String topicsPath;
  @Getter private final int number;
}
