package thexfactor117.lsc.client.render.entities;

import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;
import thexfactor117.lsc.client.models.entities.ModelGolem;
import thexfactor117.lsc.entities.monsters.EntityGolem;
import thexfactor117.lsc.util.Reference;

/**
 * 
 * @author TheXFactor117
 *
 */
public class RenderGolem extends RenderLiving<EntityGolem>
{
	private static final ResourceLocation TEXTURE = new ResourceLocation(Reference.MODID + ":textures/models/golem.png"); 
	
	public RenderGolem(RenderManager manager) 
	{
		super(manager, new ModelGolem(), 0.5F);
	}

	@Override
	protected ResourceLocation getEntityTexture(EntityGolem entity) 
	{
		return TEXTURE;
	}
}