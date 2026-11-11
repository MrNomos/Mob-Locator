package com.armaninyow.moblocator;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import net.fabricmc.loader.api.FabricLoader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class MobLocatorConfig {
	private static final Logger LOGGER = LoggerFactory.getLogger("moblocator");
	private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
	private static final File CONFIG_FILE = new File(
		FabricLoader.getInstance().getConfigDir().toFile(),
		"moblocator.json"
	);

	public static boolean usePerMobColors = false;
	public static boolean useBlackOutlines = false;
	public static boolean showInvisibleMobs = false;
	public static int hostileMobColor = 0xFF0000;
	public static int passiveMobColor = 0xFFFFFF;
	public static int invisibleMobColor = 0x808080;
	public static List<String> blacklistedMobs = new ArrayList<>();

	public static Map<String, Integer> perMobColors = new LinkedHashMap<>();

	public static final Map<String, Integer> DEFAULT_PER_MOB_COLORS = new LinkedHashMap<>();

	static {
		DEFAULT_PER_MOB_COLORS.put("Allay",              0x28B3E8);
		DEFAULT_PER_MOB_COLORS.put("Armadillo",          0x824848);
		DEFAULT_PER_MOB_COLORS.put("Bat",                0x1B1715);
		DEFAULT_PER_MOB_COLORS.put("Polar Bear",         0xF6F6F6);
		DEFAULT_PER_MOB_COLORS.put("Bee",                0xEDC343);
		DEFAULT_PER_MOB_COLORS.put("Blaze",              0xFFF847);
		DEFAULT_PER_MOB_COLORS.put("Breeze",             0xAB9CCB);
		DEFAULT_PER_MOB_COLORS.put("Camel",              0xCB9337);
		DEFAULT_PER_MOB_COLORS.put("Camel Husk",         0x453A2B);
		DEFAULT_PER_MOB_COLORS.put("Cave Spider",        0x122620);
		DEFAULT_PER_MOB_COLORS.put("Copper Golem",       0xE3826C);
		DEFAULT_PER_MOB_COLORS.put("Creaking",           0x5E5350);
		DEFAULT_PER_MOB_COLORS.put("Creeper",            0x00A500);
		DEFAULT_PER_MOB_COLORS.put("Dolphin",            0xBFD0DB);
		DEFAULT_PER_MOB_COLORS.put("Ender Dragon",       0x1C1C1C);
		DEFAULT_PER_MOB_COLORS.put("Enderman",           0x161616);
		DEFAULT_PER_MOB_COLORS.put("Endermite",          0x3C2B4E);
		DEFAULT_PER_MOB_COLORS.put("Evoker",             0x959B9B);
		DEFAULT_PER_MOB_COLORS.put("Cod",                0xC2A885);
		DEFAULT_PER_MOB_COLORS.put("Pufferfish",         0xBF841B);
		DEFAULT_PER_MOB_COLORS.put("Salmon",             0xAC5056);
		DEFAULT_PER_MOB_COLORS.put("Tropical Fish",      0xEF6915);
		DEFAULT_PER_MOB_COLORS.put("Frog (Cold)",        0x496B1E);
		DEFAULT_PER_MOB_COLORS.put("Frog (Temperate)",   0xAB623D);
		DEFAULT_PER_MOB_COLORS.put("Frog (Warm)",        0xBCB9A2);
		DEFAULT_PER_MOB_COLORS.put("Fox (Red)",          0xCC6920);
		DEFAULT_PER_MOB_COLORS.put("Fox (Snow)",         0xEBF2E8);
		DEFAULT_PER_MOB_COLORS.put("Ghast",              0xF0F0F0);
		DEFAULT_PER_MOB_COLORS.put("Happy Ghast",        0xF0F0F0);
		DEFAULT_PER_MOB_COLORS.put("Goat",               0xFAFAFA);
		DEFAULT_PER_MOB_COLORS.put("Guardian",           0x5D7F75);
		DEFAULT_PER_MOB_COLORS.put("Elder Guardian",     0xA8A597);
		DEFAULT_PER_MOB_COLORS.put("Hoglin",             0xA56350);
		DEFAULT_PER_MOB_COLORS.put("Zoglin",             0xCF6263);
		DEFAULT_PER_MOB_COLORS.put("Donkey",             0x806E5E);
		DEFAULT_PER_MOB_COLORS.put("Mule",               0x442719);
		DEFAULT_PER_MOB_COLORS.put("Skeleton Horse",     0xD3D3D3);
		DEFAULT_PER_MOB_COLORS.put("Zombie Horse",       0x32452A);
		DEFAULT_PER_MOB_COLORS.put("Illusioner",         0x135893);
		DEFAULT_PER_MOB_COLORS.put("Pillager",           0x959B9B);
		DEFAULT_PER_MOB_COLORS.put("Ravager",            0x454040);
		DEFAULT_PER_MOB_COLORS.put("Vex",                0x6D849B);
		DEFAULT_PER_MOB_COLORS.put("Vindicator",         0x959B9B);
		DEFAULT_PER_MOB_COLORS.put("Iron Golem",         0xD0B096);
		DEFAULT_PER_MOB_COLORS.put("Nautilus",           0xED634C);
		DEFAULT_PER_MOB_COLORS.put("Zombie Nautilus",    0x24342B);
		DEFAULT_PER_MOB_COLORS.put("Ocelot",             0xFFDD74);
		DEFAULT_PER_MOB_COLORS.put("Piglin",             0xD5957A);
		DEFAULT_PER_MOB_COLORS.put("Piglin Brute",       0xD5957A);
		DEFAULT_PER_MOB_COLORS.put("Zombified Piglin",   0xE6918B);
		DEFAULT_PER_MOB_COLORS.put("Silverfish",         0x7F7F7F);
		DEFAULT_PER_MOB_COLORS.put("Bogged",             0x808170);
		DEFAULT_PER_MOB_COLORS.put("Parched",            0x746E66);
		DEFAULT_PER_MOB_COLORS.put("Skeleton",           0xBCBCBC);
		DEFAULT_PER_MOB_COLORS.put("Stray",              0x839594);
		DEFAULT_PER_MOB_COLORS.put("Wither Skeleton",    0x292929);
		DEFAULT_PER_MOB_COLORS.put("Magma Cube",         0x100000);
		DEFAULT_PER_MOB_COLORS.put("Slime",              0x73C262);
		DEFAULT_PER_MOB_COLORS.put("Sulfur Cube",        0xEEF5C1);
		DEFAULT_PER_MOB_COLORS.put("Sniffer",            0x500808);
		DEFAULT_PER_MOB_COLORS.put("Snow Golem",         0xFFFFFF);
		DEFAULT_PER_MOB_COLORS.put("Spider",             0x3D342D);
		DEFAULT_PER_MOB_COLORS.put("Glow Squid",         0x095656);
		DEFAULT_PER_MOB_COLORS.put("Squid",              0x122737);
		DEFAULT_PER_MOB_COLORS.put("Strider",            0x882D2F);
		DEFAULT_PER_MOB_COLORS.put("Turtle",             0x3FA442);
		DEFAULT_PER_MOB_COLORS.put("Villager",           0xBE886C);
		DEFAULT_PER_MOB_COLORS.put("Wandering Trader",   0x24395E);
		DEFAULT_PER_MOB_COLORS.put("Warden",             0x0D1217);
		DEFAULT_PER_MOB_COLORS.put("Witch",              0x361758);
		DEFAULT_PER_MOB_COLORS.put("Wither",             0x171717);
		DEFAULT_PER_MOB_COLORS.put("Drowned",            0x27514B);
		DEFAULT_PER_MOB_COLORS.put("Husk",               0x9E8B60);
		DEFAULT_PER_MOB_COLORS.put("Zombie",             0x3E692D);
		DEFAULT_PER_MOB_COLORS.put("Zombie Villager",    0x5F7F37);
		DEFAULT_PER_MOB_COLORS.put("Phantom",            0x374377);
		DEFAULT_PER_MOB_COLORS.put("Tadpole",            0x533923);

		DEFAULT_PER_MOB_COLORS.put("Axolotl (Blue)",     0xAFB2FF);
		DEFAULT_PER_MOB_COLORS.put("Axolotl (Cyan)",     0xE2EEFF);
		DEFAULT_PER_MOB_COLORS.put("Axolotl (Gold)",     0xFFD11B);
		DEFAULT_PER_MOB_COLORS.put("Axolotl (Lucy)",     0xFBC1E3);
		DEFAULT_PER_MOB_COLORS.put("Axolotl (Wild)",     0xA27A56);

		DEFAULT_PER_MOB_COLORS.put("Cat (All Black)",            0x161524);
		DEFAULT_PER_MOB_COLORS.put("Cat (Black)",                0x1C1827);
		DEFAULT_PER_MOB_COLORS.put("Cat (British Shorthair)",    0x999D9B);
		DEFAULT_PER_MOB_COLORS.put("Cat (Calico)",                0xDCDEDE);
		DEFAULT_PER_MOB_COLORS.put("Cat (Jellie)",                0x4B4A4A);
		DEFAULT_PER_MOB_COLORS.put("Cat (Persian)",               0xFFE0B2);
		DEFAULT_PER_MOB_COLORS.put("Cat (Ragdoll)",               0xDAD6D2);
		DEFAULT_PER_MOB_COLORS.put("Cat (Red)",                   0xEAA939);
		DEFAULT_PER_MOB_COLORS.put("Cat (Siamese)",               0xE9DCC7);
		DEFAULT_PER_MOB_COLORS.put("Cat (Tabby)",                 0x74563E);
		DEFAULT_PER_MOB_COLORS.put("Cat (White)",                 0xFDF9FB);

		DEFAULT_PER_MOB_COLORS.put("Chicken (Cold)",       0x81768A);
		DEFAULT_PER_MOB_COLORS.put("Chicken (Temperate)",  0xD3D3D3);
		DEFAULT_PER_MOB_COLORS.put("Chicken (Warm)",       0xF3AC60);

		DEFAULT_PER_MOB_COLORS.put("Cow (Cold)",          0xC77A48);
		DEFAULT_PER_MOB_COLORS.put("Cow (Temperate)",     0x443626);
		DEFAULT_PER_MOB_COLORS.put("Cow (Warm)",          0x7B311F);
		DEFAULT_PER_MOB_COLORS.put("Mooshroom (Brown)",   0x977251);
		DEFAULT_PER_MOB_COLORS.put("Mooshroom (Red)",     0xA81012);

		DEFAULT_PER_MOB_COLORS.put("Horse (Black)",       0x1F2128);
		DEFAULT_PER_MOB_COLORS.put("Horse (Brown)",       0x4E220B);
		DEFAULT_PER_MOB_COLORS.put("Horse (Chestnut)",    0x763410);
		DEFAULT_PER_MOB_COLORS.put("Horse (Creamy)",      0x835623);
		DEFAULT_PER_MOB_COLORS.put("Horse (Dark Brown)",  0x28150C);
		DEFAULT_PER_MOB_COLORS.put("Horse (Gray)",        0x424242);
		DEFAULT_PER_MOB_COLORS.put("Horse (White)",       0xCECECE);

		DEFAULT_PER_MOB_COLORS.put("Llama (Brown)",       0x774D1C);
		DEFAULT_PER_MOB_COLORS.put("Llama (Creamy)",      0xF5E2B8);
		DEFAULT_PER_MOB_COLORS.put("Llama (Gray)",        0xE6E6D5);
		DEFAULT_PER_MOB_COLORS.put("Llama (White)",       0xF4F4EB);
		DEFAULT_PER_MOB_COLORS.put("Trader Llama (Brown)",  0x774D1C);
		DEFAULT_PER_MOB_COLORS.put("Trader Llama (Creamy)", 0xF5E2B8);
		DEFAULT_PER_MOB_COLORS.put("Trader Llama (Gray)",   0xE6E6D5);
		DEFAULT_PER_MOB_COLORS.put("Trader Llama (White)",  0xF4F4EB);

		DEFAULT_PER_MOB_COLORS.put("Panda",               0xE7E7E7);

		DEFAULT_PER_MOB_COLORS.put("Parrot (Blue)",         0x0C20DC);
		DEFAULT_PER_MOB_COLORS.put("Parrot (Green)",        0x89CB00);
		DEFAULT_PER_MOB_COLORS.put("Parrot (Grey)",         0xB0B0B0);
		DEFAULT_PER_MOB_COLORS.put("Parrot (Red Blue)",     0xEF0101);
		DEFAULT_PER_MOB_COLORS.put("Parrot (Yellow Blue)",  0x18BDFF);

		DEFAULT_PER_MOB_COLORS.put("Pig (Cold)",       0xBF8C6D);
		DEFAULT_PER_MOB_COLORS.put("Pig (Temperate)",  0xE6918B);
		DEFAULT_PER_MOB_COLORS.put("Pig (Warm)",       0xCE7439);

		DEFAULT_PER_MOB_COLORS.put("Rabbit (Black)",            0x19171C);
		DEFAULT_PER_MOB_COLORS.put("Rabbit (Brown)",            0x966945);
		DEFAULT_PER_MOB_COLORS.put("Rabbit (Caerbannog)",       0xF1DCDC);
		DEFAULT_PER_MOB_COLORS.put("Rabbit (Gold)",             0xF7E3AD);
		DEFAULT_PER_MOB_COLORS.put("Rabbit (Salt)",             0x95836C);
		DEFAULT_PER_MOB_COLORS.put("Rabbit (Toast)",            0xE3DCDC);
		DEFAULT_PER_MOB_COLORS.put("Rabbit (White)",            0xE3DCDC);
		DEFAULT_PER_MOB_COLORS.put("Rabbit (White Splotched)",  0xE3DCDC);

		DEFAULT_PER_MOB_COLORS.put("Sheep (Black)",        0x1C1C20);
		DEFAULT_PER_MOB_COLORS.put("Sheep (Blue)",         0x3E4DB2);
		DEFAULT_PER_MOB_COLORS.put("Sheep (Brown)",        0x7E502F);
		DEFAULT_PER_MOB_COLORS.put("Sheep (Cyan)",         0x169698);
		DEFAULT_PER_MOB_COLORS.put("Sheep (Gray)",         0x474F52);
		DEFAULT_PER_MOB_COLORS.put("Sheep (Green)",        0x658619);
		DEFAULT_PER_MOB_COLORS.put("Sheep (Light Blue)",   0x4EC5E7);
		DEFAULT_PER_MOB_COLORS.put("Sheep (Light Gray)",   0x999993);
		DEFAULT_PER_MOB_COLORS.put("Sheep (Lime)",         0x86CC26);
		DEFAULT_PER_MOB_COLORS.put("Sheep (Magenta)",      0xD660D1);
		DEFAULT_PER_MOB_COLORS.put("Sheep (Orange)",       0xF9932B);
		DEFAULT_PER_MOB_COLORS.put("Sheep (Pink)",         0xF4B2C9);
		DEFAULT_PER_MOB_COLORS.put("Sheep (Purple)",       0x9743CD);
		DEFAULT_PER_MOB_COLORS.put("Sheep (Red)",          0xB8342C);
		DEFAULT_PER_MOB_COLORS.put("Sheep (White)",        0xFEFEFE);
		DEFAULT_PER_MOB_COLORS.put("Sheep (Yellow)",       0xFED93F);

		DEFAULT_PER_MOB_COLORS.put("Shulker",               0x976997);
		DEFAULT_PER_MOB_COLORS.put("Shulker (Black)",       0x1F1F23);
		DEFAULT_PER_MOB_COLORS.put("Shulker (Blue)",        0x33359B);
		DEFAULT_PER_MOB_COLORS.put("Shulker (Brown)",       0x4E2F17);
		DEFAULT_PER_MOB_COLORS.put("Shulker (Cyan)",        0x168792);
		DEFAULT_PER_MOB_COLORS.put("Shulker (Gray)",        0x3E4246);
		DEFAULT_PER_MOB_COLORS.put("Shulker (Green)",       0x546D1C);
		DEFAULT_PER_MOB_COLORS.put("Shulker (Light Blue)",  0x3BB4DB);
		DEFAULT_PER_MOB_COLORS.put("Shulker (Light Gray)",  0x8C8C83);
		DEFAULT_PER_MOB_COLORS.put("Shulker (Lime)",        0x71BC18);
		DEFAULT_PER_MOB_COLORS.put("Shulker (Magenta)",     0xBA3FAF);
		DEFAULT_PER_MOB_COLORS.put("Shulker (Orange)",      0xF57410);
		DEFAULT_PER_MOB_COLORS.put("Shulker (Pink)",        0xF38BAA);
		DEFAULT_PER_MOB_COLORS.put("Shulker (Purple)",      0x4D167B);
		DEFAULT_PER_MOB_COLORS.put("Shulker (Red)",         0x9C2522);
		DEFAULT_PER_MOB_COLORS.put("Shulker (White)",       0xE6EAEA);
		DEFAULT_PER_MOB_COLORS.put("Shulker (Yellow)",      0xFCC724);

		DEFAULT_PER_MOB_COLORS.put("Wolf (Ashen)",      0x948B93);
		DEFAULT_PER_MOB_COLORS.put("Wolf (Black)",      0x39363C);
		DEFAULT_PER_MOB_COLORS.put("Wolf (Chestnut)",   0xE1C0AE);
		DEFAULT_PER_MOB_COLORS.put("Wolf (Pale)",       0xD3CFCF);
		DEFAULT_PER_MOB_COLORS.put("Wolf (Rusty)",      0x6B4234);
		DEFAULT_PER_MOB_COLORS.put("Wolf (Snowy)",      0xD0D9DA);
		DEFAULT_PER_MOB_COLORS.put("Wolf (Spotted)",    0xB0672B);
		DEFAULT_PER_MOB_COLORS.put("Wolf (Striped)",    0xBFA779);
		DEFAULT_PER_MOB_COLORS.put("Wolf (Woods)",      0x493B2A);
	}

	public static void load() {
		perMobColors.clear();
		perMobColors.putAll(DEFAULT_PER_MOB_COLORS);

		if (CONFIG_FILE.exists()) {
			try (FileReader reader = new FileReader(CONFIG_FILE)) {
				ConfigData data = GSON.fromJson(reader, ConfigData.class);
				if (data != null) {
					usePerMobColors = data.usePerMobColors;
					useBlackOutlines = data.useBlackOutlines;
					showInvisibleMobs = data.showInvisibleMobs;
					hostileMobColor = data.hostileMobColor & 0xFFFFFF;
					passiveMobColor = data.passiveMobColor & 0xFFFFFF;
					invisibleMobColor = data.invisibleMobColor & 0xFFFFFF;
					if (data.blacklistedMobs != null) {
						blacklistedMobs = new ArrayList<>(data.blacklistedMobs);
					}
					if (data.perMobColors != null) {
						for (Map.Entry<String, Integer> entry : data.perMobColors.entrySet()) {
							perMobColors.put(entry.getKey(), entry.getValue() & 0xFFFFFF);
						}
					}
				}
			} catch (IOException e) {
				LOGGER.error("Failed to load config", e);
			}
		}
		save();
	}

	public static void save() {
		try (FileWriter writer = new FileWriter(CONFIG_FILE)) {
			ConfigData data = new ConfigData();
			data.usePerMobColors = usePerMobColors;
			data.useBlackOutlines = useBlackOutlines;
			data.showInvisibleMobs = showInvisibleMobs;
			data.hostileMobColor = hostileMobColor & 0xFFFFFF;
			data.passiveMobColor = passiveMobColor & 0xFFFFFF;
			data.invisibleMobColor = invisibleMobColor & 0xFFFFFF;
			data.blacklistedMobs = new ArrayList<>(blacklistedMobs);
			data.perMobColors = new LinkedHashMap<>();
			for (Map.Entry<String, Integer> entry : perMobColors.entrySet()) {
				data.perMobColors.put(entry.getKey(), entry.getValue() & 0xFFFFFF);
			}
			GSON.toJson(data, writer);
		} catch (IOException e) {
			LOGGER.error("Failed to save config", e);
		}
	}

	public static int getFillColor(String displayName, boolean isHostile) {
		if (usePerMobColors) {
			return perMobColors.getOrDefault(displayName,
				isHostile ? hostileMobColor : passiveMobColor);
		}
		if (isHostile) return hostileMobColor;
		return passiveMobColor;
	}

	public static int getOutlineColor(boolean isHostile, boolean isInvisible) {
		if (isInvisible && showInvisibleMobs) return invisibleMobColor;
		if (!usePerMobColors || useBlackOutlines) return 0x000000;
		if (isHostile) return hostileMobColor;
		return passiveMobColor;
	}

	private static class ConfigData {
		boolean usePerMobColors = false;
		boolean useBlackOutlines = false;
		boolean showInvisibleMobs = false;
		int hostileMobColor = 0xFF0000;
		int passiveMobColor = 0xFFFFFF;
		int invisibleMobColor = 0x808080;
		List<String> blacklistedMobs = new ArrayList<>();
		Map<String, Integer> perMobColors = new LinkedHashMap<>();
	}
}