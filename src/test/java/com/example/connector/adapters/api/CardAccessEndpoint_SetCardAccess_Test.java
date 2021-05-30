package com.example.connector.adapters.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class CardAccessEndpoint_SetCardAccess_Test {
  private final String BASE_ENDPOINT_URL = "/access";


  @Autowired private MockMvc mockMvc;

  @Test
  void When_RequestWithNoBody_Then_StatusIs400() throws Exception {
    //        given
    //        when
    this.mockMvc
        .perform(post(BASE_ENDPOINT_URL).contentType(MediaType.APPLICATION_JSON))
        //        then
        .andDo(print())
        .andExpect(status().is(HttpStatus.BAD_REQUEST.value()));
  }

  @Test
  void When_RequestWithCorrectParameters_Then_StatusIs200() throws Exception {
    //        given
    //        when
    this.mockMvc
        .perform(
            post(BASE_ENDPOINT_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(createCorrectBodyJson()))
        //        then
        .andDo(print())
        .andExpect(status().is(HttpStatus.OK.value()));
  }

  private String createCorrectBodyJson() {
    final String EXAMPLE_READER_NUMBER = "CR1000";
    final String EXAMPLE_FROM = "2000-01-01T12:30";
    final String EXAMPLE_TO = "2200-01-01T12:30";
    final String EXAMPLE_SOURCE = "source";
    final String EXAMPLE_CARD_NUMBER = "C1000";

    ObjectMapper mapper = new ObjectMapper();

    var cardReader =  mapper.createObjectNode();
    cardReader.put("number", EXAMPLE_READER_NUMBER);

    var cardReaders = mapper.createArrayNode();
    cardReaders.add(cardReader);

    var access = mapper.createObjectNode();
    access.put("from", EXAMPLE_FROM);
    access.put("to", EXAMPLE_TO);

    access.put("source", EXAMPLE_SOURCE);
    access.set("cardReaders", cardReaders);

    var accesses = mapper.createArrayNode();
    accesses.add(access);

    var cardAccess = mapper.createObjectNode();
    cardAccess.put("cardNumber", EXAMPLE_CARD_NUMBER);
    cardAccess.set("accesses", accesses);

    return cardAccess.toString();
  }
}
