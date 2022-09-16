import org.newdawn.slick.Animation;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;
import org.newdawn.slick.geom.Rectangle;

public abstract class Entite {
    protected float x, y, width, height; // position et taille
    protected Image image; // L’image de l’entité
    protected Animation animation; // Animation de l'entité
    protected boolean detruire = false; // Ne pas détruire si false
    protected boolean isAnimated = false;
    /**
     * Constructeur d'Entite avec image sur le disque
     * @param x position de l'entité dans l'écran - x
     * @param y position de l'entité dans l'écran - y
     * @param width largeur de l'image
     * @param height hauteur de l'image
     * @param imagepath chemin d'accès de l'image sur le disque
     */
    public Entite(float x, float y, float width, float height, String imagepath) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        try {
            image = new Image(imagepath);
        } catch (SlickException e) {
            System.out.println("Image non trouvée pour " + getClass());
        }
        this.isAnimated = false;
    }
    /**
     * Constructeur d'Entite #2 - Avec SpriteSheet
     * @param x position de l'entité dans l'écran - x
     * @param y position de l'entité dans l'écran - y
     * @param width largeur de l'animation
     * @param height hauteur de l'animation
     * @param spriteSheet SpriteSheet qui contient l'image
     * @param speed la vitesse de l'animation du sprite sheet
     */
    public Entite(float x, float y, float width, float height, SpriteSheet spriteSheet, int speed) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.animation = new Animation(spriteSheet, speed);
        this.image = null;
        this.isAnimated = true;
    }
    public float getX() { // Position en X du coin supérieur gauche de l’entite
        return x;
    }
    public float getY() { // Position en Y du coin supérieur gauche de l’entite
        return y;
    }
    public float getWidth() { // Largeur de l’entite
        return width;
    }
    public float getHeight() { // Hauteur de l’entite
        return height;
    }
    public Image getImage() { // Retourne l’image de l’entité
        return image;
    }
    public Animation getAnimation(){
        return animation;
    }
    public boolean isAnimated() {
        return isAnimated;
    }
    public Rectangle getRectangle(){ // Retourne le rectangle qui englobe l’entité
        return new Rectangle(x, y, width, height);
    }
    public void setLocation(float x, float y) { // Pour déplacer l’élément depuis le Jeu
        this.x = x;
        this.y = y;
    }
    public boolean getDetruire(){ // Si l’objet doit être détruit --> true, false sinon
        return detruire;
    }

    public void setDetruire(boolean detruire) {
        this.detruire = detruire;
    }
}