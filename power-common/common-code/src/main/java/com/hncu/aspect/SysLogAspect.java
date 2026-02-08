package com.hncu.aspect;

import cn.hutool.core.util.ObjectUtil;
import com.alibaba.fastjson.JSONObject;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.Date;

/**
 * @Author caimeisahng
 * @Date 2026/2/3 4:11
 * @Version 1.0
 * 记录系统操作日志aop
 *
 */

@Component
// @Aspect 注解的作用
//1.标识切面类：标记一个类为切面类，告诉Spring这个类包含了通知（Advice）和切点（Pointcut）的定义。
//2.实现横切关注点：允许你在不修改原始代码的情况下，将横切关注点（如日志记录、事务管理、安全检查等）
// 应用到应用程序的不同部分。
//3.增强功能：通过切面可以对方法执行前、后或异常时进行额外处理
@Aspect
@Slf4j
public class SysLogAspect {
    /**
     * 切入点表达式
     * execution n.（尤指遗嘱的）执行
     */
    public static final String POINT_CUT = "execution (* com.hncu.controller.*.*(..))";

    @Around(value = POINT_CUT)
    /*在Java的AOP（面向切面编程）中，ProceedingJoinPoint 是一个重要的接口，
    它是JoinPoint 的扩展，专门用于环绕通知（@Around）中。
    ProceedingJoinPoint 的作用
    1.控制目标方法执行：最重要的功能是能够决定是否执行目标方法以及何时执行。
    2.获取连接点信息：提供关于当前连接点（方法调用）的详细信息。
    3.传递参数给目标方法：可以修改传递给目标方法的参数。
    4.处理返回值或异常：可以在方法执行前后处理结果*/

    /*核心方法包括：
    proceed()：执行被拦截的方法，并返回其结果
    getArgs()：获取传入目标方法的参数
    getTarget()：获取被代理的目标对象
    getSignature()：获取被拦截的方法签名
    getSourceLocation()：获取源码位置
    */
    public Object logAround(ProceedingJoinPoint joinPoint){
        Object result = null;

        //获取请求对象
        ServletRequestAttributes requestAttributes= (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = requestAttributes.getRequest();
        //获取请求路径
        String path = request.getRequestURI();
        //获取ip地址
        String remoteHost = request.getRemoteHost();
        //获取请求参数
        Object[] args = joinPoint.getArgs();
        //这行代码获取被拦截方法的签名信息。通过joinPoint.getSignature()获得方法签名对象
        // 然后强制转换为`MethodSignature类型，以便后续可以获取方法名、参数类型、返回类型等详细的元数据信息。
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        String methodName = method.toString();
        //获取目标方法上的@ApiOperation注解
        ApiOperation apiOperation = method.getDeclaredAnnotation(ApiOperation.class);

        //判断该注解对象是否为空
        String operation = "";
        if (ObjectUtil.isNotNull(apiOperation)){
            // 获取ApiOperation注解的描述
            operation = apiOperation.value();
        }

        String finalArgs =  "";
        // 判断参数类型
        if (ObjectUtil.isNotNull(args) && args.length !=0 && args[0] instanceof MultipartFile) {
            // 说明当前参数为文件对象
            finalArgs = "file";
        } else {
            finalArgs = JSONObject.toJSONString(args);
        }

        // 记录开始时间
        long startTime = System.currentTimeMillis();

        //执行方法
        try {
            result = joinPoint.proceed();
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }

        //记录结束时间
        long endTime = System.currentTimeMillis();

        //方法的执行时长
        long execTime = endTime - startTime;

        log.info("调用时间:{},请求接口路径:{},请求IP地址:{},方法名称:{},执行时长:{}，方法名称:{}",
                new Date(),
                path,
                remoteHost,
                methodName,
                execTime,
                operation);


        return result;
    }
}
