package io.matthewbradshaw.klu.collections

import com.google.common.truth.Truth.assertThat
import org.jetbrains.annotations.TestOnly
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import kotlinx.coroutines.runBlocking

@RunWith(JUnit4::class)
class SimpleDoubleListBufferTest {

  private val buffer = SimpleDoubleListBuffer<String>()

  @Test
  fun switch_inactiveListBecomesActive() = runBlocking {
    val firstList = buffer.getActive()

    buffer.switch()

    assertThat(buffer.getActive() === firstList).isFalse()
  }

  @Test
  fun switchTwice_originalActiveListBecomesActiveAgain() = runBlocking {
    val firstList = buffer.getActive()

    buffer.switch()
    buffer.switch()

    assertThat(buffer.getActive() === firstList).isTrue()
  }

  @Test
  fun switchThreeTimes_originalInactiveBecomesActiveAgain() = runBlocking {
    buffer.switch()
    val firstList = buffer.getActive()

    buffer.switch()
    buffer.switch()

    assertThat(buffer.getActive() === firstList).isTrue()
  }
}