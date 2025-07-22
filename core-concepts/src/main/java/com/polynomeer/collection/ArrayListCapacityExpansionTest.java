package com.polynomeer.collection;

import java.util.ArrayList;

/**
 * 디버깅 창의 Thread & Variables 탭에 아래의 리플렉션 표현식을 등록해서
 * 내부(private) 데이터인 elementData 크기를 볼 수 있다.
 * <p>
 * {
 * java.lang.reflect.Field f = list.getClass().getDeclaredField("elementData");
 * f.setAccessible(true);
 * ((Object[]) f.get(list)).length;
 * }
 */
public class ArrayListCapacityExpansionTest {
    public static void main(String[] args) {
        ArrayList<String> list = new ArrayList<>();
        for (int i = 0; i < 11; i++) {
            list.add("Item " + i);
        }
        System.out.println("size: " + list.size());
    }
}
