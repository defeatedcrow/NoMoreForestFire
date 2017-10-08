package defeatedcrow.wildfire;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import net.minecraft.util.math.BlockPos;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.terraingen.DecorateBiomeEvent;
import net.minecraftforge.event.terraingen.PopulateChunkEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLLoadCompleteEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.Event.Result;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

// @SortingIndex(1102)
@Mod(modid = WildFireCore.MOD_ID, name = WildFireCore.MOD_NAME, version = WildFireCore.MOD_MEJOR + "."
		+ WildFireCore.MOD_MINOR + "."
		+ WildFireCore.MOD_BUILD, dependencies = WildFireCore.MOD_DEPENDENCIES, acceptedMinecraftVersions = WildFireCore.MOD_ACCEPTED_MC_VERSIONS, useMetadata = true)
public class WildFireCore {
	public static final String MOD_ID = "dcs_wildfire";
	public static final String MOD_NAME = "NoMoreForestfire";
	public static final int MOD_MEJOR = 1;
	public static final int MOD_MINOR = 0;
	public static final int MOD_BUILD = 0;
	public static final String MOD_DEPENDENCIES = "";
	public static final String MOD_ACCEPTED_MC_VERSIONS = "[1.10,1.12.2]";
	public static final String PACKAGE_BASE = "dcs";
	public static final String PACKAGE_ID = "dcs_wildfire";

	@Instance("dcs_wildfire")
	public static WildFireCore instance;

	public static final Logger LOGGER = LogManager.getLogger(PACKAGE_ID);

	public static boolean isDebug = false;

	@EventHandler
	public void preInit(FMLPreInitializationEvent event) {}

	@EventHandler
	public void init(FMLInitializationEvent event) {
		MinecraftForge.TERRAIN_GEN_BUS.register(instance);
	}

	@EventHandler
	public void postInit(FMLPostInitializationEvent event) {}

	@EventHandler
	public void loadComplete(FMLLoadCompleteEvent event) {}

	@SubscribeEvent
	public void initLakeGen(PopulateChunkEvent.Populate event) {
		if (event.getType() == net.minecraftforge.event.terraingen.PopulateChunkEvent.Populate.EventType.LAVA
				&& event.getResult() == Result.DEFAULT) {
			BlockPos pos = new BlockPos(event.getChunkX() * 16 + 8, 64, event.getChunkZ() * 16 + 8);
			Biome biome = event.getWorld().getBiomeForCoordsBody(pos);
			if (BiomeDictionary.hasType(biome, BiomeDictionary.Type.FOREST)
					|| BiomeDictionary.hasType(biome, BiomeDictionary.Type.DENSE)) {
				event.setResult(Result.DENY);
			}
		}
	}

	@SubscribeEvent
	public void initFluid(DecorateBiomeEvent.Decorate event) {
		if (event.getType() == net.minecraftforge.event.terraingen.DecorateBiomeEvent.Decorate.EventType.LAKE_LAVA
				&& event.getResult() == Result.DEFAULT) {
			Biome biome = event.getWorld().getBiomeForCoordsBody(event.getPos());
			if (BiomeDictionary.hasType(biome, BiomeDictionary.Type.FOREST)
					|| BiomeDictionary.hasType(biome, BiomeDictionary.Type.DENSE)) {
				event.setResult(Result.DENY);
			}
		}
	}
}
