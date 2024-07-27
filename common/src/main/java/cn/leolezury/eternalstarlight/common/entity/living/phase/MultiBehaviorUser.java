package cn.leolezury.eternalstarlight.common.entity.living.phase;

public interface MultiBehaviorUser {
	void setBehaviorState(int state);

	int getBehaviorState();

	void setBehaviorTicks(int ticks);

	int getBehaviorTicks();
}
