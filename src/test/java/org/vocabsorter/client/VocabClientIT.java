package org.vocabsorter.client;

import com.github.tomakehurst.wiremock.junit5.WireMockTest;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.vocabsorter.model.Topic;
import org.vocabsorter.service.SubTopicsParser;
import org.vocabsorter.service.TopicsParser;

import java.io.IOException;

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
      new VocabClient("http://localhost:" + PORT, new TopicsParser(), new SubTopicsParser());

  @Test
  void getTopicsForLevel() throws IOException {
    var res = loadFile("responses/a1Topics.html");
    stubFor(get("/temak/a1.html").willReturn(ok().withBody(res)));

    var response = client.getTopicsOfLevel(A1);

    assertThat(response).hasSize(40);
  }

  @Test
  void getSubTopicsFor() throws IOException {
    var res = loadFile("responses/a1SubTopics1.html");
    stubFor(get("/temak/a1/tema/1.html").willReturn(ok().withBody(res)));

    var response = client.getSubTopicsOfTopic(Topic.builder().link("/temak/a1/tema/1.html").build());

    assertThat(response).hasSize(2);
  }
}
