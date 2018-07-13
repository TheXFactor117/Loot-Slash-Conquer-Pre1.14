package thexfactor117.lsc.client.render.entities;

import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;
import thexfactor117.lsc.client.models.entities.ModelMummy;
import thexfactor117.lsc.entities.monsters.EntityMummy;
import thexfactor117.lsc.util.Reference;

/**
 * 
 * @author TheXFactor117
 *
 */
public class RenderMummy extends RenderLiving<EntityMummy>
{
	private static final ResourceLocation TEXTURE = new ResourceLocation(Reference.MODID + ":textures/models/mummy.png"); 
	
	public RenderMummy(RenderManager manager) 
	{
		super(manager, new ModelMummy(), 0.5F);
	}

	@Override
	protected ResourceLocation getEntityTexture(EntityMummy entity) 
	{
		return TEXTURE;
	}
}