package cena;

import com.jogamp.newt.event.KeyAdapter;
import com.jogamp.newt.event.KeyEvent;
import com.jogamp.newt.event.WindowAdapter;
import com.jogamp.newt.event.WindowEvent;
import com.jogamp.newt.opengl.GLWindow;
import com.jogamp.opengl.GLCapabilities;
import com.jogamp.opengl.GLProfile;
import com.jogamp.opengl.util.FPSAnimator;
import input.KeyBoard;

public class Renderer {
    private static GLWindow window = null;
    public static int screenWidth = 1920;
    public static int screenHeight = 1080;

    // Cria a janela de renderização do JOGL
    public static void init() {
        GLProfile.initSingleton();
        GLProfile profile = GLProfile.get(GLProfile.GL2);
        GLCapabilities caps = new GLCapabilities(profile);
        window = GLWindow.create(caps);
        window.setFullscreen(true); // Define a janela em tela cheia
        window.setSize(screenWidth, screenHeight);

        Cena cena = new Cena();

        window.addGLEventListener(cena); // adiciona a Cena à Janela
        // Habilita o teclado: cena
        window.addKeyListener(new KeyBoard(cena));

        FPSAnimator animator = new FPSAnimator(window, 60);
        animator.start(); // inicia o loop de animação

        // Encerra a aplicação adequadamente
        window.addWindowListener(new WindowAdapter() {
            @Override
            public void windowDestroyNotify(WindowEvent e) {
                animator.stop();
                System.exit(0);
            }
        });

        // Captura o evento da tecla Esc para encerrar o programa em tela cheia
        window.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
                    window.destroy();
                }
            }
        });

        window.setVisible(true);
    }

    public static void main(String[] args) {
        init();
    }
}
