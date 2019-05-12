package org.dicthub.plugin.shared.util

import kotlin.js.Promise

inline fun <T, R> Promise<T>.andThen(crossinline exec: (T) -> Promise<R>) = Promise<R> { resolve, reject ->
    this.then {
        exec(it).then(resolve).catch(reject)
    }.catch(reject)
}

inline fun <T, R> Promise<T>.convert(crossinline func: (T) -> R) = Promise<R> { resolve, reject ->
    this.then {
        resolve(func(it))
    }.catch(reject)
}

@Suppress("UNCHECKED_CAST")
inline fun <A, B> allPromises(pA: Promise<A>, pB: Promise<B>): Promise<Pair<A, B>> = Promise { resolve, reject ->
    Promise.all(arrayOf(pA, pB)).then {
        resolve(Pair(it[0] as A, it[1] as B))
    }.catch(reject)
}

@Suppress("UNCHECKED_CAST")
inline fun <A, B, C> allPromises(pA : Promise<A>, pB: Promise<B>, pC: Promise<C>): Promise<Triple<A, B, C>> = Promise { resolve, reject ->
    Promise.all(arrayOf(pA, pB, pC)).then {
        resolve(Triple(it[0] as A, it[1] as B, it[2] as C))
    }.catch(reject)
}
