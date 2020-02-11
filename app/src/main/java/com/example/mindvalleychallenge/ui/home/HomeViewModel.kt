package com.example.mindvalleychallenge.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mindvalleychallenge.data.external.ApiResultHandle
import com.example.mindvalleychallenge.data.external.Repository
import com.example.mindvalleychallenge.models.Publication
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class HomeViewModel(private val repository: Repository):ViewModel() {

    private val publicationsData: MutableLiveData<ArrayList<Publication>> = MutableLiveData()


    fun requestPublications(){
        val publications : ArrayList<Publication> = ArrayList()
        viewModelScope.launch(Dispatchers.IO) {
            val repoResponse = repository.getPublications()
            if(repoResponse is ApiResultHandle.Success){
                publications.addAll(repoResponse.value)
            }
            publicationsData.postValue(publications)
        }
    }
     fun getPublications() : LiveData<ArrayList<Publication>>{
        return  publicationsData
    }
}