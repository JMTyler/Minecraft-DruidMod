// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode 

package net.minecraft.src;


// Referenced classes of package net.minecraft.src:
//            Item, EntityPig, ItemStack, EntityLiving

public class ItemHealingWater extends ItemBucket
{
	public ItemHealingWater()
    {
        super(mod_Classes.getUniqueItemId(), mod_Druid.blockHealingWaterMoving.blockID);
        this.setItemName("bucketHealingWater");
        this.setContainerItem(Item.bucketEmpty);
        ModLoader.AddName(this, "Healing Water Bucket");
    }
}
