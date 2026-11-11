package com.armaninyow.moblocator;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.hud.HudElementRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.hud.VanillaHudElements;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.NeutralMob;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.boss.enderdragon.EnderDragon;
import net.minecraft.world.entity.monster.*;
import net.minecraft.world.entity.monster.cubemob.Slime;
import net.minecraft.world.entity.monster.cubemob.MagmaCube;
import net.minecraft.world.entity.monster.piglin.Piglin;
import net.minecraft.world.entity.monster.spider.CaveSpider;
import net.minecraft.world.entity.monster.spider.Spider;
import net.minecraft.world.entity.monster.warden.Warden;
import net.minecraft.world.entity.monster.zombie.Drowned;
import net.minecraft.world.entity.monster.zombie.ZombifiedPiglin;
import net.minecraft.world.entity.animal.bee.Bee;
import net.minecraft.world.entity.animal.axolotl.Axolotl;
import net.minecraft.world.entity.animal.chicken.Chicken;
import net.minecraft.world.entity.animal.cow.Cow;
import net.minecraft.world.entity.animal.cow.MushroomCow;
import net.minecraft.world.entity.animal.dolphin.Dolphin;
import net.minecraft.world.entity.animal.equine.Horse;
import net.minecraft.world.entity.animal.equine.Llama;
import net.minecraft.world.entity.animal.equine.TraderLlama;
import net.minecraft.world.entity.animal.feline.Cat;
import net.minecraft.world.entity.animal.fish.Pufferfish;
import net.minecraft.world.entity.animal.fox.Fox;
import net.minecraft.world.entity.animal.frog.Frog;
import net.minecraft.world.entity.animal.golem.IronGolem;
import net.minecraft.world.entity.animal.nautilus.AbstractNautilus;
import net.minecraft.world.entity.animal.panda.Panda;
import net.minecraft.world.entity.animal.parrot.Parrot;
import net.minecraft.world.entity.animal.pig.Pig;
import net.minecraft.world.entity.animal.polarbear.PolarBear;
import net.minecraft.world.entity.animal.rabbit.Rabbit;
import net.minecraft.world.entity.animal.sheep.Sheep;
import net.minecraft.world.entity.animal.wolf.Wolf;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.DyeColor;
import net.minecraft.core.Holder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

public class MobLocator implements ClientModInitializer {
	public static final String MOD_ID = "moblocator";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitializeClient() {
		LOGGER.info("MobLocator - Pixel-perfect icons with color configuration!");
		MobLocatorConfig.load();

		HudElementRegistry.attachElementAfter(
			VanillaHudElements.BOSS_BAR,
			Identifier.fromNamespaceAndPath(MOD_ID, "mob_indicators"),
			this::renderMobLocator
		);
	}

	public void renderMobLocator(GuiGraphicsExtractor context, DeltaTracker deltaTracker) {
		Minecraft client = Minecraft.getInstance();
		if (client.player == null || client.level == null) return;

		Player player = client.player;
		int screenWidth = context.guiWidth();
		int screenHeight = context.guiHeight();

		int xpBarWidth = 182;
		int centerX = screenWidth / 2;
		int xpBarLeft = centerX - xpBarWidth / 2;
		int baseY = screenHeight - 27;

		List<MobInfo> detectedMobs = new ArrayList<>();

		for (LivingEntity entity : client.level.getEntitiesOfClass(LivingEntity.class,
				player.getBoundingBox().inflate(64), e -> e != player && e.isAlive())) {

			if (isBlacklisted(entity)) continue;
			if (entity.isInvisible() && !MobLocatorConfig.showInvisibleMobs) continue;

			double distance = player.distanceTo(entity);
			float hearingRange = getHearingRange(entity);

			if (distance <= hearingRange) {
				boolean isHostile   = isHostileMob(entity) || isAngryNeutralMob(entity);
				boolean isInvisible = entity.isInvisible();
				int iconType = getIconType(distance, hearingRange);

				String displayName = resolveDisplayName(entity);

				int fillColor    = MobLocatorConfig.getFillColor(displayName, isHostile);
				int outlineColor = MobLocatorConfig.getOutlineColor(isHostile, isInvisible);

				double dy = entity.getY() - player.getY();
				boolean isAbove = dy > 3;
				boolean isBelow = dy < -3;

				detectedMobs.add(new MobInfo(entity, player, iconType, fillColor, outlineColor, isAbove, isBelow));
			}
		}

		for (MobInfo mobInfo : detectedMobs) {
			renderMobIndicator(context, mobInfo, xpBarLeft, xpBarWidth, baseY);
		}
	}

