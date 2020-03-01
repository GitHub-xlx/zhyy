package com.zhyy.aspect;

import com.zhyy.entity.Log;
import com.zhyy.entity.User;
import com.zhyy.services.LogServices;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.Date;

@Controller
@Aspect
public class AOPLog
{

	private long startTime;
	@Autowired
	private HttpServletRequest request;
	@Autowired
	private LogServices logServices;

	@Pointcut("execution(* com.zhyy.controller.*.*(..))")
	public void pointCut(){

	}
	@Around("pointCut()")
	public Object recordSysLog(ProceedingJoinPoint joinPoint) throws Throwable
	{

		startTime = System.currentTimeMillis();
		//业务方法执行

		Object result = joinPoint.proceed();

		try
		{
			handle(joinPoint);
		}catch (Exception e){
			e.printStackTrace();
		}

		return result;
	}

	private void handle(ProceedingJoinPoint joinPoint) throws Exception
	{

		//这个连接点：Class,Method,RequestMapping,才处理（自定义注解）
		//获取目标类
		Class<?> clazz = joinPoint.getTarget().getClass();
		//获取目标签名
		MethodSignature signature = (MethodSignature) joinPoint.getSignature();
		//获取对应的方法
		Method method = clazz.getMethod(signature.getName(), signature.getParameterTypes());
		//判断需要忽视记录的方法
		if (method.getAnnotation(IgnoreLog.class)!=null){
			return;
		}

		//获取类注解
		RequestMapping clazzAnnotation = clazz.getAnnotation(RequestMapping.class);
		//获取方法注解
		RequestMapping methodAnnotation = method.getAnnotation(RequestMapping.class);
		//判断注解是否存在

		if (methodAnnotation==null){
			return;
		}

		String url = null;
		if (clazzAnnotation!=null){
				url=clazzAnnotation.value()[0]+methodAnnotation.value()[0];
		}else{
				url=methodAnnotation.value()[0];
		}
		
		Log log = new Log();
		log.setUrl(url);
		log.setMethod("[类名]"+clazz.getName()+"[方法名]"+method.getName());
		log.setOperationdate(new Date(startTime));
		log.setDuration(System.currentTimeMillis()-startTime);
		log.setIp(request.getRemoteAddr());
		User user = (User) request.getSession().getAttribute("user");
		if (user!=null){
			log.setAccount(user.getAccount());
			log.setUsername(user.getUsername());
		}
		logServices.save(log);

	}
}
