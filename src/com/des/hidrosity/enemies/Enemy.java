package com.des.hidrosity.enemies;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.des.hidrosity.constants.CollisionConstants;
import com.des.hidrosity.constants.EnemyConstants;
import com.des.hidrosity.constants.GameConstants;
import com.des.hidrosity.player.Player;
import com.des.hidrosity.screens.GameScreen;
import com.jakehorsfield.libld.GameObject;

public abstract class Enemy extends GameObject {

	protected Player player;

	protected Texture leftTexture;
	protected Texture rightTexture;

	protected Texture shootLeftTexture;
	protected Texture shootRightTexture;

	protected Body physicsBody;
	protected BodyDef bodyDef;
	protected FixtureDef fixtureDef;
	protected Fixture fixture;

	protected int health = 100;

	public boolean dead = false;

	protected enum Direction {
		Left, Right
	}

    protected enum State {
        Idle,
        Hurt
    }

	protected Direction currentDirection = Direction.Left;
    protected State currentState = State.Idle;

	public Enemy(Vector2 position, String textureName, Player player) {
		super(position, textureName);

		this.player = player;

		createPhysicsBody();
	}

	private void createPhysicsBody() {
		bodyDef = new BodyDef();
		fixtureDef = new FixtureDef();

		bodyDef.position.set(
				(getX() + (getWidth() * GameConstants.IMAGE_SCALE) / 2)
						* GameConstants.UNIT_SCALE,
				(getY() + (getHeight() * GameConstants.IMAGE_SCALE) / 2)
						* GameConstants.UNIT_SCALE);
		bodyDef.type = BodyType.DynamicBody;

		PolygonShape polygonShape = new PolygonShape();
		polygonShape
				.setAsBox(
						((getWidth() * GameConstants.IMAGE_SCALE) / 2)
								* GameConstants.UNIT_SCALE,
						(((getHeight() * GameConstants.IMAGE_SCALE) / 2) * GameConstants.UNIT_SCALE));

		FixtureDef fixtureDef = new FixtureDef();
		fixtureDef.shape = polygonShape;
		fixtureDef.density = EnemyConstants.DENSITY;
		fixtureDef.restitution = EnemyConstants.RESTITUTION;
        fixtureDef.friction = EnemyConstants.FRICTION;
		fixtureDef.filter.categoryBits = CollisionConstants.ENEMY;
		fixtureDef.filter.maskBits = CollisionConstants.ENEMY_MASK;
        fixtureDef.isSensor = false;

		physicsBody = GameScreen.physicsWorld.createBody(bodyDef);
		physicsBody.setFixedRotation(true);

		fixture = physicsBody.createFixture(fixtureDef);
		fixture.setUserData(this);

		polygonShape.dispose();
	}

	@Override
	public void update(float delta) {
		facePlayer();
		checkIfDead();
	}

	private void checkIfDead() {
		if (health <= 0) {
			dead = true;
		}
	}

	protected void facePlayer() {
		if (player.getX() < getX()) {
			faceLeft();
		} else if (player.getX() > getX()) {
			faceRight();
		}
	}

	private void faceRight() {
		currentDirection = Direction.Right;
		setTexture(rightTexture);
	}

	private void faceLeft() {
		currentDirection = Direction.Left;
		setTexture(leftTexture);
	}

	@Override
	public void render(SpriteBatch spriteBatch) {
		spriteBatch.draw(getTexture(), getX(), getY(), getWidth()
				* GameConstants.IMAGE_SCALE, getHeight()
				* GameConstants.IMAGE_SCALE);
	}

	public int getHealth() {
		return health;
	}

	public Body getBody() {
		return physicsBody;
	}

	public abstract void hitByBullet();

	public abstract void prepareForRemoval();
}
