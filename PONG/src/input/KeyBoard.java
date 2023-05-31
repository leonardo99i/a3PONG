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
        if (e.getKeyCode() == 149) { // Tecla esquerda
            cena.setMoverEsquerda(true);
        } else if (e.getKeyCode() == 151) { // Tecla direita
            cena.setMoverDireita(true);
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        System.out.println("Key released: " + e.getKeyCode());
        if (e.getKeyCode() == 149) { // Tecla esquerda
            cena.setMoverEsquerda(false);
        } else if (e.getKeyCode() == 151) { // Tecla direita
            cena.setMoverDireita(false);
        }
    }
}
