package br.ead.home.controllers;

import br.ead.home.commands.CloseAccountCommand;
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
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;

import java.util.Map;

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

        //when: the command is dispatched
        var command = new OpenAccountCommand(expectedAccountHolder, expectedAccountType, expectedOpeningBalance);
        var response = openABankAccount(command);

        //then: the account is opened
        response.andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.message").value("Bank Account creation request completed successfully!"))
                .andExpect(jsonPath("$.id").isNotEmpty());
    }

    @Test
    @DisplayName("Should close a Bank Account when requested")
    void shouldCloseAnBankAccountWhenRequested() throws Exception {

        // given: the account details
        var expectedAccountHolder = "daniel.vasconcelos";
        var expectedAccountType = AccountType.SAVINGS;
        var expectedOpeningBalance = 150.0;

        // and: a bank account is opened
        var openAccountCommand = new OpenAccountCommand(expectedAccountHolder, expectedAccountType, expectedOpeningBalance);
        var openAccountResponse = openABankAccount(openAccountCommand).andExpect(status().isCreated()).andReturn();

        // when: dispatching the close request
        var bankAccountId = getBankAccountId(openAccountResponse);
        var closeAccountCommand = CloseAccountCommand.builder().id(bankAccountId).build();
        var response = closeABankAccount(closeAccountCommand);

        //then: the account should be close.
        response.andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.message").value("Bank Account close request completed successfully!"));

    }

    private String getBankAccountId(MvcResult openAccountResponse) throws Exception {
        var bodyAsString = openAccountResponse.getResponse().getContentAsString();
        Map<String, String> jsonAsMap = objectMapper.readValue(bodyAsString, Map.class);
        return jsonAsMap.getOrDefault("id", "unknown");
    }

    private ResultActions closeABankAccount(CloseAccountCommand command) throws Exception {
        var urlTemplate = String.format("/api/v1/bank-accounts/%s/close", command.getId());
        var request = post(urlTemplate)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(command));

        return mvc.perform(request).andDo(print());
    }

    private ResultActions openABankAccount(OpenAccountCommand command) throws Exception {
        // given: the account details
        var request = post("/api/v1/bank-accounts")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(command));

        return mvc.perform(request).andDo(print());
    }
}