package com.acronym.tech.common.integration;

import com.acronym.tech.TECH;
import net.minecraft.client.Minecraft;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.MissingModsException;
import net.minecraftforge.fml.common.versioning.ArtifactVersion;

import java.util.*;

public class IntegrationsManager {
    private static IntegrationsManager INSTANCE = new IntegrationsManager();
    private final List<IIntegration> integrationMods = new ArrayList<IIntegration>();

    public static IntegrationsManager instance() {
        return INSTANCE;
    }

    public void index() {
        List<IntegrationIDs> integrationClasses = new ArrayList<IntegrationIDs>();
        for(IntegrationIDs id : IntegrationIDs.values()) {
            try {
                integrationClasses.add(id);
            } catch (Throwable ex) {
                ex.printStackTrace();
            }
        }

        // TODO: hmm... configuration stuff?

        for (IntegrationIDs entry : integrationClasses) {
            if (Loader.isModLoaded(entry.getModid())) {
                try {
                    integrationMods.add(entry.getIIntegration().newInstance());
                    TECH.LogHelper.info("Integration with " + entry.getModid() + ": Enabled");
                } catch (Throwable ex) {
                    TECH.LogHelper.error("Failed to load integration correctly");
                    ex.printStackTrace();
                }
            } else {
                if(entry.isRequired()) {
                    TECH.LogHelper.fatal("Integration with "+ entry.getModid() + ": Missing [REQUIRED]");
                    throw new MissingModsException(null,entry.getModid(),entry.name());
                } else {
                    TECH.LogHelper.info("Integration with " + entry.getModid() + ": Disabled");
                }
            }
        }
    }

    public void preInit() {
        for (IIntegration integration : integrationMods) {
            try {
                integration.preInit();
            } catch (Throwable ex) {
                TECH.LogHelper.error("(Pre Init) Unable to load integration from " + integration.getClass());
                ex.printStackTrace();
            }
        }
    }

    public void init() {
        for (IIntegration integration : integrationMods) {
            try {
                integration.init();
            } catch (Throwable ex) {
                TECH.LogHelper.error("(Init) Unable to load integration from " + integration.getClass());
                ex.printStackTrace();
            }
        }
    }

    public void postInit() {
        for (IIntegration integration : integrationMods) {
            try {
                integration.postInit();
            } catch (Throwable ex) {
                TECH.LogHelper.error("(Post Init) Unable to load integration from " + integration.getClass());
                ex.printStackTrace();
            }
        }
    }
}
