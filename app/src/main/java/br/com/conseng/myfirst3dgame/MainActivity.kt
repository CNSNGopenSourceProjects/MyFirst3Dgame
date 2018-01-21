package br.com.conseng.myfirst3dgame

import android.opengl.GLSurfaceView
import android.os.Bundle
import android.support.v7.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    /**
     * Saves the open GL object used on this 3D game.
     */
    private var gv: GLSurfaceView? = null

    /**
     * |Initializes the main activity.
     * @param [savedInstanceState] current instance status.
     * @see [https://developer.android.com/reference/android/support/v7/app/AppCompatActivity.html#onCreate(android.os.Bundle)]
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        gv = GLSurfaceView(this)
        gv!!.setRenderer(MyGLRenderer(this))
        this.setContentView(gv)
//        setContentView(R.layout.activity_main)
    }

    /**
     * Handles the App if it goes into background.
     * @see [https://developer.android.com/reference/android/support/v4/app/FragmentActivity.html#onPause()]
     */
    override fun onPause() {
        super.onPause()
        gv!!.onPause()
    }

    /**
     * Resumes the App when the user goes back to the App.
     * @see [https://developer.android.com/reference/android/support/v4/app/FragmentActivity.html#onResume()]
     */
    override fun onResume() {
        super.onResume()
        gv!!.onResume()
    }
}
