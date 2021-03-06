/*
 * MIT License
 *
 * Copyright (c) 2020 Pablo Pallocchi
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 *
 */

package vista.indicators

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import vista.loadAmazonData
import vista.loadIndicatorData
import vista.math.na
import vista.math.numOf
import vista.series.seriesOf

internal class ExponentialMovingAverageTest {

    @Test
    fun emaWithIntSeries() {

        val series = seriesOf(1, 2, 3)

        val ema = ema(series, 2)

        assertThat(ema[0]).isEqualTo(numOf(2.5))   // current value
        assertThat(ema[1]).isEqualTo(numOf(1.5))   // previous value
        assertThat(ema[2]).isEqualTo(na)           // oldest value
    }

    @Test
    fun emaNestedWithIntSeries() {

        val series = seriesOf(1, 2, 3)

        val ema1 = ema(series, 2)
        val ema2 = ema(ema1, 2)

        assertThat(ema2[0]).isEqualTo(numOf(2.0))   // current value
        assertThat(ema2[1]).isEqualTo(na)           // previous value
        assertThat(ema2[2]).isEqualTo(na)           // oldest value
    }

    @Test
    fun emaWithMarketData() {

        val data = loadAmazonData()
        val expected = loadIndicatorData("ema.csv")

        val actual = data.ema(9)

        for (i in 0..99)
            assertThat(actual[i].round(2)).isEqualTo(expected[i][0])
    }

    @Test
    fun rmaWithIntSeries() {

        val series = seriesOf(1, 2, 3)

        val rma = rma(series, 2)

        assertThat(rma[0]).isEqualTo(numOf(2.25))  // current value
        assertThat(rma[1]).isEqualTo(numOf(1.5))   // previous value
        assertThat(rma[2]).isEqualTo(na)           // oldest value
    }
}