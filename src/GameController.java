import org.newdawn.slick.*;

import java.util.ArrayList;
import java.util.Random;

/**
 * Class controllant le jeu
 */
public class GameController implements Modulable{

    GameContainer container;
    private final PlayerOne mainPlayer;
    private final ArrayList<Fireball> fireballs = new ArrayList<>();
    public final ArrayList<Asteroide> asteroides = new ArrayList<>();
    public final ArrayList<Asteroide> asteroidesToDestroy = new ArrayList<>();
    private final ArrayList<Explosion> explosions = new ArrayList<>();
    private final SpriteSheet vieSprite;

    /**
     * Constructeur de GameController
     * @param container Le container du jeu
     * @throws SlickException Exception slick2d
     */
    public GameController(GameContainer container) throws SlickException {
        this.container = container;
        vieSprite = new SpriteSheet("res/sprites/healthSprite.png", 300, 300);
        mainPlayer = new PlayerOne((int)(container.getWidth()/2) - 50, container.getWidth(), 128, 128, container);
        for (int i = 0; i < 5; i++) {
            Asteroide asteroide = new Asteroide(new Random().nextInt(700), -(new Random().nextInt(800)), "res/sprites/asteroidSprite.png", container, 0, this);
            asteroides.add(asteroide);
        }
    }

    public boolean isCollisionEntre(Entite a, Entite b){
        return a.getRectangle().intersects(b.getRectangle());
    }

    public int getVieJoueur(){
        return mainPlayer.getVieJoueur();
    }

    public int getPourcentage(){
        return mainPlayer.getPourcentage();
    }

    public boolean isFull(){
        return mainPlayer.isFull();
    }

    public int getVolumeMars(){
        return mainPlayer.getVolumeMars();
    }

    /**
     * Méthode qui remet les valeurs initiales
     */
    public void restart(){
        mainPlayer.setVieJoueur(100);
        mainPlayer.setLocation((int)(container.getWidth()/2) - 50, container.getWidth());
        mainPlayer.resetPlayer();
        asteroides.clear();
    }

    /**
     * Méthode qui gère les valeurs à chaque image générée
     *
     * @param delta Le temps entre les images
     * @throws SlickException Exception slick2d
     */
    @Override
    public void update(long delta) throws SlickException {
        Input input = container.getInput();
        if(input.isKeyPressed(Input.KEY_E)){
            mainPlayer.envoyerCargaison();
        }
        if(input.isKeyPressed(Input.KEY_SPACE)){
            try {
                fireballs.add(new Fireball(mainPlayer.getX() + 50, mainPlayer.getY(), 20));
            } catch (SlickException e) {
                e.printStackTrace();
            }
        }
        if(asteroides.size() < 5){
            int size = 5 - asteroides.size();
            for (int i = 0; i < size; i++) {
                Asteroide asteroide = new Asteroide(new Random().nextInt(700), -(new Random().nextInt(800)), "res/sprites/asteroidSprite.png", container, 0, this);
                asteroides.add(asteroide);
            }
        }
        ArrayList<Fireball> ballsToDestroy = new ArrayList<>();
        for(Fireball ball : fireballs){
            if(ball.getDetruire()){
                ballsToDestroy.add(ball);
            } else {
                ball.update(delta);
            }
        }
        fireballs.removeAll(ballsToDestroy);
        ballsToDestroy.clear();
        mainPlayer.update(delta);
        ArrayList<Asteroide> asteroidesToAdd = new ArrayList<>();
        for(Asteroide r : asteroides){
            r.update(delta);
            for(Fireball ball : fireballs){
                if(!ball.getDetruire() && isCollisionEntre(ball, r)){
                    SpriteSheet s = new SpriteSheet("res/sprites/explosionSprite.png", 400, 400);
                    explosions.add(new Explosion(r.getX(), r.getY(), r.getWidth(), r.getHeight(), s));
                    r.setDivision(r.getDivision()+1);
                    int middlePos = (int) (r.getX() + r.getWidth()/2);
                    r.setLocation(middlePos + 50, r.getY());
                    Asteroide newAste = new Asteroide(middlePos - 50, r.getY(), "res/sprites/asteroidSprite.png", container, r.getDivision(), this);
                    asteroidesToAdd.add(newAste);
                    ball.setDetruire(true);
                }
            }
            if(!r.getDetruire() && isCollisionEntre(r, mainPlayer)){
                if(!mainPlayer.ajouterRecolte(r)){
                    mainPlayer.setVieJoueur(mainPlayer.getVieJoueur() - 35);
                    SpriteSheet s = new SpriteSheet("res/sprites/explosionSprite.png", 400, 400);
                    explosions.add(new Explosion(r.getX(), r.getY(), r.getWidth(), r.getHeight(), s));
                }
                asteroidesToDestroy.add(r);
                r.setDetruire(true);
            }
        }
        asteroides.removeAll(asteroidesToDestroy);
        asteroidesToDestroy.clear();
        asteroides.addAll(asteroidesToAdd);
        asteroidesToAdd.clear();
        ArrayList<Explosion> explosionsToDestroy = new ArrayList<>();
        for(Explosion e : explosions){
            if(e.getDetruire()){
                explosionsToDestroy.add(e);
            } else {
                e.update(delta);
            }
        }
        explosions.removeAll(explosionsToDestroy);
        explosionsToDestroy.clear();
    }

    /**
     * Méthode qui gère l'affichage
     */
    @Override
    public void render() {
        for(Fireball ball : fireballs){
            ball.render();
        }
        mainPlayer.render();
        for(Asteroide r : asteroides){
            r.render();
        }
        for(Explosion e : explosions){
            e.render();
        }
        switch(mainPlayer.getVieJoueur()){
            case 100:
                vieSprite.getSprite(3, 0).draw(0, 0, 100, 100);
                break;
            case 65:
                vieSprite.getSprite(2, 0).draw(0, 0, 100, 100);
                break;
            case 30:
                vieSprite.getSprite(1, 0).draw(0, 0, 100, 100);
                break;
            default:
                vieSprite.getSprite(0, 0).draw(0, 0, 100, 100);
        }
    }

}
