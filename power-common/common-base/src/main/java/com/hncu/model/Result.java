package com.hncu.model;

import com.hncu.constant.BusinessEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @Author caimeisahng
 * @Date 2025/9/22 4:49
 * @Version 1.0
 */

@AllArgsConstructor
@NoArgsConstructor
@Data
@ApiModel("项目统一响应结果集")
public class Result<T> implements Serializable {
    @ApiModelProperty("状态码")
    private Integer code = 200;
    @ApiModelProperty("消息")
    private String msg = "ok";
    @ApiModelProperty("数据")
    private T data;




    /**
     *
     * @param data
     * @return
     * @param <T>
     */
    /*
    * <t>: 这是一个泛型声明，表示该方法可以处理任何类型的数据。T 是类型参数，可以是任何对象类型。
      Result<t>: 这是该方法的返回类型。方法返回一个 Result 类型的对象，且该对象可以包含类型为 T 的数据。
    * */
    public static <T> Result<T> success(T data){
        Result result = new Result<>();
        result.setData(data);
        return result;
    }

    /**
     * 操作失败
     * @param code
     * @param msg
     * @return
     * @param <T>
     */
    public static <T> Result<T> fail(Integer code,String msg){
        Result result = new Result<>();
        result.setCode(code);
        result.setMsg(msg);
        result.setData(null);
        return result;
    }

    public  static <T> Result<T> fail(BusinessEnum businessEnum){
        Result result = new Result<>();
        result.setCode(businessEnum.getCode());
        result.setMsg(result.getMsg());
        result.setData(null);
        return result;
    }
}
