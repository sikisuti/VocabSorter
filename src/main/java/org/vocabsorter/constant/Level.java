package org.vocabsorter.constant;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Value;

@RequiredArgsConstructor
public enum Level {
    A1("/temak/a1.html"),
    A2("/temak/a2.html"),
    B1("/temak/b1.html"),
    B2("/temak/b2.html"),
    C1("/temak/c1.html"),
    C2("/temak/c2.html");

    @Getter
    private final String topicsPath;
}
