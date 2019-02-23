package org.dicthub.plugin.shared.util

import kotlin.js.Promise

inline fun <T, R> Promise<T>.next(crossinline exec: (T) -> Promise<R>) = Promise<R> { resolve, reject ->
    this.then {
        exec(it).then(resolve).catch(reject)
    }.catch(reject)
}

inline fun<T, R> Promise<T>.map(crossinline func: (T) -> R) = Promise<R> { resolve, reject ->
    this.then {
        resolve(func(it))
    }.catch(reject)
}