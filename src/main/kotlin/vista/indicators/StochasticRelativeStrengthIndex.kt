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

/**
 * The stochastic RSI, which is primarily used for identifying overbought and oversold conditions.
 *
 * **See:** [Vista Docs](https://bulltimate.github.io/vista/#/momentum?id=stochastic-rsi-stochrsi)
 *
 * @param source Series of values to process
 * @param k Number of bars (length) for %K
 * @param d Number of bars (length) for %D moving average
 * @param rsiLength Number of bars (length) used by the [rsi]
 * @param stoLength Number of bars (length) used by the stochastic oscillator
 * @sample vista.indicators.StochasticRelativeStrengthIndexTest.withMarketData
 */
fun stochrsi(
    source: Series,
    k: Int = 3,
    d: Int = 3,
    rsiLength: Int = 14,
    stoLength: Int = 14
): Pair<Series, Series> {
    val rsi = rsi(source, rsiLength)
    val lowest = lowest(rsi, stoLength)
    val highest = highest(rsi, stoLength)
    val kLine = ((rsi - lowest) / (highest - lowest)) * 100
    val kLineSmoothed = sma(kLine, k)
    val dLine = sma(kLineSmoothed, d)
    return Pair(kLineSmoothed, dLine)
}

/**
 * The stochastic RSI, which is primarily used for identifying overbought and oversold conditions.
 *
 * **See:** [Vista Docs](https://bulltimate.github.io/vista/#/momentum?id=stochastic-rsi-stochrsi)
 *
 * @param k Number of bars (length) for %K
 * @param d Number of bars (length) for %D moving average
 * @param rsiLength Number of bars (length) used by the [rsi]
 * @param stoLength Number of bars (length) used by the stochastic oscillator
 * @sample vista.indicators.StochasticRelativeStrengthIndexTest.withMarketData
 */
fun Data.stochrsi(
    k: Int = 3,
    d: Int = 3,
    rsiLength: Int = 14,
    stoLength: Int = 14
) = stochrsi(close, k, d, rsiLength, stoLength)