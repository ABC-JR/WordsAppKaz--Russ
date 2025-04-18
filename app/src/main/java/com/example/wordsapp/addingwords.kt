package com.example.wordsapp

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.ListView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class addingwords : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.addingword)

        val endlisword = findViewById<EditText>(R.id.englishside)
        val motherword = findViewById<EditText>(R.id.otherside)

        val button = findViewById<Button>(R.id.confirm_button)
        val listview = findViewById<ListView>(R.id.listView)
        val todos:MutableList<String> = mutableListOf()
        val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1 , todos)

        val words = intent.getSerializableExtra("WORDS") as Words



        listview.adapter = adapter
        val allwords:MutableMap<String , String> = words.show()

        for ((key, value) in allwords) {
            adapter.insert(key + value, 0) // Если вы хотите вставить значения в адаптер, используйте value
        }

        listview.setOnItemClickListener { parent, view, position, id ->
            val removeText = listview.getItemAtPosition(position).toString()
            adapter.remove(removeText)
            Toast.makeText(this, "You removed text $removeText"  , Toast.LENGTH_SHORT).show()
        }

        button.setOnClickListener{

            val entext = endlisword.text.toString().trim()
            val other = motherword.text.toString().trim()
            val text =  entext + " " +other

            if(entext!=""  && other!= ""){
                adapter.insert(text  , 0)
                words.addword(entext , other)
                val resultIntent = Intent()
                resultIntent.putExtra("WORDS", words) // Отправляем обновленный объект
                setResult(RESULT_OK, resultIntent) // Устанавливаем результат
                finish() // Закрываем активность

            }else{
                Toast.makeText(this, "The adding field cannot be empty", Toast.LENGTH_SHORT).show()

            }

        }

    }
}