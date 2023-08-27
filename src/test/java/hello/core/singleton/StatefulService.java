package hello.core.singleton;

public class StatefulService { //ctrl + shift + t 하면 테스트 파일 자동생성
//    private int price; //상태를 유지하는 필드

    public int order(String name, int price) {
        System.out.println("name = " + name + " price = " + price);
        return price;
    }

//    public int getPrice() {
//        return price;
//    }
}
