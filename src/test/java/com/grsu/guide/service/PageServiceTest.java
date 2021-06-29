package com.grsu.guide.service;

import com.grsu.guide.domain.Page;
import com.grsu.guide.repository.PageRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class PageServiceTest {

    @Autowired
    private PageService pageService;

    @MockBean
    private PageRepository pageRepository;

    @Test
    void getHierarchyPages() {
        Page page1 = new Page();
        page1.setId(1L);
        page1.setNamePage("Page 1");
        page1.setParentPageId(0L);
        Page page2 = new Page();
        page2.setId(2L);
        page2.setNamePage("Page 2");
        page2.setParentPageId(1L);
        Page page3 = new Page();
        page3.setId(3L);
        page3.setNamePage("Page 3");
        page3.setParentPageId(2L);
        List<Page> expected = new ArrayList<>();
        expected.add(page1);
        expected.add(page2);
        expected.add(page3);
        Mockito.doReturn(Optional.of(page1)).when(pageRepository).findById(1L);
        Mockito.doReturn(Optional.of(page2)).when(pageRepository).findById(2L);
        Mockito.doReturn(Optional.of(page3)).when(pageRepository).findById(3L);
        Assertions.assertEquals(expected,pageService.getHierarchyPages(3L));
    }
}