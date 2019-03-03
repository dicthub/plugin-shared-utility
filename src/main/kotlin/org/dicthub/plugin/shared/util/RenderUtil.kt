package org.dicthub.plugin.shared.util

import kotlinx.html.*
import kotlinx.html.stream.appendHTML

fun builtinSourceIcon(id: String) = "img/plugin/$id.png"

fun renderSource(container: TagConsumer<StringBuilder>, sourceUrl: String, icon: String) {
    container.div(classes = "translation-source") {
        small {
            +"Powered by "
            a(href = sourceUrl) {
                target = "_blank"
                img(src = icon)
            }
        }
    }
}

fun renderBugReport(container: TagConsumer<StringBuilder>, id: String, query: Query, exception: Throwable) {

    val bugTitle = "$id - [${query.getText()}]/${query.getFrom()}/${query.getTo()}"
    val bugContent = "message: ${exception.message}, cause: ```${exception.cause}```"
    val bugUrl = "https://github.com/willings/DictHub/issues/new?title=$bugTitle&body=$bugContent"
    container.div(classes = "translation-report-bug") {
        small {
            +"Report issue: "
            a(href = bugUrl) {
                target = "_blank"
                img { src = "img/icon-bug.16.png" }
            }
        }
    }
}

fun renderFailure(id: String, sourceUrl: String, query: Query, failure: Throwable): String {
    console.error("Failure from $id on ${query}", failure)

    val stringBuilder = StringBuilder()
    val container = stringBuilder.appendHTML()

    when (failure) {
        is TranslationNotFoundException -> {
            container.p(classes = "translation-failure alert alert-warning") {
                +"No result found for \"${query.getText()}\""
            }
        }

        is TranslationParsingFailureException -> {
            container.p(classes = "translation-failure alert alert-danger") {
                +"Parse query html failed \"${query.getText()}\""
            }
        }

        else -> {
            container.p(classes = "translation-failure alert alert-warning") {
                +(failure.message ?: "Error when calling service")
            }
        }
    }
    renderBugReport(container, id, query, failure)
    renderSource(container, sourceUrl, builtinSourceIcon(id))

    return stringBuilder.toString()
}