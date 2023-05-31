package cena;

import com.jogamp.opengl.GL2;
import com.jogamp.opengl.GLAutoDrawable;
import com.jogamp.opengl.GLEventListener;
import com.jogamp.opengl.glu.GLU;
import com.jogamp.opengl.util.gl2.GLUT;

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

    private int vidas;
    private int pontuacao;
    private int fase;
    public boolean pausado;
    private boolean iniciar;

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

        vidas = 5;
        pontuacao = 0;
        fase = 1;
        pausado = false;
        iniciar = false;
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

    public void setPausado(boolean pausado) {
        this.pausado = pausado;
    }

    public void setIniciar(boolean iniciar) {
        this.iniciar = iniciar;
    }

    public void atualizar() {
        if (!pausado) {
            if (moverEsquerda) {
                posXRetangulo -= velocidadeMovimento;
                // Verifica limite esquerdo
                if (posXRetangulo < -0.8f) {
                    posXRetangulo = -0.8f;
                }
            } else if (moverDireita) {
                posXRetangulo += velocidadeMovimento;
                // Verifica limite direito
                if (posXRetangulo > 0.8f) {
                    posXRetangulo = 0.8f;
                }
            }
        }
    }

    public void atualizarBolinha() {
        if (!pausado) {
            // Atualizar posição da bolinha com base na velocidade
            posXBolinha += velocidadeBolinhaX;
            posYBolinha += velocidadeBolinhaY;

            // Verificar colisão com a parede direita
            if (posXBolinha + raio > 1.0f) {
                velocidadeBolinhaX = -velocidadeBolinha;
            }

            // Verificar colisão com a parede esquerda
            if (posXBolinha - raio < -1.0f) {
                velocidadeBolinhaX = velocidadeBolinha;
            }

            // Verificar colisão com a parede superior
            if (posYBolinha + raio > 1.0f) {
                velocidadeBolinhaY = -velocidadeBolinha;
            }

            // Verificar colisão com a parede inferior (perde vida)
            if (posYBolinha - raio < -1.0f) {
                vidas--;
                if (vidas <= 0) {
                    // Fim do jogo
                    pausado = true;
                } else {
                    // Reiniciar a bolinha
                    posXBolinha = 0.0f;
                    posYBolinha = 0.8f;
                    velocidadeBolinhaX = 0.01f;
                    velocidadeBolinhaY = 0.01f;
                }
            }

            // Verificar colisão com o retângulo
            if (posYBolinha - raio <= -0.7f && posXBolinha >= posXRetangulo - 0.2f && posXBolinha <= posXRetangulo + 0.2f) {
                pontuacao += 20;
                velocidadeBolinhaY = velocidadeBolinha;
                if (pontuacao >= 200) {
                    // Iniciar a segunda fase
                    fase = 2;
                    velocidadeBolinha = 0.02f;
                }
            }
        }
    }

    @Override
    public void display(GLAutoDrawable drawable) {
        GL2 gl = drawable.getGL().getGL2();

        // Limpar o buffer de cores e o buffer de profundidade
        gl.glClear(GL2.GL_COLOR_BUFFER_BIT | GL2.GL_DEPTH_BUFFER_BIT);

        // Definir a cor vermelha
        gl.glColor3f(1.0f, 0.0f, 0.0f);

        // Desenhar o retângulo
        gl.glBegin(GL2.GL_POLYGON);
        gl.glVertex2f(posXRetangulo - 0.2f, -0.8f);
        gl.glVertex2f(posXRetangulo + 0.2f, -0.8f);
        gl.glVertex2f(posXRetangulo + 0.2f, -0.9f);
        gl.glVertex2f(posXRetangulo - 0.2f, -0.9f);
        gl.glEnd();

        // Desenhar a bolinha
        gl.glColor3f(0.0f, 0.0f, 1.0f);
        gl.glTranslatef(posXBolinha, posYBolinha, 0.0f);
        gl.glBegin(GL2.GL_POLYGON);
        for (int i = 0; i < 360; i++) {
            double theta = 2.0 * Math.PI * i / 360.0;
            double x = raio * Math.cos(theta);
            double y = raio * Math.sin(theta);
            gl.glVertex2d(x, y);
        }
        gl.glEnd();
        gl.glLoadIdentity();

        // Exibir informações do jogo (vidas e pontuação)
        gl.glColor3f(1.0f, 1.0f, 1.0f);
        gl.glColor3f(1.0f, 1.0f, 1.0f);

        //Exibe regras
        String regras = "Regras do jogo: ";
        desenharTexto(gl, 0.9f, 0.8f, regras);

        // Exibir vidas
        String vidasStr = "Vidas: " + vidas;
        desenharTexto(gl, -0.9f, 0.8f, vidasStr);

        // Exibir pontuação
        String pontuacaoStr = "Pontuação: " + pontuacao;
        desenharTexto(gl, -0.9f, 0.7f, pontuacaoStr);

        //Exibir Fase
        String faseStr = "Fase: " + fase;
        desenharTexto(gl, -0.9f, 0.6f, faseStr);

        // Verificar atualizações
        atualizar();
        atualizarBolinha();
    }

    private void desenharTexto(GL2 gl, float x, float y, String texto) {
        gl.glEnable(GL2.GL_COLOR_MATERIAL);
        gl.glColor3f(1.0f, 1.0f, 1.0f);
        gl.glRasterPos2f(x, y);

        GLUT glut = new GLUT();

        for (char c : texto.toCharArray()) {
            glut.glutBitmapCharacter(GLUT.BITMAP_TIMES_ROMAN_24, c);
        }
        gl.glDisable(GL2.GL_COLOR_MATERIAL);
    }

    @Override
    public void init(GLAutoDrawable drawable) {
        // Configurações iniciais
        GL2 gl = drawable.getGL().getGL2();
        gl.glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
        gl.glMatrixMode(GL2.GL_PROJECTION);
        gl.glLoadIdentity();
        glu.gluOrtho2D(-1.0f, 1.0f, -1.0f, 1.0f);
        gl.glMatrixMode(GL2.GL_MODELVIEW);
    }

    @Override
    public void reshape(GLAutoDrawable drawable, int x, int y, int width, int height) {
    }

    @Override
    public void dispose(GLAutoDrawable drawable) {
    }
}
