package com.acronym.tech;

import com.acronym.base.util.*;
import com.acronym.tech.common.integration.IntegrationsManager;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.*;
import net.minecraftforge.fml.common.event.*;

@Mod(modid = ModInfo.MODID,version = ModInfo.VERSION_BUILD,name = ModInfo.NAME)
public class TECH {
	public static LanguageHelper LanguageHelper = new LanguageHelper(ModInfo.MODID);
	public static LogHelper LogHelper = new LogHelper(ModInfo.MODID);

	@Instance
	public static TECH instance;


	@EventHandler
	public static void preInit(FMLPreInitializationEvent event) {
		IntegrationsManager.instance().index();
		IntegrationsManager.instance().preInit();
	}
	@EventHandler
	public static void init(FMLInitializationEvent event) {
		IntegrationsManager.instance().init();

	}
	@EventHandler
	public static void postInit(FMLPostInitializationEvent event) {
		IntegrationsManager.instance().postInit();

	}
}
