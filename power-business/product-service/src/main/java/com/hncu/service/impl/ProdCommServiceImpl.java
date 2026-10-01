package com.hncu.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hncu.domain.ProdComm;
import com.hncu.mapper.ProdCommMapper;
import com.hncu.service.ProdCommService;
import com.hncu.vo.ProdCommData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Date;

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
        Long AllCount = prodCommMapper.selectCount(new LambdaQueryWrapper<ProdComm>()
                .eq(ProdComm::getProdId, prodId)
                .eq(ProdComm::getStatus, 1)

        );

        //根据商品Id查询商品评论总数
        Long goodCount = prodCommMapper.selectCount(new LambdaQueryWrapper<ProdComm>()
                .eq(ProdComm::getProdId, prodId)
                .eq(ProdComm::getStatus, 1)
                .eq(ProdComm::getEvaluate, 0)
        );
    }
}
