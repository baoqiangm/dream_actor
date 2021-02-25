package m.dreamj.core.actor;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

/**
 * 消息处理队列
 * 
 * @author dreamj
 * @Date 2021-02-25 10:32
 */
public class MessageQueue implements Runnable {

    private final LinkedBlockingQueue<MessageHandler> queues;
    private final Thread                              thread;

    public MessageQueue(ThreadGroup group, int index) {
        this(group, index, 1_0000);
    }

    public MessageQueue(ThreadGroup group, int index, int queueSize) {
        queues = new LinkedBlockingQueue<>(queueSize);
        thread = new Thread(group, this, group.getName() + "-hander-" + index);
        thread.start();
    }

    public void offer(MessageHandler c) {
        queues.offer(c);
    }

    @Override
    public void run() {
        while (true) {
            MessageHandler s;
            try {
                s = queues.poll(60, TimeUnit.SECONDS);
            } catch (InterruptedException e) {
                s = null;
            }
            if (s != null) {
                s.getActor().handle(s.getContent());
            }
        }
    }

}
