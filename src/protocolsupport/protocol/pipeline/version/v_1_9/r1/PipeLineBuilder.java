package protocolsupport.protocol.pipeline.version.v_1_9.r1;

import io.netty.channel.Channel;
import io.netty.channel.ChannelPipeline;
import net.minecraft.server.v1_10_R1.NetworkManager;
import protocolsupport.api.Connection;
import protocolsupport.protocol.packet.handler.common.ModernHandshakeListener;
import protocolsupport.protocol.pipeline.ChannelHandlers;
import protocolsupport.protocol.pipeline.IPipeLineBuilder;
import protocolsupport.protocol.pipeline.common.VarIntFrameDecoder;
import protocolsupport.protocol.pipeline.common.VarIntFrameEncoder;
import protocolsupport.protocol.storage.NetworkDataCache;

public class PipeLineBuilder implements IPipeLineBuilder {

	@Override
	public void buildPipeLine(Channel channel, Connection connection) {
		ChannelPipeline pipeline = channel.pipeline();
		NetworkManager networkmanager = (NetworkManager) pipeline.get(ChannelHandlers.NETWORK_MANAGER);
		networkmanager.setPacketListener(new ModernHandshakeListener(networkmanager, true));
		ChannelHandlers.getSplitter(pipeline).setRealSplitter(new VarIntFrameDecoder());
		ChannelHandlers.getPrepender(pipeline).setRealPrepender(new VarIntFrameEncoder());
		NetworkDataCache sharedstorage = new NetworkDataCache();
		pipeline.addAfter(ChannelHandlers.SPLITTER, ChannelHandlers.DECODER_TRANSFORMER, new PacketDecoder(connection, sharedstorage));
		pipeline.addAfter(ChannelHandlers.PREPENDER, ChannelHandlers.ENCODER_TRANSFORMER, new PacketEncoder(connection, sharedstorage));
	}

}
