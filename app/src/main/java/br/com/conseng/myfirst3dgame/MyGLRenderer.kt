package br.com.conseng.myfirst3dgame

import android.content.Context
import android.opengl.GLSurfaceView
import android.opengl.GLU
import javax.microedition.khronos.egl.EGLConfig
import javax.microedition.khronos.opengles.GL10

/**
 * Responsible for making OpenGL calls to render the game frame.
 * @constructor Container for all game image rendering functions.
 * @property [context] The game context.
 * @see [https://developer.android.com/reference/android/opengl/GLSurfaceView.Renderer.html]
 */
class MyGLRenderer(private val context: Context) : GLSurfaceView.Renderer {

    /**
     * Create the triangle to be displayed.
     */
    private val triangle = Triangle()

    /**
     * Define the rotation angle to be used on triangle rotation rendering.
     */
    private var angleTriangle = 0.0f

    /**
     * Define the rotation speed to be used on triangle rotation rendering.
     */
    private val speedTriangle = 0.5f

    /**
     * Called when the rendering thread starts and whenever the EGL context is lost.
     * The EGL context will typically be lost when the Android device awakes after going to sleep.
     * @param [gles] GL10: the GL interface. Use instanceof to test if the interface supports GL11 or higher interfaces.
     * @param [config] The EGLConfig of the created surface. Can be used to create matching pbuffers.
     * @see [https://developer.android.com/reference/android/opengl/GLSurfaceView.Renderer.html#onSurfaceCreated(javax.microedition.khronos.opengles.GL10, javax.microedition.khronos.egl.EGLConfig)]
     * @see [https://www.khronos.org/registry/OpenGL-Refpages/es3.0/]
     * @since Note that when the EGL context is lost, all OpenGL resources associated with that
     *        context will be automatically deleted.  The created resources needs to be recreated
     *        when the EGL context is lost
     * */
    override fun onSurfaceCreated(gles: GL10?, config: EGLConfig?) {
        gles!!.glClearColor(0.0f, 0.0f, 0.0f, 1.0f)     // Set color's clear-value to black
        gles.glClearDepthf(1.0f)                                         // Set depth's clear-value to farthest
        gles.glEnable(GL10.GL_DEPTH_TEST)                                       // Enables depth-buffer for hidden surface removal
        gles.glDepthFunc(GL10.GL_LEQUAL)                                        // The type of depth testing to do
        gles.glHint(GL10.GL_PERSPECTIVE_CORRECTION_HINT, GL10.GL_NICEST)        // Nice perspective view
        gles.glShadeModel(GL10.GL_SMOOTH)                                       // Enable smooth shading of color
        gles.glDisable(GL10.GL_DITHER)                                          // Disable dithering for better performance
    }

    /**
     * Called after the surface is created and whenever the OpenGL ES surface size changes.
     * @param [gles] GL10: the GL interface. Use instanceof to test if the interface supports GL11 or higher interfaces.
     * @param [width] The frame width on pixels.
     * @param [height] The frame height on pixels.
     * @see [https://developer.android.com/reference/android/opengl/GLSurfaceView.Renderer.html#onSurfaceChanged(javax.microedition.khronos.opengles.GL10, int, int)]
     */
    override fun onSurfaceChanged(gles: GL10?, width: Int, height: Int) {
        val h = if (0 == height) 1 else height                      // To prevent divide by zero
        val aspect: Float = width.toFloat() / h.toFloat()           // Set the viewport (display area) to cover the entire window

        gles!!.glViewport(0, 0, width, h)                     // Setup perspective projection, with aspect ratio matches viewport
        gles.glMatrixMode(GL10.GL_PROJECTION)                       // Select projection matrix
        gles.glLoadIdentity()                                       // Reset projection matrix
        GLU.gluPerspective(gles, 45.0f, aspect, 0.1f, 100.0f)       // Use perspective projection
        gles.glMatrixMode(GL10.GL_MODELVIEW)                        // Select model-view matrix
        gles.glLoadIdentity()                                       // Reset
    }

    /**
     * Called to draw the current frame.
     * @param [gles] GL10: the GL interface. Use instanceof to test if the interface supports GL11 or higher interfaces.
     * @see [https://developer.android.com/reference/android/opengl/GLSurfaceView.Renderer.html#onDrawFrame(javax.microedition.khronos.opengles.GL10)]
     */
    override fun onDrawFrame(gles: GL10?) {
        // Clear color and depth buffers using clear-values set earlier.
        gles!!.glClear(GL10.GL_COLOR_BUFFER_BIT or GL10.GL_DEPTH_BUFFER_BIT)
        // Draw the triangle.
        gles.glLoadIdentity()
        gles.glTranslatef(-1.5f, 0.0f, -6.0f)
        gles.glRotatef(angleTriangle, -1.5f, 0.0f, -6.0f)
        triangle.draw(gles)
        angleTriangle += speedTriangle          // Increment the rotation angle
    }
}