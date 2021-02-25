package m.dreamj.core.actor;

import io.netty.buffer.ByteBuf;

/**
 * 消息处理类
 * 
 * @author dreamj
 * @Date 2021-02-25 10:32
 */
public class MessageHandler {
    private MessageActor actor;
    private ByteBuf      content;

    public MessageHandler(MessageActor actor, ByteBuf content) {
        super();
        this.actor   = actor;
        this.content = content;
    }

    public MessageActor getActor() {
        return actor;
    }

    public void setActor(MessageActor actor) {
        this.actor = actor;
    }

    public ByteBuf getContent() {
        return content;
    }

    public void setContent(ByteBuf content) {
        this.content = content;
    }

}
