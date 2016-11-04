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
        Product product = productRepository.save(new Product("Plumbus", "1.00", "Just a regular old plumbus"));
        MvcResult result = mvc.perform(MockMvcRequestBuilders.get("/products/")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();
        List<Product> products = mapper.readValue(result.getResponse().getContentAsString(),
                typeFactory.constructCollectionType(List.class, Product.class));
        Assert.assertEquals(1, products.size());
        Assert.assertEquals(product.getName(), products.get(0).getName());
        Assert.assertEquals(product.getPrice(), products.get(0).getPrice());
        Assert.assertEquals(product.getDescription(), products.get(0).getDescription());
    }

    @Test
    public void getProductById() throws Exception {
        Product product = productRepository.save(new Product("Plumbus", "1.00", "Just a regular old plumbus"));
        MvcResult result = mvc.perform(MockMvcRequestBuilders.get("/products/" + product.getId())
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();
        List<Product> products = mapper.readValue(result.getResponse().getContentAsString(),
                typeFactory.constructCollectionType(List.class, Product.class));
        Assert.assertEquals(1, products.size());
        Assert.assertEquals(product.getName(), products.get(0).getName());
        Assert.assertEquals(product.getPrice(), products.get(0).getPrice());
        Assert.assertEquals(product.getDescription(), products.get(0).getDescription());
    }

    @Test
    public void postProduct() throws Exception {
        Product product = productRepository.save(new Product("Plumbus", "1.00", "Just a regular old plumbus"));
        Category category = categoryRepository.save(new Category("Action Heroes"));
        product.getCategories().add(category);
        MvcResult result = mvc.perform(MockMvcRequestBuilders.post("/products/")
                .content(new ObjectMapper().writeValueAsBytes(product))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();
        Product returned = mapper.readValue(
                result.getResponse().getContentAsString(),
                typeFactory.constructType(Product.class)
        );
        Assert.assertNotNull(returned);
        Assert.assertNotNull(returned.getId());
        Assert.assertEquals(product.getName(), returned.getName());
        Assert.assertEquals(product.getPrice(), returned.getPrice());
        Assert.assertEquals(product.getDescription(), returned.getDescription());
        Assert.assertEquals(1, product.getCategories().size());
    }

    @Test
    public void putProduct() throws Exception {
        Product product = productRepository.save(new Product("Plumbus", "1.00", "Just a regular old plumbus"));
        MvcResult result = mvc.perform(MockMvcRequestBuilders.put("/products/" + product.getId())
                .content(new ObjectMapper().writeValueAsBytes(product))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();
        Product returned = mapper.readValue(result.getResponse().getContentAsString(),
                typeFactory.constructType(Product.class));
        Assert.assertNotNull(returned);
        Assert.assertEquals(product.getId(), returned.getId());
        Assert.assertEquals(product.getName(), returned.getName());
        Assert.assertEquals(product.getPrice(), returned.getPrice());
        Assert.assertEquals(product.getDescription(), returned.getDescription());
    }

    @Test
    public void deleteProduct() throws Exception {
        Product product = productRepository.save(new Product("Plumbus", "1.00", "Just a regular old plumbus"));
        mvc.perform(MockMvcRequestBuilders.delete("/products/" + product.getId())
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

}