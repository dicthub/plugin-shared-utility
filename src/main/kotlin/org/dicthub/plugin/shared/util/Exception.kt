package org.dicthub.plugin.shared.util

import kotlin.js.json

open class TranslationException(message: String? = null, cause: Throwable? = null) : Throwable(message, cause) {

    constructor(cause: Throwable?) : this(null, cause)

    var query: Query = json()
    var providerId: String = ""
    var requestUrl: String = ""
    var manualCheckUrl: String? = null
}

class TranslationHttpFailureException: TranslationException() {

    override val message = "Failed to fetch $requestUrl due to http failure."
}

class TranslationNotFoundException : TranslationException() {

    override val message = "$query not found from $providerId, $requestUrl"
}

class TranslationParsingFailureException : TranslationException() {

    var rawContent: String? = null

    override val message = "Failed to parse content for $query by $providerId, $requestUrl"
}