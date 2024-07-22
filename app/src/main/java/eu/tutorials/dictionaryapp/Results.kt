package eu.tutorials.dictionaryapp

class Results : ArrayList<ResultsItem>()

data class ResultsItem(
    val meanings: List<Meaning>?,
    val phonetic: String?,
    val word: String?
)

data class Meaning(
    val definitions: List<Definition>?,
    val partOfSpeech: String?
)


data class Definition(
    val definition: String?,
    val example: String?
)