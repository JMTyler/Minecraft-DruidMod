// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode 

package net.minecraft.src;

import java.util.*;

// Referenced classes of package net.minecraft.src:
//            EntityAnimal, DataWatcher, NBTTagCompound, World, 
//            EntityPlayer, EntitySheep, AxisAlignedBB, Entity, 
//            InventoryPlayer, ItemStack, Item, ItemFood, 
//            MathHelper, DamageSource, EntityArrow, EntityLiving

public class EntityWolfFamiliar extends EntityWolf
{

    public EntityWolfFamiliar(World world)
    {
        super(world);
        this.setIsTamed(true);
    }
    
    public String getEntityTexture()
    {
        if(isWolfTamed())
        {
            return "/mob/familiar.png";
        }
        if(isWolfAngry())
        {
            return "/mob/familiar_angry.png";
        } else
        {
            return super.getEntityTexture();
        }
    }
}
