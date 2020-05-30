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

package xmas.indicators

import xmas.math.NaN
import xmas.math.Num
import xmas.math.numOf
import xmas.series.Series

/**
 * Exponential Moving Average (EMA) indicator.
 */
internal class ExponentialMovingAverage(
    private val source: Series,
    private val n: Int,
    private val alpha: Num = numOf(2) / (n + 1)
) : Indicator(source) {

    override fun calculate(index: Int): Num {
        if (index == size - n)
            return sma(source, n)[index]
        if (index in 0 until size - n)
            return alpha * source[index] + (Num.ONE - alpha) * this[index + 1]
        return NaN
    }
}

/**
 * The exponential moving average, that places a greater weight and significance on the most recent data points.
 *
 * Uses the `alpha = 2 / (n + 1)`.
 *
 * **See:** [Investopedia](https://www.investopedia.com/terms/e/ema.asp),
 * [TradingView](https://www.tradingview.com/pine-script-reference/#fun_ema)
 *
 * @param source Series of values to process
 * @param n Number of bars (length)
 * @sample xmas.indicators.ExponentialMovingAverageTest.emaWithIntSeries
 * @see [sma]
 */
fun ema(source: Series, n: Int): Series = ExponentialMovingAverage(source, n)

/**
 * The exponential moving average used by RSI.
 *
 * Uses the `alpha = 1 / n`.
 *
 * **See:** [TradingView](https://www.tradingview.com/pine-script-reference/#fun_rma)
 *
 * @param source Series of values to process
 * @param n Number of bars (length)
 * @sample xmas.indicators.ExponentialMovingAverageTest.rmaWithIntSeries
 * @see [ema]
 */
fun rma(source: Series, n: Int): Series = ExponentialMovingAverage(source, n, Num.ONE.div(n))
