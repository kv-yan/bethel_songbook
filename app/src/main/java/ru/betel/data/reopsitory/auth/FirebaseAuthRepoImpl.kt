package ru.betel.data.reopsitory.auth

import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await
import ru.betel.domain.repository.auth.FirebaseAuthRepo

class FirebaseAuthRepoImpl(private val firebaseAuth: FirebaseAuth) : FirebaseAuthRepo {
    override suspend fun logIn(email: String, password: String): Result<Unit> {
        return try {
            firebaseAuth.signInWithEmailAndPassword(email, password).await()
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override fun logOut(): Result<Unit> {
        return try {
            firebaseAuth.signOut()
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override fun isSignIn() = flow {
        if (firebaseAuth.currentUser != null) {
            emit(true)
        } else {
            emit(false)
        }

    }
}
