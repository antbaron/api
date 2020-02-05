package fr.epsi.api.user;

import javax.ws.rs.core.MediaType;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerIT {

	@Autowired
	private MockMvc mockMvc;
	
	@Test
	public void should_return_all_users() throws Exception {
		String response = "[{\"pseudo\":\"pseudo\",\"financialDto\":{\"id\":1,\"mensualNetIncome\":2000,\"netImposable\":32000.50,\"mensualCreditAmount\":700.00}},{\"pseudo\":\"Bill\",\"financialDto\":{\"id\":2,\"mensualNetIncome\":15000,\"netImposable\":100000.00,\"mensualCreditAmount\":0.00}}]";
		mockMvc.perform(MockMvcRequestBuilders.get("/users")
                .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(response))
                .andReturn();
	}

}
