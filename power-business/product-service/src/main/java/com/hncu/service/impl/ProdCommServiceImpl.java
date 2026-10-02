package com.hncu.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hncu.domain.ProdComm;
import com.hncu.mapper.ProdCommMapper;
import com.hncu.service.ProdCommService;
import com.hncu.vo.ProdCommData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProdCommServiceImpl extends ServiceImpl<ProdCommMapper, ProdComm> implements ProdCommService {

    @Autowired
    private ProdCommMapper prodCommMapper;


    @Override
    public Boolean replayAndExamineProdComm(ProdComm prodComm) {
        //获取商品的评论内容
        String content = prodComm.getContent();
        //判断评论内容是否有值
        if (StringUtils.hasText(content)) {
            prodComm.setReplyTime(new Date());
            prodComm.setReplySts(1);
        }
        return prodCommMapper.updateById(prodComm ) > 0;
    }

    @Override
    public ProdCommData queryWxProdCommDataByProdId(Long prodId) {
         //根据商品Id查询商品评论总数
        Long allCount = prodCommMapper.selectCount(new LambdaQueryWrapper<ProdComm>()
                .eq(ProdComm::getProdId, prodId)
                .eq(ProdComm::getStatus, 1)

        );
        //根据商品Id查询商品好评数量
        Long goodCount = prodCommMapper.selectCount(new LambdaQueryWrapper<ProdComm>()
                .eq(ProdComm::getProdId, prodId)
                .eq(ProdComm::getStatus, 1)
                .eq(ProdComm::getEvaluate, 0)
        );

        //根据商品Id查询商品中评数量
        Long secondCount = prodCommMapper.selectCount(new LambdaQueryWrapper<ProdComm>()
                .eq(ProdComm::getProdId, prodId)
                .eq(ProdComm::getStatus, 1)
                .eq(ProdComm::getEvaluate, 1)
        );

        //根据商品Id查询商品差评数量
        Long badCount = prodCommMapper.selectCount(new LambdaQueryWrapper<ProdComm>()
                .eq(ProdComm::getProdId, prodId)
                .eq(ProdComm::getStatus, 1)
                .eq(ProdComm::getEvaluate, 2)
        );

        //根据商品Id查询商品有图评论数量
        Long picCount = prodCommMapper.selectCount(new LambdaQueryWrapper<ProdComm>()
                .eq(ProdComm::getProdId, prodId)
                .eq(ProdComm::getStatus, 1)
                .isNotNull(ProdComm::getPics)
        );

        //好评率 = 好评数量/评论总数量
        BigDecimal goodLv = BigDecimal.ZERO;
        if (0 != allCount) {
            goodLv = new BigDecimal(goodCount)
                    .divide(new BigDecimal(allCount),3,BigDecimal.ROUND_DOWN)
                    .multiply(new BigDecimal(100));
        }

        return ProdCommData.builder()
                .allCount(allCount)
                .badCount(badCount)
                .goodCount(goodCount)
                .secondCount(secondCount)
                .picCount(picCount)
                .goodLv(goodLv)
                .build();
    }

    @Override
    public Page<ProdComm> queryWxProdCommPageByProd(Long current, Long size, Long prodId, Long evaluate) {
        //创建评论分页对象
        Page<ProdComm> page = new Page<>(current,size);
        //根据商品id分页查询单个商品的评论
        page = prodCommMapper.selectPage(page,new LambdaQueryWrapper<ProdComm>()
                .eq(ProdComm::getProdId,prodId)
                .eq(ProdComm::getStatus,1)
                .eq(0 == evaluate || 1 == evaluate || 2 == evaluate ,ProdComm::getEvaluate,evaluate)
                .isNotNull(3 == evaluate,ProdComm::getPics)
                .orderByDesc(ProdComm::getCreateTime)
        );
        //从分页对象中获取评论记录
        List<ProdComm> prodCommList = page.getRecords();
        //判断是否有值
        if (CollectionUtils.isEmpty(prodCommList)) {
            return page;
        }
        //从商品评论列表中获取会员OpenId集合
        List<String> openIdList = prodCommList.stream().map(ProdComm::getOpenId).collect(Collectors.toList());
        //远程调用:根据会员openId查询会员对象集合

        return page;
    }
}
