package com.hncu.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hncu.domain.MemberCollection;
import com.hncu.domain.Prod;
import com.hncu.mapper.MemberCollectionMapper;
import com.hncu.service.MemberCollectionService;
import com.hncu.util.AuthUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class MemberCollectionServiceImpl extends ServiceImpl<MemberCollectionMapper, MemberCollection> implements MemberCollectionService{

    @Autowired
    private MemberCollectionMapper memberCollectionMapper;


    @Override
    public Long queryMemberCollectionProdCount() {
        //获取会员OpenId
        String openId = AuthUtils.getMemberOpenId();
        Long count = memberCollectionMapper.selectCount(new LambdaQueryWrapper<MemberCollection>()
                .eq(MemberCollection::getOpenId, openId)
        );
        return count;
    }

    @Override
    public Page<Prod> queryMemberCollectionProdPageByOpenId(String openId, Long current, Long size) {
        //创建商品分页对象
        Page<Prod> prodPage = new Page<>(current,size);
        //场景会员与商品收藏关系分页对象
        Page<MemberCollection> memberCollectionPage = new Page<>(current,size);
        //根据会员OpenId分页查询会员与商品收藏关系记录
        memberCollectionPage = memberCollectionMapper.selectPage(memberCollectionPage,new LambdaQueryWrapper<MemberCollection>()
                .eq(MemberCollection::getOpenId,openId)
                .orderByDesc(MemberCollection::getCreateTime)
        );
        //从会员与商品收藏关系分页对象中获取收藏记录
        List<MemberCollection> memberCollectionList = memberCollectionPage.getRecords();
        if (CollectionUtils.isEmpty(memberCollectionList)) {
            return prodPage;
        }
        //从会员与商品收藏关系对象集合中获取收藏商品id的集合
        List<Long> prodList = memberCollectionList.stream().map(MemberCollection::getProdId).collect(Collectors.toList());
        //远程调用，根据商品id查询商品对象的集合



        return prodPage;
    }

    @Override
    public Boolean addOrCancelMemberCollection(String openId, Long prodId) {
        return null;
    }
}
