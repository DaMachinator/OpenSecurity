package pcl.opensecurity;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import pcl.opensecurity.CommonProxy;
import pcl.opensecurity.containers.MagCardContainer;
import pcl.opensecurity.containers.RFIDWriterContainer;
import pcl.opensecurity.tileentity.TileEntityMagReader;
import pcl.opensecurity.tileentity.TileEntityRFIDReader;
import pcl.opensecurity.tileentity.TileEntityRFIDWriter;

public class ClientProxy extends CommonProxy {

	public void registerRenderers() {
		if (OpenSecurity.render3D) {

		}
	}

	@Override
	public Object getClientGuiElement(int ID, EntityPlayer player, World world,
			int x, int y, int z) {
		TileEntity te = world.getTileEntity(x, y, z);
		if (te != null && te instanceof TileEntityMagReader) {
			TileEntityMagReader icte = (TileEntityMagReader) te;
			return new MagCardContainer(player.inventory, icte);
		} else if (te != null && te instanceof TileEntityRFIDReader) {
			TileEntityRFIDWriter icte = (TileEntityRFIDWriter) te;
			return new RFIDWriterContainer(player.inventory, icte);
		} else {
			return null;
		}
	}

}