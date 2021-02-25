package m.dreamj.core.actor;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import org.junit.Test;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

public class MessageActorTest {

    @Test
    public void Test() {

        // 初始化数据
        ThreadGroup          group      = new ThreadGroup("message");
        int                  size       = 10;
        int                  playerSize = size * 5;
        MessageQueue[]       qs         = new MessageQueue[size];// 缓存处理队列
        Map<Integer, Player> players    = new HashMap<>();// 缓存玩家
        for (int i = 0; i < qs.length; i++) {
            qs[i] = new MessageQueue(group, i);
        }

        for (int i = 0; i < playerSize; i++) {
            players.put(i, new Player(i));
        }

        // 模仿接收数据
        final Random r = new Random();
        for (int i = 0; i < size; i++) {
            new Thread(() -> {
                for (int x = 0; x < playerSize; x++) {
                    int     index  = r.nextInt(playerSize);
                    Player  player = players.get(index);
                    ByteBuf buff   = Unpooled.buffer();
                    buff.writeBytes((Thread.currentThread().getName() + "\tplayerId=" + index + ",\tmessageId=" + x).getBytes());

                    qs[index % size].offer(new MessageHandler(player, buff));// 接收都消息加入处理队列
                }

            }).start();

        }
        // 等待数据处理完成
        try {
            Thread.sleep(5_000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    public static class Player extends MessageActor {
        private int id;

        public Player(int id) {
            super();
            this.id = id;
        }

        public int getId() {
            return id;
        }

        @Override
        public void handle(ByteBuf content) {
            byte[] bs = new byte[content.readableBytes()];
            content.readBytes(bs);
            System.out.println(Thread.currentThread().getName() + " >>>> " + new String(bs));

        }

    }
}
