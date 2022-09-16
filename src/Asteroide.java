import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;
import org.newdawn.slick.geom.Rectangle;

import java.util.Random;

/**
 * Class de l'astéroïde
 */
public class Asteroide extends Entite implements Modulable{
    private final GameContainer container;
    private final SpriteSheet asteroideSprite;
    private int speed;
    private int division;
    private final GameController gameController;

    /**
     * Constructeur pour l'astéroïde
     * @param x Position x
     * @param y Position y
     * @param imagepath Chemin d'accès de l'image sur le disque
     * @param container Le container du jeu
     * @param division Le nombre de division de l'astéroïde
     * @param gameController L'objet du controlleur de jeu
     * @throws SlickException Exception slick2d
     */
    public Asteroide(float x, float y, String imagepath, GameContainer container, int division, GameController gameController) throws SlickException {
        super(x, y, 256, 256, imagepath);
        asteroideSprite = new SpriteSheet(imagepath, 256, 256);
        int random = new Random().nextInt(4);
        image = asteroideSprite.getSprite(random, 0);
        this.container = container;
        this.speed = new Random().nextInt(5) + 1;
        this.division = division;
        this.gameController = gameController;
    }

    /**
     * Méthode qui remet les valeurs initiales
     */
    public void resetAsteroid(){
        if(gameController.asteroides.size() > 5){
            gameController.asteroidesToDestroy.add(this);
        } else {
            y = -(new Random().nextInt(800));
            x = new Random().nextInt(700);
            int random = new Random().nextInt(4);
            image = asteroideSprite.getSprite(random, 0);
            this.speed = new Random().nextInt(5) + 1;
            this.division = 0;
        }
    }

    public void setDivision(int division) {
        this.division = division;
    }

    public int getDivision() {
        return division;
    }

    @Override
    public float getWidth() {
        int diviserPar = (int) Math.pow(2,division);
        return super.getWidth()/diviserPar;
    }

    @Override
    public float getHeight() {
        int diviserPar = (int) Math.pow(2,division);
        return super.getHeight()/diviserPar;
    }

    @Override
    public Rectangle getRectangle() {
        int diviserPar = (int) Math.pow(2,division);
        return new Rectangle(x+(int)(40/diviserPar), y, width/diviserPar-(int)(80/diviserPar), height/diviserPar-(int)(40/diviserPar));
    }

    /**
     * Méthode qui gère les valeurs à chaque image générée
     *
     * @param delta Le temps entre les images
     */
    @Override
    public void update(long delta) {
        y+=speed;
        if(y > container.getHeight()){
            resetAsteroid();
        }
    }

    /**
     * Méthode qui gère l'affichage
     */
    @Override
    public void render() {
        int diviserPar = (int) Math.pow(2,division);
        image.draw(x, y, width/diviserPar, height/diviserPar);
    }
}
