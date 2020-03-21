package th.ac.kku.cis.mobileapp.myapplication2

class student {
    companion object Factory {
        fun create(): student = student()
    }
    var id: String? = null
    var name: String? = null
    var objectId: String? = null
    var sex: String? = null
    var pass: String? = null
}