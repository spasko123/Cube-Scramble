package com.spas.spasov.cubescrambleapp

import android.animation.Animator
import android.animation.ObjectAnimator
import android.app.Dialog
import android.content.Intent
import android.media.MediaPlayer
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout

class MainActivity : AppCompatActivity() {

    var randomMove: String = ""
    var lastMove: String = ""
    var scramble: String = ""
    var cubeType: String = "3x3"
    lateinit var txtScramble: TextView
    lateinit var btnNewScramble: Button
    lateinit var conLayoutNavBar: ConstraintLayout
    lateinit var imgArrowDropDown: ImageView
    lateinit var txtCubeType: TextView
    lateinit var imgList: ImageView
    var mediaPlayer: MediaPlayer? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.statusBarColor = resources.getColor(R.color.status_bar_color, theme)
        }


        txtScramble = findViewById(R.id.txtScramble)
        btnNewScramble = findViewById(R.id.btnNewScramble)
        conLayoutNavBar = findViewById(R.id.conLayoutNavBar)
        imgArrowDropDown = findViewById(R.id.imgArrowDropDown)
        txtCubeType = findViewById(R.id.txtCubeType)
        imgList = findViewById(R.id.imgList)

        imgList.setOnClickListener(){
            val dialog = Dialog(this)
            dialog.setContentView(R.layout.dialog_list)
            val edtxScrambles = dialog.findViewById<EditText>(R.id.edtxScrambles)
            val txtCubeType = dialog.findViewById<TextView>(R.id.txtCubeType)
            val btnGenerateScrambles = dialog.findViewById<Button>(R.id.btnGenerateScrambles)
            txtCubeType.text = "Current cube type: ${cubeType}"

            btnGenerateScrambles.setOnClickListener(){
                val numberOfScrambles: String = edtxScrambles.text.toString()
                if(numberOfScrambles.isEmpty()){
                    edtxScrambles.error = "You haven't typed anything"
                }
                else{
//                    val intNumberOfScrambles: Int = numberOfScrambles.toInt()
//                    for (i in 0 until intNumberOfScrambles){
//                       val currentScramble: String = checkCubeType(cubeType)
//                        scramblesList.add(currentScramble)
//                    }
                    val intent = Intent(this, ListActivity::class.java)
//                    intent.putStringArrayListExtra("scramblesList", ArrayList(scramblesList))
                    intent.putExtra("cubeType", cubeType)
                    intent.putExtra("numberOfScrambles", numberOfScrambles)
                    startActivity(intent)
                }
            }


            dialog.show()
        }

        scramble = generateRandomScrambleFor3x3()
        txtScramble.text = scramble

        btnNewScramble.setOnClickListener(){
            scramble = checkCubeType(cubeType)
            animateTextChange(scramble)
        }

        conLayoutNavBar.setOnClickListener(){
            showCustomDialog()
        }

    }

    fun showCustomDialog() {
        val customDialog = Dialog(this)
        customDialog.setContentView(R.layout.dialog_menu)
        val txt2x2: TextView = customDialog.findViewById(R.id.txt2x2)
        val txt3x3: TextView = customDialog.findViewById(R.id.txt3x3)
        val txt4x4: TextView = customDialog.findViewById(R.id.txt4x4)
        val txtSkewb: TextView = customDialog.findViewById(R.id.txtSkewb)
        val txtPyra: TextView = customDialog.findViewById(R.id.txtPyra)

        txt2x2.setOnClickListener() {
            cubeType = "2x2"
            scramble = checkCubeType(cubeType)
            animateTextChange(scramble)
            customDialog.dismiss()
            txtCubeType.text = "2x2"
            imgArrowDropDown.setImageResource(R.drawable.down_arrow)
        }

        txt3x3.setOnClickListener() {
            cubeType = "3x3"
            scramble = checkCubeType(cubeType)
            animateTextChange(scramble)
            customDialog.dismiss()
            txtCubeType.text = "3x3"
            imgArrowDropDown.setImageResource(R.drawable.down_arrow)
        }
        txt4x4.setOnClickListener() {
            cubeType = "4x4"
            scramble = checkCubeType(cubeType)
            animateTextChange(scramble)
            customDialog.dismiss()
            txtCubeType.text = "4x4"
            imgArrowDropDown.setImageResource(R.drawable.down_arrow)
        }
        txtSkewb.setOnClickListener() {
            cubeType = "Skewb"
            scramble = checkCubeType(cubeType)
            animateTextChange(scramble)
            customDialog.dismiss()
            txtCubeType.text = "Skewb"
            imgArrowDropDown.setImageResource(R.drawable.down_arrow)
        }

        txtPyra.setOnClickListener() {
            cubeType = "Pyraminx"
            scramble = checkCubeType(cubeType)
            animateTextChange(scramble)
            customDialog.dismiss()
            txtCubeType.text = "Pyraminx"
            imgArrowDropDown.setImageResource(R.drawable.down_arrow)
        }

        customDialog.setOnShowListener {
            imgArrowDropDown.setImageResource(R.drawable.up_arrow)
        }

        customDialog.setOnDismissListener {
            imgArrowDropDown.setImageResource(R.drawable.down_arrow)
        }

        customDialog.show()
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

            var randomRotation = ""

            // Generate a random number between 0 and 2
            val randomNumber = (0..2).random()

            // Set randomRotation based on the random number
            when (randomNumber) {
                // 1/3 chance for prime (')
                0 -> randomRotation = "'"
                // 2/3 chance for empty string
                1 -> randomRotation = ""
            }


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
    private fun animateTextChange(newText: String) {
        val fadeOut = ObjectAnimator.ofFloat(txtScramble, "alpha", 1f, 0f)
        fadeOut.duration = 200 // in milliseconds

        fadeOut.addListener(object : android.animation.AnimatorListenerAdapter() {
            override fun onAnimationEnd(animation: Animator) {
                txtScramble.text = newText
                val fadeIn = ObjectAnimator.ofFloat(txtScramble, "alpha", 0f, 1f)
                fadeIn.duration = 200 // in milliseconds
                fadeIn.start()
            }
        })

        fadeOut.start()
    }

    private fun playSound() {
        // Release any existing MediaPlayer resources
        mediaPlayer?.release()

        // Create a new MediaPlayer instance
        mediaPlayer = MediaPlayer.create(this, R.raw.tap_sound) // Replace 'your_mp3_file' with the actual filename (without the extension)

        // Start playing the sound
        mediaPlayer?.start()

        // Optionally, you can set an OnCompletionListener to perform actions when the sound finishes playing
        mediaPlayer?.setOnCompletionListener {
            // Perform actions after the sound finishes playing (if needed)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        // Release MediaPlayer resources when the activity is destroyed
        mediaPlayer?.release()
    }

}