package com.example.employee;

import com.example.employee.util.EmployeeProfileUtil;
import com.example.employee.web.AbstractControllerIntegrationTest;
import com.example.employee.web.EmployeeAdminController;
import com.example.employee.web.UserController;
import com.example.employee.web.schema.EmployeeDetailsResponseDTO;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.tomakehurst.wiremock.common.Json;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

@SpringBootTest
@AutoConfigureMockMvc
class EmployeeDatabaseApplicationTests extends AbstractControllerIntegrationTest{


    @Autowired
    private WebApplicationContext wac;

    private MockMvc mockMvc;

    @BeforeEach
    public void setup(){
        this.mockMvc = webAppContextSetup(this.wac).build();
    }

    @Test
    void createEmployeeTest() throws Exception {
        this.mockMvc.perform(post("/admin/employees")
                        .accept(EmployeeProfileUtil.MEDIA_TYPE_JSON_UTF8)
                        .contentType(EmployeeProfileUtil.MEDIA_TYPE_JSON_UTF8)
                        .content(new ObjectMapper().writeValueAsString(EmployeeProfileUtil.createEmployeeRequest())))
                .andExpect(status().isCreated());
    }

    @Test
    void getEmployeeByEmployeeId() throws Exception {
        String response = this.mockMvc.perform(post("/admin/employees")
                        .accept(EmployeeProfileUtil.MEDIA_TYPE_JSON_UTF8)
                        .contentType(EmployeeProfileUtil.MEDIA_TYPE_JSON_UTF8)
                        .content(new ObjectMapper().writeValueAsString(EmployeeProfileUtil.createEmployeeRequest())))
                .andReturn()
                .getResponse().getContentAsString();

        EmployeeDetailsResponseDTO resultDto = new ObjectMapper().readValue(response, EmployeeDetailsResponseDTO.class);

        this.mockMvc.perform(get("/admin/employees")
                .accept(EmployeeProfileUtil.MEDIA_TYPE_JSON_UTF8)
                .contentType(EmployeeProfileUtil.MEDIA_TYPE_JSON_UTF8)
                .header("Employee-id", resultDto.getEmployeeId()))
                .andExpect(status().is2xxSuccessful());
    }

}
