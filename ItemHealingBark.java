// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode 

package net.minecraft.src;


// Referenced classes of package net.minecraft.src:
//            Item, EntityPig, ItemStack, EntityLiving

public class ItemHealingBark extends ItemClassWeapon
{
	public ItemHealingBark()
    {
        super(mod_Classes.getUniqueItemId(), 64);
    }
    
    @Override
    public boolean onSuccessfulUse(ItemStack itemstack, EntityPlayer entityplayer, World world, int i, int j, int k, int l)
    {
    	super.onSuccessfulUse(itemstack, entityplayer, world, i, j, k, l);
    	
    	entityplayer.heal(10);
    	itemstack.stackSize--;
    	
		return true;
    }
    
    @Override
    public void onSuccessfulRightClick(ItemStack itemstack, World world, EntityPlayer entityplayer)
    {
    	super.onSuccessfulRightClick(itemstack, world, entityplayer);
    	
    	entityplayer.heal(10);
    	itemstack.stackSize--;
    }
    
    @Override
    public void onFailedRightClick(ItemStack itemstack, World world, EntityPlayer entityplayer)
    {
    	super.onFailedRightClick(itemstack, world, entityplayer);
    	
    	itemstack.stackSize--;
    }
}
