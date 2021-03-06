package nettyclient;

import java.net.InetSocketAddress;
import java.util.Scanner;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;

public class NettyClient {
	
	private static final String lineDelimitor = "\r\n";
    private String host;
    private int port;

    public NettyClient(String host, int port){
        
    	this.host = host;
        
    	this.port = port;
    }
    
    public static void main(String[] args) throws Exception{
        
    	new NettyClient("127.0.0.1", 9000).start();
    }

    public void start() throws Exception{
        
    	Scanner scanner = new Scanner(System.in);
        
    	String msg;
        
    	EventLoopGroup group = new NioEventLoopGroup();
        
    	try {
            
    		Bootstrap bootstrap = new Bootstrap();
            
    		bootstrap.group(group)
                    .channel(NioSocketChannel.class)
                    .remoteAddress(new InetSocketAddress(host,port))
                    .handler(new NettyClientInitializer());
            
    		Channel serverChannel = bootstrap.connect().sync().channel();
            
    		ChannelFuture future = null;
            
    		System.out.println("Client connect complete");
            
    		while(true) {
                
    			msg = scanner.nextLine();
                
    			future = serverChannel.writeAndFlush(msg.concat(lineDelimitor));

                if("quit".equals(msg)) {
                    
                	serverChannel.closeFuture().sync();
                    
                	break;
                }
            }

            // 채널이 닫히기 전 모든 메시지가 flush될때까지 대기.
            if(future != null) {
                
            	future.sync();
            }
        } finally {
            group.shutdownGracefully();
        }
    }	
}