package com.hncu.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.hncu.domain.ProdComm;
import com.hncu.vo.ProdCommData;

public interface ProdCommService extends IService<ProdComm> {

    Boolean replayAndExamineProdComm(ProdComm prodComm);

    ProdCommData queryWxProdCommDataByProdId(Long prodId);
}
