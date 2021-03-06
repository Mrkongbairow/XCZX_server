package com.xuecheng.manage_cms.controller;

import com.xuecheng.api.cms.CmsPageControllerApi;
import com.xuecheng.framework.domain.cms.CmsPage;
import com.xuecheng.framework.domain.cms.request.QueryPageRequest;
import com.xuecheng.framework.domain.cms.response.CmsPageResult;
import com.xuecheng.framework.model.response.*;
import com.xuecheng.manage_cms.service.PageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/cms/page")
public class CmsPageController implements CmsPageControllerApi {
    @Autowired
    PageService pageService;

    @Override
    @RequestMapping("/list/{page}/{size}")
    public QueryResponseResult findList(@PathVariable("page") int page,@PathVariable("size") int size, QueryPageRequest querypageRequest) {
        return pageService.findList(page,size,querypageRequest);
    }

    @Override
    @PostMapping("/add")
    public CmsPageResult add(@RequestBody CmsPage cmsPage) {
        return pageService.add(cmsPage);
    }

    @Override
    @GetMapping("/get/{id}")
    public CmsPage findById(@PathVariable("id") String id) {
        return pageService.findById(id);
    }

    @Override
    @PutMapping("/edit/{id}")//在http中，put表示更新操作
    public CmsPageResult edit(@PathVariable("id") String id,@RequestBody CmsPage cmsPage) {
        return pageService.edit(id,cmsPage);
    }

    @Override
    @DeleteMapping("/del/{id}")
    public ResponseResult delte(@PathVariable("id") String id) {
        return pageService.delte(id);
    }
}
