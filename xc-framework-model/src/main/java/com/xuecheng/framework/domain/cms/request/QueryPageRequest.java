package com.xuecheng.framework.domain.cms.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class QueryPageRequest {
    //接收页面的查询条件
    //站点Id
    @ApiModelProperty("站点id")
    private String  siteId;
    //页面id
    @ApiModelProperty("页面ID")
    private String pageId;
    //页面名称
    @ApiModelProperty("页面名称")
    private String pageName;
    //别名
    @ApiModelProperty("页面别名")
    private String pageAliase;
    //模板Id
    @ApiModelProperty("模版id")
    private String templateId;

}
