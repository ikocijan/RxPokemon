package co.infinum.rxpokemon

import co.infinum.rxpokemon.data.model.response.LoginResponse
import co.infinum.rxpokemon.data.network.ErrorHandler
import co.infinum.rxpokemon.data.network.ErrorHandlerDelegate
import co.infinum.rxpokemon.shared.interfaces.StringProvider
import co.infinum.rxpokemon.ui.login.*
import com.jakewharton.retrofit2.adapter.rxjava2.HttpException
import io.reactivex.Observable.error
import io.reactivex.Observable.just
import io.reactivex.Scheduler
import io.reactivex.internal.schedulers.ExecutorScheduler
import okhttp3.MediaType
import okhttp3.ResponseBody
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.ArgumentMatchers
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.*
import org.mockito.junit.MockitoJUnit
import retrofit2.Response
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.util.concurrent.Executor


class LoginTest {

    @get:Rule
    var mockitoJUnitRule = MockitoJUnit.rule()

    @Mock
    lateinit var view: LoginMvp.View

    @Mock
    lateinit var stringProvider: StringProvider

    @Mock
    lateinit var interactor: LoginInteractor

    lateinit var errorHandler: ErrorHandler

    lateinit var presenter: LoginMvp.Presenter

    val mediaType: MediaType = MediaType.parse("application/json")

    @Before
    fun setup() {

        val immediateScheduler = object : Scheduler() {
            override fun createWorker(): Scheduler.Worker {
                return ExecutorScheduler.ExecutorWorker(Executor { it.run() })
            }
        }

        errorHandler = ErrorHandlerDelegate(view, stringProvider)
        presenter = LoginPresenter(view, interactor, immediateScheduler, immediateScheduler, errorHandler)
        Mockito.`when`(stringProvider.getString(Mockito.anyInt())).thenReturn("#yolo")

    }


    @Test
    fun loginSuccess() {

        `when`(interactor.execute(ArgumentMatchers.any())).thenReturn(
                just(LoginResponse("email", "pass", "authToken"))
        )

        presenter.login("email", "pass")
        verify(view).setState(LoginViewState(State.LOADING))
        verify(view).setState(LoginViewState(State.LOGIN_COMPLETED))
        verifyNoMoreInteractions(view)
    }

    @Test
    fun loginFailedWithUnauthorizedResponse() {

        val response: Response<LoginResponse> = Response.error(HttpURLConnection.HTTP_UNAUTHORIZED,
                ResponseBody.create(mediaType, getFileAsString("wrong_login_data_response.json")))

        `when`(interactor.execute(ArgumentMatchers.any())).thenReturn(error { HttpException(response) })

        presenter.login("email", "pass")
        verify(view).showMessage("invalid email or password")

    }

    fun getFileAsString(filename: String): String {
        val fileStream = javaClass.classLoader.getResourceAsStream(filename)
        val fileReader: InputStreamReader? = fileStream.reader()
        return fileReader?.readText() ?: ""
    }

}
