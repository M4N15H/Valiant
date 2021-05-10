package com.moaapps.valiant.pojo

import java.io.Serializable

data class Question(val question:String="", val hint:String="", var answer:String=""):Serializable {
}