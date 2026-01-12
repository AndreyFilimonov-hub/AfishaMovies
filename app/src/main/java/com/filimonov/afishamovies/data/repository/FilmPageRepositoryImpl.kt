package com.filimonov.afishamovies.data.repository

import com.filimonov.afishamovies.data.database.dao.FilmPageDao
import com.filimonov.afishamovies.data.database.dao.FilmPersonDao
import com.filimonov.afishamovies.data.database.dao.FilmSimilarMediaBannerDao
import com.filimonov.afishamovies.data.database.dao.MediaBannerDao
import com.filimonov.afishamovies.data.database.dao.PersonDao
import com.filimonov.afishamovies.data.database.model.FilmPersonCrossRef
import com.filimonov.afishamovies.data.database.model.FilmSimilarMediaBannerCrossRef
import com.filimonov.afishamovies.data.database.model.MediaBannerDbModel
import com.filimonov.afishamovies.data.mapper.toDbModel
import com.filimonov.afishamovies.data.mapper.toEntity
import com.filimonov.afishamovies.data.mapper.toEntityList
import com.filimonov.afishamovies.data.mapper.toMediaBannerEntityList
import com.filimonov.afishamovies.data.mapper.toPersonBannerEntityList
import com.filimonov.afishamovies.data.network.FilmPageService
import com.filimonov.afishamovies.domain.entities.FilmPageCollectionsState
import com.filimonov.afishamovies.domain.entities.FilmPageEntity
import com.filimonov.afishamovies.domain.entities.ImagePreviewEntity
import com.filimonov.afishamovies.domain.entities.MediaBannerEntity
import com.filimonov.afishamovies.domain.entities.PersonBannerEntity
import com.filimonov.afishamovies.domain.enums.TypeImage
import com.filimonov.afishamovies.domain.repository.FilmPageRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class FilmPageRepositoryImpl @Inject constructor(
    private val apiService: FilmPageService,
    private val mediaBannerDao: MediaBannerDao,
    private val filmPageDao: FilmPageDao,
    private val personDao: PersonDao,
    private val filmPersonDao: FilmPersonDao,
    private val filmSimilarMediaBannerDao: FilmSimilarMediaBannerDao
) : FilmPageRepository {

    private val persons = mutableMapOf<Int, List<PersonBannerEntity>>()
    private val similarMovies = mutableMapOf<Int, List<MediaBannerEntity>>()

    override suspend fun getFilmPageById(id: Int): FilmPageEntity {
        val filmPageFromDb = filmPageDao.getFilmPageById(id)
        filmPageFromDb?.let {
            val similarMediaBannersFromDb =
                filmSimilarMediaBannerDao.getSimilarMediaBannersByFilmId(id)
                    ?.toMediaBannerEntityList() ?: emptyList()
            similarMovies[id] = similarMediaBannersFromDb

            val personsFromDb = filmPersonDao.getPersonsByFilmId(id)
            persons[id] = personsFromDb.toPersonBannerEntityList()

            return it.toEntity(personsFromDb.toPersonBannerEntityList(), similarMediaBannersFromDb)
        }
        return apiService.getFilmPageById(id).toEntity().also {
            persons[id] = it.persons
            similarMovies[id] = it.similarMovies ?: emptyList()
        }
    }

    override fun getFilmPageCollectionsState(movieId: Int): Flow<FilmPageCollectionsState> {
        return filmPageDao.getFilmPageCollectionsStateFlow(movieId)
            .map {
                FilmPageCollectionsState(
                    it?.isLiked ?: false,
                    it?.isWantToWatch ?: false,
                    it?.isWatched ?: false
                )
            }
    }

    override suspend fun getImagePreviewsByMovieId(movieId: Int): List<ImagePreviewEntity> {
        val images = mutableListOf<ImagePreviewEntity>()
        TypeImage.FRAME.typeNames.forEach { typeName ->
            images.addAll(
                apiService.getPreviewImagesByMovieId(
                    movieId = movieId,
                    type = typeName
                ).images.toEntityList()
            )
        }
        return images
    }

    override suspend fun saveFilmPageToDb(filmPageEntity: FilmPageEntity) {
        filmPageDao.addFilmPage(filmPageEntity.toDbModel())
        filmPageEntity.persons.forEach {
            personDao.addPerson(it.toDbModel())
            filmPersonDao.addFilmPerson(
                FilmPersonCrossRef(
                    filmPageEntity.id,
                    it.id,
                    it.character,
                    it.profession
                )
            )
        }
        filmPageEntity.similarMovies?.forEach {
            mediaBannerDao.addMediaBanner(
                MediaBannerDbModel(
                    0,
                    it.id,
                    it.name,
                    it.genreMain,
                    it.rating,
                    it.posterUrl
                )
            )
            filmSimilarMediaBannerDao.addSimilarMediaBanner(
                FilmSimilarMediaBannerCrossRef(filmPageEntity.id, it.id)
            )
        }
    }

    override suspend fun deleteUnusedFilmPage() {
        filmPageDao.deleteUnusedFilmPages()
    }

    override suspend fun updateDefaultCategoriesFlags(
        filmId: Int,
        isLiked: Boolean?,
        isWantToWatch: Boolean?,
        isWatched: Boolean?
    ) {
        filmPageDao.updateDefaultCategoriesFlags(filmId, isLiked, isWantToWatch, isWatched)
    }

    override fun getPersonList(id: Int): List<PersonBannerEntity> {
        return persons[id] ?: emptyList()
    }

    override fun getSimilarMovies(id: Int): List<MediaBannerEntity> {
        return similarMovies[id] ?: emptyList()
    }

    override fun clearCachedPersonListAndSimilarMovies(movieId: Int) {
        persons.remove(movieId)
        similarMovies.remove(movieId)
    }
}