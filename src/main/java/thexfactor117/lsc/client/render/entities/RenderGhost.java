package thexfactor117.lsc.client.render.entities;

import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;
import thexfactor117.lsc.client.models.entities.ModelGhost;
import thexfactor117.lsc.entities.monsters.EntityGhost;
import thexfactor117.lsc.util.Reference;

/**
 * 
 * @author TheXFactor117
 *
 */
public class RenderGhost extends RenderLiving<EntityGhost>
{
	private static final ResourceLocation TEXTURE = new ResourceLocation(Reference.MODID + ":textures/models/ghost.png"); 
	
	public RenderGhost(RenderManager manager) 
	{
		super(manager, new ModelGhost(), 0.5F);
	}

	@Override
	protected ResourceLocation getEntityTexture(EntityGhost entity) 
	{
		return TEXTURE;
	}
}