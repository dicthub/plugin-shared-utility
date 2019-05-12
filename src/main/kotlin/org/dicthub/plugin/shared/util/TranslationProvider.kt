package org.dicthub.plugin.shared.util


import kotlin.js.Json
import kotlin.js.Promise
import kotlin.js.json

typealias Query = Json

fun Query.getText(): String = attribute("text")
fun Query.getFrom(): String = attribute("from")
fun Query.getTo(): String = attribute("to")

fun createQuery(text: String, from: String, to: String) = json(
        "text" to text,
        "from" to from,
        "to" to to
)

typealias Meta = Json
fun Meta.getName(): String = attribute("name")
fun Meta.getDescription(): String? = attribute("description")
fun Meta.getSource(): String? = attribute("source")
fun Meta.getSourceUrl(): String? = attribute("sourceUrl")
fun Meta.getAuthor(): String? = attribute("author")
fun Meta.getAuthorUrl(): String? = attribute("authorUrl")
fun Meta.getOptions(): MetaOptionsConfig? = attribute("options")

typealias MetaOptionsConfig = Json
fun MetaOptionsConfig.getOption(name: String): MetaOptionConfig = attribute(name)

typealias MetaOptionConfig = Json
fun MetaOptionConfig.getType(): String = attribute("type")
fun MetaOptionConfig.getOptionDescription(): String = attribute("description")
fun MetaOptionConfig.getDefault(): String? = attribute("default")

typealias MetaOptions = Json

fun createMeta(name: String, description: String, source: String, sourceUrl: String, author: String, authorUrl: String, options: MetaOptionsConfig = json()) = json(
        "name" to name,
        "description" to description,
        "source" to source,
        "sourceUrl" to sourceUrl,
        "author" to author,
        "authorUrl" to authorUrl,
        "options" to options
)

fun createMetaOptionConfig(type: String, description: String?, default: String?) = json("type" to type).apply {
    description?.let { this["description"] = it }
    default?.let { this["default"] = it }
}

interface TranslationProvider {

    @JsName("id")
    fun id(): String

    @JsName("meta")
    fun meta(): Meta

    @JsName("canTranslate")
    fun canTranslate(query: Query): Boolean

    @JsName("translate")
    fun translate(query: Query): Promise<String>

    @JsName("updateOptions")
    fun updateOptions(options: MetaOptions) { console.log("Update options for ${id()}", options) }
}

@Suppress("UNCHECKED_CAST")
fun <T> Json.attribute(name: String): T {
    return this[name] as T
}