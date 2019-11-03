package us.martypants.rightpointdemo

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider



/**
 * Created by Martin Rehder on 2019-11-02.
 */
class MyViewModelFactory(private val mApplication: App) :
    ViewModelProvider.Factory {


    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return ImdbViewmodel(mApplication) as T
    }
}