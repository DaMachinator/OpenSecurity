package pcl.opensecurity;

import java.io.File;

import cpw.mods.fml.client.registry.ClientRegistry;
import cpw.mods.fml.client.registry.RenderingRegistry;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraftforge.client.MinecraftForgeClient;
import pcl.opensecurity.CommonProxy;
import pcl.opensecurity.client.renderer.BlockEnergyTurretTESR;
import pcl.opensecurity.client.renderer.RenderDisplayPanel;
import pcl.opensecurity.client.renderer.RenderEntityEnergyBolt;
import pcl.opensecurity.client.renderer.RendererItemEnergyTurret;
import pcl.opensecurity.client.renderer.RendererKeypadTESR;
import pcl.opensecurity.client.sounds.AlarmResource;
import pcl.opensecurity.containers.MagCardContainer;
import pcl.opensecurity.containers.CardWriterContainer;
import pcl.opensecurity.entity.EntityEnergyBolt;
import pcl.opensecurity.tileentity.TileEntityEnergyTurret;
import pcl.opensecurity.tileentity.TileEntityKeypadLock;
import pcl.opensecurity.tileentity.TileEntityMagReader;
import pcl.opensecurity.tileentity.TileEntityRFIDReader;
import pcl.opensecurity.tileentity.TileEntityCardWriter;

public class ClientProxy extends CommonProxy {

	@Override
	public Object getClientGuiElement(int ID, EntityPlayer player, World world,
			int x, int y, int z) {
		TileEntity te = world.getTileEntity(x, y, z);
		if (te != null && te instanceof TileEntityMagReader) {
			TileEntityMagReader icte = (TileEntityMagReader) te;
			return new MagCardContainer(player.inventory, icte);
		} else if (te != null && te instanceof TileEntityRFIDReader) {
			TileEntityCardWriter icte = (TileEntityCardWriter) te;
			return new CardWriterContainer(player.inventory, icte);
		} else {
			return null;
		}
	}

	public void registerRenderers()
	{
		TileEntitySpecialRenderer panelDisplayPanel = new RenderDisplayPanel();
		ClientRegistry.bindTileEntitySpecialRenderer(pcl.opensecurity.tileentity.TileEntityDisplayPanel.class, panelDisplayPanel);

	    BlockEnergyTurretTESR render = new BlockEnergyTurretTESR();
	    ClientRegistry.bindTileEntitySpecialRenderer(TileEntityEnergyTurret.class, render);
	    MinecraftForgeClient.registerItemRenderer(Item.getItemFromBlock(ContentRegistry.energyTurretBlock), new RendererItemEnergyTurret(render));
	    RenderingRegistry.registerEntityRenderingHandler(EntityEnergyBolt.class, new RenderEntityEnergyBolt());
		
	    RendererKeypadTESR terk = new RendererKeypadTESR();
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityKeypadLock.class, terk);
		MinecraftForgeClient.registerItemRenderer(new ItemStack(ContentRegistry.keypadLock).getItem(), terk);
	    
		OpenSecurity.logger.info("Registered TESRs");
	}
	
	public void listFilesForFolder(final File folder) {
		AlarmResource r = new AlarmResource();
		int i = 1;
	    for (final File fileEntry : folder.listFiles()) {
	        if (fileEntry.isDirectory()) {
	            listFilesForFolder(fileEntry);
	        } else {
	        	r.addSoundReferenceMapping(i, fileEntry.getName()); //add map soundlocation -> recordX
	        	i++;
	            System.out.println(OpenSecurity.alarmSounds + "\\" + fileEntry.getName());
	        }
	    }
	    r.registerAsResourceLocation(); //finalise IResourcePack
	}
	
	@Override
	public World getWorld(int dimId) {
		World world = Minecraft.getMinecraft().theWorld;
		if (world.provider.dimensionId == dimId) {
			return world;
		}
		return null;
	}
	
    @Override
    public void registerSounds () {
        
        //for (ItemExtraRecord record : ExtraRecords.records) {
        //    r.addSoundReferenceMapping(record.data.recordNum, record.data.sound.getResourcePath()); //add map soundlocation -> recordX
        //}
        listFilesForFolder(OpenSecurity.alarmSounds);
    }
}