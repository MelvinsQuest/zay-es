/*
 * $Id$
 *
 * Copyright (c) 2013 jMonkeyEngine
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are
 * met:
 *
 * * Redistributions of source code must retain the above copyright
 *   notice, this list of conditions and the following disclaimer.
 *
 * * Redistributions in binary form must reproduce the above copyright
 *   notice, this list of conditions and the following disclaimer in the
 *   documentation and/or other materials provided with the distribution.
 *
 * * Neither the name of 'jMonkeyEngine' nor the names of its contributors
 *   may be used to endorse or promote products derived from this software
 *   without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
 * "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED
 * TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR
 * PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR
 * CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL,
 * EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO,
 * PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR
 * PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF
 * LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 * NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package trap;

import com.jme3.asset.AssetManager;
import com.jme3.audio.AudioNode;
import com.jme3.effect.ParticleEmitter;
import com.jme3.effect.ParticleMesh.Type;
import com.jme3.effect.shapes.EmitterSphereShape;
import com.jme3.material.Material;
import com.jme3.material.RenderState.BlendMode;
import com.jme3.math.ColorRGBA;
import com.jme3.math.FastMath;
import com.jme3.math.Vector3f;
import com.jme3.scene.Node;
import trap.game.TimeProvider;


/**
 *  Some animated special effects... mostly particle stuff with
 *  sounds.  This may be temporary.
 *
 *  @author    Paul Speed
 */
public class Effects {
 
    private static TimeProvider time;   
    private static AssetManager assets;
    
    public static void initialize( TimeProvider time, AssetManager assets ) {
        Effects.time = time;
        Effects.assets = assets;
    }
    
    public static Node playSpurt( Node target, long startTime ) {
 
        Node wrapper = new Node("Spurt");

        ParticleEmitter emitter = createSpurtEmitter(startTime);
        wrapper.attachChild(emitter);
                    
        target.attachChild(wrapper);           
        return wrapper;            
    
    }

    protected static ParticleEmitter createSpurtEmitter( long startTime ) {
    
        ParticleEmitter emitter = new ParticleEmitter("Spurt", Type.Point, 32);
        emitter.setSelectRandomImage(true);
        emitter.setStartColor(ColorRGBA.Red);
        emitter.setEndColor(new ColorRGBA(0.1f, 0, 0, 0));
        emitter.setStartSize(0.05f);
        emitter.setEndSize(0.1f);
        emitter.setShape(new EmitterSphereShape(Vector3f.ZERO, 0.1f));
        emitter.setParticlesPerSec(0);
        emitter.setGravity(0, -1, 0);
        emitter.setLowLife(0.4f);
        emitter.setHighLife(0.75f);
        emitter.getParticleInfluencer().setInitialVelocity(new Vector3f(0, 2, 0));
        emitter.getParticleInfluencer().setVelocityVariation(1f);
        emitter.setImagesX(16);
        emitter.setImagesY(1);
            
        Material mat = new Material(assets, "Common/MatDefs/Misc/Particle.j3md");
        mat.setTexture("Texture", assets.loadTexture("Textures/Smoke.png"));
        mat.setBoolean("PointSprite", true);
        emitter.setMaterial(mat);
        emitter.setLocalTranslation(0, 1f, 0);
            
        emitter.addControl(new ParticleControl(emitter, startTime, time));
        return emitter;
    }
 
    protected static ParticleEmitter createGibEmitter( long startTime, String debris ) {
        ParticleEmitter emitter = new ParticleEmitter("Gib", Type.Triangle, 32);
        
        emitter.setSelectRandomImage(true);
        emitter.setRandomAngle(true);
        emitter.setRotateSpeed(FastMath.TWO_PI * 4);
        emitter.setStartColor(new ColorRGBA(1f, 1f, 1f, 1));
        emitter.setEndColor(new ColorRGBA(0.5f, 0.5f, 0.5f, 0.5f));
        emitter.setStartSize(0.12f);
        emitter.setEndSize(0.12f);
        emitter.setParticlesPerSec(0);
        emitter.setGravity(0, 5f, 0);
        emitter.setLowLife(1.4f);
        emitter.setHighLife(1.5f);
        emitter.getParticleInfluencer().setInitialVelocity(new Vector3f(0, 3, 0));
        emitter.getParticleInfluencer().setVelocityVariation(0.6f);
        emitter.setImagesX(3);
        emitter.setImagesY(3);
        Material mat = new Material(assets, "Common/MatDefs/Misc/Particle.j3md");
        mat.setTexture("Texture", assets.loadTexture(debris));
        mat.getAdditionalRenderState().setBlendMode(BlendMode.Alpha);
        emitter.setMaterial(mat);
        
        emitter.move(0, 1, 0);
        emitter.addControl(new ParticleControl(emitter, startTime, time));
        return emitter;
    }
      
