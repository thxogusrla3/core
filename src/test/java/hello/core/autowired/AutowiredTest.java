package hello.core.autowired;

import hello.core.member.Member;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.lang.Nullable;

import java.util.Optional;

public class AutowiredTest {

    @Test
    void AutowiredOption() {
        ApplicationContext ac = new AnnotationConfigApplicationContext(TestBean.class);
    }

    static class TestBean {

        /**
         * required = false 해주면 의존관계가 없으면 애초에 호출이 되지 않는다.
         * Member 는 스프링 빈이 아니기 떄문에 null 이다.
         * */
        @Autowired(required = false)
        public void setNoBean1(Member noBean1) {
            System.out.println("noBean1 = " + noBean1);
        }

        /**
         * @Nullable 이면 주입할 대상이 없다하더라도 호출한다.
         * */
        @Autowired
        public void setNoBean2(@Nullable Member noBean2) {
            System.out.println("TestBean.setNoBean2 = " + noBean2);
        }

        @Autowired
        public void setNoBean3(Optional<Member> noBean3) {
            System.out.println("TestBean.setNoBean3 = " + noBean3);
        }
    }
}
