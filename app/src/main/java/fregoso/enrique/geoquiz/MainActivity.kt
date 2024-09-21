package fregoso.enrique.geoquiz

import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import android.widget.Button
import android.widget.Toast
import fregoso.enrique.geoquiz.databinding.ActivityMainBinding
import android.util.Log
import androidx.activity.viewModels

private const val TAG ="MainActivity"

class MainActivity : AppCompatActivity() {
    private lateinit var trueButton: Button
    private lateinit var falseButton: Button
    private lateinit var binding: ActivityMainBinding
    private val PreguntasViewmodel: PreguntasViewmodel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        //setContentView(R.layout.activity_main)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        Log.d(TAG, "Got a QuizViewModel: $PreguntasViewmodel")
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        trueButton = findViewById(R.id.true_button)
        falseButton = findViewById(R.id.false_button)

        binding.trueButton.setOnClickListener { view: View ->

            checkAnswer(true)

        }
        binding.nextButton.setOnClickListener{
            PreguntasViewmodel.moveToNext()
            updateQuestion()
        }
        binding.falseButton.setOnClickListener { view: View ->
            checkAnswer(false)

        }

        binding.nextButton.setOnClickListener { view: View ->
            PreguntasViewmodel.moveToNext()
            updateQuestion()
        }

        binding.prevButton.setOnClickListener { view: View ->
            PreguntasViewmodel.moveToPrev()
            updateQuestion()
        }

        updateQuestion()
        Log.d(TAG,"Pase por el metodo onCreate")
        Log.d(TAG,"Tengo un QuizviewModel: $PreguntasViewmodel")


    }
    private fun updateQuestion() {
        val questionTextResId = PreguntasViewmodel.currentQuestionText
        binding.run { questionTextView.setText(questionTextResId) }

    }

    private fun checkAnswer(userAnswer:Boolean){
        val correctAnswer = PreguntasViewmodel.currentQuestionAnswer


        val messageResId = if (userAnswer == correctAnswer){
            R.string.correct_toast
        }
        else{
            R.string.incorrect_toast
        }

        Toast.makeText(this,messageResId,Toast.LENGTH_LONG).show()
    }


}