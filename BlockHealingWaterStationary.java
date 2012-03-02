// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode 

package net.minecraft.src;

import java.util.Random;

// Referenced classes of package net.minecraft.src:
//            BlockFluid, Material, World, Block, 
//            BlockFire

public class BlockHealingWaterStationary extends BlockStationary
{

    protected BlockHealingWaterStationary()
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
    	super.randomDisplayTick(world, i, j, k, random);
    	
        for(int l = 0; l < 4; l++)
        {
            double d = (float)i + random.nextFloat();
            double d1 = (float)j + random.nextFloat();
            double d2 = (float)k + random.nextFloat();
//            double d3 = 0.0D;
//            double d4 = 0.0D;
//            double d5 = 0.0D;
//            int i1 = random.nextInt(2) * 2 - 1;
//            d3 = ((double)random.nextFloat() - 0.5D) * 0.5D;
//            d4 = ((double)random.nextFloat() - 0.5D) * 0.5D;
//            d5 = ((double)random.nextFloat() - 0.5D) * 0.5D;
//            if(world.getBlockId(i - 1, j, k) == blockID || world.getBlockId(i + 1, j, k) == blockID)
//            {
//                d2 = (double)k + 0.5D + 0.25D * (double)i1;
//                d5 = random.nextFloat() * 2.0F * (float)i1;
//            } else
//            {
//                d = (double)i + 0.5D + 0.25D * (double)i1;
//                d3 = random.nextFloat() * 2.0F * (float)i1;
//            }
            world.spawnParticle("portal", d, d1, d2, d, d1, d2);
        }

    }
}
