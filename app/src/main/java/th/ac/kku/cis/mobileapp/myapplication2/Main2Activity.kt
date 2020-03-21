package th.ac.kku.cis.mobileapp.myapplication2

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_main2.*

class Main2Activity : AppCompatActivity() {

    lateinit var mDatabase: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)

        mDatabase = FirebaseDatabase.getInstance().reference

        register_registerBtn.setOnClickListener {
            val name = editText.text.toString()
            val id = editText3.text.toString().trim { it <= ' ' }
            val password1 = editText4.text.toString().trim { it <= ' ' }
            val password2 = editText5.text.toString().trim { it <= ' ' }

            if (id.isEmpty()||id.length<11) {
                Toast.makeText(this,"Please enter your student id.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            if (password1.isEmpty()||password2.isEmpty()) {
                Toast.makeText(this,"Please enter your pass.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            if (name.isEmpty()){
                Toast.makeText(this,"Please enter your name.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            mDatabase.child("student")
                .addListenerForSingleValueEvent(object : ValueEventListener {
                    override fun onDataChange(dataSnapshot: DataSnapshot) {
                        val student = dataSnapshot.children.iterator()
                        if(student.hasNext()){
                            while (student.hasNext()){
                                val studentItem = student.next().getValue() as HashMap<String, Any>
                                if (studentItem.get("id") != id){
                                    if (password1==password2){
                                        val student = th.ac.kku.cis.mobileapp.myapplication2.student.create()
                                        val newItem = mDatabase.child("student").push()
                                        student.id=id
                                        student.name=name
                                        student.pass=password1
                                        student.objectId = newItem.key
                                        student.sex = spinner.selectedItem.toString()
                                        newItem.setValue(student)
                                        Toast.makeText(this@Main2Activity,"Register Success!.", Toast.LENGTH_SHORT).show()
                                        finish()
                                    }else{
                                        Toast.makeText(this@Main2Activity,"Password does not match.", Toast.LENGTH_SHORT).show()
                                    }
                                }else{
                                    Toast.makeText(this@Main2Activity,"Student id registed", Toast.LENGTH_SHORT).show()
                                }
                            }
                        }
//                        else{
//                            Toast.makeText(this@MainActivity,"Wrong email or password.", Toast.LENGTH_SHORT).show()
//                        }
                    }
                    override fun onCancelled(databaseError: DatabaseError) {
                    }
                })
        }
        val spinner: Spinner = findViewById(R.id.spinner)
        val arrayList: ArrayList<String> = ArrayList()
        arrayList.add("ชาย")
        arrayList.add("หญิง")
        val arrayAdapter = ArrayAdapter(this, R.layout.support_simple_spinner_dropdown_item, arrayList)
        arrayAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item)
        spinner.adapter = arrayAdapter
    }
}
