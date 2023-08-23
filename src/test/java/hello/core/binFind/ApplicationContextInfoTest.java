package hello.core.binFind;

import hello.core.AppConfig;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class ApplicationContextInfoTest {
    AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(AppConfig.class);

    @Test
    @DisplayName("모든 빈 출력하기")
    void findAllBean() {
        String[] beanDefinitionNames = ac.getBeanDefinitionNames();

        for (String beanDefinitionName : beanDefinitionNames) {
            //타입을 정의해주지 않았기 때문에 Object로 나옴.
            Object bean = ac.getBean(beanDefinitionName);
            System.out.println("name = " + beanDefinitionName + "\n" + "Object = " + bean);
            System.out.println("\n");
        }
    }

    @Test
    @DisplayName("등록한 빈만 출력하기")
    void findApplicationBean() {
        //getBeanDefinitionNames() 빈 정의된 이름을 다 가져옴
        String[] beanDefinitionNames = ac.getBeanDefinitionNames();

        for (String beanDefinitionName : beanDefinitionNames) {
            BeanDefinition beanDefinition = ac.getBeanDefinition(beanDefinitionName);

            //BeanDefinition.ROLE_APPLICATION 내가 등록한 빈만 가져오는 것
            //BeanDefinition.ROLE_INFRASTRUCTURE 스프링이 내부에서 등록한 빈
            if(beanDefinition.getRole() == BeanDefinition.ROLE_INFRASTRUCTURE) { //스프링이 등록한 것이 아닌 내가 등록한 빈
                Object bean = ac.getBean(beanDefinitionName);
                System.out.println("name = " + beanDefinitionName + "\n" + "Object = " + bean);
                System.out.println("\n");
            }
        }
    }
}
