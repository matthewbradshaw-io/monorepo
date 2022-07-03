package io.matthewbradshaw.merovingian.engine

import com.jme3.app.SimpleApplication
import com.jme3.app.VRAppState
import com.jme3.app.state.AppStateManager
import com.jme3.asset.AssetManager
import com.jme3.bullet.BulletAppState
import com.jme3.renderer.Camera
import com.jme3.scene.Node
import kotlinx.coroutines.CoroutineScope

/**
 * The core elements of the [jMonkey 3 game engine](https://jmonkeyengine.org/).
 */
interface Engine {

  /**
   * Extracts the default camera from the engine.
   */
  fun extractCamera(): Camera

  /**
   * Extracts the asset manager from the engine.
   */
  fun extractAssetManager(): AssetManager

  /**
   * Extracts the root application object from the game engine.
   */
  fun extractApp(): SimpleApplication

  /**
   * Extracts the VR controller from the game engine. Returns null if the engine is not configured for VR.
   */
  fun extractVr(): VRAppState?

  /**
   * Extracts the physics controller from the game engine.
   */
  fun extractPhysics(): BulletAppState

  /**
   * Extracts the physics controller from the game engine.
   */
  fun extractPhysics(): BulletAppState

  /**
   * Extracts the state manager from the game engine.
   */
  fun extractStateManager(): AppStateManager

  /**
   * Extracts a node for use by the framework. Application programmers should not modify this node and should
   * instead use the node supplied by [extractApplicationNode] if they need direct access to a node near the root.
   */
  fun extractFrameworkNode(): Node

  /**
   * Extracts a node for use by applications. Application programmers should use this node instead of
   * [extractFrameworkNode] if they need direct access to a node near the root.
   */
  fun extractApplicationNode(): Node

  /**
   * Extracts a coroutine scope which tracks the engine state. The scope is cancelled when the game engine stops.
   */
  fun extractCoroutineScope(): CoroutineScope

  /**
   * Extracts the time since game start, measured in seconds.
   */
  fun extractTotalTime(): Double
}