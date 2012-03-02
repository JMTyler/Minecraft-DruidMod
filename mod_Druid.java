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
	
	public static Block blockHealingWaterMoving;
	public static Block blockHealingWaterStill;
	
	public static Item healingBark;
	public static Item magicLeaf;
	public static Item itemHealingWater;
	
	public static Item druidHelmet;
	public static Item druidBracers;
	public static Item druidPantaloons;
	public static Item druidBooties;
	
	public static Vector3D _leafGrowthOrigin = new Vector3D();
	public static int _leafGrowthState = 0;
	public static int _leafGrowthType = LEAF_GROWTH_NONE;
	
	protected boolean _hasWolfSpawned = false;
	protected EntityWolfFamiliar _familiar = null;
	
	
	static
	{
		if (mod_Classes.addArmorGroup(armorGroup)) {
			druidHelmet = mod_Classes.addArmor("druidHelmet", "Wolf-head Helmet", new ItemClassArmor(mod_Classes.getUniqueItemId(), armorGroup, EnumArmorType.Helmet, 0));
			druidBracers = mod_Classes.addArmor("druidBracers", "Bracers of Contempt", new ItemClassArmor(mod_Classes.getUniqueItemId(), armorGroup, EnumArmorType.Plate, 0));
			druidPantaloons = mod_Classes.addArmor("druidPantaloons", "Natural Pantaloons", new ItemClassArmor(mod_Classes.getUniqueItemId(), armorGroup, EnumArmorType.Legs, 0));
			druidBooties = mod_Classes.addArmor("druidBooties", "Le Bare Feet", new ItemClassArmor(mod_Classes.getUniqueItemId(), armorGroup, EnumArmorType.Boots, 0));
			
			if (druidPantaloons != null) {
				blockHealingWaterMoving = new BlockHealingWaterFlowing();
				blockHealingWaterStill = new BlockHealingWaterStationary();
				
				healingBark = mod_Classes.addWeapon("healingBark", "Healing Bark", new ItemHealingBark()).dependsOn((ItemClassArmor)druidPantaloons);
				magicLeaf = mod_Classes.addWeapon("magicLeaf", "Magical Leaf", new ItemMagicLeaf()).dependsOn((ItemClassArmor)druidPantaloons);
				itemHealingWater = new ItemHealingWater(); // TODO: should be able to integrate something like this into mod_Classes
			}
		}
	}
	
	public mod_Druid()
	{
		/* Setting up blocks */
		ModLoader.RegisterBlock(blockHealingWaterMoving);
		ModLoader.RegisterBlock(blockHealingWaterStill);
		
		
		/* Setting up item icons */
		healingBark.iconIndex = ModLoader.addOverride("/gui/items.png", "/gui/druidBark.png");
		magicLeaf.iconIndex = ModLoader.addOverride("/gui/items.png", "/gui/druidLeaf.png");
		itemHealingWater.iconIndex = ModLoader.addOverride("/gui/items.png", "/gui/healingWaterBucket.png");
		druidHelmet.iconIndex = ModLoader.addOverride("/gui/items.png", "/gui/druidHelmet.png");
		druidBracers.iconIndex = ModLoader.addOverride("/gui/items.png", "/gui/druidBracers.png");
		druidPantaloons.iconIndex = ModLoader.addOverride("/gui/items.png", "/gui/druidLeggings.png");
		druidBooties.iconIndex = ModLoader.addOverride("/gui/items.png", "/gui/druidBooties.png");
		
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
		
		
		/* Setting up recipes */
		ModLoader.AddRecipe(new ItemStack(itemHealingWater, 1), new Object[] {
			"X", "Y", Character.valueOf('X'), healingBark, Character.valueOf('Y'), Item.bucketWater
		});
		
		
		/* Setting up entities */
		ModLoader.RegisterEntityID(EntityWolfFamiliar.class, "WolfFamiliar", mod_Classes.getUniqueEntityId());
		
		
		ModLoader.SetInGameHook(this, true, true);
	}
	
	@Override
	public void AddRenderer(Map map)
	{
		map.put(EntityWolfFamiliar.class, new RenderWolf(new ModelWolf(), 0.5F));
	}
	
	@Override
	public boolean OnTickInGame(float f, Minecraft minecraft)
	{
		super.OnTickInGame(f, minecraft);
		
		EntityPlayer player = minecraft.thePlayer;
		
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
		
		boolean wearingHelmet = mod_Classes.checkDependency(minecraft.thePlayer, mod_Classes.getArmor("druidHelmet"));
		if (!_hasWolfSpawned && wearingHelmet) {
			boolean isSpawned = _spawnWolfFamiliar(minecraft.theWorld, player);
			_hasWolfSpawned = true;
		} else if (_hasWolfSpawned && !wearingHelmet) {
			if (_familiar != null) minecraft.theWorld.setEntityDead(_familiar);
			_hasWolfSpawned = false;
		}
		
		return true;
	}
	
	protected boolean _spawnWolfFamiliar(World world, EntityPlayer player)
	{
		EntityLiving entityliving = (EntityLiving)EntityList.createEntityInWorld("WolfFamiliar", world);
        if(entityliving == null)
        {
            return false;
        }
        double d6 = (double)player.posX + (world.rand.nextDouble() - world.rand.nextDouble()) * 4D;
        double d7 = (player.posY + world.rand.nextInt(3)) - 1;
        double d8 = (double)player.posZ + (world.rand.nextDouble() - world.rand.nextDouble()) * 4D;
        entityliving.setLocationAndAngles(d6, d7, d8, world.rand.nextFloat() * 360F, 0.0F);
//        if(!entityliving.getCanSpawnHere())
//        {
//            return false;
//        }
        world.entityJoinedWorld(entityliving);
        for(int k = 0; k < 20; k++)
        {
//            double d1 = (double)xCoord + 0.5D + ((double)worldObj.rand.nextFloat() - 0.5D) * 2D;
//            double d3 = (double)yCoord + 0.5D + ((double)worldObj.rand.nextFloat() - 0.5D) * 2D;
//            double d5 = (double)zCoord + 0.5D + ((double)worldObj.rand.nextFloat() - 0.5D) * 2D;
//            worldObj.spawnParticle("smoke", d1, d3, d5, 0.0D, 0.0D, 0.0D);
        }

        entityliving.spawnExplosionParticle();
        
        _familiar = (EntityWolfFamiliar)entityliving;
        _familiar.setOwner(player.username);
        
        return true;
	}
	
	@Override
	public String Version()
	{
		return null;
	}
}
