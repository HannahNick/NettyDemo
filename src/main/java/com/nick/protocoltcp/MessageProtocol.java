package com.nick.protocoltcp;

/**
 * @author zwj
 * @date 2020/7/21
 */
public class MessageProtocol {
    private int len;//关键
    private byte[] content;

    public int getLen() {
        return len;
    }

    public void setLen(int len) {
        this.len = len;
    }

    public byte[] getContent() {
        return content;
    }

    public void setContent(byte[] content) {
        this.content = content;
    }
}
