package com.hncu.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hncu.domain.ProdComm;
import com.hncu.mapper.ProdCommMapper;
import com.hncu.service.ProdCommService;
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
}
