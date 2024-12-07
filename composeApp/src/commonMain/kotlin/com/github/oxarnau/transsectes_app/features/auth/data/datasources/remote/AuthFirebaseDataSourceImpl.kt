package com.github.oxarnau.transsectes_app.features.auth.data.datasources.remote

import com.github.oxarnau.transsectes_app.core.domain.DataError
import com.github.oxarnau.transsectes_app.core.domain.Result
import com.github.oxarnau.transsectes_app.features.auth.data.mappers.UserMapper
import com.github.oxarnau.transsectes_app.features.auth.data.models.UserModel
import com.github.oxarnau.transsectes_app.features.auth.domain.datasources.AuthDataSource
import com.github.oxarnau.transsectes_app.features.user.entity.User
import dev.gitlive.firebase.Firebase
import dev.gitlive.firebase.auth.FirebaseAuth
import dev.gitlive.firebase.auth.FirebaseUser
import dev.gitlive.firebase.auth.auth
import dev.gitlive.firebase.firestore.firestore

/**
 * Remote data source for authentication operations using Firebase.
 */
class AuthFirebaseDataSourceImpl : AuthDataSource {

    private val auth: FirebaseAuth by lazy { Firebase.auth }

    override suspend fun signIn(
        email: String,
        password: String,
    ): Result<UserModel, DataError> {
        return try {
            val user = auth.signInWithEmailAndPassword(email, password).user
                ?: return Result.Error(DataError.User.USER_NOT_FOUND)

            if (user.email == null) {
                return Result.Error(DataError.User.EMAIL_NOT_FOUND)
            }

            // TODO: remove
            println("id=${user.uid}, email=${user.email!!}, isEmailVerified=${user.isEmailVerified}")

            return Result.Success(
                UserModel(
                    id = user.uid,
                    email = user.email!!,
                    isEmailVerified = user.isEmailVerified
                )
            )
        } catch (e: Exception) {
            mapFirebaseException(e)
        }
    }

    override suspend fun signUp(
        email: String,
        password: String
    ): Result<User, DataError.Remote> {
        return try {
            val user = auth.createUserWithEmailAndPassword(email, password).user

            TODO()
        } catch (e: Exception) {
            mapFirebaseException(e)
        }
    }

    override suspend fun signOut(): Result<Unit, DataError.Remote> {
        return try {
            auth.signOut()
            Result.Success(Unit)
        } catch (e: Exception) {
            mapFirebaseException(e)
        }
    }

    override suspend fun verifyEmail(): Result<Boolean, DataError.Remote> {
        return try {
            auth.currentUser?.sendEmailVerification()
            Result.Success(true)
        } catch (e: Exception) {
            mapFirebaseException(e)
        }
    }

    override suspend fun isUserAutehnticated(): Result<Boolean, DataError.Remote> {
        return try {
            val isAuthenticated = auth.currentUser != null
            Result.Success(isAuthenticated)
        } catch (e: Exception) {
            mapFirebaseException(e)
        }
    }

    override suspend fun isEmailVerified(): Result<Boolean, DataError.Remote> {
        return try {
            val isVerified = auth.currentUser?.isEmailVerified ?: false
            Result.Success(isVerified)
        } catch (e: Exception) {
            mapFirebaseException(e)
        }
    }

    override suspend fun getUserInfo(): Result<User?, DataError> {
        return try {
            val user: FirebaseUser? = auth.currentUser
            Result.Success(UserMapper().toEntity(user))
        } catch (e: Exception) {
            mapFirebaseException(e)
        }
    }

    override suspend fun isTechnician(): Result<Boolean, DataError> {
        return try {
            // Obtener el email
            val email = auth.currentUser?.email

            // Mirar si el email es null
            if (email.isNullOrEmpty()) return Result.Error(DataError.User.EMAIL_NOT_FOUND)

            // Consultar la colección "tecnics" en Firestore con el email como el ID del documento
            val documentSnapshot = Firebase.firestore
                .collection("tecnics")
                .document(email)
                .get()

            // Verificar si el documento existe
            val isTechnician = documentSnapshot.exists

            Result.Success(isTechnician)
        } catch (e: Exception) {
            mapFirebaseException(e)
        }
    }


    /**
     * Maps Firebase exceptions to custom [DataError.Remote].
     *
     * @param e Exception thrown by Firebase.
     * @return [Result.Error] with the appropriate [DataError.Remote].
     */
    private fun mapFirebaseException(e: Exception): Result.Error<DataError.Remote> {
        val error = when (e.message) {
            "A network error (such as timeout, interrupted connection or unreachable host) has occurred." ->
                DataError.Remote.NO_INTERNET

            else -> DataError.Remote.UNKNOWN
        }
        return Result.Error(error)
    }
}