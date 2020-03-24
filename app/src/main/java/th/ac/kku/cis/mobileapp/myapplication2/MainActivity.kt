package th.ac.kku.cis.mobileapp.myapplication2

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    lateinit var mDatabase: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mDatabase = FirebaseDatabase.getInstance().reference

        button2.setOnClickListener {
            val id = user.text.toString().trim { it <= ' ' }
            val password = pass.text.toString().trim { it <= ' ' }

            if (id.isEmpty()||id.length<11) {
                Toast.makeText(this,"Please enter your Student id.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            if (password.isEmpty()) {
                Toast.makeText(this,"Please enter your password.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            mDatabase.child("student")
                .addListenerForSingleValueEvent(object : ValueEventListener {
                    override fun onDataChange(dataSnapshot: DataSnapshot) {
                        val student = dataSnapshot.children.iterator()
                        var i=0
                        if(student.hasNext()){
                            while (student.hasNext()){
                                val studentItem = student.next().getValue() as HashMap<String, Any>
                                if (studentItem.get("id")==id && studentItem.get("pass") == password){
                                    i=0
                                    val intent = Intent(this@MainActivity, Main3Activity::class.java)
                                    intent.putExtra("id",studentItem.get("id") as String)
                                    intent.putExtra("sex",studentItem.get("sex") as String)
                                    intent.putExtra("name",studentItem.get("name") as String)
                                    startActivity(intent)
                                    user.text=null
                                    pass.text=null
                                    break
                                }else{
                                    i=1
                                }
                            }
                        }
                        if(i!=0){
                            Toast.makeText(this@MainActivity,"Wrong email or password.", Toast.LENGTH_SHORT).show()
                        }
//                        else{
//                            Toast.makeText(this@MainActivity,"Wrong email or password.", Toast.LENGTH_SHORT).show()
//                        }
                    }
                    override fun onCancelled(databaseError: DatabaseError) {
                    }
                })
        }
        button.setOnClickListener {
            startActivity(
                Intent(
                    this, Main2Activity::class.java
                )
            )
        }
    }
}
