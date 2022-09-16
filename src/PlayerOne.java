import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Input;

/**
 * Class du joueur principal
 */
public class PlayerOne extends Entite implements Modulable{
    private final int SPEED = 350;
    private final GameContainer container;
    private int vieJoueur = 100;
    private final int CAPACITE = 16384;
    private int volumeRecolte;
    private int volumeMars;

    /**
     * Constructeur de PlayerOne
     * @param x Position x
     * @param y Position y
     * @param width Longueur
     * @param height Hauteur
     * @param container Le container du jeu
     */
    public PlayerOne(float x, float y, float width, float height, GameContainer container) {
        super(x, y, width, height, "res/images/SSTemp.png");
        this.container = container;
        volumeRecolte = 0;
        volumeMars = 0;
    }

    /**
     * Méthode qui récolte les astéroïdes
     * @param asteroide L'astéroïde à récolter
     * @return Si ça a pris la récolte ou non
     */
    public boolean ajouterRecolte(Asteroide asteroide){
        int volume = (int) (asteroide.getWidth() * asteroide.getHeight())/2;
        if((volumeRecolte + volume) <= CAPACITE) {
            volumeRecolte += volume;
            return true;
        } else {
            return false;
        }
    }

    /**
     * Méthode qui envoie la cargaison sur Mars
     */
    public void envoyerCargaison(){
        volumeMars += volumeRecolte;
        volumeRecolte = 0;
    }

    public boolean isFull(){
        return volumeRecolte == CAPACITE;
    }

    public int getPourcentage(){
        return (int) ((float) volumeRecolte/(float) CAPACITE * 100);
    }

    public int getVolumeMars() {
        return volumeMars;
    }

    public int getVieJoueur() {
        return vieJoueur;
    }

    public void resetPlayer(){
        volumeRecolte = 0;
        volumeMars = 0;
    }

    public void setVieJoueur(int vieJoueur) {
        this.vieJoueur = vieJoueur;
    }

    /**
     * Méthode qui gère les valeurs à chaque image générée
     *
     * @param delta Le temps entre les images
     */
    @Override
    public void update(long delta) {
        Input input = container.getInput();
        float distance = SPEED * ((float) delta/1000);
        if (input.isKeyDown(Input.KEY_RIGHT) || input.isKeyDown(Input.KEY_D)) {
            x += distance;
        }
        if (input.isKeyDown(Input.KEY_LEFT) || input.isKeyDown(Input.KEY_A)) {
            x -= distance;
        }
        if (input.isKeyDown(Input.KEY_DOWN) || input.isKeyDown(Input.KEY_S)) {
            y += distance;
        }
        if (input.isKeyDown(Input.KEY_UP) || input.isKeyDown(Input.KEY_W)) {
            y -= distance;
        }
        if(x < 0){
            x = 0;
        }
        if(y < 100){
            y = 100;
        }
        if(x > container.getWidth() - width){
            x = container.getWidth() - width;
        }
        if(y > container.getHeight() - height){
            y = container.getHeight() - height;
        }
    }

    /**
     * Méthode qui gère l'affichage
     */
    @Override
    public void render() {
        image.draw(x, y, width, height);
    }
}
