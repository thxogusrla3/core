package hello.core;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;

@Configuration
@ComponentScan(//@component 어노테이션 붙은 것들을 다 찾아서 스프링 빈으로 등록해줌.
    excludeFilters = @ComponentScan.Filter(type = FilterType.ANNOTATION, classes = Configuration.class) //예제코드를 살리기 위해 이 코드를 추가함.
)
public class AutoAppConfig {

}
