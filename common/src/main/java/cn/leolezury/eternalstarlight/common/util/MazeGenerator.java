package cn.leolezury.eternalstarlight.common.util;

import net.minecraft.util.RandomSource;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class MazeGenerator {
	private final int size;
	private final boolean[][] maze;
	private RandomSource random;

	public MazeGenerator(int size, RandomSource random) {
		// size should be odd or else it generates weird
		this.size = size;
		this.maze = new boolean[size][size];
		this.random = random;
	}

	public void setRandom(RandomSource random) {
		this.random = random;
	}

	public boolean[][] generateMaze(int startX, int startY) {
		// fill with wall
		for (int i = 0; i < size; i++) {
			Arrays.fill(maze[i], true);
		}

		// do the generation
		generate(startX, startY);

		// true for wall, false for path
		return maze;
	}

	private void generate(int x, int y) {
		maze[x][y] = false; // make it passable

		// pick random directions
		List<Integer> directions = Arrays.asList(0, 1, 2, 3);
		Collections.shuffle(directions, new Random() {
			@Override
			public int nextInt(int i) {
				return MazeGenerator.this.random.nextInt(i);
			}
		});

		for (int direction : directions) {
			int dx = x + 2 * getDX(direction); // next target x
			int dy = y + 2 * getDY(direction); // next target y

			// still in the maze and not visited
			if (isInRange(dx, dy) && maze[dx][dy]) {
				maze[x + getDX(direction)][y + getDY(direction)] = false; // break the wall
				generate(dx, dy); // continue generating
			}
		}
	}

	private boolean isInRange(int x, int y) {
		return x >= 0 && x < size && y >= 0 && y < size;
	}

	private int getDX(int direction) {
		if (direction == 1) return -1; // L
		if (direction == 3) return 1;  // R
		return 0; // U, D
	}

	private int getDY(int direction) {
		if (direction == 0) return -1; // U
		if (direction == 2) return 1;  // D
		return 0; // L, R
	}
}
