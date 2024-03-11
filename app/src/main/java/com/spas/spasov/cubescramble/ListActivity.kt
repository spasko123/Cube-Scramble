package com.spas.spasov.cubescramble

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class ListActivity : AppCompatActivity() {

    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list)

        val recyclerView: RecyclerView = findViewById(R.id.rVList)
        val dataSet = listOf(
            "U2 L D' B2 R' U R2 F' L U D2 R2 D' B2 U2 D R2 B2 U2 R2",
            "U2 L D' B2 R' U R2 F' L U D2 R2 D' B2 U2 D R2 B2 U2 R2"
        )
        val adapter = ListScrambleAdapter(dataSet)
        recyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        recyclerView.adapter = adapter

    }
}