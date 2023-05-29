package com.aayar94.foodrecipes.data

import com.aayar94.foodrecipes.data.remote.RemoteDataSource
import dagger.hilt.android.scopes.ActivityRetainedScoped
import javax.inject.Inject

@ActivityRetainedScoped
class Repository @Inject constructor(
    private val remoteDataSource: RemoteDataSource
) {

    val remote = remoteDataSource

}