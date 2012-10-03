package multitallented.redcastlemedia.spout.proxis.skills;

import java.util.HashMap;
import multitallented.redcastlemedia.spout.proxis.managers.SkillManager;
import multitallented.redcastlemedia.spout.proxis.managers.UserManager;
import multitallented.redcastlemedia.spout.proxis.models.effects.EffectSource;
import multitallented.redcastlemedia.spout.proxis.models.effects.ExpirableEffect;
import multitallented.redcastlemedia.spout.proxis.models.users.User;
import org.spout.api.entity.Entity;
import org.spout.api.entity.Player;
import org.spout.api.util.config.ConfigurationHolder;
import org.spout.vanilla.component.living.VanillaEntity;
import org.spout.vanilla.component.misc.HealthComponent;
import org.spout.vanilla.source.DamageCause;

/**
 *
 * @author Multitallented
 */
public class EffectDamage extends EffectSource implements ExpirableEffect {
	public static final ConfigurationHolder DAMAGE = new ConfigurationHolder(10, "EffectDamage", "damage");
	public static final ConfigurationHolder PERIOD = new ConfigurationHolder(1000, "EffectDamage", "period");
	public static final ConfigurationHolder DURATION = new ConfigurationHolder(10000, "EffectDamage", "duration");
	public static final ConfigurationHolder DAMAGE_INCREASE_PER_TICK = new ConfigurationHolder(1, "EffectDamage", "damage-increase-per-tick");
	public static final ConfigurationHolder CANCEL_IF_INVULNERABLE_TARGET = new ConfigurationHolder(false, "EffectDamage", "cancel-if-invulnerable-target");
	public static final String NAME = "EffectDamage";
	
	private HashMap<String, Integer> ticks = new HashMap<>();

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

	@Override
	public long setDuration() {
		return DURATION.getLong();
	}
	
	@Override
	public long setPeriod() {
		return PERIOD.getLong();
	}

	@Override
	public void applyEffect(String skillName, User user, Entity target) {
		Player p = getPlugin().getEngine().getPlayer(user.getName(), false);
		try {
			ticks.put(user.getName(), 0);
			target.get(HealthComponent.class).damage(DAMAGE.getInt(), DamageCause.COMMAND, p, true);
		} catch (Exception e) {
			
		}
	}

	@Override
	public void removeEffect(String skillName, User user, Entity target) {
		throw new UnsupportedOperationException("Not supported yet.");
	}

	@Override
	public void tick(Entity e) {
		throw new UnsupportedOperationException("Not supported yet.");
	}

	@Override
	public void execute(String skillName, User user, Entity e) {
		try {
			Player p = (Player) e;
			UserManager.getUser(p.getName()).effects.add(SkillManager.getSkill(skillName).getEffect(NAME));
		} catch (Exception ex) {
			
		}
	}
}
