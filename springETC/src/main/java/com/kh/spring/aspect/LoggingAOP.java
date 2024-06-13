package com.kh.spring.aspect;

import java.lang.reflect.Method;
import java.util.Arrays;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

/*
 * - @Aspect : 해당 클래스가 Aspect라는 것을 선언한다.
 * - @Component : Spring이 해당 빈을 서칭할 수 있도록 선언함(빈에등록)
 * - @EnableAspectJAutoProxy : aop를 활성화시켜주는 선언
 * 
 */
@Aspect
@Component
@Slf4j
@EnableAspectJAutoProxy
public class LoggingAOP {
	/*
	 * <시점>
	 * @Before : 대상메소드 실행 전에 Advice(추가기능)이 실행된다.
	 * 
	 * @After : 대상메소드 실행 후에 Advice(추가기능)이 실행된다.
	 * 
	 * @AfterReturning : 대상 메소드가 정상적으로 반환된 후에 Advice가 실행됩니다.
	 * 
	 * @Afterthrowing : 대상 메소드가 예외를 던진 후에 Advice가 실행된다.
	 * 
	 * @Around : 대상 메소드를 감싸서 메소드 호출 전후에 Advice를 실행할 수 있다.
	 */
	
	/*
	 * <대상>
	 * target : 특정 인터페이스와 그 자식클래스
	 * within : 특정 패키지 또는 클래스
	 * execution : 표현식으로 형태 지정
	 */
	
	//@Pointcut : 내가 기능을 사용할 지점을 정의
	//com.kh.spring패키지 하위 패키지중 controller 내에 있는 모든 클래스의 모든 메소드
	@Pointcut("execution(* com.kh.spring..controller.*.*(..))")
	private void cut() {
		
	}
	
	//cut 메소드가 실행되는 지점 이전에 before()메소드를 실행
	//JoinPoint는 프로그램의 실행중 특정 지점을 나타내며,
	//메소드 실행이 가장 일반적인 JoinPoint이다.
	@Before("cut()")
	public void before(JoinPoint joinPoint) {
		// 실행되는 메소드의 이름을 가져오기 
		MethodSignature methodSignature = (MethodSignature)joinPoint.getSignature();
		Method method = methodSignature.getMethod();
		
		//메소드에 들어가는 매개변수 배열을 읽어온다.
		Object[] args = joinPoint.getArgs();
		
		log.info("==================start=========================");
		log.info("--------------API Controller--------------------");
		log.info("Information : " + methodSignature);
		log.info("Method Name : " + method);
		log.info("Parameter : " + Arrays.toString(args));
	}
	
	@AfterReturning(value="cut()", returning = "obj")
	public void afterReturn(JoinPoint joinPoint, Object obj) {
		log.info("==================end=========================");
		log.info("Object : " + obj);
	}
	
	//API 시간측정
	@Around("cut()")
	public Object displayLogInfo(ProceedingJoinPoint pJoinPoint) throws Throwable {
		long start = System.currentTimeMillis();
		Object result = pJoinPoint.proceed(); // 원래 해야되는 기능을 실행해준다.
		
		long end = System.currentTimeMillis();
		long pTime = end - start;
		
		log.info("------------------------------------------"); 
		log.info("Information      : " + pJoinPoint.getSignature()); 
		log.info("Processing Time  : " + pTime + "ms");
		
		return result;
	}
	
}
