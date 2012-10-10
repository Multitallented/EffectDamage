package multitallented.redcastlemedia.spout.proxis.skills;

import java.util.logging.Level;
import multitallented.redcastlemedia.spout.proxis.models.effects.EffectSource;
import multitallented.redcastlemedia.spout.proxis.models.users.User;
import org.spout.api.entity.Player;
import org.spout.api.geo.cuboid.Block;
import org.spout.api.util.config.ConfigurationHolder;
import org.spout.vanilla.component.living.VanillaEntity;
import org.spout.vanilla.component.misc.HealthComponent;
import org.spout.vanilla.source.DamageCause;

/**
 *
 * @author Multitallented
 */
public class EffectDamage extends EffectSource {
	public static final ConfigurationHolder DAMAGE = new ConfigurationHolder(10, "EffectDamage", "damage");
	public static final ConfigurationHolder DAMAGE_LEVEL = new ConfigurationHolder(0, "EffectDamage", "damage-level");
	public static final String NAME = "EffectDamage";
	
    @Override
    public void execute(User user, User target){
        Player player = getPlugin().getEngine().getPlayer(user.getName(), false);
        Player tPlayer = getPlugin().getEngine().getPlayer(target.getName(), false);
        if (tPlayer.get(HealthComponent.class) == null) {
            getPlugin().log(Level.WARNING, "[Proxis] failed to do damage to Player");
            return;
        }
        tPlayer.get(HealthComponent.class).damage(DAMAGE.getInt(), DamageCause.COMMAND, player, true);
    }
    
    @Override
    public void execute(User user, VanillaEntity target) {
        Player player = getPlugin().getEngine().getPlayer(user.getName(), false);
        try {
            target.getHolder().get(HealthComponent.class).damage(DAMAGE.getInt(), DamageCause.COMMAND, player, true);
        } catch (NullPointerException npe) {
            getPlugin().log(Level.WARNING, "[Proxis] failed to do damage to VanillaEntity");
        }
    }
    
    @Override
    public void execute(User user, Block target) { 
        
    }
}