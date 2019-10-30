package c.m.shorturl.util

class StringWithTag(var value: Any, var key: String) {

    override fun toString(): String {
        return key
    }
}
