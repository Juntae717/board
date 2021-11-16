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

    /**
     * LINE :: insert(등록) 메소드 가져오기
     */
    @Pointcut("execution(* com.assignment.board.Board.service.BoardService.insert*(..))")
    public void getInsert() {

    }

    /**
     * LINE :: select(조회) 메소드 가져오기
     */
    @Pointcut("execution(* com.assignment.board.Board.service.BoardService.select*(..))")
    public void getSelect() {

    }

    /**
     * FUNCTION :: Exception 발생 시 트랜잭션 롤백 처리
     * @param joinPoint
     * @return
     * @throws Throwable
     */
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
