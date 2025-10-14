package cn.leolezury.eternalstarlight.common.item.combat;

import cn.leolezury.eternalstarlight.common.entity.projectile.ShotSeeds;
import cn.leolezury.eternalstarlight.common.registry.ESParticles;
import cn.leolezury.eternalstarlight.common.registry.ESSoundEvents;
import cn.leolezury.eternalstarlight.common.util.ESMathUtil;
import cn.leolezury.eternalstarlight.common.util.ESTags;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ProjectileWeaponItem;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.joml.Quaternionf;
import org.joml.Vector3f;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

public class SeedsLauncherItem extends ProjectileWeaponItem {
	public SeedsLauncherItem(Properties properties) {
		super(properties);
	}

	@Override
	public int getUseDuration(ItemStack stack, LivingEntity entity) {
		return 72000;
	}

	@Override
	public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
		ItemStack stack = player.getItemInHand(hand);
		ItemStack projectile = player.getProjectile(stack);
		boolean success = performShooting(level, player, projectile, hand);
		if (success) {
			SeedsLauncherAmmoType type = SeedsLauncherAmmoType.getAmmoType(level.registryAccess(), projectile.getItem()).value();
			player.getCooldowns().addCooldown(this, type.cooldownAsTicks());
		}
		return success ? InteractionResultHolder.consume(stack) : super.use(level, player, hand);
	}

	public boolean performShooting(Level level, LivingEntity living, ItemStack projectile, InteractionHand hand) {
		ItemStack stack = living.getItemInHand(hand);
		if (!projectile.isEmpty()) {
			List<ItemStack> list = draw(stack, projectile, living);
			if (!list.isEmpty()) {
				if (level instanceof ServerLevel serverLevel) {
					this.shoot(serverLevel, living, living.getUsedItemHand(), stack, list, 0.75F, 7.5F, true, null);
					Vec3 particlePos = ESMathUtil.rotationToPosition(living.position().add(0, 3 * living.getBbHeight() / 4, 0), 1f, -living.getXRot(), living.getYHeadRot() + 90);
					serverLevel.sendParticles(ESParticles.PUNGENCY_FRUIT_SMOKE.get(), particlePos.x(), particlePos.y(), particlePos.z(), 20, 0.1, 0.1, 0.1, 0.025);
					stack.hurtAndBreak(1, living, LivingEntity.getSlotForHand(living.getUsedItemHand()));
				}
				return true;
			}
		}
		return false;
	}

	// copied from ProjectileWeaponItem
	// modified default projectile count to 6
	@NotNull
	protected static List<ItemStack> draw(ItemStack weapon, ItemStack ammo, LivingEntity shooter) {
		if (ammo.isEmpty()) {
			return List.of();
		} else {
			int count = shooter.level() instanceof ServerLevel serverlevel ? EnchantmentHelper.processProjectileCount(serverlevel, weapon, shooter, 6) : 6;
			List<ItemStack> list = new ArrayList<>(count);
			ItemStack ammoCopy = ammo.copy();

			for (int i = 0; i < count; i++) {
				ItemStack itemstack = useAmmo(weapon, i == 0 ? ammo : ammoCopy, shooter, i > 0);
				if (!itemstack.isEmpty()) {
					list.add(itemstack);
				}
			}

			return list;
		}
	}

	@Override
	public Predicate<ItemStack> getAllSupportedProjectiles() {
		return stack -> stack.is(ESTags.Items.SEEDS_LAUNCHER_AMMO);
	}

	@Override
	public int getDefaultProjectileRange() {
		return 5;
	}

	@Override
	protected int getDurabilityUse(ItemStack itemStack) {
		return 0;
	}

	@Override
	protected Projectile createProjectile(Level level, LivingEntity shooter, ItemStack weapon, ItemStack ammo, boolean isCrit) {
		return new ShotSeeds(level, shooter, ammo, weapon);
	}

	@Override
	protected void shootProjectile(LivingEntity shooter, Projectile projectile, int index, float velocity, float inaccuracy, float angle, @Nullable LivingEntity target) {
		Vector3f direction;
		if (target != null) {
			double xDiff = target.getX() - shooter.getX();
			double zDiff = target.getZ() - shooter.getZ();
			double dist = Math.sqrt(xDiff * xDiff + zDiff * zDiff);
			double yDiff = target.getY(0.3333333333333333) - projectile.getY() + dist * 0.2F;
			direction = getProjectileShotVector(shooter, new Vec3(xDiff, yDiff, zDiff), angle);
		} else {
			Vec3 upVector = shooter.getUpVector(1.0F);
			Quaternionf rotation = new Quaternionf().setAngleAxis(angle * (Math.PI / 180.0), upVector.x, upVector.y, upVector.z);
			Vec3 viewVector = shooter.getViewVector(1.0F);
			direction = viewVector.toVector3f().rotate(rotation);
		}
		float speedMultiplier = 1;
		if (projectile instanceof ShotSeeds seeds) {
			SeedsLauncherAmmoType type = SeedsLauncherAmmoType.getAmmoType(shooter.level().registryAccess(), seeds.getItem().getItem()).value();
			speedMultiplier *= type.speedMultiplier();
		}
		projectile.shoot(direction.x(), direction.y(), direction.z(), velocity * speedMultiplier, inaccuracy);
		shooter.level().playSound(null, shooter.getX(), shooter.getY(), shooter.getZ(), ESSoundEvents.SEEDS_LAUNCHER_SHOOT.get(), shooter.getSoundSource(), 1.0F, 1.0F);
	}

	private static Vector3f getProjectileShotVector(LivingEntity shooter, Vec3 distance, float angle) {
		Vector3f direction = distance.toVector3f().normalize();
		Vector3f vec = new Vector3f(direction).cross(new Vector3f(0.0F, 1.0F, 0.0F));
		if (vec.lengthSquared() <= 1.0E-7) {
			Vec3 vec3 = shooter.getUpVector(1.0F);
			vec = new Vector3f(direction).cross(vec3.toVector3f());
		}

		Vector3f rotated = new Vector3f(direction).rotateAxis((float) (Math.PI / 2), vec.x, vec.y, vec.z);
		return new Vector3f(direction).rotateAxis(angle * (float) (Math.PI / 180.0), rotated.x, rotated.y, rotated.z);
	}
}
