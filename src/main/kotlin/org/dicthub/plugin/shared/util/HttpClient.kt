package org.dicthub.plugin.shared.util

import org.w3c.xhr.XMLHttpRequest
import kotlin.js.Promise

interface HttpAsyncClient {

    fun get(url: String): Promise<String>

    fun post(url: String, header: Map<String, String>, body: String): Promise<String>
}

object AjaxHttpClient : HttpAsyncClient {

    override fun get(url: String): Promise<String> {

        return Promise { resolve, reject ->
            val xhr = XMLHttpRequest()
            xhr.addEventListener("load", {
                if (xhr.status.toInt() == 200) {
                    resolve(xhr.responseText)
                } else {
                    reject(RuntimeException("${xhr.status} ${xhr.statusText} when getting $url"))
                }
            })
            xhr.addEventListener("error", {
                reject(RuntimeException("Failed to load $url."))
            })
            xhr.open("GET", url)
            xhr.send()
        }
    }


    override fun post(url: String, header: Map<String, String>, body: String): Promise<String> {

        return Promise { resolve, reject ->

            val xhr = XMLHttpRequest()
            xhr.addEventListener("load", {
                if (xhr.status.toInt() == 200) {
                    resolve(xhr.responseText)
                } else {
                    reject(RuntimeException("${xhr.status} ${xhr.statusText} when posting $url"))
                }
            })
            xhr.addEventListener("error", {
                reject(RuntimeException("Failed to load $url."))
            })
            xhr.open("POST", url)
            header.forEach { xhr.setRequestHeader(it.key, it.value) }
            xhr.send(body)
        }
    }
}
