package protocolsupport.protocol.packet.middle.clientbound.play;

import io.netty.buffer.ByteBuf;
import protocolsupport.protocol.ConnectionImpl;
import protocolsupport.protocol.packet.middle.ClientBoundMiddlePacket;
import protocolsupport.protocol.serializer.ArraySerializer;
import protocolsupport.protocol.serializer.PositionSerializer;
import protocolsupport.protocol.serializer.VarNumberSerializer;
import protocolsupport.protocol.types.ChunkCoord;
import protocolsupport.protocol.types.chunk.ChunkConstants;
import protocolsupport.utils.Utils;

public abstract class MiddleChunkLight extends ClientBoundMiddlePacket {

	public MiddleChunkLight(ConnectionImpl connection) {
		super(connection);
	}

	protected ChunkCoord coord;
	protected int setSkyLightMask;
	protected int setBlockLightMask;
	protected int emptySkyLightMask;
	protected int emptyBlockLightMask;
	protected final byte[][] skyLight = new byte[ChunkConstants.SECTION_COUNT_LIGHT][];
	protected final byte[][] blockLight = new byte[ChunkConstants.SECTION_COUNT_LIGHT][];

	@Override
	public void readFromServerData(ByteBuf serverdata) {
		coord = PositionSerializer.readVarIntChunkCoord(serverdata);

		setSkyLightMask = VarNumberSerializer.readVarInt(serverdata);
		setBlockLightMask = VarNumberSerializer.readVarInt(serverdata);
		emptySkyLightMask = VarNumberSerializer.readVarInt(serverdata);
		emptyBlockLightMask = VarNumberSerializer.readVarInt(serverdata);

		for (int i = 0; i < ChunkConstants.SECTION_COUNT_LIGHT; i++) {
			skyLight[i] = Utils.isBitSet(setSkyLightMask, i) ? ArraySerializer.readVarIntByteArray(serverdata) : null;
		}
		for (int i = 0; i < ChunkConstants.SECTION_COUNT_LIGHT; i++) {
			blockLight[i] = Utils.isBitSet(setBlockLightMask, i) ? ArraySerializer.readVarIntByteArray(serverdata) : null;
		}
	}

}
