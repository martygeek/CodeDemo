package us.martypants.rightpointdemo

import android.app.Activity
import android.content.Context
import android.net.ConnectivityManager
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AlertDialog


/**
 * Created by Martin Rehder on 2019-11-03.
 */
fun closeKeyboard(activity: Activity) {
    val imm = activity
        .getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
    //Find the currently focused view, so we can grab the correct window token from it.
    var view = activity.currentFocus
    //If no view currently has focus, create a new one, just so we can grab a window token from it
    if (view == null) {
        view = View(activity)
    }
    imm.hideSoftInputFromWindow(view.windowToken, 0)
}


fun isConnectedToNetwork(ctx: Context): Boolean {
    val cm = ctx.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    val activeNetwork = cm.activeNetworkInfo
    return activeNetwork != null && activeNetwork.isConnectedOrConnecting
}

fun errorDialog(context: Context, error:String) {
    val builder: AlertDialog.Builder? = context.let {
        AlertDialog.Builder(it)
    }

    builder?.setMessage(error)
        ?.setTitle("Error")
        ?.setPositiveButton(android.R.string.ok,   { dialog, _ ->
            dialog.dismiss()
        })

    val dialog: AlertDialog? = builder?.create()
    dialog?.show()
}