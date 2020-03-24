package th.ac.kku.cis.mobileapp.myapplication2

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ListView
import android.widget.Toast
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_main3.*

class Main3Activity : AppCompatActivity() {

    lateinit var mDatabase: DatabaseReference

    var event_list: MutableList<event>? = null
    lateinit var adapter: event_adapter
    private var listViewItems: ListView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main3)

        val id = getIntent().getExtras()!!.getString("id")
        val name = getIntent().getExtras()!!.getString("name")
        val sex = getIntent().getExtras()!!.getString("sex")

        textView.text=name
        textView2.text=id
        if(sex == "ชาย"){
            imageView.setImageResource(R.drawable.m)
        }else if (sex == "หญิง"){
            imageView.setImageResource(R.drawable.w)
        }

        mDatabase = FirebaseDatabase.getInstance().reference
        listViewItems = findViewById<View>(R.id.list_view) as ListView

        event_list = mutableListOf<event>()
        adapter = event_adapter(this, event_list!!)
        listViewItems!!.setAdapter(adapter)
        mDatabase.orderByKey().addListenerForSingleValueEvent(itemListener)

        list_view.setOnItemClickListener{parent, view, position, id ->
            val selectedItem = parent.getItemAtPosition(position) as event
            Toast.makeText(this,selectedItem.event_name, Toast.LENGTH_SHORT).show()
            val intent = Intent(this, Main4Activity::class.java)
            intent.putExtra("event_name",selectedItem.event_name)
            startActivity(intent)
        }
    }
    var itemListener: ValueEventListener = object : ValueEventListener {

        override fun onDataChange(dataSnapshot: DataSnapshot) {
            // call function
            addDataToList(dataSnapshot.child("event_item"))
        }

        override fun onCancelled(databaseError: DatabaseError) {
            // Getting Item failed, display log a message
            Log.w("MainActivity", "loadItem:onCancelled", databaseError.toException())
        }
    }
    fun addDataToList(dataSnapshot: DataSnapshot) {
        val id = getIntent().getExtras()!!.getString("id")
        val items = dataSnapshot.children.iterator()
        while (items.hasNext()) {
            val currentItem = items.next()
            val map = currentItem.getValue() as HashMap<String, Any>
            val todoItem = event.create()
            todoItem.user_id = map.get("user_id") as String?
            if (todoItem.user_id == id && map.get("done") == true){
                todoItem.objectId = currentItem.key
//                    todoItem.user_id = map.get("user_id") as String?
                todoItem.event_name = map.get("event_name") as String?
                event_list!!.add(todoItem)
            }
        }
        adapter.notifyDataSetChanged()
//        }
    }
}
