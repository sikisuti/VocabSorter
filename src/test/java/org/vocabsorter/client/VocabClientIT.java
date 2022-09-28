package org.vocabsorter.client;

import com.github.tomakehurst.wiremock.junit5.WireMockTest;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.vocabsorter.model.Topic;
import org.vocabsorter.service.SubTopicsParser;
import org.vocabsorter.service.TopicsParser;
import org.vocabsorter.service.WordsGroupParser;

import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.ok;
import static com.github.tomakehurst.wiremock.client.WireMock.stubFor;
import static org.assertj.core.api.Assertions.assertThat;
import static org.vocabsorter.client.VocabClientIT.PORT;
import static org.vocabsorter.constant.Level.A1;
import static org.vocabsorter.util.TestHelper.loadFile;

@Slf4j
@WireMockTest(httpPort = PORT)
class VocabClientIT {
  public static final int PORT = 8080;
  private final VocabClient client =
      new VocabClient(
          "http://localhost:" + PORT,
          new TopicsParser(),
          new SubTopicsParser(),
          new WordsGroupParser());

  @Test
  void getTopicsForLevel() {
    var res = loadFile("responses/a1Topics.html");
    stubFor(get("/temak/a1.html").willReturn(ok().withBody(res)));

    var response = client.getTopicsOfLevel(A1);

    assertThat(response).hasSize(40);
  }

  @Test
  void getSubTopicsOfTopic() {
    var res = loadFile("responses/a1SubTopics1.html");
    stubFor(get("/temak/a1/tema/1.html").willReturn(ok().withBody(res)));

    var response = client.getSubTopicsOfTopic(Topic.builder().link("/temak/a1/tema/1.html").build());

    assertThat(response).hasSize(2);
  }

  @Test
  void getWordsOfSubTopic() {
    var res = loadFile("responses/a1SubTopics1Words.html");
    stubFor(get("/temak/a1/tema/1/2/9/szavak.html").willReturn(ok().withBody(res)));

    var response =
        client.getWordsOfSubTopic(Topic.builder().link("/temak/a1/tema/1/2/9/szavak.html").build());

    assertThat(response).hasSize(15);
  }
}
