package jp.co.paint

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import jp.co.paint.dialog.DialogMessageRepository
import jp.co.paint.dialog.DialogMessageRepositoryImpl
import javax.inject.Singleton

/**
 * Created by vegeta
 */
@Module
@InstallIn(SingletonComponent::class)
internal abstract class RepositoryModule {
    companion object {
        @Provides
        @Singleton
        fun provideDialogMessageRepository(): DialogMessageRepository {
            return DialogMessageRepositoryImpl()
        }
    }
}