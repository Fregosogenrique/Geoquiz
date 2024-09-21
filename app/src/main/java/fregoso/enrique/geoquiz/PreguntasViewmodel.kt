package fregoso.enrique.geoquiz

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel

private const val TAG = "PreguntasViewModel"
const val CURRENT_INDEX_KEY = "CURRENT_INDEX_KEY"
class PreguntasViewmodel(private val savedStateHandle : SavedStateHandle) : ViewModel() {
    init{
        Log.d(TAG, "ViewModel instance created")
    }
    override fun onCleared(){
        super.onCleared()
        Log.d(TAG, "ViewModel instance about to be destroyed")
    }
    private val questionBank = listOf(
        Pregunta(R.string.question_australia, true),
        Pregunta(R.string.question_oceans, true),
        Pregunta(R.string.question_mideast, false),
        Pregunta(R.string.question_africa, false),
        Pregunta(R.string.question_americas, true),
        Pregunta(R.string.question_asia, true))
    private var currentIndex
        get() = savedStateHandle.get(CURRENT_INDEX_KEY) ?: 0
        set(value) = savedStateHandle.set(CURRENT_INDEX_KEY, value)

    val currentQuestionAnswer: Boolean
        get() = questionBank[currentIndex].resuesta
    val currentQuestionText: Int
        get() = questionBank[currentIndex].textResId

    fun moveToNext() {
        currentIndex = (currentIndex + 1) % questionBank.size
    }

    fun moveToPrev() {
        currentIndex = if (currentIndex == 0) {
            questionBank.size - 1
        } else {
            (currentIndex - 1) % questionBank.size
        }
    }

}