package org.vocabsorter.util;

import static java.nio.charset.StandardCharsets.UTF_8;

public class TestHelper {
  public static String loadFile(String path) {
    try (var is = TestHelper.class.getClassLoader().getResourceAsStream(path)) {
      if (is != null) {
        return new String(is.readAllBytes(), UTF_8);
      } else {
        throw new RuntimeException();
      }
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }
}
