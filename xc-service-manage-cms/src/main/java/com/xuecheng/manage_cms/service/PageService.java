package com.xuecheng.manage_cms.service;

import com.xuecheng.framework.domain.cms.CmsPage;
import com.xuecheng.framework.domain.cms.request.QueryPageRequest;
import com.xuecheng.framework.domain.cms.response.CmsCode;
import com.xuecheng.framework.domain.cms.response.CmsPageResult;
import com.xuecheng.framework.exception.ExceptionCast;
import com.xuecheng.framework.model.response.*;
import com.xuecheng.manage_cms.controller.CmsPageController;
import com.xuecheng.manage_cms.dao.CmsConfigRepository;
import com.xuecheng.manage_cms.dao.CmsPageRepository;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Optional;

@Service
public class PageService {

    @Autowired
    CmsPageRepository pageRepository;


    //查询所有页面
    public QueryResponseResult findList(int page, int size, QueryPageRequest querypageRequest) {
        if (querypageRequest == null) {
            querypageRequest = new QueryPageRequest();
        }
        //条件
        CmsPage cmsPage = new CmsPage();
        //页面别名
        if (StringUtils.isNoneEmpty(querypageRequest.getPageAliase())) {
            cmsPage.setPageAliase(querypageRequest.getPageAliase());
        }
        //页面id
        if (StringUtils.isNoneEmpty(querypageRequest.getPageId())) {
            cmsPage.setPageId(querypageRequest.getPageId());
        }
        //页面名称
        if (StringUtils.isNoneEmpty(querypageRequest.getPageName())) {
            cmsPage.setPageName(querypageRequest.getPageName());
        }
        //站点id
        if (StringUtils.isNoneEmpty(querypageRequest.getSiteId())) {
            cmsPage.setSiteId(querypageRequest.getSiteId());
        }
        if (StringUtils.isNoneEmpty(querypageRequest.getTemplateId())) {
            cmsPage.setTemplateId(querypageRequest.getTemplateId());
        }

        ExampleMatcher matcher = ExampleMatcher.matching();
        matcher = matcher.withMatcher("pageAliase", ExampleMatcher.GenericPropertyMatchers.contains());
        Example<CmsPage> example = Example.of(cmsPage, matcher);

        //先判断page和size是否合法
        if (page <= 0) {
            page = 1;
        }
        page = page - 1;
        if (size <= 0) {
            size = 5;
        }

        //查找所有记录
        Pageable pabgeable = PageRequest.of(page, size);

        Page<CmsPage> all = pageRepository.findAll(example, pabgeable);

        QueryResult queryResult = new QueryResult();
        queryResult.setList(all.getContent());
        queryResult.setTotal(all.getTotalElements());

        QueryResponseResult queryResponseResult = new QueryResponseResult(CommonCode.SUCCESS, queryResult);
        return queryResponseResult;
    }

    public CmsPageResult add(CmsPage cmsPage){
        //根据页面名称，站点id，页面路径查询页面是否存在
      //先判断新添加页面是否存在
        CmsPage cms = pageRepository.findByPageNameAndSiteIdAndPageWebPath(cmsPage.getPageName(),cmsPage.getSiteId(),cmsPage.getPageWebPath());
        if (cms != null){//如果页面存在，则不执行添加操作，并抛出异常
            ExceptionCast.cast(CmsCode.CMS_ADDPAGE_EXISTSNAME);
        }
        cmsPage.setPageId(null);//避免前端人为添加主键
        pageRepository.save(cmsPage);
        return new CmsPageResult(CommonCode.SUCCESS,cmsPage);

    }

    //根据id查找页面
    public CmsPage findById(String id){
        if ( id != null){
            Optional<CmsPage> byId = pageRepository.findById(id);
            if (byId.isPresent()){
                return  byId.get();
            }
        }
        return null;
    }

    public CmsPageResult edit(String id,CmsPage cmsPage){
        CmsPage one = this.findById(id);
        if (one != null){
            //更新模板id
            one.setTemplateId(cmsPage.getTemplateId());
            //更新所属站点
            one.setSiteId(cmsPage.getSiteId());
            //更新页面别名
            one.setPageAliase(cmsPage.getPageAliase());
            //更新页面名称
            one.setPageName(cmsPage.getPageName());
            //更新访问路径
            one.setPageWebPath(cmsPage.getPageWebPath());
            //更新物理路径
            one.setPagePhysicalPath(cmsPage.getPagePhysicalPath());
            //更新dataUrl
            one.setDataUrl(cmsPage.getDataUrl());
            //执行更新操作
            CmsPage save = pageRepository.save(one);
            if (save != null){//如果保存成功
                return new CmsPageResult(CommonCode.SUCCESS,save);
            }
        }
        //保存失败
        return new CmsPageResult(CommonCode.FAIL,null);
    }

    public ResponseResult delte(String id){
        Optional<CmsPage> byId = pageRepository.findById(id);
        if (byId.isPresent()){
            pageRepository.delete(byId.get());
            //删除成功
            return new ResponseResult(CommonCode.SUCCESS);
        }
        //删除失败
        return new ResponseResult(CommonCode.FAIL);
    }


}
