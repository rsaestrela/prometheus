package com.github.rsaestrela.poi

data class LogRequest(var id: String,
                      var numberOfPeople: Int,
                      var temperature: Double,
                      var wind: Double,
                      var precipitation: Double)