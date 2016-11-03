package com.acme.controller;

import com.acme.model.Category;
import com.acme.model.Product;
import com.acme.repository.CategoryRepository;
import com.acme.repository.ProductRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.TypeFactory;
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

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class ProductControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    ProductRepository productRepository;

    @Autowired
    CategoryRepository categoryRepository;

    private static final ObjectMapper mapper = new ObjectMapper();
    private static final TypeFactory typeFactory = mapper.getTypeFactory();

    @Before
    public void cleanup() {
        productRepository.deleteAll();
    }

    @Test
    public void getProducts() throws Exception {
        Product testProduct = productRepository.save(new Product("Jack", "Bauer"));
        MvcResult result = mvc.perform(MockMvcRequestBuilders.get("/products")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();
        List<Product> products = mapper.readValue(result.getResponse().getContentAsString(),
                typeFactory.constructCollectionType(List.class, Product.class));
        Assert.assertEquals(1, products.size());
        Assert.assertEquals(testProduct.getFirstName(), products.get(0).getFirstName());
        Assert.assertEquals(testProduct.getLastName(), products.get(0).getLastName());
    }

    @Test
    public void getProductById() throws Exception {
        Product testProduct = productRepository.save(new Product("Jack", "Bauer"));
        MvcResult result = mvc.perform(MockMvcRequestBuilders.get("/products/" + testProduct.getId())
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();
        List<Product> products = mapper.readValue(result.getResponse().getContentAsString(),
                typeFactory.constructCollectionType(List.class, Product.class));
        Assert.assertEquals(1, products.size());
        Assert.assertEquals(testProduct.getFirstName(), products.get(0).getFirstName());
        Assert.assertEquals(testProduct.getLastName(), products.get(0).getLastName());
    }

    @Test
    public void postProduct() throws Exception {
        Product product = productRepository.save(new Product("Jack", "Bauer"));
        Category category = categoryRepository.save(new Category("Action Heroes"));
        product.getCategogies().add(category);
        MvcResult result = mvc.perform(MockMvcRequestBuilders.post("/products")
                .content(new ObjectMapper().writeValueAsBytes(product))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();
        Product returned = mapper.readValue(result.getResponse().getContentAsString(),
                typeFactory.constructType(Product.class));
        Assert.assertNotNull(returned);
        Assert.assertNotNull(returned.getId());
        Assert.assertEquals(product.getFirstName(), returned.getFirstName());
        Assert.assertEquals(product.getLastName(), returned.getLastName());
    }

    @Test
    public void putProduct() throws Exception {
        Product testProduct = productRepository.save(new Product("Jack", "Bauer"));
        MvcResult result = mvc.perform(MockMvcRequestBuilders.put("/products/" + testProduct.getId())
                .content(new ObjectMapper().writeValueAsBytes(testProduct))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();
        Product returned = mapper.readValue(result.getResponse().getContentAsString(),
                typeFactory.constructType(Product.class));
        Assert.assertNotNull(returned);
        Assert.assertEquals(testProduct.getId(), returned.getId());
        Assert.assertEquals(testProduct.getFirstName(), returned.getFirstName());
        Assert.assertEquals(testProduct.getLastName(), returned.getLastName());
    }

    @Test
    public void deleteProduct() throws Exception {
        Product testProduct = productRepository.save(new Product("Jack", "Bauer"));
        mvc.perform(MockMvcRequestBuilders.delete("/products/" + testProduct.getId())
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

}