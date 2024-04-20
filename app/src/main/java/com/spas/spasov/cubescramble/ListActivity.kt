package com.spas.spasov.cubescramble

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class ListActivity : AppCompatActivity() {

    lateinit var imgBack: ImageView
    lateinit var imgCopy: ImageView
    lateinit var txtCubeType: TextView
    var randomMove: String = ""
    var lastMove: String = ""
    var scramble: String = ""
    var cubeType: String = ""
    var scramblesList: MutableList<String> = mutableListOf()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.statusBarColor = resources.getColor(R.color.status_bar_color, theme)
        }

        imgBack = findViewById(R.id.imgBack)
        imgCopy = findViewById(R.id.imgCopy)
        txtCubeType = findViewById(R.id.txtCubeType)

        imgBack.setOnClickListener(){
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

        imgCopy.setOnClickListener(){
            copyScramblesToClipboard(scramblesList)
        }

        val cubeType = intent.getStringExtra("cubeType").toString()
        val numberOfScrambles = intent.getStringExtra("numberOfScrambles").toString()
        val numberOfScramblesInt = numberOfScrambles.toInt()

        for (i in 0 until numberOfScramblesInt){
            var currentScramble: String = checkCubeType(cubeType)
            scramblesList.add(currentScramble)
        }

        txtCubeType.text = cubeType

        val recyclerView: RecyclerView = findViewById(R.id.rVList)
        val adapter = ListScrambleAdapter(scramblesList)
        recyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        recyclerView.adapter = adapter
    }

    fun copyScramblesToClipboard(strings: List<String>) {
        val clipboardManager = getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        val stringBuilder = StringBuilder()

        strings.forEachIndexed { index, string ->
            stringBuilder.append("${index + 1}. $string\n")
        }

        val clip = ClipData.newPlainText("Strings", stringBuilder.toString())
        clipboardManager.setPrimaryClip(clip)

        // Optionally, you can show a toast message to indicate successful copying
        // Toast.makeText(this, "Strings copied to clipboard", Toast.LENGTH_SHORT).show()
    }
    fun checkCubeType(cubeType: String): String{
        if(cubeType == "3x3"){
            var scramble = generateRandomScrambleFor3x3()
            return scramble
        }
        else if(cubeType == "2x2"){
            var scramble = generateRandomScrambleFor2x2()
            return scramble
        }
        else if(cubeType == "4x4"){
            var scramble = generateRandomScrambleFor4x4()
            return scramble
        }
        else if(cubeType == "Skewb"){
            var scramble = generateRandomScrambleForSkewb()
            return scramble
        }
        else if(cubeType == "Pyraminx"){
            var scramble = generateRandomScrambleForPyraminx()
            return scramble
        }
        else{
            return scramble
        }
    }

    fun generateRandomScrambleFor3x3(): String {
        val moves = listOf("U", "D", "L", "R", "F", "B")
        val count = listOf("", "", "2")
        val stringBuilder = StringBuilder()

        for (i in 1..20) {
            randomMove = moves.random()

            while (randomMove == lastMove) {
                randomMove = moves.random()
            }
            lastMove = randomMove
            val count2 = count.random()

            val randomRotation = if (Math.random() < 0.5) "" else "'"
            stringBuilder.append("$randomMove$count2$randomRotation ")
        }
        return stringBuilder.toString().trim()
    }

    fun generateRandomScrambleFor4x4(): String {
        val moves = listOf("U", "D", "R", "L", "F", "B")
        val moves2 = listOf("U", "D", "R", "L", "F", "B", "Rw", "Lw", "Fw", "Bw", "Uw", "Dw")
        val count = listOf("", "", "2")
        val stringBuilder = StringBuilder()

        for (i in 1..20) {
            randomMove = moves.random()

            while (randomMove == lastMove) {
                randomMove = moves.random()
            }
            lastMove = randomMove
            val count2 = count.random()

            val randomRotation = if (Math.random() < 0.5) "" else "'"
            stringBuilder.append("$randomMove$count2$randomRotation ")
        }
        for (i in 1..25){
            randomMove = moves2.random()

            while (randomMove == lastMove) {
                randomMove = moves.random()

            }
            lastMove = randomMove
            val count2 = count.random()

            val randomRotation = if (Math.random() < 0.5) "" else "'"
            stringBuilder.append("$randomMove$count2$randomRotation ")
        }
        return stringBuilder.toString().trim()
    }

    fun generateRandomScrambleForSkewb(): String {
        val moves = listOf("U", "L", "R", "B")
        val stringBuilder = StringBuilder()

        for (i in 1..9) {
            randomMove = moves.random()

            while (randomMove == lastMove) {
                randomMove = moves.random()
            }
            lastMove = randomMove

            val randomRotation = if (Math.random() < 0.5) "" else "'"
            stringBuilder.append("$randomMove$randomRotation ")
        }
        return stringBuilder.toString().trim()
    }

    fun generateRandomScrambleForPyraminx(): String {
        val moves = listOf("U", "L", "R", "B")
        val moves2 = listOf("u", "l", "r", "b")
        val randomNumber = listOf(2, 3)
        var lastMove2: String = "wd"
        val stringBuilder = StringBuilder()
        val myList: MutableList<String> = mutableListOf()

        for (i in 1..9) {
            randomMove = moves.random()

            while (randomMove == lastMove) {
                randomMove = moves.random()
            }
            lastMove = randomMove

            val randomRotation = if (Math.random() < 0.5) "" else "'"
            stringBuilder.append("$randomMove$randomRotation ")
        }
        for (i in 1..randomNumber.random()){
            var randomMove2 = moves2.random()
            while (randomMove2 == lastMove2 || myList.contains(randomMove2)) {
                randomMove2 = moves2.random()
            }
            lastMove2 = randomMove2
            myList.add(lastMove2)
            val randomRotation = if (Math.random() < 0.5) "" else "'"
            stringBuilder.append("$randomMove2$randomRotation ")
        }
        return stringBuilder.toString().trim()
    }

    fun generateRandomScrambleFor2x2(): String {
        val moves = listOf("U", "R", "F")
        val count = listOf("", "2", "")
        val stringBuilder = StringBuilder()

        for (i in 1..10) {
            randomMove = moves.random()

            while (randomMove == lastMove) {
                randomMove = moves.random()
            }
            lastMove = randomMove
            val count2 = count.random()

            val randomRotation = if (Math.random() < 0.5) "" else "'"
            stringBuilder.append("$randomMove$count2$randomRotation ")
        }
        return stringBuilder.toString().trim()
    }
}