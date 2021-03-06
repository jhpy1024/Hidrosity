package com.des.hidrosity.bullets;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Filter;
import com.badlogic.gdx.physics.box2d.World;
import com.des.hidrosity.constants.CollisionConstants;

public class PlayerBullet extends Bullet {

	public PlayerBullet(Vector2 position, String textureName, int direction,
			World gameWorld, Object parent) {
		super(position, textureName, direction, gameWorld, parent);

		Filter filterData = new Filter();
		filterData.categoryBits = CollisionConstants.PLAYER_BULLET;
		filterData.maskBits = CollisionConstants.PLAYER_BULLET_MASK;

		fixture.setFilterData(filterData);
		fixture.setUserData(this);
	}

	@Override
	public Body getBody() {
		return physicsBody;
	}
}
