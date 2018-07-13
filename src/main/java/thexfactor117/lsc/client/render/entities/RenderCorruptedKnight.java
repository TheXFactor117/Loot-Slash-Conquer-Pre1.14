package thexfactor117.lsc.client.render.entities;

import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;
import thexfactor117.lsc.client.models.entities.ModelCorruptedKnight;
import thexfactor117.lsc.entities.bosses.EntityCorruptedKnight;
import thexfactor117.lsc.util.Reference;

/**
 *
 * @author TheXFactor117
 *
 */
public class RenderCorruptedKnight extends RenderLiving<EntityCorruptedKnight>
{
	private static final ResourceLocation TEXTURE = new ResourceLocation(Reference.MODID + ":textures/models/corrupted_knight.png");
	
	public RenderCorruptedKnight(RenderManager manager) 
	{
		super(manager, new ModelCorruptedKnight(), 0.5F);
	}

	@Override
	protected ResourceLocation getEntityTexture(EntityCorruptedKnight entity) 
	{
		return TEXTURE;
	}
}
