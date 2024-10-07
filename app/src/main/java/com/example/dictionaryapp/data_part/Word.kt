package com.example.dictionaryapp.data_part

data class Word (
    val word : String,
    val phonetics : List<Phonetic>,
    val meanings: List<Meaning>
)

data class Phonetic(
    val text: String,
    val audio: String
)

data class Meaning(
    var partOfSpeech: String,
    var definitions: List<Definition>
)

data class Definition(
    val definition: String,
    val example: String?
)