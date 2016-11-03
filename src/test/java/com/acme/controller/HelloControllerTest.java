package com.acme.controller;

import static org.hamcrest.Matchers.equalTo;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.acme.model.Customer;
import com.acme.repository.CustomerRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.TypeFactory;
import org.apache.commons.lang.StringUtils;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class HelloControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    CustomerRepository repository;

    private static final ObjectMapper mapper = new ObjectMapper();
    private static final TypeFactory typeFactory = mapper.getTypeFactory();
    private static final Customer testCustomer = new Customer("Jack", "Bauer");

    @Before
    public void insertData() {
        repository.deleteAll();
        repository.save(testCustomer);
    }

    @Test
    public void getCustomers() throws Exception {
        MvcResult result = mvc.perform(MockMvcRequestBuilders.get("/customers")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();
        List<Customer> customers = mapper.readValue(result.getResponse().getContentAsString(),
                typeFactory.constructCollectionType(List.class, Customer.class));
        Assert.assertEquals(1, customers.size());
        Assert.assertEquals(testCustomer.getFirstName(), customers.get(0).getFirstName());
        Assert.assertEquals(testCustomer.getLastName(), customers.get(0).getLastName());
    }

    @Test
    public void getCustomerById() throws Exception {
        MvcResult result = mvc.perform(MockMvcRequestBuilders.get("/customers/1")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();
        List<Customer> customers = mapper.readValue(result.getResponse().getContentAsString(),
                typeFactory.constructCollectionType(List.class, Customer.class));
        Assert.assertEquals(1, customers.size());
        Assert.assertEquals(testCustomer.getFirstName(), customers.get(0).getFirstName());
        Assert.assertEquals(testCustomer.getLastName(), customers.get(0).getLastName());
    }

    @Test
    public void postCustomer() throws Exception {
        Customer customer = new Customer("Brent", "Gardner");
        MvcResult result = mvc.perform(MockMvcRequestBuilders.post("/customers")
                .content(new ObjectMapper().writeValueAsBytes(customer))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();
        Customer returned = mapper.readValue(result.getResponse().getContentAsString(),
                typeFactory.constructType(Customer.class));
        Assert.assertNotNull(returned);
        Assert.assertEquals(customer.getFirstName(), returned.getFirstName());
        Assert.assertEquals(customer.getLastName(), returned.getLastName());
    }

    @Test
    public void putCustomer() throws Exception {
        Customer customer = new Customer("Bob", "Hope");
        customer.setId(1L);
        MvcResult result = mvc.perform(MockMvcRequestBuilders.put("/customers/1")
                .content(new ObjectMapper().writeValueAsBytes(customer))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();
        Customer returned = mapper.readValue(result.getResponse().getContentAsString(),
                typeFactory.constructType(Customer.class));
        Assert.assertNotNull(returned);
        Assert.assertEquals(customer.getFirstName(), returned.getFirstName());
        Assert.assertEquals(customer.getLastName(), returned.getLastName());
    }

    @Test
    public void deleteCustomer() throws Exception {
        Customer customer = repository.save(new Customer("Bob", "Newheart"));
        mvc.perform(MockMvcRequestBuilders.delete("/customers/" + customer.getId())
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

}