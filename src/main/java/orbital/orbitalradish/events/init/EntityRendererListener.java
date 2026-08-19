package orbital.orbitalradish.events.init;

import net.mine_diver.unsafeevents.listener.EventListener;
import net.modificationstation.stationapi.api.client.event.render.entity.EntityRendererRegisterEvent;
import orbital.orbitalradish.entity.RadishStickEntity;
import orbital.orbitalradish.entity.RadishStickEntityRenderer;

public class EntityRendererListener {

    @EventListener
    public void registerEntityRenderers(EntityRendererRegisterEvent event) {
        event.register(RadishStickEntity.class, new RadishStickEntityRenderer());
    }
}