package top.arieslee.myblog.controller;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

/**
 * @ClassName IndexControllerTest
 * @Description 接口测试方法，使用mock工具测试
 * @Author Aries
 * @Date 2019/5/19 11:23
 * @Version 1.0
 **/
@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class IndexControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void index() throws Exception{
        mockMvc.perform(MockMvcRequestBuilders.get("/page/2")).andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void getArticleByCid() throws Exception{
        mockMvc.perform(MockMvcRequestBuilders.get("/article/6")).andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void getArticleBySlug() throws Exception{
        mockMvc.perform(MockMvcRequestBuilders.get("/article/about")).andExpect(MockMvcResultMatchers.status().isOk());
    }
}
