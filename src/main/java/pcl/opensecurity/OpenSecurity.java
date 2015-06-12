package pcl.opensecurity;

import java.util.List;

import pcl.opensecurity.BuildInfo;
import pcl.opensecurity.blocks.BlockAlarm;
import pcl.opensecurity.blocks.BlockMagReader;
import pcl.opensecurity.blocks.BlockRFIDReader;
import pcl.opensecurity.gui.SecurityGUIHandler;
import pcl.opensecurity.items.ItemMagCard;
import pcl.opensecurity.items.ItemRFIDCard;
import pcl.opensecurity.tileentity.TileEntityAlarm;
import pcl.opensecurity.tileentity.TileEntityMagReader;
import pcl.opensecurity.tileentity.TileEntityRFIDReader;
import net.minecraftforge.common.config.Configuration;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.registry.GameRegistry;

@Mod(modid=OpenSecurity.MODID, name="OpenSecurity", version=BuildInfo.versionNumber + "." + BuildInfo.buildNumber, dependencies = "after:OpenComputers")

public class OpenSecurity {
	
	public static final String MODID = "opensecurity";
	
		public static Block magCardReader;
		public static Block rfidCardReader;
		public static Block Alarm;
		public static Item  magCard;
		public static Item  rfidCard;
		public static ItemBlock  securityitemBlock;
		
        @Instance(value = MODID)
        public static OpenSecurity instance;
        
        @SidedProxy(clientSide="pcl.opensecurity.ClientProxy", serverSide="pcl.opensecurity.CommonProxy")
        public static CommonProxy proxy;
        public static Config cfg = null;
        public static boolean render3D = true;

        public static org.apache.logging.log4j.Logger logger;

		public static List<String> alarmList;
        
        @EventHandler
        public void preInit(FMLPreInitializationEvent event) {      	
        	cfg = new Config(new Configuration(event.getSuggestedConfigurationFile()));
        	render3D = cfg.render3D;
        	alarmList = cfg.alarmsConfigList;
        	/*
            if((event.getSourceFile().getName().endsWith(".jar") || debug) && event.getSide().isClient() && cfg.enableMUD){
                try {
                    Class.forName("pcl.openprinter.mud.ModUpdateDetector")
                    		.getDeclaredMethod("registerMod", 
                    		ModContainer.class, URL.class, URL.class).invoke(null,
                            FMLCommonHandler.instance().findContainerFor(this),
                            new URL("http://PC-Logix.com/OpenSecurity/get_latest_build.php"),
                            new URL("http://PC-Logix.com/OpenSecurity/changelog.txt")
                    );
                } catch (Throwable e) {
                    e.printStackTrace();
                }
            }
            */
            logger = event.getModLog();
        	
        	
            NetworkRegistry.INSTANCE.registerGuiHandler(this, new SecurityGUIHandler());
        	GameRegistry.registerTileEntity(TileEntityMagReader.class, "MagCardTE");
        	GameRegistry.registerTileEntity(TileEntityRFIDReader.class, "RFIDTE");
        	GameRegistry.registerTileEntity(TileEntityAlarm.class, "AlarmTE");
        	
        	//Register Blocks
        	magCardReader = new BlockMagReader();
        	GameRegistry.registerBlock(magCardReader, "magreader");
        	magCardReader.setCreativeTab(li.cil.oc.api.CreativeTab.instance);
        	
        	rfidCardReader = new BlockRFIDReader();
        	GameRegistry.registerBlock(rfidCardReader, "rfidreader");
        	rfidCardReader.setCreativeTab(li.cil.oc.api.CreativeTab.instance);

        	Alarm = new BlockAlarm();
        	GameRegistry.registerBlock(Alarm, "alarm");
        	Alarm.setCreativeTab(li.cil.oc.api.CreativeTab.instance);
        	
        	
        	
        	//Register Items
        	magCard = new ItemMagCard();
    		GameRegistry.registerItem(magCard, "opensecurity.magCard");
    		magCard.setUnlocalizedName("magCard");
    		magCard.setTextureName("minecraft:paper");
    		magCard.setCreativeTab(li.cil.oc.api.CreativeTab.instance);
    		
        	rfidCard = new ItemRFIDCard();
    		GameRegistry.registerItem(rfidCard, "opensecurity.rfidCard");
    		rfidCard.setUnlocalizedName("rfidCard");
    		rfidCard.setTextureName("opensecurity:rfidCard");
    		rfidCard.setCreativeTab(li.cil.oc.api.CreativeTab.instance);
        	
        }
        
        @EventHandler
    	public void load(FMLInitializationEvent event)
    	{
        	
    		proxy.registerRenderers();
    	}
}