package thexfactor117.lsc.client.render.entities;

import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;
import thexfactor117.lsc.client.models.entities.ModelBanshee;
import thexfactor117.lsc.entities.monsters.EntityBanshee;
import thexfactor117.lsc.util.Reference;

/**
 * 
 * @author TheXFactor117
 *
 */
public class RenderBanshee extends RenderLiving<EntityBanshee>
{
	private static final ResourceLocation TEXTURE = new ResourceLocation(Reference.MODID + ":textures/models/banshee.png"); 
	
	public RenderBanshee(RenderManager manager) 
	{
		super(manager, new ModelBanshee(), 0.5F);
	}

	@Override
	protected ResourceLocation getEntityTexture(EntityBanshee entity) 
	{
		return TEXTURE;
	}
}