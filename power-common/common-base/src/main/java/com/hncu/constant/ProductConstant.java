package com.hncu.constant;

/**
 * @Author caimeisahng
 * @Date 2026/3/22 17:23
 * @Version 1.0
 * 商品业务模块常量类
 */

/**
 * 1. 历史原因与语法特性
 * 隐式公共静态最终成员：在接口中定义的字段默认都是 public static final 的。
 * 开发者无需显式编写这三个修饰符，代码更简洁。
 * 例如：String ALL_CATEGORY_LIST_KEY = "all_category";
 * 等价于 public static final String ALL_CATEGORY_LIST_KEY = "all_category";。
 * 实现即继承：如果一个类 implements 了这个接口，就可以直接使用常量名，
 * 无需通过类名限定（如直接写 ALL_CATEGORY_LIST_KEY 而不是 ProductConstant.ALL_CATEGORY_LIST_KEY）。
 * 这在早期被认为能减少打字量
 */
public interface ProductConstant {

    /*
    * 商品所有类目数据存放到redis中的key
    * */
    String ALL_CATEGORY_LIST_KEY = "'allCategory'";

    /*
     * 商品一级类目数据存放到redis中的key
     * */
    String FIRST_CATEGORY_LIST_KEY = "'firstCategory'";

    /**
     * 状态正常的商品分组标签数据存放到redis中的key
     */
    String PROD_TAG_NORMAL_KEY = "'prodTagNormal'";

    /**
     * 商品属性数据存放到redis中的key
     */
    String PROP_PROD_KEY = "'prodProp'";
}
