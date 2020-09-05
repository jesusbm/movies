import com.ruskiikot.mentoria01.model.network.FilmRaw
import com.ruskiikot.mentoria01.repository.FilmRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class FilmDetailsInteractor(private val filmRepository: FilmRepository) {

    suspend fun getFilmDetails(movieId: String, isFullPlot: Boolean = false): FilmRaw {
        return withContext(Dispatchers.IO) {
            filmRepository.getFilmDetails(movieId, isFullPlot)
        }
    }

}
