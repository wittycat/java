package utils;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by chenxun on 2018/1/2.
 */
public class DelayQueueCache<K, V> {
    private final static AtomicInteger count = new AtomicInteger(0);
    private ConcurrentMap<K, V> map = new ConcurrentHashMap<>();
    private DelayQueue<DelayEntity<Entity<K, V>>> delayQueue = new DelayQueue();

    public DelayQueueCache() {
        Thread thread = new Thread(() -> {
            for (; ; ) {
                try {
                    DelayEntity<Entity<K, V>> delayItem = delayQueue.take();
                    if (delayItem != null) {
                        Entity<K, V> pair = delayItem.getItem();
                        map.remove(pair.k);
                    }
                    TimeUnit.MINUTES.sleep(1);
                } catch (InterruptedException ignore) {
                    ignore.printStackTrace();
                }

            }
        });
        thread.setName("util-delayQueueCache-thread-" + count.getAndIncrement());
        thread.start();
    }

    public void put(K key, V value, long time, TimeUnit unit) {
        V oldValue = map.put(key, value);
        if (oldValue != null) {
            delayQueue.remove(key);
        }
        delayQueue.put(new DelayEntity(new Entity(key, value), TimeUnit.NANOSECONDS.convert(time, unit)));
    }

    public V get(K key) {
        return map.get(key);
    }

    private class Entity<K, V> {
        public K k;
        public V v;

        public Entity(K k, V v) {
            this.k = k;
            this.v = v;
        }
    }

    private class DelayEntity<T> implements Delayed {
        private final long start = System.nanoTime();
        private final long time;//到期时间
        private final T entity;

        private long now() {
            return System.nanoTime() - start;
        }

        private DelayEntity(T entity, long timeout) {
            this.time = now() + timeout;
            this.entity = entity;
        }

        public T getItem() {
            return this.entity;
        }

        /**
         * 剩余时间=到期时间-当前时间
         */
        @Override
        public long getDelay(TimeUnit timeUnit) {
            return timeUnit.convert(time - now(), TimeUnit.NANOSECONDS);
        }

        /**
         * 优先队列里面优先级规则
         */
        @Override
        public int compareTo(Delayed delayed) {
            if (delayed == this) {
                return 0;
            }
            DelayEntity delayEntity = (DelayEntity) delayed;
            long diff = this.time - delayEntity.time;
            if (diff < 0) {
                return -1;
            } else {
                return 1;
            }
        }
    }
}