    protected static ParticleEmitter createShockwaveEmitter( long startTime ) {
        ParticleEmitter emitter = new ParticleEmitter("Shockwave", Type.Triangle, 1);
        
        emitter.setFaceNormal(Vector3f.UNIT_Y);
        emitter.setStartColor(new ColorRGBA(.48f, 0.48f, 0.48f, 0.8f));
        emitter.setEndColor(new ColorRGBA(0.48f, 0.48f, 0.48f, 0f));

        emitter.setStartSize(0f);
        emitter.setEndSize(1.6f);

        emitter.setParticlesPerSec(0);
        emitter.setGravity(0, 0, 0);
        emitter.setLowLife(0.5f);
        emitter.setHighLife(0.5f);
        emitter.getParticleInfluencer().setInitialVelocity(new Vector3f(0, 0, 0));
        emitter.getParticleInfluencer().setVelocityVariation(0f);
        emitter.setImagesX(1);
        emitter.setImagesY(1);
        Material mat = new Material(assets, "Common/MatDefs/Misc/Particle.j3md");
        mat.setTexture("Texture", assets.loadTexture("Textures/shockwave.png"));
        emitter.setMaterial(mat);
        
        emitter.move(0, 0.75f, 0);        
        emitter.addControl(new ParticleControl(emitter, startTime, time));
        return emitter;
    }

    protected static ParticleEmitter createSmokeTrailEmitter( long startTime ) {
        ParticleEmitter emitter = new ParticleEmitter("Smoke Trail", Type.Triangle, 25);
        
        emitter.setStartColor(new ColorRGBA(0.9f, 0.8f, 0.7f, 1.0f));
        emitter.setEndColor(new ColorRGBA(1f, 0.8f, 0.7f, 0f));
        emitter.setStartSize(.1f);
        emitter.setEndSize(0.5f);
        emitter.setFacingVelocity(true);
        emitter.setParticlesPerSec(0);
        emitter.setGravity(0, 0.5f, 0);
        emitter.setLowLife(.4f);
        emitter.setHighLife(.5f);
        emitter.getParticleInfluencer().setInitialVelocity(new Vector3f(0, 3, 0));
        emitter.getParticleInfluencer().setVelocityVariation(1);
        emitter.setImagesX(1);
        emitter.setImagesY(3);
        Material mat = new Material(assets, "Common/MatDefs/Misc/Particle.j3md");
        mat.setTexture("Texture", assets.loadTexture("Textures/smoketrail.png"));
        emitter.setMaterial(mat);
        
        emitter.move(0, 1, 0);        
        emitter.addControl(new ParticleControl(emitter, startTime, time));
        return emitter;
    }

    protected static ParticleEmitter createSmokeEmitter( long startTime ) {
        ParticleEmitter emitter = new ParticleEmitter("Smoke", Type.Triangle, 25);
        
        emitter.setSelectRandomImage(true);
        emitter.setStartColor(new ColorRGBA(0.25f, 0.2f, 0.15f, 0.75f));
        emitter.setEndColor(new ColorRGBA(0.5f, 0.45f, 0.43f, 0f));
        emitter.setStartSize(0.5f);
        emitter.setEndSize(1.0f);
        emitter.setShape(new EmitterSphereShape(Vector3f.ZERO, 1f));
        emitter.setParticlesPerSec(0);
        emitter.setGravity(0, -1.5f, 0);
        emitter.setLowLife(.4f);
        emitter.setHighLife(.85f);
        emitter.getParticleInfluencer().setInitialVelocity(new Vector3f(0, 2, 0));
        emitter.getParticleInfluencer().setVelocityVariation(0.85f);
        emitter.setImagesX(16);
        emitter.setImagesY(1);
        Material mat = new Material(assets, "Common/MatDefs/Misc/Particle.j3md");
        mat.setTexture("Texture", assets.loadTexture("Textures/Smoke.png"));
        mat.setBoolean("PointSprite", true);
        emitter.setMaterial(mat);
        
        emitter.move(0, 1, 0);        
        emitter.addControl(new ParticleControl(emitter, startTime, time));
        return emitter;
    }

