package com.acme.controller;

import com.acme.model.Category;
import com.acme.repository.CategoryRepository;
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
public class CategoryControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    CategoryRepository repository;

    private static final ObjectMapper mapper = new ObjectMapper();
    private static final TypeFactory typeFactory = mapper.getTypeFactory();

    @Before
    public void cleanup() {
        repository.deleteAll();
    }

    @Test
    public void getCategories() throws Exception {
        Category testCategory = repository.save(new Category("Action Heroes"));
        MvcResult result = mvc.perform(MockMvcRequestBuilders.get("/categories")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();
        List<Category> categories = mapper.readValue(result.getResponse().getContentAsString(),
                typeFactory.constructCollectionType(List.class, Category.class));
        Assert.assertEquals(1, categories.size());
        Assert.assertEquals(testCategory.getName(), categories.get(0).getName());
    }

    @Test
    public void getCategoryById() throws Exception {
        Category testCategory = repository.save(new Category("Action Heroes"));
        MvcResult result = mvc.perform(MockMvcRequestBuilders.get("/categories/" + testCategory.getId())
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();
        List<Category> categories = mapper.readValue(result.getResponse().getContentAsString(),
                typeFactory.constructCollectionType(List.class, Category.class));
        Assert.assertEquals(1, categories.size());
        Assert.assertEquals(testCategory.getName(), categories.get(0).getName());
    }

    @Test
    public void postCategory() throws Exception {
        Category testCategory = repository.save(new Category("Action Heroes"));
        MvcResult result = mvc.perform(MockMvcRequestBuilders.post("/categories")
                .content(new ObjectMapper().writeValueAsBytes(testCategory))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();
        Category returned = mapper.readValue(result.getResponse().getContentAsString(),
                typeFactory.constructType(Category.class));
        Assert.assertNotNull(returned);
        Assert.assertNotNull(returned.getId());
        Assert.assertEquals(testCategory.getName(), returned.getName());
    }

    @Test
    public void putCategory() throws Exception {
        Category testCategory = repository.save(new Category("Action Heroes"));
        MvcResult result = mvc.perform(MockMvcRequestBuilders.put("/categories/" + testCategory.getId())
                .content(new ObjectMapper().writeValueAsBytes(testCategory))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();
        Category returned = mapper.readValue(result.getResponse().getContentAsString(),
                typeFactory.constructType(Category.class));
        Assert.assertNotNull(returned);
        Assert.assertEquals(testCategory.getId(), returned.getId());
        Assert.assertEquals(testCategory.getName(), returned.getName());
    }

    @Test
    public void deleteCategory() throws Exception {
        Category testCategory = repository.save(new Category("Action Heroes"));
        mvc.perform(MockMvcRequestBuilders.delete("/categories/" + testCategory.getId())
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

}