import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;

/**
 * Class de boule de feu
 */
public class Fireball extends Entite implements Modulable{
    private final int speed;

    /**
     * Constructeur pour Fireball
     * @param x Position x
     * @param y Position y
     * @param speed Vitesse de la boule de feu
     * @throws SlickException Exception slick2d
     */
    public Fireball(float x, float y, int speed) throws SlickException {
        super(x, y, 24, 72, new SpriteSheet("res/sprites/fireUpSprite.png", 144, 432), 100);
        this.speed = speed;
    }

    /**
     * Méthode qui gère les valeurs à chaque image générée
     *
     * @param delta Le temps entre les images
     */
    @Override
    public void update(long delta) {
        if(y > -height) {
            this.getAnimation().update(delta);
            setLocation(x, y - speed);
        } else {
            detruire = true;
        }
    }

    /**
     * Méthode qui gère l'affichage
     */
    @Override
    public void render() {
        if(y > -height) {
            this.getAnimation().draw(x, y, width, height);
        }
    }
}
