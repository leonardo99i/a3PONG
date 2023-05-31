package cena;

import com.jogamp.opengl.GL2;
import com.jogamp.opengl.GLAutoDrawable;
import com.jogamp.opengl.GLEventListener;
import com.jogamp.opengl.glu.GLU;


public class Cena implements GLEventListener{    
    private float xMin, xMax, yMin, yMax, zMin, zMax;    
    GLU glu;
    
    @Override
    public void init(GLAutoDrawable drawable) {
        //dados iniciais da cena
        glu = new GLU();
        //Estabelece as coordenadas do SRU (Sistema de Referencia do Universo)
        xMin = yMin = zMin = -1;
        xMax = yMax = zMax = 1;        
    }

@Override
public void display(GLAutoDrawable drawable) {
    // obtem o contexto OpenGL
    GL2 gl = drawable.getGL().getGL2();
    // define a cor de fundo da janela (R, G, B, alpha)
    gl.glClearColor(0, 0, 0, 1);
    // limpa a janela com a cor especificada
    gl.glClear(GL2.GL_COLOR_BUFFER_BIT);
    gl.glLoadIdentity(); // lê a matriz identidade

    /*
        desenho da cena        
    */

    gl.glColor3f(0, 0, 1); // cor azul

    // desenha um retângulo fino e baixo
    gl.glBegin(GL2.GL_QUADS);
    gl.glVertex2f(-0.5f, -1.0f); // canto inferior esquerdo
    gl.glVertex2f(0.5f, -1.0f); // canto inferior direito
    gl.glVertex2f(0.5f, -0.9f); // canto superior direito
    gl.glVertex2f(-0.5f, -0.9f); // canto superior esquerdo
    gl.glEnd();

    gl.glColor3f(0, 0, 1); // cor azul

    // desenha uma bolinha
    gl.glTranslatef(0.0f, -0.45f, 0.0f); // translada para a posição central na parte inferior
    int numSegments = 100; // número de segmentos do círculo
    float radius = 0.1f; // raio do círculo
    float angle;

    gl.glBegin(GL2.GL_TRIANGLE_FAN);
    gl.glVertex2f(0.0f, 0.0f); // centro do círculo
    for (int i = 0; i <= numSegments; i++) {
        angle = (float) (2.0f * Math.PI * i / numSegments);
        gl.glVertex2f((float) Math.cos(angle) * radius, (float) Math.sin(angle) * radius);
    }
    gl.glEnd();

    gl.glFlush();
}




    @Override
    public void reshape(GLAutoDrawable drawable, int x, int y, int width, int height) {    
        //obtem o contexto grafico Opengl
        GL2 gl = drawable.getGL().getGL2();  
        
        //evita a divisão por zero
        if(height == 0) height = 1;
        //calcula a proporção da janela (aspect ratio) da nova janela
        float aspect = (float) width / height;
        
        //seta o viewport para abranger a janela inteira
        gl.glViewport(0, 0, width, height);
                
        //ativa a matriz de projeção
        gl.glMatrixMode(GL2.GL_PROJECTION);      
        gl.glLoadIdentity(); //lê a matriz identidade
        
        //Projeção ortogonal
        //true:   aspect >= 1 configura a altura de -1 para 1 : com largura maior
        //false:  aspect < 1 configura a largura de -1 para 1 : com altura maior
        if(width >= height)            
            gl.glOrtho(xMin * aspect, xMax * aspect, yMin, yMax, zMin, zMax);
        else        
            gl.glOrtho(xMin, xMax, yMin / aspect, yMax / aspect, zMin, zMax);
                
        //ativa a matriz de modelagem
        gl.glMatrixMode(GL2.GL_MODELVIEW);
        gl.glLoadIdentity(); //lê a matriz identidade
        System.out.println("Reshape: " + width + ", " + height);
    }    
       
    @Override
    public void dispose(GLAutoDrawable drawable) {}         
}
