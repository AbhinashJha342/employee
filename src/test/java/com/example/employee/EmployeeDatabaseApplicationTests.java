package com.example.employee;

import com.example.employee.util.EmployeeProfileUtil;
import com.example.employee.web.AbstractControllerIntegrationTest;
import com.example.employee.web.schema.EmployeeDetailsResponseDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

import java.util.List;
import java.util.UUID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
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
    void getEmployeeByEmployeeIdByCreating() throws Exception {
        String newEmployee =  this.mockMvc.perform(post("/admin/employees")
                        .accept(EmployeeProfileUtil.MEDIA_TYPE_JSON_UTF8)
                        .contentType(EmployeeProfileUtil.MEDIA_TYPE_JSON_UTF8)
                        .content(new ObjectMapper().writeValueAsString(EmployeeProfileUtil.createAnotherEmployeeRequest())))
                .andReturn().getResponse().getContentAsString();

        EmployeeDetailsResponseDTO responseDTO = new ObjectMapper().readValue(newEmployee, EmployeeDetailsResponseDTO.class);

        this.mockMvc.perform(get("/admin/employees")
                        .accept(EmployeeProfileUtil.MEDIA_TYPE_JSON_UTF8)
                        .contentType(EmployeeProfileUtil.MEDIA_TYPE_JSON_UTF8)
                        .header("Employee-id", responseDTO.getEmployeeId()))
                        .andExpect(status().is2xxSuccessful());
    }

    @Test
    void getEmployeeByEmployeeId() throws Exception {
        List<UUID> employeeIds = List.of();
        this.mockMvc.perform(get("/admin/employees")
                        .accept(EmployeeProfileUtil.MEDIA_TYPE_JSON_UTF8)
                        .contentType(EmployeeProfileUtil.MEDIA_TYPE_JSON_UTF8)
                        .header("Employee-ids", ""))
                .andExpect(status().is2xxSuccessful());
    }
}
