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

import vista.data.Data
import vista.series.Series
import kotlin.math.sqrt

/**
 * The hull moving average of [source] for [n] bars back, which reduces lag of traditional moving averages.
 *
 * `hma = wma(2 * wma(n/2) − wma(n)), sqrt(n))`
 *
 * **See:** [Vista Docs](https://bulltimate.github.io/vista/#/trend?id=hull-moving-average-hma)
 *
 * @param source Series of values to process
 * @param n Number of bars (length)
 * @sample vista.indicators.HullMovingAverageTest.withIntSeries
 * @see [wma]
 */
fun hma(source: Series, n: Int = 9): Series {
    val wma1 = wma(source, n / 2) * 2
    val wma2 = wma(source, n)
    val length = sqrt(n.toDouble()).toInt()
    return wma(wma1 - wma2, length)
}

/**
 * The hull moving average of close price for [n] bars back, which reduces lag of traditional moving averages.
 *
 * `hma = wma(2 * wma(n/2) − wma(n)), sqrt(n))`
 *
 * **See:** [Vista Docs](https://bulltimate.github.io/vista/#/trend?id=hull-moving-average-hma)
 *
 * @param n Number of bars (length)
 * @sample vista.indicators.HullMovingAverageTest.withMarketData
 * @see [wma]
 */
fun Data.hma(n: Int = 9) = hma(close, n)