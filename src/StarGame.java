import org.lwjgl.input.Mouse;
import org.newdawn.slick.*;

/**
 * Class du jeu
 */
public class StarGame extends BasicGame {
    private GameContainer container;
    private Animation bgAnim;
    private Music menuMusic;
    private Music jeuMusic;
    private Music finMusic;
    private Image playImage;
    private Image quitImage;
    private Image restartImage;
    java.awt.Font font;
    TrueTypeFont ttf;
    GameController gameController;
    private enum GAME_STATE{
        MENU,
        JEU,
        FIN
    }
    private GAME_STATE status = GAME_STATE.MENU;

    /**
     * @param title titre de la fenêtre
     */
    public StarGame(String title) {
        super(title);
    }

    /**
     * Méthode se lançant au début du programme
     *
     * @param gameContainer Le container de jeu
     * @throws SlickException Exception de slick2d
     */
    @Override
    public void init(GameContainer gameContainer) throws SlickException {
        font = new java.awt.Font("Verdana", java.awt.Font.BOLD, 20);
        ttf = new TrueTypeFont(font, true);
        menuMusic = new Music("res/audio/menuMusic.wav");
        jeuMusic = new Music("res/audio/jeuMusic.wav");
        finMusic = new Music("res/audio/finMusic.wav");
        playImage = new Image("res/images/playButton.png");
        quitImage = new Image("res/images/quitButton.png");
        restartImage = new Image("res/images/restartButton.png");
        SpriteSheet bgSprite = new SpriteSheet("res/sprites/bgSprite.png", 500, 500);
        this.bgAnim = new Animation(bgSprite, 150);
        this.container = gameContainer;
        gameController = new GameController(container);
    }

    /**
     * Méthode qui gère les valeurs à chaque image générée
     *
     * @param gameContainer Le container du jeu
     * @param delta Le temps entre les images
     * @throws SlickException Exception slick2d
     */
    @Override
    public void update(GameContainer gameContainer, int delta) throws SlickException {
        if(gameController.getVieJoueur() < 0){
            status = GAME_STATE.FIN;
        }
        switch(status){
            case MENU:
                if(jeuMusic.playing()){
                    jeuMusic.stop();
                }
                if(finMusic.playing()){
                    finMusic.stop();
                }
                if(!menuMusic.playing()){
                    menuMusic.loop();
                }
                int mouseX = Mouse.getX();
                int mouseY = Mouse.getY();

                if((mouseX > 418 && mouseX < 606) && (mouseY > 490 && mouseY < 570)){ // Play
                    if(Mouse.isButtonDown(0)){
                        status = GAME_STATE.JEU;
                    }
                }

                if((mouseX >= 418 && mouseX < 604) && (mouseY > 290 && mouseY < 370)){ // Quit
                    if(Mouse.isButtonDown(0)){
                        container.exit();
                    }
                }
                break;
            case JEU:
                if(menuMusic.playing()){
                    menuMusic.stop();
                }
                if(finMusic.playing()){
                    finMusic.stop();
                }
                if(!jeuMusic.playing()){
                    jeuMusic.loop();
                }
                bgAnim.update(delta);
                gameController.update(delta);
                break;
            case FIN:
                if(jeuMusic.playing()){
                    jeuMusic.stop();
                }
                if(menuMusic.playing()){
                    menuMusic.stop();
                }
                if(!finMusic.playing()){
                    finMusic.loop();
                }
                mouseX = Mouse.getX();
                mouseY = Mouse.getY();

                if((mouseX > 384 && mouseX < 641) && (mouseY > 490 && mouseY < 570)){ // Restart
                    if(Mouse.isButtonDown(0)){
                        status = GAME_STATE.JEU;
                        gameController.restart();
                    }
                }

                if((mouseX >= 418 && mouseX < 604) && (mouseY > 290 && mouseY < 370)){ // Quit
                    if(Mouse.isButtonDown(0)){
                        container.exit();
                    }
                }
                break;
        }
    }

    /**
     * Méthode qui gère l'affichage
     *
     * @param gameContainer Le container du jeu
     * @param graphics L'affichage du jeu
     * @throws SlickException Exception slick2d
     */
    @Override
    public void render(GameContainer gameContainer, Graphics graphics) throws SlickException {
        switch(status){
            case MENU:
                playImage.draw(418, 200);
                quitImage.draw(420, 400);
                break;
            case JEU:
                bgAnim.draw(0, 0, 1200, 1200);
                gameController.render();
                break;
            case FIN:
                restartImage.draw(384, 200);
                quitImage.draw(420, 400);
                break;
        }
        if(status == GAME_STATE.JEU || status == GAME_STATE.FIN){
            ttf.drawString(770, 10, "CARGAISON : " + gameController.getPourcentage() + "%", Color.cyan);
            if(gameController.isFull()){
                ttf.drawString(120, 10, "Appuyez sur E pour envoyer la cargaison vers Mars !", Color.red);
            }
            ttf.drawString(770, 40, "MARS : " + gameController.getVolumeMars(), Color.orange);
        }
    }

    /**
     * Événement quand une touche est appuyée
     *
     * @param key Touche appuyée
     * @param c Caractère c
     */
    @Override
    public void keyPressed(int key, char c){
        if(Input.KEY_ESCAPE == key){
            this.container.exit();
        }
    }
}
