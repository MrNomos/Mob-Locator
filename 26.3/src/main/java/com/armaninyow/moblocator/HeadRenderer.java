package com.armaninyow.moblocator;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.model.Model;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.renderer.entity.state.EntityRenderState;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.LivingEntity;
import org.joml.Vector3fc;

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Draws a mob's head (front face of the "head" model cube, read from the live entity model
 * and texture) as the locator icon. Everything touching private model fields is reflective,
 * so a failure just falls back to the old coloured icon.
 */
public class HeadRenderer {
	// Virtual texture size: UVs in the model are normalised, so any big size gives exact sampling.
	private static final int T = 8192;
	private static final int[] SIZES = {3, 4, 6, 8}; // indexed by iconType

	private record Head(Identifier texture, float u0, float v0, float u1, float v1) {}

	// shortcut: per-entity-type cache ignores variant/baby textures, upgrade by keying on texture too
	private static final Map<Object, float[]> UV_CACHE = new HashMap<>();
	private static final float[] NONE = new float[0];

	/** @return true if the head was drawn, false to let the caller draw the old icon. */
	public static boolean draw(GuiGraphicsExtractor ctx, LivingEntity entity, int cx, int cy,
	                           int iconType, int outlineColor) {
		Head head = resolve(entity);
		if (head == null) return false;

		int s = SIZES[Math.max(0, Math.min(3, iconType))];
		int x = cx - s / 2;
		int y = cy - s / 2;

		ctx.fill(x - 1, y - 1, x + s + 1, y + s + 1, outlineColor | 0xFF000000);
		int rw = Math.max(1, Math.round((head.u1 - head.u0) * T));
		int rh = Math.max(1, Math.round((head.v1 - head.v0) * T));
		ctx.blit(RenderPipelines.GUI_TEXTURED, head.texture, x, y, head.u0 * T, head.v0 * T, s, s, rw, rh, T, T);
		return true;
	}

	@SuppressWarnings({"unchecked", "rawtypes"})
	private static Head resolve(LivingEntity entity) {
		try {
			Minecraft mc = Minecraft.getInstance();
			EntityRenderer renderer = mc.getEntityRenderDispatcher().getRenderer(entity);
			if (!(renderer instanceof LivingEntityRenderer living)) return null;

			float[] uv = UV_CACHE.get(entity.getType());
			if (uv == null) {
				uv = findHeadUv(((Model) field(LivingEntityRenderer.class, "model").get(living)).root());
				UV_CACHE.put(entity.getType(), uv == null ? NONE : uv);
			}
			if (uv == null || uv.length == 0) return null;

			EntityRenderState state = mc.getEntityRenderDispatcher().extractEntity(entity, 1.0F);
			Identifier tex = living.getTextureLocation(state);
			return new Head(tex, uv[0], uv[1], uv[2], uv[3]);
		} catch (Throwable t) {
			UV_CACHE.putIfAbsent(entity.getType(), NONE);
			MobLocator.LOGGER.warn("Head icon failed for {}: {}", entity.getType(), t.toString());
			return null;
		}
	}

	/** Finds the "head" part (else any part with "head" in its name, else the first part with a cube). */
	@SuppressWarnings("unchecked")
	private static float[] findHeadUv(ModelPart root) throws Exception {
		ModelPart part = find(root, true);
		if (part == null) part = find(root, false);
		if (part == null) return null;

		for (Object cube : (List<Object>) field(ModelPart.class, "cubes").get(part)) {
			Object polys = field(cube.getClass(), "polygons").get(cube);
			for (int i = 0; i < Array.getLength(polys); i++) {
				Object poly = Array.get(polys, i);
				Vector3fc n = (Vector3fc) field(poly.getClass(), "normal").get(poly);
				if (n.z() > -0.5f) continue; // front face looks toward -Z in model space

				float u0 = 2, v0 = 2, u1 = -1, v1 = -1;
				Object verts = field(poly.getClass(), "vertices").get(poly);
				for (int j = 0; j < Array.getLength(verts); j++) {
					Object v = Array.get(verts, j);
					float u = field(v.getClass(), "u").getFloat(v);
					float w = field(v.getClass(), "v").getFloat(v);
					u0 = Math.min(u0, u); u1 = Math.max(u1, u);
					v0 = Math.min(v0, w); v1 = Math.max(v1, w);
				}
				return new float[] {u0, v0, u1, v1};
			}
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	private static ModelPart find(ModelPart part, boolean exact) throws Exception {
		Map<String, ModelPart> children = (Map<String, ModelPart>) field(ModelPart.class, "children").get(part);
		for (Map.Entry<String, ModelPart> e : children.entrySet()) {
			String name = e.getKey().toLowerCase();
			if (exact ? name.equals("head") : name.contains("head")) return e.getValue();
		}
		for (ModelPart child : children.values()) {
			ModelPart r = find(child, exact);
			if (r != null) return r;
		}
		// last resort (non-exact pass only): the first part that has a cube at all
		if (!exact && !((List<?>) field(ModelPart.class, "cubes").get(part)).isEmpty()) return part;
		return null;
	}

	private static Field field(Class<?> c, String name) throws NoSuchFieldException {
		for (Class<?> k = c; k != null; k = k.getSuperclass()) {
			try {
				Field f = k.getDeclaredField(name);
				f.setAccessible(true);
				return f;
			} catch (NoSuchFieldException ignored) {}
		}
		throw new NoSuchFieldException(name);
	}
}