	private boolean isBlacklisted(LivingEntity entity) {
		String entityName = entity.getType().getDescription().getString();
		for (String blacklisted : MobLocatorConfig.blacklistedMobs) {
			if (entityName.equalsIgnoreCase(blacklisted.trim())) return true;
		}
		return false;
	}

	private String resolveDisplayName(LivingEntity entity) {
		String base = entity.getType().getDescription().getString();

		if (entity instanceof Axolotl axolotl)
			return base + " (" + capitalize(axolotl.getVariant().getSerializedName()) + ")";

		if (entity instanceof TraderLlama llama)
			return "Trader Llama (" + capitalize(llama.getVariant().getSerializedName()) + ")";

		if (entity instanceof Llama llama)
			return base + " (" + capitalize(llama.getVariant().getSerializedName()) + ")";

		if (entity instanceof Horse horse)
			return base + " (" + capitalize(horse.getVariant().getSerializedName()) + ")";

		if (entity instanceof Rabbit rabbit) {
			String name = rabbit.getVariant().getSerializedName();
			if (name.equals("evil")) name = "caerbannog";
			return base + " (" + capitalize(name) + ")";
		}

		if (entity instanceof Parrot parrot)
			return base + " (" + capitalize(parrot.getVariant().getSerializedName()) + ")";

		if (entity instanceof Fox fox)
			return base + " (" + capitalize(fox.getVariant().getSerializedName()) + ")";

		if (entity instanceof MushroomCow mooshroom)
			return base + " (" + capitalize(mooshroom.getVariant().getSerializedName()) + ")";

		if (entity instanceof Sheep sheep)
			return base + " (" + capitalize(sheep.getColor().getName()) + ")";

		if (entity instanceof Shulker shulker) {
			DyeColor color = shulker.getColor();
			return color == null ? base : base + " (" + capitalize(color.getName()) + ")";
		}

		if (entity instanceof Cat cat)
			return base + " (" + capitalize(variantPath(cat.get(net.minecraft.core.component.DataComponents.CAT_VARIANT))) + ")";

		if (entity instanceof Wolf wolf)
			return base + " (" + capitalize(variantPath(wolf.get(net.minecraft.core.component.DataComponents.WOLF_VARIANT))) + ")";

		if (entity instanceof Cow cow)
			return base + " (" + capitalize(variantPath(cow.get(net.minecraft.core.component.DataComponents.COW_VARIANT))) + ")";

		if (entity instanceof Chicken chicken)
			return base + " (" + capitalize(variantPath(chicken.get(net.minecraft.core.component.DataComponents.CHICKEN_VARIANT))) + ")";

		if (entity instanceof Pig pig)
			return base + " (" + capitalize(variantPath(pig.get(net.minecraft.core.component.DataComponents.PIG_VARIANT))) + ")";

		if (entity instanceof Frog frog)
			return base + " (" + capitalize(variantPath(frog.get(net.minecraft.core.component.DataComponents.FROG_VARIANT))) + ")";

		return base;
	}

	private String variantPath(Holder<?> variantHolder) {
		if (variantHolder == null) return "unknown";
		return variantHolder.unwrapKey()
			.map(key -> key.identifier().getPath())
			.orElse("unknown");
	}

	private String capitalize(String input) {
		String[] words = input.replace('_', ' ').split(" ");
		StringBuilder sb = new StringBuilder();
		for (String word : words) {
			if (word.isEmpty()) continue;
			if (sb.length() > 0) sb.append(' ');
			sb.append(Character.toUpperCase(word.charAt(0))).append(word.substring(1));
		}
		return sb.toString();
	}

