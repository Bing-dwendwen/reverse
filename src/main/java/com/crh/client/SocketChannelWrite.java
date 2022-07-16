package com.crh.client;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.util.Scanner;

public class SocketChannelWrite {
    public static void main(String[] args) throws InterruptedException, IOException {
        SocketChannel channel = SocketChannel.open(); // 打开通道
        // 进行连接
        channel.connect(new InetSocketAddress("localhost", 9001));
        // 设置缓冲区
        ByteBuffer buf = ByteBuffer.allocate(10);
        Scanner scanner = new Scanner(System.in);
        scanner.useDelimiter("\n");
        while(true){
            String msg = scanner.next() + "\r\n"; // 读取控制台输入的消息，再拼接上换行符
            // 遍历输入的内容
            for(int i=0; i<msg.length(); i++){
                buf.put((byte)msg.charAt(i));     // 将字符逐个放入缓冲区
                if(buf.position() == buf.limit() || i == msg.length()-1){
                    buf.flip();
                    // 往通道写入数据
                    channel.write(buf);
                    // 清空缓冲区
                    buf.clear();
                }
            }
            if("Bye\r\n".equals(msg)){
                channel.shutdownOutput();
                channel.close();
                break;
            }
        }
    }
}
