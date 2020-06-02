package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_4_5_6_7_8_9r1_9r2_10_11_12r1_12r2_13;

import protocolsupport.protocol.ConnectionImpl;
import protocolsupport.protocol.packet.PacketType;
import protocolsupport.protocol.packet.middle.clientbound.play.MiddleEntityStatus;
import protocolsupport.protocol.packet.middleimpl.ClientBoundPacketData;
import protocolsupport.protocol.types.networkentity.NetworkEntityType;

public abstract class AbstractRemoveEquipmentOnDeathEntityStatus extends MiddleEntityStatus {

	public AbstractRemoveEquipmentOnDeathEntityStatus(ConnectionImpl connection) {
		super(connection);
	}

	@Override
	protected void writeToClient() {
		if ((entity.getType() == NetworkEntityType.PLAYER) && (status == STATUS_LIVING_DEATH)) {
			writeEquipmentRemove();
		}

		ClientBoundPacketData entitystatus = ClientBoundPacketData.create(PacketType.CLIENTBOUND_PLAY_ENTITY_STATUS);
		entitystatus.writeInt(entity.getId());
		entitystatus.writeByte(status);
		codec.write(entitystatus);
	}

	protected abstract void writeEquipmentRemove();

}
