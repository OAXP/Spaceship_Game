import org.newdawn.slick.SlickException;

/**
 * Interface des objets modulables
 */
public interface Modulable {

    /**
     * Méthode qui gère les valeurs à chaque image générée
     *
     * @param delta Le temps entre les images
     */
    void update(long delta) throws SlickException;
    /**
     * Méthode qui gère l'affichage
     */
    void render();

}
