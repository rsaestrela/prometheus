package com.github.rsaestrela.election

data class VoteRequest(var age: Int,
                       var gender: String,
                       var country: String,
                       var vote: String,
                       var votingFirstTime: Boolean)