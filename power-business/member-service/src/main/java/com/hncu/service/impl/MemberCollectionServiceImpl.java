package com.hncu.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hncu.domain.MemberCollection;
import com.hncu.domain.Prod;
import com.hncu.mapper.MemberCollectionMapper;
import com.hncu.service.MemberCollectionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MemberCollectionServiceImpl extends ServiceImpl<MemberCollectionMapper, MemberCollection> implements MemberCollectionService{

    @Autowired
    private MemberCollectionMapper memberCollectionMapper;


    @Override
    public Long queryMemberCollectionProdCount() {
        return null;
    }

    @Override
    public Page<Prod> queryMemberCollectionProdPageByOpenId(String openId, Long current, Long size) {
        return null;
    }

    @Override
    public Boolean addOrCancelMemberCollection(String openId, Long prodId) {
        return null;
    }
}
