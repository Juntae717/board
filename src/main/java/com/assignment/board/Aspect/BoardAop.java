package com.assignment.board.Aspect;

import lombok.RequiredArgsConstructor;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

@Aspect
@Component
@RequiredArgsConstructor
public class BoardAop {
    private final PlatformTransactionManager transactionManager;

    @Pointcut("execution(* com.assignment.board.Board.controller.BoardController.insert*(..))")
    public void getInsert() {

    }

    @Pointcut("execution(* com.assignment.board.Board.controller.BoardController.select*(..))")
    public void getSelect() {

    }

    @Around("getInsert() || getSelect()")
    public Object TransactionHandle(ProceedingJoinPoint joinPoint) throws Throwable {
        TransactionStatus transactionStatus = transactionManager.getTransaction(new DefaultTransactionDefinition());
        try {
            Object object = joinPoint.proceed();
            transactionManager.commit(transactionStatus);
            return object;
        } catch (RuntimeException runtimeException) {
            transactionManager.rollback(transactionStatus);
            throw runtimeException;
        }
    }
}