	private void renderMobIndicator(GuiGraphicsExtractor context, MobInfo mobInfo,
	                                int xpBarLeft, int xpBarWidth, int baseY) {
		double clampedAngle = Math.max(-Math.PI / 2, Math.min(Math.PI / 2, mobInfo.angle));
		double normalizedPos = 0.5 - (clampedAngle / Math.PI);

		int x = xpBarLeft + (int)(normalizedPos * xpBarWidth);
		int y = baseY;

		if (mobInfo.isAbove) {
			IconRenderer.drawArrow(context, x, y - 6, true, mobInfo.fillColor, mobInfo.outlineColor);
		} else if (mobInfo.isBelow) {
			IconRenderer.drawArrow(context, x, y + 6, false, mobInfo.fillColor, mobInfo.outlineColor);
		}

		IconRenderer.drawIcon(context, x, y, mobInfo.iconType, mobInfo.fillColor, mobInfo.outlineColor);
	}

	private float getHearingRange(LivingEntity entity) {
		String entityName = entity.getClass().getSimpleName();
		if (entity instanceof EnderDragon)  return 192.0f;
		if (entity instanceof Phantom)      return 36.0f;
		if (entity instanceof Warden)       return 48.0f;
		if (entity instanceof Ghast)        return 64.0f;
		if (entity instanceof Blaze)        return 48.0f;
		if (entityName.contains("Wither"))  return 40.0f;
		if (entity instanceof Enderman)     return 64.0f;
		if (entity instanceof Drowned)      return 32.0f;
		if (entityName.equals("Allay"))     return 64f;
		if (entityName.equals("Bat"))       return 8.0f;
		return 16.0f;
	}

	private int getIconType(double distance, float hearingRange) {
		double ratio = distance / hearingRange;
		if (ratio <= 0.25) return 3;
		if (ratio <= 0.5)  return 2;
		if (ratio <= 0.75) return 1;
		return 0;
	}

	private boolean isHostileMob(LivingEntity entity) {
		if (entity instanceof Enderman)        return false;
		if (entity instanceof CaveSpider)      return false;
		if (entity instanceof Spider)          return false;
		if (entity instanceof ZombifiedPiglin) return false;
		if (entity instanceof Piglin)          return false;
		if (entity instanceof Drowned)         return false;
		return entity instanceof Monster   ||
			   entity instanceof Slime     ||
			   entity instanceof Phantom   ||
			   entity instanceof MagmaCube ||
			   entity instanceof Shulker;
	}

	private boolean isAngryNeutralMob(LivingEntity entity) {
		if (entity instanceof Enderman Enderman)
			return Enderman.isCreepy();

		if (entity instanceof Bee bee)
			return bee.getPersistentAngerEndTime() > bee.level().getGameTime();

		if (entity instanceof Wolf wolf)
			return wolf.isAngry();

		if (entity instanceof PolarBear bear)
			return bear.isStanding();

		if (entity instanceof Piglin piglin)
			return piglin.isAggressive();

		if (entity instanceof Pufferfish puffer)
			return puffer.getPuffState() > 0;

		if (entity instanceof AbstractNautilus naut)
			return ((com.armaninyow.moblocator.MobTargetAccessor) naut).moblocator$isNautilusAngry();

		if (entity instanceof Mob mob)
			return ((com.armaninyow.moblocator.MobTargetAccessor) mob).moblocator$hasTarget();

		return false;
	}

	private static class MobInfo {
		double angle;
		int iconType;
		int fillColor;
		int outlineColor;
		boolean isAbove;
		boolean isBelow;

		MobInfo(LivingEntity mob, Player player, int iconType,
		        int fillColor, int outlineColor, boolean isAbove, boolean isBelow) {
			this.iconType     = iconType;
			this.fillColor    = fillColor;
			this.outlineColor = outlineColor;
			this.isAbove      = isAbove;
			this.isBelow      = isBelow;

			double dx = mob.getX() - player.getX();
			double dz = mob.getZ() - player.getZ();
			double angleToMob = Math.atan2(dx, dz);
			double playerYawRad = Math.toRadians(-player.getYRot());

			this.angle = angleToMob - playerYawRad;
			while (this.angle > Math.PI)  this.angle -= 2 * Math.PI;
			while (this.angle < -Math.PI) this.angle += 2 * Math.PI;
		}
	}
}