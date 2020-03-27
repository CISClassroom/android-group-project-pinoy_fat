package th.ac.kku.cis.mobileapp.myapplication2

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_main4.*

class Main4Activity : AppCompatActivity() {

    lateinit var mDatabase: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main4)

        mDatabase = FirebaseDatabase.getInstance().getReference("event_data")
        mDatabase.orderByKey().addListenerForSingleValueEvent(itemListener)

    }
    var itemListener: ValueEventListener = object : ValueEventListener {
        override fun onDataChange(p0: DataSnapshot) {
            val event_name = getIntent().getExtras()!!.getString("event_name")
            val items = p0.children.iterator()
            while (items.hasNext()){
                val currentItem = items.next()
                val map = currentItem.getValue() as HashMap<String, Any>
                if(map.get("event_name")==event_name){
                    name.text = map.get("event_name") as String
                    detail.text = map.get("event_detail") as String
                    side.text = "กิจกรรม"+map.get("event_side") as String
                    unit.text = "จำนวน "+map.get("event_unit") as String+" หน่วยกิจ"
                    start.text = map.get("date_start") as String
                    end.text = map.get("date_end") as String
                }
            }
        }
        override fun onCancelled(p0: DatabaseError) {

        }
    }
}
