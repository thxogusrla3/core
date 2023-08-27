package hello.core.singleton;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;

import static org.junit.jupiter.api.Assertions.*;

class StatefulServiceTest {

    @Test
    void statefulServiceSingleton() {
        ApplicationContext ac = new AnnotationConfigApplicationContext(TestConfig.class);
        StatefulService statefulService1 = ac.getBean("statefulService", StatefulService.class);
        StatefulService statefulService2 = ac.getBean("statefulService", StatefulService.class);

        //여기서 상태 싱글톤은 하나의 객체가 공유되므로  userB의 가격이 들어가버림
        //이러한 이유는 statefulService.price 가 상태유지가 되기 때문이다.
        //statefulService1.order("userA", 10000);
        //statefulService2.order("userB", 20000);

        //아래는 무상태로 객체를 설계한 것이다.
        //다른 점은 StatefuleService.price 를 함수에서 return 해주는 형식으로 바꾼 것이다.
        int userAPrice = statefulService1.order("userA", 10000);
        int userBPrice = statefulService2.order("userB", 20000);

        //Assertions.assertThat(statefulService1.getPrice()).isEqualTo(20000);

        Assertions.assertThat(userAPrice).isEqualTo(10000);

    }

    static class TestConfig {
        @Bean
        public StatefulService statefulService() {
            return new StatefulService();
        }
    }
}