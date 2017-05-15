package pcl.opensecurity;

import java.util.HashSet;
import java.util.Set;
import java.util.function.Function;

import javax.annotation.Nullable;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemDoor;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.translation.I18n;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import pcl.opensecurity.common.blocks.BlockAlarm;
import pcl.opensecurity.common.blocks.BlockBiometricReader;
import pcl.opensecurity.common.blocks.BlockCardWriter;
import pcl.opensecurity.common.blocks.BlockData;
import pcl.opensecurity.common.blocks.BlockDoorController;
import pcl.opensecurity.common.blocks.BlockMagReader;
import pcl.opensecurity.common.blocks.BlockSecureDoor;
import pcl.opensecurity.common.blocks.BlockSecurePrivateDoor;
import pcl.opensecurity.common.items.ItemCard;
import pcl.opensecurity.common.items.ItemMagCard;
import pcl.opensecurity.common.items.ItemRFIDCard;
import pcl.opensecurity.common.items.ItemSecureDoor;
import pcl.opensecurity.common.items.ItemSecurePrivateDoor;
import pcl.opensecurity.common.tileentity.TileEntityAlarm;
import pcl.opensecurity.common.tileentity.TileEntityBiometricReader;
import pcl.opensecurity.common.tileentity.TileEntityCardWriter;
import pcl.opensecurity.common.tileentity.TileEntityDataBlock;
import pcl.opensecurity.common.tileentity.TileEntityDoorController;
import pcl.opensecurity.common.tileentity.TileEntityMagReader;
import pcl.opensecurity.common.tileentity.TileEntitySecureDoor;

public class ContentRegistry {
	public static CreativeTabs creativeTab;
	public static Block alarmBlock;
	public static Block biometricReaderBlock;
	public static Block dataBlock;
	public static Block cardWriter;
	public static Block magReader;
	public static Block doorController;
	public static Block secureDoor;
	public static Block privateSecureDoor;
	
	public static ItemCard itemRFIDCard;
	public static ItemCard itemMagCard;
	
	private ContentRegistry() {}
	
	public static final Set<Block> blocks = new HashSet<>();
	
	// Called on mod preInit()
	public static void preInit() {
        registerTabs();
        registerBlocks();
        registerItems();
	}

	//Called on mod init()
	public static void init() {
		registerRecipes();
	}
	
	@SuppressWarnings("deprecation")
	private static void registerItems() {
		itemRFIDCard = new ItemRFIDCard();
		GameRegistry.register( itemRFIDCard.setRegistryName( new ResourceLocation( OpenSecurity.MODID, "rfidcard" ) ) );
        itemRFIDCard.setCreativeTab(creativeTab);
        
        itemMagCard = new ItemMagCard();
		GameRegistry.register( itemMagCard.setRegistryName( new ResourceLocation( OpenSecurity.MODID, "magcard" ) ) );
		itemMagCard.setCreativeTab(creativeTab);
		
		//secureDoorItemBlock = new ItemSecureDoor(secureDoor);
		//GameRegistry.register( secureDoorItemBlock.setRegistryName( new ResourceLocation( OpenSecurity.MODID, "secure_door" ) ) );
		//secureDoorItemBlock.setCreativeTab(creativeTab);
	}
	
	private static void registerBlocks() {
		alarmBlock = new BlockAlarm(Material.IRON);
		registerBlock(alarmBlock);
		alarmBlock.setCreativeTab(creativeTab);
		GameRegistry.registerTileEntity(TileEntityAlarm.class, "alarm");
		
		biometricReaderBlock = new BlockBiometricReader(Material.IRON);
		registerBlock(biometricReaderBlock);
		biometricReaderBlock.setCreativeTab(creativeTab);
		GameRegistry.registerTileEntity(TileEntityBiometricReader.class, "biometric_reader");
		
		dataBlock = new BlockData(Material.IRON);
		registerBlock(dataBlock);
		dataBlock.setCreativeTab(creativeTab);
		GameRegistry.registerTileEntity(TileEntityDataBlock.class, "data_block");
		
		cardWriter = new BlockCardWriter(Material.IRON);
		registerBlock(cardWriter);
		cardWriter.setCreativeTab(creativeTab);
		GameRegistry.registerTileEntity(TileEntityCardWriter.class, "card_writer");
		
		magReader = new BlockMagReader(Material.IRON);
		registerBlock(magReader);
		magReader.setCreativeTab(creativeTab);
		GameRegistry.registerTileEntity(TileEntityMagReader.class, "mag_reader");
		
		doorController = new BlockDoorController(Material.IRON);
		registerBlock(doorController);
		doorController.setCreativeTab(creativeTab);
		GameRegistry.registerTileEntity(TileEntityDoorController.class, "door_controller");
		
		secureDoor = new BlockSecureDoor(Material.IRON);
		GameRegistry.registerBlock(secureDoor, ItemSecureDoor.class, "secure_door");
		secureDoor.setCreativeTab(creativeTab);
		privateSecureDoor = new BlockSecurePrivateDoor(Material.IRON);
		GameRegistry.registerBlock(privateSecureDoor, ItemSecurePrivateDoor.class, "secure_private_door");
		privateSecureDoor.setCreativeTab(creativeTab);
		
		GameRegistry.registerTileEntity(TileEntitySecureDoor.class, "secure_door");
	}
	
	private static void registerRecipes() {
		
	}
	
	/**
	 * Register a Block with the default ItemBlock class.
	 *
	 * @param block The Block instance
	 * @param <BLOCK>   The Block type
	 * @return The Block instance
	 */
	protected static <BLOCK extends Block> BLOCK registerBlock(BLOCK block) {
		return registerBlock(block, ItemBlock::new);
	}

	/**
	 * Register a Block with a custom ItemBlock class.
	 *
	 * @param <BLOCK>     The Block type
	 * @param block       The Block instance
	 * @param itemFactory A function that creates the ItemBlock instance, or null if no ItemBlock should be created
	 * @return The Block instance
	 */
	protected static <BLOCK extends Block> BLOCK registerBlock(BLOCK block, @Nullable Function<BLOCK, ItemBlock> itemFactory) {
		GameRegistry.register(block);

		if (itemFactory != null) {
			final ItemBlock itemBlock = itemFactory.apply(block);

			GameRegistry.register(itemBlock.setRegistryName(block.getRegistryName()));
		}

		blocks.add(block);
		return block;
	}
	
	public static void registerTabs() {

		creativeTab = new CreativeTabs("tabOpenSecurity") {
			@SideOnly(Side.CLIENT)
			public Item getTabIconItem() {
				return Item.getItemFromBlock(dataBlock);
			}

			@SideOnly(Side.CLIENT)
			public String getTranslatedTabLabel() {
				return I18n.translateToLocal("itemGroup.OpenSecurity.tabOpenSecurity");
			}
		};
	}
}
