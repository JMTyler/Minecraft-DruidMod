// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode 

package net.minecraft.src;

import javax.swing.text.AbstractDocument.LeafElement;


// Referenced classes of package net.minecraft.src:
//            Item, EntityPig, ItemStack, EntityLiving

public class ItemMagicLeaf extends ItemClassWeapon
{
	public ItemMagicLeaf()
    {
        super(mod_Classes.getUniqueItemId());
        this.setMaxDamage(16);
    }
    
    @Override
    public boolean onSuccessfulUse(ItemStack itemstack, EntityPlayer entityplayer, World world, int i, int j, int k, int l)
    {
    	super.onSuccessfulUse(itemstack, entityplayer, world, i, j, k, l);
    	
    	itemstack.damageItem(1, entityplayer);
    	
    	mod_Druid._leafGrowthOrigin.x = i;
    	mod_Druid._leafGrowthOrigin.y = j;
    	mod_Druid._leafGrowthOrigin.z = k;
    	mod_Druid._leafGrowthType = mod_Druid.LEAF_GROWTH_TOWER;
    	mod_Druid._leafGrowthState = 1;
    	
		return true;
    }
    
//    @Override
//    public void onSuccessfulRightClick(ItemStack itemstack, World world, EntityPlayer entityplayer)
//    {
//    	super.onSuccessfulRightClick(itemstack, world, entityplayer);
//    	
//    	entityplayer.heal(10);
//    	itemstack.stackSize--;
//    }
    
    @Override
    public void onFailedRightClick(ItemStack itemstack, World world, EntityPlayer entityplayer)
    {
    	super.onFailedRightClick(itemstack, world, entityplayer);
    	
    	entityplayer.dropPlayerItemWithRandomChoice(new ItemStack(Block.leaves), false);
    	itemstack.stackSize = 0;
    }
}
