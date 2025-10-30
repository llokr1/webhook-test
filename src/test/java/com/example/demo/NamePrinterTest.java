package com.example.demo;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("NamePrinter 클래스 단위 테스트")
public class NamePrinterTest {

    private final NamePrinter namePrinter = new NamePrinter();

    /**
     * 테스트 1: getMyName() 메서드가 정확한 이름을 반환하는지 검증
     */
    @Test
    @DisplayName("getMyName은 '홍길동'을 정확히 반환해야 한다.")
    void getMyName_shouldReturnCorrectName() {
        // 1. Arrange (준비) - 테스트에 필요한 객체 준비 완료 (namePrinter 객체)
        String expectedName = "홍진기";

        // 2. Act (실행) - 테스트 대상 메서드 실행
        String actualName = namePrinter.getMyName();

        // 3. Assert (검증) - AssertJ를 사용하여 결과 검증
        assertThat(actualName)
                .as("반환된 이름은 '홍진기'이어야 합니다.") // 테스트 실패 시 출력될 메시지
                .isNotNull()                             // 결과가 null이 아닌지
                .isEqualTo(expectedName)                 // 예상 값과 정확히 일치하는지
                .startsWith("홍")                        // '홍'으로 시작하는지
                .contains("진기")                        // '길동'을 포함하는지
                .hasSize(3);                             // 문자열 길이가 3인지
    }

    /**
     * 테스트 2: getGreetingName() 메서드가 예상되는 인사말을 반환하는지 검증
     */
    @Test
    @DisplayName("getGreetingName은 'Hello, 홍길동'을 반환해야 하며, 대소문자를 무시하면 안 된다.")
    void getGreetingName_shouldReturnCorrectGreeting() {
        // 1. Arrange (준비)
        String expectedGreeting = "Hello, 진기";

        // 2. Act (실행)
        String actualGreeting = namePrinter.getGreetingName();

        // 3. Assert (검증)
        assertThat(actualGreeting)
                .as("반환된 인사말은 정확한 포맷을 가져야 합니다.")
                .isEqualTo(expectedGreeting); // 정확히 일치하는지 검증

        // 대소문자를 무시한 비교 (Negative Test Case 예시)
        assertThat(actualGreeting)
                .as("대소문자 무시 비교 시 'hello'와 달라야 합니다.")
                .isNotEqualToIgnoringCase("hello, 홍길동");
    }
}