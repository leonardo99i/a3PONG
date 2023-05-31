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
        switch (e.getKeyCode()) {
            case KeyEvent.VK_LEFT -> {
                cena.setPosXRetangulo(cena.getPosXRetangulo() - 0.01f);
                // Verifica limite esquerdo
                if (cena.getPosXRetangulo() < -0.8f) {
                    cena.setPosXRetangulo(-0.8f);
                }
            }
            case KeyEvent.VK_RIGHT -> {
                cena.setPosXRetangulo(cena.getPosXRetangulo() + 0.01f);
                // Verifica limite direito
                if (cena.getPosXRetangulo() > 0.6f) {
                    cena.setPosXRetangulo(0.6f);
                }
            }
            case KeyEvent.VK_ESCAPE -> System.exit(0);
            default -> {
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }
}
