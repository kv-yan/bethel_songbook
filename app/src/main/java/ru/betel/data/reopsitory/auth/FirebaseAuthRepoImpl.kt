package ru.betel.data.reopsitory.auth

import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await
import ru.betel.domain.repository.auth.FirebaseAuthRepo

/*class FirebaseAuthRepoImpl(private val firebaseAuth: FirebaseAuth) : FirebaseAuthRepo {
    private val auth = MutableStateFlow(firebaseAuth.currentUser != null)

    override fun logIn(email: String, password: String) = flow {
        firebaseAuth.signInWithEmailAndPassword(email, password).await()
        emit((firebaseAuth.currentUser != null))
        auth.value = (firebaseAuth.currentUser != null)
        println("---------------------------------------")
        println("----------login state is working :: ${(firebaseAuth.currentUser != null)}------")
        println("---------------------------------------")
    }

    override fun logOut() = flow {
        firebaseAuth.signOut()
        emit((firebaseAuth.currentUser != null))
        auth.value = (firebaseAuth.currentUser != null)


        println("---------------------------------------")
        println("----------logOut state is working :: ${auth.value}------")
        println("---------------------------------------")
    }

    override fun isSignIn() = auth
}*/

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
