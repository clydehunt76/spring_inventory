package com.acme.controller;

import com.acme.model.Category;
import com.acme.repository.CategoryRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.verification.VerificationMode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import static org.mockito.BDDMockito.*;
import static org.assertj.core.api.Assertions.*;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CategoryControllerMockTest {
    @MockBean
    CategoryRepository mockRepo;

    @Autowired
    CategoryController controller;

    @Test
    public void listCategoryTest() {
        List<Category> expected = Arrays.asList(new Category("Frustration"));
        given(this.mockRepo.findAll()).willReturn(expected);

        Iterable<Category> actual = controller.listCategories();

        List<Category> categories = StreamSupport.stream(actual.spliterator(), false).collect(Collectors.toList());

        verify(mockRepo, times(1)).findAll();
        assertThat(categories).isEqualTo(expected);
    }


    @Test
    public void createCategoryTest() {

        Category expected = new Category("Frustration");
        Category saveReturns = new Category("BigFrustration");

        given(this.mockRepo.save(expected)).willReturn(saveReturns);

        Category actual = controller.createCategory(expected);

        verify(mockRepo, times(1)).save(expected);
        assertThat(actual).isEqualTo(saveReturns);
    }

    @Test
    public void updateCategoryTest() {

        Category expected = new Category("Frustration");
//        Category saveReturns = new Category("BigFrustration");

        expected.setId(1L);

        given(this.mockRepo.save(expected)).willReturn(expected);
        Long id = 1L;

        ResponseEntity<Category> actual = controller.updateCategory(id, expected);

        verify(mockRepo, times(1)).save(expected);
        assertThat(actual.getBody()).isEqualTo(expected);

    }
}
