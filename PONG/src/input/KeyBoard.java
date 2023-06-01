package input;

import cena.Cena;
import com.jogamp.newt.event.KeyEvent;
import com.jogamp.newt.event.KeyListener;

public class KeyBoard implements KeyListener {

    private Cena cena;

    public KeyBoard(Cena cena) {
        this.cena = cena;
    }

    @Override
    public void keyPressed(KeyEvent e) {
        System.out.println("Key pressed: " + e.getKeyCode());
        switch (e.getKeyCode()) {
            case KeyEvent.VK_LEFT -> // Tecla esquerda
                cena.setMoverEsquerda(true);
            case KeyEvent.VK_RIGHT -> // Tecla direita
                cena.setMoverDireita(true);
            case KeyEvent.VK_S -> // Tecla S para pausar/retornar o Jogo
                cena.setPausado(!cena.pausado);
            case KeyEvent.VK_ESCAPE -> //Tecla Espaço para Iniciar o jogo
                cena.setIniciar(false);
            default -> {
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        System.out.println("Key released: " + e.getKeyCode());
        if (e.getKeyCode() == KeyEvent.VK_LEFT) { // Tecla esquerda
            cena.setMoverEsquerda(false);
        } else if (e.getKeyCode() == KeyEvent.VK_RIGHT) { // Tecla direita
            cena.setMoverDireita(false);
        }
    }
}
