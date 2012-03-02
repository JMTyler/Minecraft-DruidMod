// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode 

package net.minecraft.src;

import java.util.Random;

// Referenced classes of package net.minecraft.src:
//            BlockFluid, World, Material, WorldProvider, 
//            Block

public class BlockHealingWaterFlowing extends BlockFlowing
{

    protected BlockHealingWaterFlowing()
    {
        super(mod_Classes.getUniqueBlockId(), Material.water);
        
        this.setHardness(100F)
        	.setLightValue(0.5F)
        	.setLightOpacity(255)
        	.setBlockName("healingWater")
        	.disableStats()
        	.setRequiresSelfNotify();
    }
    
    @Override
    public void randomDisplayTick(World world, int i, int j, int k, Random random)
    {
    	super.updateTick(world, i, j, k, random);
    	
        for(int l = 0; l < 4; l++)
        {
            double d = (float)i + random.nextFloat();
            double d1 = (float)j + random.nextFloat();
            double d2 = (float)k + random.nextFloat();
            world.spawnParticle("portal", d, d1, d2, 0.0F, 0.5F, 0.0F);
        }

    }
}
