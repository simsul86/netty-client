package nettyclient;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

public class NettyClientHandler extends SimpleChannelInboundHandler {

	@Override
	protected void channelRead0(ChannelHandlerContext ctx, Object msg) throws Exception {
		
		System.out.println(msg.toString());
	}
	
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable throwable) {
		
		throwable.printStackTrace();
		
		ctx.close();
	}
}