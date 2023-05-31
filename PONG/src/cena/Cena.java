package cena;

import com.jogamp.opengl.GL2;
import com.jogamp.opengl.GLAutoDrawable;
import com.jogamp.opengl.GLEventListener;
import com.jogamp.opengl.glu.GLU;

public class Cena implements GLEventListener {

    private float posXRetangulo;
    private float posYBolinha;
    private float velocidadeBolinha;
    private float velocidadeMovimento;
    private boolean moverEsquerda;
    private boolean moverDireita;
    private GLU glu;

    private float posXBolinha;
    private float velocidadeBolinhaX;
    private float velocidadeBolinhaY;
    private final float raio = 0.1f;

    public Cena() {
        posXRetangulo = 0.0f;
        posYBolinha = 0.0f;
        velocidadeBolinha = 0.01f;
        velocidadeMovimento = 0.05f;
        moverEsquerda = false;
        moverDireita = false;
        glu = new GLU();
        posXBolinha = 0.0f;
        posYBolinha = 0.8f;
        velocidadeBolinhaX = 0.01f;
        velocidadeBolinhaY = 0.01f;
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

    public void setMoverEsquerda(boolean moverEsquerda) {
        this.moverEsquerda = moverEsquerda;
    }

    public void setMoverDireita(boolean moverDireita) {
        this.moverDireita = moverDireita;
    }

    public void atualizar() {
        if (moverEsquerda) {
            posXRetangulo -= velocidadeMovimento;
            // Verifica limite esquerdo
            if (posXRetangulo < -0.8f) {
                posXRetangulo = -0.8f;
            }
        } else if (moverDireita) {
            posXRetangulo += velocidadeMovimento;
            // Verifica limite direito
            if (posXRetangulo > 0.6f) {
                posXRetangulo = 0.6f;
            }
        }
    }

    public void atualizarBolinha() {
        // Atualizar posição da bolinha com base na velocidade
        posXBolinha += velocidadeBolinhaX;
        posYBolinha += velocidadeBolinhaY;

        // Verificar colisão com a parede direita
        if (posXBolinha + raio > 1.0f) {
            posXBolinha = 1.0f - raio;
            velocidadeBolinhaX = -velocidadeBolinhaX;
        }

        // Verificar colisão com a parede esquerda
        if (posXBolinha - raio < -1.0f) {
            posXBolinha = -1.0f + raio;
            velocidadeBolinhaX = -velocidadeBolinhaX;
        }

        // Verificar colisão com a parede superior
        if (posYBolinha + raio > 1.0f) {
            posYBolinha = 1.0f - raio;
            velocidadeBolinhaY = -velocidadeBolinhaY;
        }

        // Verificar colisão com a raquete
        if (posYBolinha - raio < -0.75f && posXBolinha >= posXRetangulo - 0.2f && posXBolinha <= posXRetangulo + 0.2f) {
            posYBolinha = -0.75f + raio;
            velocidadeBolinhaY = -velocidadeBolinhaY;
        }

        // Verificar colisão com a parede inferior (perdeu o jogo)
        if (posYBolinha - raio < -1.0f) {
            System.out.println("Você perdeu!");
            // Reiniciar o jogo
            posXBolinha = 0.0f;
            posYBolinha = 0.8f;
            velocidadeBolinhaX = 0.01f;
            velocidadeBolinhaY = 0.01f;
        }
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

        // Desenha o retângulo (barra)
        gl.glColor3f(0, 0, 1); // cor azul
        gl.glPushMatrix();
        gl.glTranslatef(posXRetangulo, -0.8f, 0.0f); // translada para a posição da barra
        gl.glBegin(GL2.GL_QUADS);
        gl.glVertex2f(-0.2f, 0.0f); // canto inferior esquerdo
        gl.glVertex2f(0.2f, 0.0f); // canto inferior direito
        gl.glVertex2f(0.2f, 0.05f); // canto superior direito
        gl.glVertex2f(-0.2f, 0.05f); // canto superior esquerdo
        gl.glEnd();
        gl.glPopMatrix();

        // Desenha a bolinha
        gl.glColor3f(1, 1, 1); // cor branca
        gl.glPushMatrix();
        gl.glTranslatef(posXBolinha, posYBolinha, 0.0f); // translada para a posição da bolinha
        int numSegmentos = 50;
        gl.glBegin(GL2.GL_TRIANGLE_FAN);
        gl.glVertex2f(0, 0);
        for (int i = 0; i <= numSegmentos; i++) {
            float angulo = (float) (2.0f * Math.PI * i / numSegmentos);
            gl.glVertex2f((float) Math.cos(angulo) * raio, (float) Math.sin(angulo) * raio);
        }
        gl.glEnd();
        gl.glPopMatrix();

        gl.glFlush();
        atualizar();
        atualizarBolinha();
    }

    @Override
    public void reshape(GLAutoDrawable drawable, int x, int y, int width, int height) {
        GL2 gl = drawable.getGL().getGL2();
        gl.glViewport(0, 0, width, height);
        gl.glMatrixMode(GL2.GL_PROJECTION);
        gl.glLoadIdentity();
        glu.gluOrtho2D(-1.0, 1.0, -1.0, 1.0);
        gl.glMatrixMode(GL2.GL_MODELVIEW);
    }

    @Override
    public void dispose(GLAutoDrawable drawable) {

    }
}
