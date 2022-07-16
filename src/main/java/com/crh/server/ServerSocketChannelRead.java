package com.crh.server;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;

public class ServerSocketChannelRead {
    public static void main(String[] args) throws IOException {
        ServerSocketChannel server = ServerSocketChannel.open();
        server.bind(new InetSocketAddress(9001));   // 绑定端口
        ByteBuffer buff = ByteBuffer.allocate(10);
        while (true) {
            // 阻塞，直到有连接过来
            SocketChannel client = server.accept();
            System.out.println("Client connected.");
            while (true) {
                // 循环读取客户端发送过来的数据
                if(client.read(buff) == -1){
                    client.close();
                    System.out.println("Client closed the connection.");
                    break;
                }
                buff.flip();
                while (buff.position() < buff.limit()) {
                    // 打印
                    System.out.print((char) buff.get());
                }
                // 切换到写模式
                buff.clear();
            }
        }
    }
}
