package ru.javawebinar.topjava.web;

import org.junit.jupiter.api.Test;

import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class ResourceControllerTest extends AbstractControllerTest {
    @Test
    void getResourcesCss() throws Exception {
        perform(MockMvcRequestBuilders.get("/resources/css/style.css"))
                .andDo(print())
                .andExpectAll(status().isOk(), content().contentTypeCompatibleWith("text/css;charset=UTF-8"));
    }
}
