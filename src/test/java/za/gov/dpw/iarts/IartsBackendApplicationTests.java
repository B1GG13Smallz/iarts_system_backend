package za.gov.dpw.iarts;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@SpringBootTest
class IartsBackendApplicationTests {

	@Autowired
	private MockMvc mockMvc;

	@Test
	void contextLoads() {
	}

	@Test
	void registerRequiresAuthentication() throws Exception {
		mockMvc.perform(post("/api/auth/register")
						.contentType(MediaType.APPLICATION_JSON)
						.content("""
								{
								  "username": "new.user",
								  "email": "new.user@iarts.local",
								  "password": "Password@123",
								  "fullName": "New User",
								  "roles": ["END_USER"]
								}
								"""))
				.andExpect(status().isUnauthorized());
	}

	@Test
	void invalidBearerTokenReturnsUnauthorized() throws Exception {
		mockMvc.perform(post("/api/auth/register")
						.header("Authorization", "Bearer not-a-valid-token")
						.contentType(MediaType.APPLICATION_JSON)
						.content("{}"))
				.andExpect(status().isUnauthorized());
	}

	@Test
	void loginEndpointRemainsPublic() throws Exception {
		mockMvc.perform(post("/api/auth/login")
						.contentType(MediaType.APPLICATION_JSON)
						.content("""
								{
								  "username": "missing",
								  "password": "wrong"
								}
								"""))
				.andExpect(status().isUnauthorized());
	}

	@Test
	void malformedJsonReturnsBadRequest() throws Exception {
		mockMvc.perform(post("/api/auth/login")
						.contentType(MediaType.APPLICATION_JSON)
						.content("{\\\"username\\\":\\\"missing\\\"}"))
				.andExpect(status().isBadRequest());
	}

	@Test
	@WithMockUser(roles = "END_USER")
	void endUserCannotReadAuditLogs() throws Exception {
		mockMvc.perform(get("/api/audit/logs"))
				.andExpect(status().isForbidden());
	}

	@Test
	@WithMockUser(roles = "AUDITOR")
	void auditorCanReadAuditLogs() throws Exception {
		mockMvc.perform(get("/api/audit/logs"))
				.andExpect(status().isOk());
	}

	@Test
	void technicianLoginReturnsTechnicianRoute() throws Exception {
		mockMvc.perform(post("/api/auth/login")
						.contentType(MediaType.APPLICATION_JSON)
						.content("""
								{
								  "username": "technician",
								  "password": "Technician@123"
								}
								"""))
				.andExpect(status().isOk())
				.andExpect(content().string(containsString("\"TECHNICIAN\"")))
				.andExpect(content().string(containsString("\"/technician\"")));
	}

	@Test
	void storeroomLoginReturnsStoreroomRoutes() throws Exception {
		mockMvc.perform(post("/api/auth/login")
						.contentType(MediaType.APPLICATION_JSON)
						.content("""
								{
								  "username": "storeroom",
								  "password": "Storeroom@123"
								}
								"""))
				.andExpect(status().isOk())
				.andExpect(content().string(containsString("\"ICT_STOREROOM\"")))
				.andExpect(content().string(containsString("\"/register\"")));
	}

	@Test
	void assetManagementLoginReturnsAssetApprovalRoute() throws Exception {
		mockMvc.perform(post("/api/auth/login")
						.contentType(MediaType.APPLICATION_JSON)
						.content("""
								{
								  "username": "assets",
								  "password": "Assets@123"
								}
								"""))
				.andExpect(status().isOk())
				.andExpect(content().string(containsString("\"ASSET_MANAGEMENT\"")))
				.andExpect(content().string(containsString("\"/assets-approval\"")));
	}

}
