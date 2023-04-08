package task1;

import java.util.*;

public class Main {
    public static final Map<Integer, Integer> sizeToFreq = new HashMap<>();

    public static void main(String[] args) throws InterruptedException {
        List<Thread> list = new ArrayList<>();
        for (int i = 0; i < 1000; i++) {
            Thread thread = new Thread(() -> {
                String mes = generateRoute("RLRFR", 100);
                int count = 0;
                for (int j = 0; j < mes.length(); j++) {
                    if (mes.charAt(j) == 'R') count++;
                }
                synchronized (sizeToFreq) {
                    if (!sizeToFreq.containsKey(count)) {
                        sizeToFreq.put(count, 1);
                    } else {
                        sizeToFreq.put(count, sizeToFreq.get(count) + 1);
                    }
                }
            });
            list.add(thread);
            thread.start();
        }
        for (Thread thread : list) {
            thread.join();
        }
        print(sizeToFreq);

    }

    public static void print(Map<Integer, Integer> map) {
        Map.Entry<Integer, Integer> max = map.entrySet().stream()
                .max(Comparator.comparing(Map.Entry::getValue))
                .orElse(null);
        System.out.printf("Самое частое количество повторений: %d встретилось %d раз \n", max.getKey(), max.getValue());
        System.out.println("Другие размеры: ");
        for (Map.Entry<Integer, Integer> entry : map.entrySet()) {
            System.out.println("- " + entry.getKey() + " (" + entry.getValue() + " раз)");
        }

    }

    public static String generateRoute(String letters, int length) {
        Random random = new Random();
        StringBuilder route = new StringBuilder();
        for (int i = 0; i < length; i++) {
            route.append(letters.charAt(random.nextInt(letters.length())));
        }
        return route.toString();
    }
}
