package org.example.golrang;
import java.util.*;

public class SafflowerShampoo {
    static class Shampoo {
        int id, arrivalTime, duration;

        Shampoo(int id, int arrivalTime, int duration) {
            this.id = id;
            this.arrivalTime = arrivalTime;
            this.duration = duration;
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();

        List<Shampoo> shampoos = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            int arrivalTime = scanner.nextInt();
            int duration = scanner.nextInt();
            shampoos.add(new Shampoo(i + 1, arrivalTime, duration));
        }

        // Sort shampoos by arrival time
        shampoos.sort(Comparator.comparingInt(s -> s.arrivalTime));

        PriorityQueue<Shampoo> queue = new PriorityQueue<>(
                (a, b) -> a.duration != b.duration
                        ? Integer.compare(a.duration, b.duration)
                        : Integer.compare(a.id, b.id)
        );

        int currentTime = 0;
        int shampooIndex = 0;

        while (shampooIndex < shampoos.size() || !queue.isEmpty()) {
            // Add shampoos to the queue that are available at currentTime
            while (shampooIndex < shampoos.size() && shampoos.get(shampooIndex).arrivalTime <= currentTime) {
                queue.offer(shampoos.get(shampooIndex));
                shampooIndex++;
            }

            if (!queue.isEmpty()) {
                // Use the shampoo with the shortest duration
                Shampoo currentShampoo = queue.poll();
                System.out.println(currentShampoo.id);
                currentTime++;
                currentShampoo.duration--;

                // If there's still remaining time, re-add it to the queue
                if (currentShampoo.duration > 0) {
                    queue.offer(currentShampoo);
                }
            } else {
                // Move to the next shampoo's arrival time
                if (shampooIndex < shampoos.size()) {
                    currentTime = shampoos.get(shampooIndex).arrivalTime;
                }
            }
        }

        scanner.close();
    }
}
