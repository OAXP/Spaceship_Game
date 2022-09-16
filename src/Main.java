import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.SlickException;

/**
 * Class principale
 */
public class Main{
    public static final int LARGEUR = 1024, HAUTEUR = 768; // Constantes publiques

    /**
     * La main method
     * @param args Non utilisé
     */
    public static void main(String[] args) {
        try
        {
            AppGameContainer app = new AppGameContainer(new StarGame("Jeu"));
            app.setDisplayMode(LARGEUR, HAUTEUR, false);
            app.setShowFPS(false); // true for display the numbers of FPS
            app.setVSync(true); // false for disable the FPS synchronize
            app.setTargetFrameRate(30);
            app.start();
        }
        catch (SlickException e)
        {
            e.printStackTrace();
        }
    }
}
