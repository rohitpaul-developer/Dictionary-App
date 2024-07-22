package eu.tutorials.dictionaryapp

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {

    private val _meaningState = mutableStateOf(MeaningState())
    val meaningState = _meaningState

    fun fetchMeanings(word : String){
        viewModelScope.launch {
            try {
                val response = dictionaryService.getMeanings(word)
                _meaningState.value = _meaningState.value.copy(
                    result = response,
                    loading = false,
                    error = null
                )
            }catch (e : Exception){
                _meaningState.value = _meaningState.value.copy(
                    loading = true,
                    error = "${e.message}"
                )
            }
        }
    }



    data class MeaningState(
        val result: List<ResultsItem> = emptyList(),
        val loading: Boolean = false,
        val error: String ?= null
    )

}