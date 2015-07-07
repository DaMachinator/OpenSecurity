package pcl.opensecurity.tileentity;

import li.cil.oc.api.Network;
import li.cil.oc.api.network.ComponentConnector;
import li.cil.oc.api.network.Environment;
import li.cil.oc.api.network.Message;
import li.cil.oc.api.network.Node;
import li.cil.oc.api.network.Visibility;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import pcl.opensecurity.items.ItemMagCard;

/**
 * @author Caitlyn
 *
 */
public class TileEntityMagReader extends TileEntityMachineBase implements Environment {

	public String data;

	protected ComponentConnector node = Network.newNode(this, Visibility.Network).withComponent(getComponentName()).withConnector(32).create();

	@Override
	public Node node() {
		return node;
	}

	@Override
	public void onChunkUnload() {
		super.onChunkUnload();
		if (node != null)
			node.remove();
	}

	@Override
	public void invalidate() {
		super.invalidate();
		if (node != null)
			node.remove();
	}

	private String getComponentName() {
		return "os_magreader";
	}

	@Override
	public void onConnect(Node arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onDisconnect(final Node node) {

	}

	@Override
	public void onMessage(Message arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void readFromNBT(NBTTagCompound par1NBTTagCompound) {
		super.readFromNBT(par1NBTTagCompound);
		node.load(par1NBTTagCompound);
	}

	@Override
	public void writeToNBT(NBTTagCompound par1NBTTagCompound) {
		super.writeToNBT(par1NBTTagCompound);
		node.save(par1NBTTagCompound);
	}

	public boolean doRead(ItemStack itemStack, EntityPlayer em) {
		if (itemStack != null && itemStack.getItem() instanceof ItemMagCard && itemStack.stackTagCompound != null && itemStack.stackTagCompound.hasKey("data")) {
			data = itemStack.stackTagCompound.getString("data");
			String uuid = itemStack.stackTagCompound.getString("uuid");
			node.sendToReachable("computer.signal", "magData", em.getDisplayName(), data, uuid);
			return true;
		} else {
			return false;
		}
	}

	@Override
	public void updateEntity() {
		super.updateEntity();
		if (node != null && node.network() == null) {
			Network.joinOrCreateNetwork(this);
		}
	}
}