package com.veryfi.lens.w9.demo.logs

import org.json.JSONObject

data class Log(
    val title: String = "",
    val message: JSONObject = JSONObject()
)