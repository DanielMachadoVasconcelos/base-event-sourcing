package br.ead.home.controllers;

import br.ead.home.commands.OpenAccountCommand;
import br.ead.home.model.AccountType;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class BankAccountControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @DisplayName("Should open a Current Bank Account when requested")
    void shouldOpenAnCurrentBankAccountWhenRequested() throws Exception {
        // given: the account details
        var expectedAccountHolder = "daniel.vasconcelos";
        var expectedAccountType = AccountType.CURRENT;
        var expectedOpeningBalance = 100.0;
        var command = new OpenAccountCommand(expectedAccountHolder, expectedAccountType, expectedOpeningBalance);

        // and: an open account request
        var request = post("/api/v1/bank-accounts")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(command));

        // when: performing the request
        var response = mvc.perform(request);

        // then:
        response.andDo(print())
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.message").value("Bank Account creation request completed successfully!"));
    }
}