package com.des.hidrosity.bullets;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;

public class StationaryEnemyBullet extends Bullet {

	public StationaryEnemyBullet(Vector2 position, String textureName, int direction, World gameWorld) {
		super(position, textureName, direction, gameWorld);
	}
}