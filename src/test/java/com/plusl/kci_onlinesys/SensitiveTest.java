package com.plusl.kci_onlinesys;

import com.plusl.kci_onlinesys.util.SensitiveFilter;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @program: kci_onlinesys
 * @description: 敏感词过滤测试
 * @author: PlusL
 * @create: 2022-03-22 16:55
 **/
@SpringBootTest
@ContextConfiguration(classes = KciOnlinesysApplication.class)
@RunWith(SpringRunner.class)
public class SensitiveTest {

    @Autowired
    private SensitiveFilter sensitiveFilter;

    @Test
    public void testFilter(){
        String text = "这里可以嫖娼，赌博，吸毒，等等等";
        text = sensitiveFilter.filter(text);
        System.out.println(text);

        text = "这里可以/*-/**嫖娼-=·~，赌博，吸毒，等等等";
        text = sensitiveFilter.filter(text);
        System.out.println(text);

    }
}
