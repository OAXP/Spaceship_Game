import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;

/**
 * Class pour les explosions
 */
public class Explosion extends Entite implements Modulable{
    /**
     * Constructeur de Explosion
     * @param x Position x
     * @param y Position y
     * @param width Longueur
     * @param height Hauteur
     * @param s Spritesheet de l'explosion
     * @throws SlickException Exception slick2d
     */
    public Explosion(float x, float y, float width, float height, SpriteSheet s) throws SlickException {
        super(x, y, width, height, s, 100);
        animation.setLooping(false);
    }

    /**
     * Méthode qui gère les valeurs à chaque image générée
     *
     * @param delta Le temps entre les images
     */
    @Override
    public void update(long delta) {
        animation.update(delta);
        if(animation.isStopped()){
            detruire= true;
        }
    }

    /**
     * Méthode qui gère l'affichage
     */
    @Override
    public void render() {
        animation.draw(x, y, width, height);
    }
}
