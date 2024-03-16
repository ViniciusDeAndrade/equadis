package pt.com.equadis.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import pt.com.equadis.dto.form.AccountForm;
import pt.com.equadis.dto.form.CustomerForm;
import pt.com.equadis.dto.form.DepositForm;

import java.math.BigDecimal;

@SpringBootTest
@AutoConfigureMockMvc
public class AccountControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void testCreateAccount() throws Exception {
        CustomerForm customerForm = new CustomerForm("John Doe");

        // Perform POST request to create customer
        mockMvc.perform(MockMvcRequestBuilders.post("/v1/customers")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\": \"John Doe\"}"))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("John Doe"));

        AccountForm accountForm = new AccountForm(BigDecimal.valueOf(60.00), 1L);

        mockMvc.perform(MockMvcRequestBuilders.post("/v1/accounts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(accountForm)))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").exists());
    }

    @Test
    public void testDeposit() throws Exception {
        CustomerForm customerForm = new CustomerForm("John Doe");

        // Perform POST request to create customer
        mockMvc.perform(MockMvcRequestBuilders.post("/v1/customers")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\": \"John Doe\"}"))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("John Doe"));

        DepositForm depositForm = new DepositForm(1L, BigDecimal.valueOf(80.00));

        mockMvc.perform(MockMvcRequestBuilders.post("/v1/accounts/deposit")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(depositForm)))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.transactionId").exists());
    }

}

