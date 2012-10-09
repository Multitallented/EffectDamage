package multitallented.redcastlemedia.spout.proxis.skills;

import multitallented.redcastlemedia.spout.proxis.models.effects.ActiveEffect;
import multitallented.redcastlemedia.spout.proxis.models.effects.EffectSource;
import multitallented.redcastlemedia.spout.proxis.models.users.User;
import org.spout.api.entity.Entity;
import org.spout.api.entity.Player;
import org.spout.api.util.config.ConfigurationHolder;
import org.spout.vanilla.component.misc.HealthComponent;
import org.spout.vanilla.source.DamageCause;

/**
 *
 * @author Multitallented
 */
public class EffectDamage extends EffectSource implements ActiveEffect{
	public static final ConfigurationHolder DAMAGE = new ConfigurationHolder(10, "EffectDamage", "damage");
	public static final ConfigurationHolder DAMAGE_LEVEL = new ConfigurationHolder(0, "EffectDamage", "damage-level");
	public static final String NAME = "EffectDamage";

	public EffectDamage() {
		super(NAME);
	}

	@Override
	public void save() {
		//Do nothing because there is no file associated with the skillsource
	}

	@Override
	public void load() {
		//Do nothing because there is no file associated with the skillsource
	}
	
	public void execute(String skillName, User user, Entity target){
		Player player = getPlugin().getEngine().getPlayer(user.getName(), false);
		try{
			target.get(HealthComponent.class).damage(DAMAGE.getInt() + (DAMAGE_LEVEL.getInt()/* * user.getLevel() */), DamageCause.COMMAND, player, true);
		}catch (Exception e){
			
		}
	}
}