package m.dreamj.core.actor;

import io.netty.buffer.ByteBuf;

/**
 * 消息接收者
 * 
 * @author dreamj
 * @Date 2021-02-25 10:31
 */
public abstract class MessageActor {
    public abstract void handle(ByteBuf content);
}
