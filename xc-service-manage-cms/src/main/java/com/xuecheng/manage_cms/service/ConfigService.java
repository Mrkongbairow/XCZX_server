package com.xuecheng.manage_cms.service;

import com.xuecheng.framework.domain.cms.CmsConfig;
import com.xuecheng.manage_cms.dao.CmsConfigRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ConfigService {
    @Autowired
    CmsConfigRepository configRepository;

    //根据id获取CmsConfig
    public CmsConfig getConfigById(String id){
        Optional<CmsConfig> byId = configRepository.findById(id);
        if (byId.isPresent()){
            return byId.get();
        }
        return null;
    }
}
