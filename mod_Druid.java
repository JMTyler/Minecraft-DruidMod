package net.minecraft.src;

import java.util.Map;
import java.util.Random;

import net.minecraft.client.Minecraft;

import org.lwjgl.opengl.GL11;

import paulscode.sound.Vector3D;

public class mod_Druid extends BaseMod
{
	public static final int LEAF_GROWTH_NONE = 0;
	public static final int LEAF_GROWTH_TOWER = 1;
	public static final int LEAF_GROWTH_BRIDGE = 2;
	
	public static final String armorGroup = "druid";
	
	public static Item healingBark;
	public static Item magicLeaf;
	
	public static Item druidHelmet;
	public static Item druidBracers;
	public static Item druidPantaloons;
	public static Item druidBooties;
	
	public static Vector3D _leafGrowthOrigin = new Vector3D();
	public static int _leafGrowthState = 0;
	public static int _leafGrowthType = LEAF_GROWTH_NONE;
	
	
	static
	{
		if (mod_Classes.addArmorGroup(armorGroup)) {
			druidHelmet = mod_Classes.addArmor("druidHelmet", "Wolf-head Helmet", new ItemClassArmor(mod_Classes.getUniqueItemId(), armorGroup, EnumArmorType.Helmet, 0));
			druidBracers = mod_Classes.addArmor("druidBracers", "Bracers of Contempt", new ItemClassArmor(mod_Classes.getUniqueItemId(), armorGroup, EnumArmorType.Plate, 0));
			druidPantaloons = mod_Classes.addArmor("druidPantaloons", "Natural Pantaloons", new ItemClassArmor(mod_Classes.getUniqueItemId(), armorGroup, EnumArmorType.Legs, 0));
			druidBooties = mod_Classes.addArmor("druidBooties", "Le Bare Feet", new ItemClassArmor(mod_Classes.getUniqueItemId(), armorGroup, EnumArmorType.Boots, 0));
			
			if (druidHelmet != null) {
				healingBark = mod_Classes.addWeapon("healingBark", "Healing Bark", new ItemHealingBark()).dependsOn((ItemClassArmor)druidPantaloons);
				magicLeaf = mod_Classes.addWeapon("magicLeaf", "Magical Leaf", new ItemMagicLeaf()).dependsOn((ItemClassArmor)druidPantaloons);
			}
		}
	}
	
	public mod_Druid()
	{
		/* Setting up item icons */
		healingBark.iconIndex = ModLoader.addOverride("/gui/items.png", "/gui/druidBark.png");
		magicLeaf.iconIndex = ModLoader.addOverride("/gui/items.png", "/gui/druidLeaf.png");
		druidHelmet.iconIndex = ModLoader.addOverride("/gui/items.png", "/gui/druidHelmet.png");
		druidPantaloons.iconIndex = ModLoader.addOverride("/gui/items.png", "/gui/druidLeggings.png");
		
		Item testItem = new Item(mod_Classes.getUniqueItemId()) {
			@Override
		    public ItemStack onItemRightClick(ItemStack itemstack, World world, EntityPlayer entityplayer)
		    {
				// removes 5 hunger
				entityplayer.func_35191_at().func_35771_a(-5, -0.6F);
				return itemstack;
		    }
		};
		testItem.setItemName("test");
		ModLoader.AddName(testItem, "test");
		
		
		ModLoader.SetInGameHook(this, true, true);
	}
	
	@Override
	public boolean OnTickInGame(float f, Minecraft minecraft)
	{
		super.OnTickInGame(f, minecraft);
		
		if (_leafGrowthState > 0) {
			World world = minecraft.theWorld;
			int x = (int)_leafGrowthOrigin.x;
			int y = (int)_leafGrowthOrigin.y;
			int z = (int)_leafGrowthOrigin.z;
			
			switch(_leafGrowthType) {
			case LEAF_GROWTH_TOWER:
				if (_leafGrowthState <= 5) {
					world.setBlockWithNotify(x, y + _leafGrowthState, z, Block.leaves.blockID);
					// TODO: play this sound effect at each block, not at origin
					world.playSoundEffect((float)x + 0.5F, (float)y + 1.5F, (float)z + 0.5F, Block.leaves.stepSound.stepSoundDir2(), (Block.leaves.stepSound.getVolume() + 1.0F) / 2.0F, Block.leaves.stepSound.getPitch() * 0.8F);
					_leafGrowthState++;
				} else {
					_leafGrowthState = 0;
				}
				break;
			}
		}
		
		return true;
	}
	
	@Override
	public String Version()
	{
		return null;
	}
}
