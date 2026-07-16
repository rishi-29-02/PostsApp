package com.rm.postapp.presentation.screen.profile

import androidx.lifecycle.ViewModel
import com.rm.postapp.data.utils.AppInfoProviderImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val appInfoProviderImpl: AppInfoProviderImpl
) : ViewModel() {
    private var _versionName = MutableStateFlow("")
    val versionName = _versionName.asStateFlow()

    init {
        _versionName.value = appInfoProviderImpl.getVersionName()
    }
}