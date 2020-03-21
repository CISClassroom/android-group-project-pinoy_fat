package th.ac.kku.cis.mobileapp.myapplication2

class event {
    companion object Factory {
        fun create(): event = event()
    }
    var user_id: String? = null
    var event_name: String? = null
    var objectId: String? = null
    var done: Boolean? = false
}