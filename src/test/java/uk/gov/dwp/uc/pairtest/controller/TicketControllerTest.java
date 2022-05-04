package uk.gov.dwp.uc.pairtest.controller;


import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import uk.gov.dwp.uc.pairtest.domain.TicketReservationRequest;
import uk.gov.dwp.uc.pairtest.domain.TicketTypeRequest;
import uk.gov.dwp.uc.pairtest.service.TicketService;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static uk.gov.dwp.uc.pairtest.util.JsonMapper.getObjectAsJson;

@WebMvcTest(TicketController.class)
@RunWith(SpringRunner.class)
public class TicketControllerTest {
    @Autowired
    private TicketController ticketController;
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TicketService ticketService;
    private TicketReservationRequest ticketReservationRequest = new TicketReservationRequest();

    @Before
    public void setUp() {
        ticketReservationRequest.setAccountId(1L);
        List<TicketTypeRequest> ticketTypeRequests = new ArrayList<>();
        ticketTypeRequests.add(new TicketTypeRequest(TicketTypeRequest.Type.ADULT, 3));
        ticketReservationRequest.setTicketTypeRequests(ticketTypeRequests);
    }

    @Test
    @DisplayName("Test that reserve endpoint works standalone when correct input")
    public void testReservation() throws Exception {
        MvcResult mvcResult = mockMvc.perform(post("/reserve")).andReturn();
        mockMvc.perform(post("/reserve").content(getObjectAsJson(ticketReservationRequest))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.status").value("SUCCESS"))
                .andExpect(jsonPath("$.message").value("Ticket(s) have been successfully booked for account id: 1"))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("Test that reserve endpoint returns error when account id is 0 or less")
    public void testReservationAccountIdMinValue() throws Exception {
        MvcResult mvcResult = mockMvc.perform(post("/reserve")).andReturn();
        ticketReservationRequest.setAccountId(0L);
        mockMvc.perform(post("/reserve").content(getObjectAsJson(ticketReservationRequest))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.status").value("FAILED"))
                .andExpect(jsonPath("$.message").value("Method arguments not valid"))
                .andExpect(jsonPath("$.errors.accountId").value("The account id must be greater than 0"))
                .andExpect(status().isBadRequest());

        ticketReservationRequest.setAccountId(-2L);
        mockMvc.perform(post("/reserve").content(getObjectAsJson(ticketReservationRequest))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.status").value("FAILED"))
                .andExpect(jsonPath("$.message").value("Method arguments not valid"))
                .andExpect(jsonPath("$.errors.accountId").value("The account id must be greater than 0"))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("Test that reserve endpoint returns error when field is missing in main TicketReservationRequest")
    public void testReservationMissingRequiredField() throws Exception {
        MvcResult mvcResult = mockMvc.perform(post("/reserve")).andReturn();
        MvcResult mvcResult1 = mockMvc.perform(post("/reserve").content("{}")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)).andReturn();
        String h = mvcResult1.getResponse().getContentAsString();
        mockMvc.perform(post("/reserve").content("{}")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.status").value("FAILED"))
                .andExpect(jsonPath("$.message").value("Method arguments not valid"))
                .andExpect(jsonPath("$.errors.ticketTypeRequests").value("Ticket requests cannot be null or blank"))
                .andExpect(jsonPath("$.errors.accountId").value("Account id cannot be null"))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("Test that reserve endpoint returns error when field is missing in composed TicketTypeRequest List")
    public void testTicketTypeRequestMissingRequiredField() throws Exception {
        mockMvc.perform(post("/reserve").content("{\"accountId\": 1, \"ticketTypeRequests\": [{}]}")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.status").value("FAILED"))
                .andExpect(jsonPath("$.message").value("Method arguments not valid"))
                .andExpect(jsonPath("$.errors.*", hasItem("Type of ticket cannot be null")))
                .andExpect(jsonPath("$.errors.*", hasItem("The ticket value should be min 1")))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("Test that reserve endpoint returns error when json structure has issues")
    public void testReservationForInvalidJsonStructure() throws Exception {
        mockMvc.perform(post("/reserve").content("{\"accountId\": 1, \"ticketTypeRequests\": [{}],}")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.status").value("FAILED"))
                .andExpect(jsonPath("$.message").value("Json is not formatted properly"))
                .andExpect(status().isBadRequest());
    }
}
