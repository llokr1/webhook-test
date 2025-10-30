package com.example.demo;

public class NamePrinter {

    // 이 메서드를 단위 테스트로 검증할 것입니다.
    public String getMyName() {
        return "홍진기";
    }

    // 이름을 "Hello, [이름]" 형태로 반환하는 다른 메서드도 함께 테스트해봅니다.
    public String getGreetingName() {
        return "Hello, 진기";
    }
}