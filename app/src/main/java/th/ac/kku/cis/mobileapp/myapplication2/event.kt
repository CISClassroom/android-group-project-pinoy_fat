package th.ac.kku.cis.mobileapp.myapplication2

class event {
    companion object Factory {
        fun create(): event = event()
    }
    var user_id: String? = null
    var event_name: String? = null
    var objectId: String? = null
}
class event_data {
    companion object Factory {
        fun create(): event_data = event_data()
    }
    var event_name: String? = null
    var event_detail: String? = null
    var objectId: String? = null
    var date_start: String? = null
    var date_end: String? = null
}