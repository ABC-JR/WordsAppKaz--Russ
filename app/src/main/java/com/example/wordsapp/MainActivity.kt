package com.example.wordsapp

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageButton
import android.widget.LinearLayout
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import org.json.JSONArray

import kotlin.random.Random

class MainActivity : AppCompatActivity() {


    var words = Words()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        val json = this.assets.open("dictinary.json").bufferedReader().use{
            it.readText()
        }

        val jsonArray = JSONArray(json)
        for(i in 0 until jsonArray.length()){
            val obj  = jsonArray.getJSONObject(i)
            words.addword(obj.getString("rus")  , obj.getString("kaz"))
        }


        enableEdgeToEdge()

        val question: String = creatinalPage()

        val exit = findViewById<ImageButton>(R.id.Exit)
        exit.setOnClickListener{
            finish()
        }

    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_CODE_ADD_WORD && resultCode == RESULT_OK) {
            val updatedWords = data?.getSerializableExtra("WORDS") as? Words
            if (updatedWords != null) {
                words = updatedWords // Обновляем объект words
            }
        }
    }

    companion object {
        const val REQUEST_CODE_ADD_WORD = 1
    }

    @SuppressLint("WrongViewCast")
    private fun chekingPage(question: String, correctAnswer: String) {
        val option1 = findViewById<TextView>(R.id.option1)
        val option2 = findViewById<TextView>(R.id.option2)
        val option3 = findViewById<TextView>(R.id.option3)
        val option4 = findViewById<TextView>(R.id.option4)
        val option1_layout = findViewById<LinearLayout>(R.id.option1layout)
        val option2_layout = findViewById<LinearLayout>(R.id.option2layout)
        val option3_layout = findViewById<LinearLayout>(R.id.option3layout)
        val option4_layout = findViewById<LinearLayout>(R.id.option4layout)
        val skip:Button = findViewById(R.id.skipbutton)
        var oneattempt:Boolean = true
        val continuebutton = findViewById<LinearLayout>(R.id.Continue)
        val realcontinuebt = findViewById<Button>(R.id.continuebt)


        val wrongshow = findViewById<LinearLayout>(R.id.Wrong)
        val wrongButton = findViewById<Button>(R.id.continuebtwrong)


        val add  = findViewById<Button>(R.id.addingword)


        add.setOnClickListener{
            val intent = Intent(this , addingwords::class.java)
            intent.putExtra("WORDS" , words)
            startActivity(intent)

        }




        option1_layout.setOnClickListener {
            if(oneattempt){
                if (option1.text == correctAnswer) {
                    option1.setTextColor(Color.parseColor("#0EAD69")) // Зеленый
                    skip.visibility = View.GONE
                    continuebutton.visibility = View.VISIBLE

                } else {
                    option1.setTextColor(Color.parseColor("#FA4169")) // Красный
                    skip.visibility = View.GONE
                    wrongshow.visibility= View.VISIBLE
                }
                oneattempt = false
            }

        }

        option2_layout.setOnClickListener {
            if(oneattempt) {

                if (option2.text == correctAnswer) {
                    option2.setTextColor(Color.parseColor("#0EAD69"))
                    skip.visibility = View.GONE
                    continuebutton.visibility = View.VISIBLE
                } else {
                    option2.setTextColor(Color.parseColor("#FA4169"))
                    skip.visibility = View.GONE
                    wrongshow.visibility= View.VISIBLE
                }
                oneattempt = false
            }
        }

        option3_layout.setOnClickListener {
            if(oneattempt) {
                if (option3.text == correctAnswer) {
                    option3.setTextColor(Color.parseColor("#0EAD69"))
                    skip.visibility = View.GONE
                    continuebutton.visibility = View.VISIBLE
                } else {
                    option3.setTextColor(Color.parseColor("#FA4169"))
                    skip.visibility = View.GONE
                    wrongshow.visibility= View.VISIBLE
                }
                oneattempt = false
            }
        }

        option4_layout.setOnClickListener {
            if(oneattempt) {
                if (option4.text == correctAnswer) {
                    option4.setTextColor(Color.parseColor("#0EAD69"))
                    skip.visibility = View.GONE
                    continuebutton.visibility = View.VISIBLE
                } else {
                    option4.setTextColor(Color.parseColor("#FA4169"))
                    skip.visibility = View.GONE
                    wrongshow.visibility= View.VISIBLE
                }
                oneattempt = false
            }
        }


        skip.setOnClickListener{
            creatinalPage()
        }
        realcontinuebt.setOnClickListener {
            creatinalPage()
        }
        wrongButton.setOnClickListener{
            creatinalPage()
        }



    }

    private fun creatinalPage(): String {
        setContentView(R.layout.activity_page)

        // Получаем случайное слово на английском
        val question: String = words.getEnglishWord().toString()

        // Устанавливаем вопрос в TextView
        val realquestion: TextView = findViewById(R.id.TextView)
        realquestion.text = question

        // Получаем правильный перевод для этого слова
        val correctAnswer: String = words.getword(question).toString()

        // Создаем список для всех вариантов ответа
        val options = mutableListOf<String>(correctAnswer)

        // Добавляем случайные неверные ответы
        while (options.size < 4) {
            val randomWord = words.getEnglishWord() // Получаем другое слово
            val randomTranslation = words.getword(randomWord.toString())

            if (randomTranslation != correctAnswer && !options.contains(randomTranslation)) {
                options.add(randomTranslation.toString())
            }
        }

        // Перемешиваем варианты ответов
        options.shuffle()

        // Присваиваем значения опциям на экране
        findViewById<TextView>(R.id.option1).text = options[0]
        findViewById<TextView>(R.id.option2).text = options[1]
        findViewById<TextView>(R.id.option3).text = options[2]
        findViewById<TextView>(R.id.option4).text = options[3]

        // Передаем правильный ответ в функцию chekingPage
        chekingPage(question, correctAnswer)


        return question
    }
}
