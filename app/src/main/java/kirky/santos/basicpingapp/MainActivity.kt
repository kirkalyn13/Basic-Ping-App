package kirky.santos.basicpingapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.util.Log
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*
import java.io.BufferedReader
import java.io.InputStreamReader

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        Toast.makeText(this, "Start Testing.", Toast.LENGTH_SHORT).show()

        btnPing.setOnClickListener {
            val host: Editable = etIPAdd.getText()
            Toast.makeText(this, "Ping $host", Toast.LENGTH_SHORT).show()
            executePing(host)

        }


    }


    private fun executePing(host: Editable) {
        val listPingResponse: MutableList<String> = ArrayList()
        val packetCount: Editable = etPacketCount.getText() //declare number of packet counts
        //ListView Adapter
        val adapterList = ArrayAdapter(this,
            android.R.layout.simple_list_item_1, listPingResponse)
        try {
            val cmdPing = "ping -c $packetCount $host"
            val r = Runtime.getRuntime()
            val p = r.exec(cmdPing)
            val input = BufferedReader(InputStreamReader(p.inputStream))
            var inputLine: String
            while (input?.readLine().also { inputLine = it } != null) {
                listPingResponse.add(inputLine)
                lvResults.setAdapter(adapterList)
            }
            Toast.makeText(this@MainActivity, "Ping Test Done!", Toast.LENGTH_SHORT).show()

        } catch (e: Exception) {
            Log.d("MainActivity", "Error Occurred")
            Toast.makeText(this@MainActivity, "Error: " + e.message.toString(), Toast.LENGTH_LONG).show()
        }

    }


}