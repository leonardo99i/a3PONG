package cena;

import com.jogamp.opengl.GL2;
import com.jogamp.opengl.GLAutoDrawable;
import com.jogamp.opengl.GLEventListener;
import com.jogamp.opengl.glu.GLU;

public class Cena implements GLEventListener {
    private float posXRetangulo;
    private float posYBolinha;
    private float velocidadeBolinha;
    private GLU glu;

    public Cena() {
        posXRetangulo = 0.0f;
        posYBolinha = 0.0f;
        velocidadeBolinha = 0.01f;
        glu = new GLU();
    }

    public float getPosXRetangulo() {
        return posXRetangulo;
    }

    public void setPosXRetangulo(float posXRetangulo) {
        this.posXRetangulo = posXRetangulo;
    }

    public float getPosYBolinha() {
        return posYBolinha;
    }

    public void setPosYBolinha(float posYBolinha) {
        this.posYBolinha = posYBolinha;
    }

    public void atualizar() {
        posXRetangulo += velocidadeBolinha;
        // Verifica limite direito
        if (posXRetangulo > 0.6f)
            posXRetangulo = 0.6f;
        // Verifica limite esquerdo
        if (posXRetangulo < -0.8f)
            posXRetangulo = -0.8f;
    }

    @Override
    public void init(GLAutoDrawable drawable) {
        GL2 gl = drawable.getGL().getGL2();
        gl.glClearColor(0, 0, 0, 1);
    }

    @Override
    public void display(GLAutoDrawable drawable) {
        GL2 gl = drawable.getGL().getGL2();
        gl.glClear(GL2.GL_COLOR_BUFFER_BIT);
        gl.glLoadIdentity();

        // Desenha o retângulo
        gl.glColor3f(0, 0, 1); // cor azul
        gl.glTranslatef(posXRetangulo, -0.8f, 0.0f); // translada para a posição do retângulo
        gl.glBegin(GL2.GL_QUADS);
        gl.glVertex2f(-0.2f, 0.0f); // canto inferior esquerdo
        gl.glVertex2f(0.2f, 0.0f); // canto inferior direito
        gl.glVertex2f(0.2f, 0.1f); // canto superior direito
        gl.glVertex2f(-0.2f, 0.1f); // canto superior esquerdo
        gl.glEnd();

        // Desenha a bolinha
        gl.glColor3f(1, 0, 0); // cor vermelha
        gl.glLoadIdentity();
        gl.glTranslatef(0.0f, posYBolinha, 0.0f); // translada para a posição da bolinha
        int numSegmentos = 100; // número de segmentos do círculo
        float raio = 0.1f; // raio do círculo
        float angulo;

        gl.glBegin(GL2.GL_TRIANGLE_FAN);
        gl.glVertex2f(0.0f, 0.0f); // centro do círculo
        for (int i = 0; i <= numSegmentos; i++) {
            angulo = (float) (2.0f * Math.PI * i / numSegmentos);
            gl.glVertex2f((float) Math.cos(angulo) * raio, (float) Math.sin(angulo) * raio);
        }
        gl.glEnd();

        gl.glFlush();
        atualizar();
    }

    @Override
    public void reshape(GLAutoDrawable drawable, int x, int y, int width, int height) {
        GL2 gl = drawable.getGL().getGL2();
        if (height == 0) {
            height = 1;
        }
        float aspect = (float) width / height;

        gl.glViewport(0, 0, width, height);
        gl.glMatrixMode(GL2.GL_PROJECTION);
        gl.glLoadIdentity();

        if (width >= height) {
            gl.glOrtho(-1 * aspect, 1 * aspect, -1, 1, -1, 1);
        } else {
            gl.glOrtho(-1, 1, -1 / aspect, 1 / aspect, -1, 1);
        }

        gl.glMatrixMode(GL2.GL_MODELVIEW);
        gl.glLoadIdentity();
    }

    @Override
    public void dispose(GLAutoDrawable drawable) {
    }
}
