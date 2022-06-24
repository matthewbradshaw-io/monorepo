package io.matthewbradshaw.merovingian.testing

import kotlinx.coroutines.sync.withLock
import kotlinx.coroutines.sync.Mutex
import com.jme3.material.Material
import com.jme3.math.ColorRGBA
import com.jme3.math.Vector3f
import com.jme3.scene.Geometry
import com.jme3.scene.Node
import com.jme3.scene.Spatial
import io.matthewbradshaw.merovingian.model.GameItem
import io.matthewbradshaw.merovingian.engine.MainDispatcher
import com.jme3.scene.shape.Box
import kotlin.math.acos
import kotlinx.coroutines.flow.collect
import kotlin.math.cos
import kotlinx.coroutines.launch
import kotlin.math.sin
import com.google.auto.factory.Provided
import com.google.auto.factory.AutoFactory
import io.matthewbradshaw.merovingian.ticker.Ticker
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.Dispatchers
import kotlin.random.Random

@TestingScope
@AutoFactory
class CubeSwarm(
  @Provided private val cubeFactory: CubeFactory,
  @Provided private val materials: Materials,
  @Provided private val ticker: Ticker,
  @Provided private val random: Random,
  @Provided @MainDispatcher private val mainDispatcher: CoroutineDispatcher
) : GameItem {

  private val prepared = Mutex()
  private lateinit var cubeMaterials: List<Material>
  private lateinit var timeOffsets: List<Int>
  private lateinit var swarm: Node

  override suspend fun prepare() {
    if (!this::cubeMaterials.isInitialized) {
      cubeMaterials = List(INDEPENDENCE_COUNT) { materials.createUnshadedGreen() }
    }

    if (!this::timeOffsets.isInitialized) {
      timeOffsets = List(INDEPENDENCE_COUNT) { (random.nextFloat() * MAX_TIME_OFFSET).toInt() }
    }

    if (!this::swarm.isInitialized) {
      swarm = Node("origin").apply {
        for (i in 0 until CUBE_COUNT) {
          val material = cubeMaterials[random.nextInt(INDEPENDENCE_COUNT - 1)]
          val cube = Cube(material)
          cube.prepare()
          attachChild(cube.representation())
          cube.representation().setLocalTranslation(generateRandomPositionOnSphere())
        }
      }
    }
  }

  override suspend fun representation(): Spatial {
    return swarm
  }

  override suspend fun logic(){
    coroutineScope {
      async(Dispatchers.Default) {
        ticker
          .pulseTotalS()
          .flowOn(Dispatchers.IO)
          .map { sin(time) }
          .map {
            for (i in 0 until INDEPENDENCE_COUNT) {
              val time = it + timeOffsets[i]
              val green = (GREEN_CHANNEL_CONSTANT_OFFSET + (GREEN_CHANNEL_AMPLITUDE_MODIFIER * sin(time))).toFloat()
              cubeMaterials[i].setColor("Color", ColorRGBA(0f, green, 0f, 1f))
            }
          }
          .onEach {
            println("tick")
            for (i in 0 until INDEPENDENCE_COUNT) {
              val time = it + timeOffsets[i]
              val green = (GREEN_CHANNEL_CONSTANT_OFFSET + (GREEN_CHANNEL_AMPLITUDE_MODIFIER * sin(time))).toFloat()
              cubeMaterials[i].setColor("Color", ColorRGBA(0f, green, 0f, 1f))
            }
          }.collect()
      }
    }
  }

  private fun generateRandomPositionOnSphere(): Vector3f {
    val radius = random.nextInt(MAX_RADIUS) + MIN_RADIUS
    val u = random.nextFloat()
    val v = random.nextFloat()
    val theta = _2PI * u
    val phi = acos((2 * v) - 1)
    val x = (radius * cos(theta) * sin(phi)).toFloat()
    val y = (radius * sin(theta) * sin(phi)).toFloat()
    val z = (radius * cos(phi)).toFloat()
    return Vector3f(x, y, z)
  }

  companion object {
    private const val CUBE_COUNT = 1000
    private const val MAX_TIME_OFFSET = 10
    private const val INDEPENDENCE_COUNT = 10
    private const val MAX_RADIUS = 1000
    private const val MIN_RADIUS = 10
    private const val GREEN_CHANNEL_CONSTANT_OFFSET = 0.6F
    private const val GREEN_CHANNEL_AMPLITUDE_MODIFIER = 0.4F
    private const val _2PI = 2 * 3.14f
  }
}