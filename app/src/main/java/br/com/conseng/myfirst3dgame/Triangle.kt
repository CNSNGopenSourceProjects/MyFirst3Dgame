package br.com.conseng.myfirst3dgame

import java.nio.ByteBuffer
import java.nio.ByteOrder
import java.nio.FloatBuffer
import javax.microedition.khronos.opengles.GL10

/**
 * Create a 2D triangle to be used on the creation of the 3D image.
 * @constructor Plot a triangle on screen using three vertices.
 */
class Triangle {
    /**
     * Buffer for vertex-array.
     */
    private lateinit var vb: FloatBuffer

    /**
     * Buffer for index-array.
     */
    private lateinit var ib: ByteBuffer

    /**
     * Vertices of our triangle.
     */
    private val v: FloatArray = floatArrayOf(
            0.0f, 1.0f, 0.0f,                   // 0. top vertices
            -1.0f, -1.0f, 0.0f,                 // 1. left-bottom vertices
            1.0f, -1.0f, 0.0f                   // 2. right-bottom vertices
    )

    /**
     * Buffer for color-array.
     */
    private lateinit var cb: FloatBuffer

    /**
     * Colors for the vertices.
     */
    private val colors: FloatArray = floatArrayOf(
            1.0f, 0.0f, 0.0f, 1.0f,             // R
            0.0f, 1.0f, 0.0f, 1.0f,             // G
            0.0f, 0.0f, 1.0f, 1.0f              // B
    )

    /**
     * Indices to above vertices in a counter-clockwise (CCW) direction.
     */
    private val ind: ByteArray = byteArrayOf(0, 1, 2)

    /**
     * Setup the data-array buffers.
     * Initialize the our triangle in a counter-clockwise (CCW) direction with positive z
     * direction facing toward the screen.
     */
    init {
        // Setup vertex-array buffer. Vertices in float. A float has 4 bytes.
        val vbb = ByteBuffer.allocateDirect(v.size * 4)
        vbb.order(ByteOrder.nativeOrder())                          // Use native byte order
        vb = vbb.asFloatBuffer()                                    // Convert byte buffer to float
        vb.put(v)                                                   // Copy data into buffer
        vb.position(0)                                  // Rewind
        // Setup color-array buffer. Colors in float. A float has 4 bytes
        val cbb = ByteBuffer.allocateDirect(colors.size * 4)
        cbb.order(ByteOrder.nativeOrder())                          // Use native byte order
        cb = cbb.asFloatBuffer()                                    // Convert byte buffer to float
        cb.put(colors)                                              // Copy data into buffer
        cb.position(0)                                  // Rewind
        // Setup index-array buffer. Indices in byte.
        ib = ByteBuffer.allocateDirect(ind.size)
        ib.put(ind)
        ib.position(0)                                  // Rewind
    }

    /**
     * Render this shape.
     * Draw our triangle on screen; for this we will go through the following four simple steps:
     * (1) We enable vertex array client states.
     * (2) We specify the location of the buffers.
     * (3) We render our primitive shapes using glDrayElements() that uses index array to reference the vertex array.
     * (4) We disable our vertex array client state.
     * @param [gles] GL10: the GL interface. Use instanceof to test if the interface supports GL11 or higher interfaces.
     */
    fun draw(gles: GL10) {
        // Enable vertex-array and define the buffers
        gles.glEnableClientState(GL10.GL_VERTEX_ARRAY)
        gles.glVertexPointer(3, GL10.GL_FLOAT, 0, vb)
        // Enable color-array
        gles.glEnableClientState(GL10.GL_COLOR_ARRAY)
        gles.glColorPointer(4, GL10.GL_FLOAT, 0, cb)
        // Draw the primitives via index-array
        gles.glDrawElements(GL10.GL_TRIANGLES, ind.size, GL10.GL_UNSIGNED_BYTE, ib)
        // Disable the vertex-array and the color-array
        gles.glDisableClientState(GL10.GL_VERTEX_ARRAY)
        gles.glDisableClientState(GL10.GL_COLOR_ARRAY)
    }
}