package com.hncu.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.hncu.domain.Prod;

public interface ProdService extends IService<Prod> {


    /**
     * 新增商品
     * @param prod
     * @return
     */
    Boolean saveProd(Prod prod);

    /**
     * 根据标识查询商品详情
     * @param prodId
     * @return
     */
    Prod queryProdInfoById(Long prodId);

    /**
     * 修改商品信息
     * @param prod
     * @return
     */
    Boolean modifyProdInfo(Prod prod);

    /**
     * 删除商品
     * @param prodId
     * @return
     */
    Boolean removeProdById(Long prodId);

    /**
     * 小程序根据商品标识查询商品详情
     * @param prodId
     * @return
     */
    Prod queryWxProdInfoByProdId(Long prodId);


}
