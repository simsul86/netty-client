package nettyclient;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.LineBasedFrameDecoder;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;

public class NettyClientInitializer extends ChannelInitializer<SocketChannel> {

	@Override
	protected void initChannel(SocketChannel socketChannel) throws Exception {

		socketChannel.pipeline().addLast(new LineBasedFrameDecoder(64 * 1024))
							    .addLast(new StringDecoder())
							    .addLast(new StringEncoder())
							    .addLast(new NettyClientHandler());
		
	}	
}