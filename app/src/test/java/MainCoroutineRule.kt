import com.hemraj.hackernews.util.DispatcherProvider
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.*
import org.junit.rules.TestWatcher
import org.junit.runner.Description

@ExperimentalCoroutinesApi
class MainCoroutineRule(
    private val testDispatcher: TestDispatcher = StandardTestDispatcher()
) : TestWatcher() {

    private val testScope = TestScope(testDispatcher)

    val testDispatcherProvider = object : DispatcherProvider {
        override val Default: CoroutineDispatcher = testDispatcher
        override val IO: CoroutineDispatcher = testDispatcher
        override val Main: CoroutineDispatcher = testDispatcher
    }

    override fun starting(description: Description) {
        super.starting(description)
        Dispatchers.setMain(testDispatcher)
    }

    override fun finished(description: Description) {
        super.finished(description)
        Dispatchers.resetMain()
    }

    fun runTest(block: suspend TestScope.() -> Unit) =
            testScope.runTest { block() }
}
