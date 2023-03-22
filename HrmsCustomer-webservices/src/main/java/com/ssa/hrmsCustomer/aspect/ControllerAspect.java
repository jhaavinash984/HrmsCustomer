package com.ssa.hrmsCustomer.aspect;

import java.util.Arrays;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.ssa.hrmsCustomer.webservices.controller.TokenController;

@Aspect
@Component
public class ControllerAspect {
	
	private static final Logger logger = LoggerFactory.getLogger(ControllerAspect.class);

	/**
	 * Run before the method execution.
	 * @param joinPoint
	 */
	@Before("execution(* com.ssa.hrmsCustomer.webservices.controller..*(..))")
	public void logBefore(JoinPoint joinPoint) {
		logger.info("logBefore running .....");
		logger.info("Enter: {}() with argument[s] = {}", joinPoint.getSignature().getDeclaringTypeName(),
				joinPoint.getSignature().getName(), Arrays.toString(joinPoint.getArgs()));
		
	}

	/**
	 * Run after the method returned a result.
	 * @param joinPoint
	 */
	@After("execution(* com.ssa.hrmsCustomer.webservices.controller..*(..))")
	public void logAfter(JoinPoint joinPoint) {
		logger.info("logAfter running .....");
		logger.info("Enter: {}() with argument[s] = {}", joinPoint.getSignature().getDeclaringTypeName(),
				joinPoint.getSignature().getName(), Arrays.toString(joinPoint.getArgs()));
	}

	/**
	 * Run after the method returned a result, intercept the returned result as well.
	 * @param joinPoint
	 * @param result
	 */
	@AfterReturning(pointcut = "execution(* com.ssa.hrmsCustomer.webservices.controller..*(..))", returning = "result")
	public void logAfterReturning(JoinPoint joinPoint, Object result) {
		logger.info("logAfterReturning running .....");
		logger.info("Enter: {}() with argument[s] = {}", joinPoint.getSignature().getDeclaringTypeName(),
				joinPoint.getSignature().getName(), Arrays.toString(joinPoint.getArgs()));

	}

	/**
	 * Run around the method execution.
	 * @param joinPoint
	 * @return
	 * @throws Throwable
	 */
	@Around("execution(* com.ssa.hrmsCustomer.webservices.controller..*(..))")
	public Object logAround(ProceedingJoinPoint joinPoint) throws Throwable {
		logger.info("logAround running .....");
		if (logger.isDebugEnabled()) {
			logger.debug("Enter: {}.{}() with argument[s] = {}", joinPoint.getSignature().getDeclaringTypeName(),
					joinPoint.getSignature().getName(), Arrays.toString(joinPoint.getArgs()));
		}
		try {
			Object result = joinPoint.proceed();
			if (logger.isDebugEnabled()) {
				logger.debug("Exit: {}.{}() with result = {}", joinPoint.getSignature().getDeclaringTypeName(),
						joinPoint.getSignature().getName(), result);
			}
			return result;
		} catch (IllegalArgumentException e) {
			logger.error("Illegal argument: {} in {}.{}()", Arrays.toString(joinPoint.getArgs()),
					joinPoint.getSignature().getDeclaringTypeName(), joinPoint.getSignature().getName());
			throw e;
		}

	}

	/**
	 * Advice that logs methods throwing exceptions.
	 *
	 * @param joinPoint join point for advice
	 * @parame   exception
	 */

	@AfterThrowing(pointcut = "execution(* com.ssa.hrmsCustomer.webservices.controller..*(..))", throwing = "error")
	public void logAfterThrowing(JoinPoint joinPoint, Throwable error) {
		logger.debug("logAfterThrowing running .....");
		logger.error("Exception in {}.{}() with cause = {}", joinPoint.getSignature().getDeclaringTypeName(),
				joinPoint.getSignature().getName(), error.getCause() != null ? error.getCause() : "NULL");
	}

}
