package com.example.wordsapp

import java.io.Serializable

class Words:Serializable {
    private var words = mutableMapOf<String, String>()
    private var currentIndex = 0
    private var ruindex = 0

    fun addword(eng: String, ru: String) {
        words[eng] = ru
    }
    fun removeword(eng: String , ru: String){
        words.remove(eng , ru)
    }
    fun getword(eng: String): String? {
        return words[eng]
    }

    fun getEnglishWord(): String? {
        val keys = words.keys.toList() // Преобразуем ключи в список
        return if (currentIndex < keys.size) {
            keys[currentIndex++]
        } else {
            currentIndex = 0 // Сбрасываем индекс, если конец достигнут
            null // Уведомляем, что слов больше нет
        }
    }

    fun getRussianWords(): String? {
        val keys = words.values.toList()
        return if (ruindex < keys.size) {
            keys[ruindex++]
        } else {
            ruindex = 0
            return null
        }
    }


    fun show(): MutableMap<String, String> {
        return words
    }
}
