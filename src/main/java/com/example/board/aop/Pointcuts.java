package com.example.board.aop;

import org.aspectj.lang.annotation.Pointcut;

public class Pointcuts {


    /**
     * 접근제어자 : 생략
     * 반환타입 : *
     * 타입이름: com.example.board.service 패키지 경로의 모든 하위 타입
     * 메서드 이름: 모든 메서드
     * 매개변수 : 모든 매개변수
     * 예외 : 생략
     */
    @Pointcut("execution(* com.example.board.service..*(..))")
    public void allService() {
    }


    @Pointcut("execution(* com.example.board.api..*(..))")
    public void allController() {

    }
}
