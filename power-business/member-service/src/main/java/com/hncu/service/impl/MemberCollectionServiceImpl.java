package com.hncu.service.impl;

import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hncu.constant.BusinessEnum;
import com.hncu.domain.MemberCollection;
import com.hncu.domain.Prod;
import com.hncu.ex.handler.BusinessException;
import com.hncu.feign.MemberProdFeign;
import com.hncu.mapper.MemberCollectionMapper;
import com.hncu.model.Result;
import com.hncu.service.MemberCollectionService;
import com.hncu.util.AuthUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class MemberCollectionServiceImpl extends ServiceImpl<MemberCollectionMapper, MemberCollection> implements MemberCollectionService{

    @Autowired
    private MemberCollectionMapper memberCollectionMapper;

    @Autowired
    private MemberProdFeign memberProdFeign;


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
        List<Long> prodIdList = memberCollectionList.stream().map(MemberCollection::getProdId).collect(Collectors.toList());
        //远程调用，根据商品id查询商品对象的集合
        Result<List<Prod>> result = memberProdFeign.getProdListByIds(prodIdList);
        if (BusinessEnum.OPERATION_FAIL.getCode().equals(result.getCode())) {
            throw new BusinessException("远程调用:根据商品Id集合查询商品对象集合失败");
        }
        List<Prod> prodList = result.getData();
        prodPage.setRecords(prodList);
        prodPage.setTotal(memberCollectionPage.getTotal());
        prodPage.setPages(memberCollectionPage.getPages());

        return prodPage;
    }

    @Override
    public Boolean addOrCancelMemberCollection(String openId, Long prodId) {
        //根据会员openId与商品Id查询收藏记录
        MemberCollection memberCollection = memberCollectionMapper.selectOne(new LambdaQueryWrapper<MemberCollection>()
                .eq(MemberCollection::getOpenId, openId)
                .eq(MemberCollection::getProdId, prodId)
        );
        //判断收藏记录是否存在
        if (ObjectUtil.isNull(memberCollection)) {
            //为空，说明当前商品没有被收藏 -> 将当前商品添加到收藏记录
            memberCollection = new MemberCollection();
            memberCollection.setCreateTime(new Date());
            memberCollection.setProdId(prodId);
            memberCollection.setOpenId(openId);
            return memberCollectionMapper.insert(memberCollection) > 0;
        }
        //不为空，说明当前商品已经被收藏 -> 将当前商品取消收藏记录
        return memberCollectionMapper.deleteById(memberCollection.getId()) > 0;
    }
}
