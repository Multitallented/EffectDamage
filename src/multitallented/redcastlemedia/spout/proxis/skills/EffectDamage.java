package multitallented.redcastlemedia.spout.proxis.skills;

import com.redcastlemedia.multitallented.spout.proxis.Proxis;
import com.redcastlemedia.multitallented.spout.proxis.models.effects.EffectSource;
import com.redcastlemedia.multitallented.spout.proxis.models.skills.Skill.CastSkill;
import com.redcastlemedia.multitallented.spout.proxis.models.users.User;
import java.util.HashMap;
import org.spout.api.Spout;
import org.spout.api.entity.Entity;
import org.spout.api.entity.Player;
import org.spout.api.geo.LoadOption;
import org.spout.api.geo.cuboid.Block;
import org.spout.api.geo.cuboid.Chunk;
import org.spout.vanilla.component.misc.HealthComponent;
import org.spout.vanilla.source.DamageCause;

/**
 *
 * @author Multitallented
 */
public class EffectDamage extends EffectSource {
    public EffectDamage() {
        super("EffectDamage");
    }

    @Override
    public void execute(Proxis plugin, CastSkill cs, User target, HashMap<String, Object> node) {
        Player player = Spout.getEngine().getPlayer(target.NAME, true);
        if (player == null) {
            return;
        }
        damageEntity(player, cs.getCaster(), node);
    }

    @Override
    public void execute(Proxis plugin, CastSkill cs, Entity target, HashMap<String, Object> node) {
        damageEntity(target, cs.getCaster(), node);
    }

    @Override
    public void execute(Proxis plugin, CastSkill cs, Block target, HashMap<String, Object> node) {
        int radius = 1;
        if (node.containsKey("radius")) {
            try {
                radius = (int) node.get("radius");
            } catch (Exception e) {
                
            }
        }
        Chunk chunk = target.getPosition().getChunk(LoadOption.NO_LOAD);
        if (!chunk.isLoaded()) {
            return;
        } 
        for (Entity e : chunk.getEntities()) {
            if (target.getPosition().getDistance(e.getTransform().getPosition()) > radius) {
                continue;
            }
            if (e.has(HealthComponent.class) && damageCheck(cs.getCaster(), e)) {
                damageEntity(e, cs.getCaster(), node);
                break;
            }
        }
    }
    
    private void damageEntity(Entity target, Player caster, HashMap<String, Object> node) {
        if (!target.has(HealthComponent.class)) {
            return;
        }
        int amount = 1;
        if (node.containsKey("damage")) {
            try {
                amount = (int) node.get("damage");
            } catch (Exception e) {

            }
        }
        boolean knockback = true;
        if (node.containsKey("knockback")) {
            try {
                knockback = (boolean) node.get("knockback");
            } catch (Exception e) {

            }
        }
        target.get(HealthComponent.class).damage(amount, DamageCause.COMMAND, caster, knockback);
    }
    
    @Override
    public HashMap<String, Object> getDefaultNode() {
        HashMap<String, Object> tempMap = new HashMap<>();
        tempMap.put("knockback", true);
        tempMap.put("damage", 1);
        tempMap.put("radius", 1);
        return tempMap;
    }
}
