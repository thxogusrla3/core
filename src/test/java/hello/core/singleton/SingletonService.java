package hello.core.singleton;

public class SingletonService {

    //1. static 영역에 객체를 딱 1개만 생성해둔다.
    private static final SingletonService instance = new SingletonService();

    //싱글톤을 구현하려면, 객체 인스턴스를 2개 이상 생성하지 못하도록 막아야한다.
    //private 생성자를 사용해서 외부에서 임의로 new 키워드를 사용하지 못하도록 막아야 한다.
    public static SingletonService getInstance() {
        return instance;
    }

    //이미 static 으로 만들어두었기 때문에
    //private 생성자로 생성하지 못하도록 막아야하는 것이다.

    private SingletonService() {

    }

    public void logic() {
        System.out.println("싱글톤 객체 로직 호출");
    }
}
