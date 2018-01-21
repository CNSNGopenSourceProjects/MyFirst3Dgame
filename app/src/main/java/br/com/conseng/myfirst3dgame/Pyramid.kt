package br.com.conseng.myfirst3dgame

import java.nio.ByteBuffer
import java.nio.ByteOrder
import java.nio.FloatBuffer
import javax.microedition.khronos.opengles.GL10

/**
 * Our pyramid has five faces, so we will require five vertices to draw our pyramid.
 */
class Pyramid {
    /**
     * Buffer for vertex-array.
     */
    private var vb: FloatBuffer

    /**
     * Buffer for index-array.
     */
    private var ib: ByteBuffer

    /**
     * The 5 vertices of our pyramid in (x,y,z).
     */
    private val vp: FloatArray = floatArrayOf(
            -1.0f, -1.0f, -1.0f,                // 0. left-bottom-back vertices
            1.0f, -1.0f, -1.0f,                 // 1. right-bottom-back vertices
            1.0f, -1.0f, 1.0f,                  // 2. right-bottom-front vertices
            -1.0f, -1.0f, 1.0f,                 // 3. left-bottom-front vertices
            0.0f, 1.0f, 0.0f                    // 4. top
    )

    /**
     * Buffer for color-array.
     */
    private var cb: FloatBuffer

    /**
     * Colors for the 5 vertices in RGBA.
     */
    private val colors: FloatArray = floatArrayOf(
            0.0f, 0.0f, 1.0f, 1.0f,             // Blue
            0.0f, 1.0f, 0.0f, 1.0f,             // Green
            0.0f, 0.0f, 1.0f, 1.0f,             // Blue
            0.0f, 1.0f, 0.0f, 1.0f,             // Green
            1.0f, 0.0f, 1.0f, 1.0f              // Red
    )

    /**
     * Vertex indices of the 4 Triangles
     */
    private val ind: ByteArray = byteArrayOf(
            2, 4, 3,                            // Front face
            1, 4, 2,                            // Right face
            0, 4, 1,                            // Back face
            4, 0, 3                             // Left face
    )

    /**
     * Setup the data-array buffers.
     * Initialize the our pyramid in a counter-clockwise (CCW) direction with positive z
     * direction facing toward the screen.
     */
    init {
        // Setup vertex-array buffer. Vertices in float. A float has 4 bytes.
        val vbb = ByteBuffer.allocateDirect(vp.size * 4)
        vbb.order(ByteOrder.nativeOrder())                          // Use native byte order
        vb = vbb.asFloatBuffer()                                    // Convert byte buffer to float
        vb.put(vp)                                                  // Copy data into buffer
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
     * Draw our pyramid on screen; for this we will go through the following four simple steps:
     * (1) We enable vertex array client states.
     * (2) We specify the location of the buffers.
     * (3) We render our primitive shapes using glDrayElements() that uses index array to reference the vertex array.
     * (4) We disable our vertex array client state.
     * @param [gles] GL10: the GL interface. Use instanceof to test if the interface supports GL11 or higher interfaces.
     */
    fun draw(gles: GL10) {
        // Front face in counter-clockwise orientation.
        gles.glFrontFace(GL10.GL_CCW)
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