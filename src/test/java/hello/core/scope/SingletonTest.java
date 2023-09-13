package hello.core.scope;

import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Scope;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import static org.assertj.core.api.Assertions.assertThat;
/**
 * 싱글톤 빈 스코프를 테스트 한 것이며, 싱글톤 빈 조회 시 컨테이너가 같은 객체를 반환하고 소멸자까지 관리해주는 것을 알 수 있다.
 * */
public class SingletonTest {
    @Test
    public void singletonBeanFind() {
        //아래 스프링 컨테이너를 등록하면 자동으로 @ComponentScan 이 등록된다.
        AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(SingletonBean.class);

        SingletonBean singletonBean1 = ac.getBean(SingletonBean.class);
        SingletonBean singletonBean2 = ac.getBean(SingletonBean.class);

        System.out.println("singletonBean1 = " + singletonBean1);
        System.out.println("singletonBean2 = " + singletonBean2);

        assertThat(singletonBean1).isSameAs(singletonBean2);
        ac.close(); //스프링 컨테이너 종료
        
    }

    //Scope가 singleton 일 때는 스프링 컨테이너가 생성 관리까지 해주며 클라이언트 요청이 들어오면 같은 객체를 주입해준다.
    @Scope("singleton")
    static class SingletonBean {
        @PostConstruct
        public void init() {
            System.out.println("SingletonBean.init");
        }
        @PreDestroy
        public void destroy() {
            System.out.println("SingletonBean.destroy");
        }
    }
}
