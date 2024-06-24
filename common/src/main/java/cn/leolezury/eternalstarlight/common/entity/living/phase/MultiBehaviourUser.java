package cn.leolezury.eternalstarlight.common.entity.living.phase;

public interface MultiBehaviourUser {
	void setBehaviourState(int state);

	int getBehaviourState();

	void setBehaviourTicks(int ticks);

	int getBehaviourTicks();
}
