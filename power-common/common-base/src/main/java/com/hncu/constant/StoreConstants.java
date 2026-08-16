package com.hncu.constant;

/**
 * @Author caimeisahng
 * @Date 2026/4/19 20:43
 * @Version 1.0
 * 门店业务模块常量类
 */
public interface StoreConstants {
    /*
    * 全国地区数据存放到redis中的key
    *
    * */
    String ALL_AREA_KEY = "'areaList'";

    /*
    * 小程序轮播图数据数据存放到redis中的key
    * */
    String WX_INDEX_IMG_KEY = "'wxIndexImg'";

    /*
    * 小程序:置顶公告数据存放到redis中存放到redis中的key
    * */
    String WX_TOP_NOTICE="'wxTopNotice'";

    /**
     * 小程序:所有公告都存放到redis中的key
     */
    String WX_ALL_NOTICE = "'wxAllNotice'";


}
