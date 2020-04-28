
import com.google.gson.Gson
import com.hemraj.hackernews.data.HackerNewsApi
import java.io.File


fun getSearchResult(): HackerNewsApi {
    return Gson().fromJson(GetJson()("json/NewsList.json"),
        HackerNewsApi::class.java)
}


internal class GetJson {
    operator fun invoke(path: String): String {
        val uri = this.javaClass.classLoader!!.getResource(path)
        val file = File(uri.path)
        return String(file.readBytes())
    }
}