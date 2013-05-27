package com.codefest2013.game.scenes.objects;

import org.andengine.entity.sprite.Sprite;

import com.badlogic.gdx.physics.box2d.Body;

public class Gift {
	public Body body;
	public Sprite sprite;
	
	public Gift(Sprite sprite, Body body) {
		this.body = body;
		this.sprite = sprite;
	}

}
