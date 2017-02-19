package co.infinum.rxpokemon

import io.reactivex.Observable
import io.reactivex.Single
import org.mockito.configuration.DefaultMockitoConfiguration
import org.mockito.internal.stubbing.defaultanswers.ReturnsEmptyValues
import org.mockito.invocation.InvocationOnMock
import org.mockito.stubbing.Answer

/**
 * @see https://medium.com/@fabioCollini/testing-asynchronous-rxjava-code-using-mockito-8ad831a16877#.ukd2jdc34
 */
class MockitoConfig : DefaultMockitoConfiguration() {

    override fun getDefaultAnswer(): Answer<Any> {
        return object : ReturnsEmptyValues() {
            override fun answer(inv: InvocationOnMock): Any {
                val type = inv.method.returnType
                if (type.isAssignableFrom(Observable::class.java)) {
                    return Observable.error<Any>(createException(inv))
                } else if (type.isAssignableFrom(Single::class.java)) {
                    return Single.error<Any>(createException(inv))
                } else {
                    return super.answer(inv)
                }
            }
        }
    }

    private fun createException(
            invocation: InvocationOnMock): RuntimeException {
        val s = invocation.toString()
        return RuntimeException(
                "No mock defined for invocation " + s)
    }
}

