package com.assignment.board.Aspect;

import lombok.RequiredArgsConstructor;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
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
     * FUNCTION :: Exception 발생 시 트랜잭션 롤백 처리 ( Unchecked Exception )
     * @param joinPoint
     * @return
     * @throws Throwable
     */
    @Around("getInsert()")
    public Object TransactionHandle(ProceedingJoinPoint joinPoint) throws Throwable {
        TransactionDefinition definition = new DefaultTransactionDefinition(); // 트랜잭션 기본 설정 ( 4가지 속성 )
        TransactionStatus status = transactionManager.getTransaction(definition); // 현재 참여하고 있는 트랜잭션의 관리 기능 엑세스
        try { // Partially Commit
            Object object = joinPoint.proceed();
            transactionManager.commit(status); // Commit
            return object;
        } catch (RuntimeException runtimeException) { // Failed
            transactionManager.rollback(status); // Aborted
            throw runtimeException;
        }
    }
}