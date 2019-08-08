package com.xuecheng.manage_cms.dao;

import com.xuecheng.framework.domain.cms.CmsPage;
import com.xuecheng.framework.domain.cms.CmsSite;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.*;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;


@SpringBootTest
@RunWith(SpringRunner.class)
public class CmsPageRepositoryTest {
    @Autowired
    CmsPageRepository pageRepository;
    @Test
    public void cmsPageFindAll(){
        //测试查找所有的方法
        List<CmsPage> all = pageRepository.findAll();

        System.out.println(all);
    }

    @Test
    public void cmsPageFindPage(){
        //测试分页方法
        Pageable pageable = PageRequest.of(0,5);
        Page<CmsPage> all = pageRepository.findAll(pageable);

        System.out.println(all);
    }

    @Test
    public void cmsPageFindPageByExample(){
        //测试分页方法
        int page = 0;
        int size = 5;
        Pageable pageable = PageRequest.of(page,size);

        //自定义条件查询
        CmsPage cmsPage = new CmsPage();
        //cmsPage.setSiteId("5a751fab6abb5044e0d19ea1");
        //cmsPage.setPageAliase("cc");
        cmsPage.setTemplateId("5a925be7b00ffc4b3c1578b5");

        //匹配器 匹配条件
        ExampleMatcher matcher = ExampleMatcher.matching();
        matcher = matcher.withMatcher("pageAliase", ExampleMatcher.GenericPropertyMatchers.contains());
        //ExampleMatcher.GenericPropertyMatchers.endsWith();末尾匹配
        //ExampleMatcher.GenericPropertyMatchers.exact()精确匹配
        //ExampleMatcher.GenericPropertyMatchers.ignoreCase()忽略大小写
        Example<CmsPage> example = Example.of(cmsPage,matcher);
        Page<CmsPage> all = pageRepository.findAll(example,pageable);

        System.out.println(all);
    }

    @Test
    public void cmsPageSave(){
        //测试保存方法
       CmsPage page = new CmsPage();
       page.setPageName("测试保存方法");
       page.setPageAliase("测试页面");
       page.setPageHtml("test.html");
       page.setPageStatus("1");
       page.setHtmlFileId("首页");
       page.setPageCreateTime(new Date());

        pageRepository.save(page);
    }

    @Test
    public void cmsPageUpdate(){
       //测试修改方法
        //先查询
        Optional<CmsPage> optionPage = pageRepository.findById("5d4388f790a91c102cc3ed44");

        if (optionPage.isPresent()){//如果CMSPage不为null
            //再修改
            CmsPage cmsPage = optionPage.get();
            cmsPage.setSiteId("6666");
            //保存
            pageRepository.save(cmsPage);
        }
    }

    @Test
    public void cmsPageFindBy(){
        CmsPage cmsPage = pageRepository.findByPageName("测试保存方法");

        System.out.println(cmsPage);
    }
    //测试查询站点
    @Autowired
    CmsSiteRepository sitRepository;
    @Test
    public void findSite(){
        List<CmsSite> all = sitRepository.findAll();
        System.out.println(all);
    }

    @Autowired
    RestTemplate restTemplate;
    @Test
    public void testRestTemplate(){
        ResponseEntity<Map> forEntity = restTemplate.getForEntity("http://localhost:31001/cms/config/getModel/5a791725dd573c3574ee333f", Map.class);
        System.out.println(forEntity);
    }
}