    protected static ParticleEmitter createDebrisEmitter( long startTime, String debris ) {
        ParticleEmitter emitter = new ParticleEmitter("Debris", Type.Triangle, 25);
        
        emitter.setSelectRandomImage(true);
        emitter.setRandomAngle(true);
        emitter.setRotateSpeed(FastMath.TWO_PI * 4);
        emitter.setStartColor(new ColorRGBA(1f, 1f, 1f, 1));
        emitter.setEndColor(new ColorRGBA(1f, 1f, 1f, 0.5f));
        emitter.setStartSize(0.12f);
        emitter.setEndSize(0.12f);
        emitter.setParticlesPerSec(0);
        emitter.setGravity(0, 5f, 0);
        emitter.setLowLife(1.4f);
        emitter.setHighLife(1.5f);
        emitter.getParticleInfluencer().setInitialVelocity(new Vector3f(0, 3, 0));
        emitter.getParticleInfluencer().setVelocityVariation(0.6f);
        emitter.setImagesX(3);
        emitter.setImagesY(3);
        Material mat = new Material(assets, "Common/MatDefs/Misc/Particle.j3md");
        mat.setTexture("Texture", assets.loadTexture(debris));
        mat.getAdditionalRenderState().setBlendMode(BlendMode.Alpha);
        emitter.setMaterial(mat);
        
        emitter.move(0, 1, 0);        
        emitter.addControl(new ParticleControl(emitter, startTime, time));
        return emitter;
    } 
 
    public static Node playExplosion( Node target, long startTime, String debris ) {
        Node wrapper = new Node("Explosion");
        
        wrapper.attachChild(createDebrisEmitter(startTime, debris));
        wrapper.attachChild(createShockwaveEmitter(startTime));
        wrapper.attachChild(createSmokeTrailEmitter(startTime));
        wrapper.attachChild(createSmokeEmitter(startTime));
        
        target.attachChild(wrapper);
        return wrapper;
    } 
 
    public static Node playGib( Node target, long startTime, String debris ) {
    
        Node wrapper = new Node("Gib");

        wrapper.attachChild(createGibEmitter(startTime, debris));
        wrapper.attachChild(createSpurtEmitter(startTime));
        
        target.attachChild(wrapper);           
        return wrapper;            
    }
   
    public static Node playBling( Node target, long startTime, ColorRGBA startColor, ColorRGBA endColor ) {

        Node wrapper = new Node("Bling");

        ParticleEmitter emitter = new ParticleEmitter("StarBurst", Type.Point, 32);
        emitter.setSelectRandomImage(true);
        emitter.setStartColor(startColor);
        emitter.setEndColor(endColor);
        emitter.setStartSize(0.05f);
        emitter.setEndSize(0.1f);
        emitter.setShape(new EmitterSphereShape(Vector3f.ZERO, 0.1f));
        emitter.setParticlesPerSec(0);
        emitter.setGravity(0, -1, 0);
        emitter.setLowLife(0.4f);
        emitter.setHighLife(0.75f);
        emitter.getParticleInfluencer().setInitialVelocity(new Vector3f(0, 2, 0));
        emitter.getParticleInfluencer().setVelocityVariation(1f);
        emitter.setImagesX(16);
        emitter.setImagesY(1);
            
        Material mat = new Material(assets, "Common/MatDefs/Misc/Particle.j3md");
        mat.setTexture("Texture", assets.loadTexture("Textures/Smoke.png"));
        mat.setBoolean("PointSprite", true);
        emitter.setMaterial(mat);
        emitter.setLocalTranslation(0, 1f, 0);
            
        wrapper.attachChild(emitter);
            
        AudioNode bling = new AudioNode(assets, "Sounds/bling.ogg", false);
        bling.setVolume(0.25f);
        bling.setPositional(false);
 
        if( startTime < 0 ) {
            startTime = time.getTime();
        }           
        wrapper.addControl(new ParticleControl(emitter, bling, startTime, time));
 
        target.attachChild(wrapper);           
        return wrapper;            
    }

}

