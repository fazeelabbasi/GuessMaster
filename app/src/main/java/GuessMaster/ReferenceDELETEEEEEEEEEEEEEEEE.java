import java.util.Random;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import java.lang.reflect.Method;
import java.util.Random;

public class ReferenceDELETEEEEEEEEEEEEEE extends AppCompatActivity {

    String entName;
    int entityId = 0;
    private TextView entityName;
    private TextView ticketsum;
    private Button guessButton;
    private EditText UserIn;
    private Button btnclearContent;
    private String user_input;
    private ImageView entityImage;
    String answer;
    int currentTickets;
    private int numOfEntities;
    private Entity[] entities;
    private int totalTicketNum = 0; ///

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guess_activity);

        guessButton = (Button) findViewById(R.id.btnGuess);
        UserIn = (EditText) findViewById(R.id.guessinput);
        ticketsum = (TextView) findViewById(R.id.ticket);
        entityName = (TextView) findViewById(R.id.entityName);
        btnclearContent = (Button) findViewById(R.id.btnClear);
        entityImage = (ImageView) findViewById((R.id.entityImage));
        Politician jTrudeau = new Politician("Justin Trudeau", new Date("December", 25, 1971), "Male", "Liberal", 0.25);////
        Singer cDion = new Singer("Celine Dion", new Date("March", 30, 1961), "Female", "La voix du bon Dieu", new Date("November", 6, 1981), 0.5);////
        Person myCreator = new Person("My Creator", new Date("September", 1, 2000), "Female", 1);////
        Country usa = new Country("United States", new Date("July", 4, 1776), "Washinton D.C.", 0.1);////

        final ReferenceDELETEEEEEEEEEEEEEE newGame = this;

        newGame.addEntity(jTrudeau);
        newGame.addEntity(cDion);
        newGame.addEntity(myCreator);
        newGame.addEntity(usa);

        btnclearContent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ChangeEntity();
            }
        });

        guessButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Entity entity = entities[entityId];
                playGame(entity);
            }
        });
        newInstance();
    }

    public void newInstance() {

        entityId = genRandomEntityId();
        Entity entity = entities[entityId];
        entName = entity.getName();
        ImageSetter();
        welcomeToGame(entity);
        playGame(entity);
    }

    public void ChangeEntity() {

        UserIn.getText().clear(); //or you can use editText.setText("");
        entityId = genRandomEntityId();
        Entity entity = entities[entityId];
        entName = entity.getName();
        ImageSetter();
        playGame(entity);


    }
    public void incorrectFormat(){

        AlertDialog.Builder after = new AlertDialog.Builder(ReferenceDELETEEEEEEEEEEEEEE.this);
        after.setTitle("Incorrect Date");
        after.setMessage("Try the following format: mm/dd/yyyy");
        after.setNegativeButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        AlertDialog dialog = after.create();
        dialog.show();
    }

    public void ImageSetter() {
        if (entName.equals("Justin Trudeau")) {
            entityImage.setImageResource(R.drawable.justint);
        } else if (entName.equals("Celine Dion")) {
            entityImage.setImageResource(R.drawable.celidion);
        } else if (entName.equals("United States")) {
            entityImage.setImageResource(R.drawable.usaflag);
        } else {
            entityImage.setImageResource(R.drawable.games_dinner_games_question);
        }

    }

    public void welcomeToGame(Entity entity) {
        AlertDialog.Builder welcomealert = new AlertDialog.Builder(ReferenceDELETEEEEEEEEEEEEEE.this);

        welcomealert.setTitle("GuessMaster_Game_v3");
        welcomealert.setMessage(entity.welcomeMessage());
        welcomealert.setCancelable(false);
        welcomealert.setNegativeButton("START_GAME", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(getBaseContext(), "Game_is_Starting...Enjoy", Toast.LENGTH_SHORT).show();
            }
        });
        AlertDialog dialog = welcomealert.create();
        dialog.show();
    }

    public ReferenceDELETEEEEEEEEEEEEEE() {
        numOfEntities = 0;
        entities = new Entity[10];
    }

    public void ContinueGame() {

        entityId = genRandomEntityId();
        Entity entity = entities[entityId];
        entName = entity.getName();
        ImageSetter();
        entityName.setText(entName);
        UserIn.getText().clear();

    }

    public void addEntity(Entity entity) {
//		entities[numOfEntities++] = new Entity(entity);
//		entities[numOfEntities++] = entity;//////
        entities[numOfEntities++] = entity.clone();
    }

    public void playgame() {
        entityId = genRandomEntityId();
        Entity entity = entities[entityId];
        entName = entity.getName();
        ImageSetter();
        playGame(entity);
    }

    public void playGame (Entity entity){
        try {
            entityName.setText(entity.getName());
            answer = UserIn.getText().toString();
            answer = answer.replace("\n", "").replace("\r", "");
            answer = answer.replace("\n", "").replace("\r", "");
            if ((!answer.equals(""))) {
                Date date = new Date(answer);
//			System.out.println("you guess is: " + date);

                if (date.precedes(entity.getBorn())) {
                    AlertDialog.Builder precedes = new AlertDialog.Builder(ReferenceDELETEEEEEEEEEEEEEE.this);
                    precedes.setTitle("Incorrect.");
                    precedes.setMessage("Try a later date.");
                    precedes.setNegativeButton("Ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    });
                    AlertDialog dialog = precedes.create();
                    dialog.show();
                } else if (entity.getBorn().precedes(date)) {
                    AlertDialog.Builder after = new AlertDialog.Builder(ReferenceDELETEEEEEEEEEEEEEE.this);
                    after.setTitle("Incorrect.");
                    after.setMessage("Try an earlier date.");
                    after.setNegativeButton("Ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    });
                    AlertDialog dialog = after.create();
                    dialog.show();
                } else {

                    currentTickets = entity.getAwardedTicketNumber();
                    totalTicketNum += entity.getAwardedTicketNumber();
                    Integer temp = new Integer(totalTicketNum);
                    final String totalTicketNumString = temp.toString();
                    AlertDialog.Builder youwin = new AlertDialog.Builder(ReferenceDELETEEEEEEEEEEEEEE.this);
                    youwin.setTitle("You Won");
                    youwin.setMessage("BINGO! " + entity.closingMessage());
                    youwin.setCancelable(false);
                    youwin.setNegativeButton("Ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Toast.makeText(getBaseContext(), " you won: " + currentTickets, Toast.LENGTH_SHORT).show();

                            ticketsum.setText(totalTicketNumString);
                            ContinueGame();

                        }
                    });
                    AlertDialog dialog = youwin.create();
                    dialog.show();

                }
            }
        }

        catch (Exception e) {

            AlertDialog.Builder after = new AlertDialog.Builder(ReferenceDELETEEEEEEEEEEEEEE.this);
            after.setTitle("Incorrect Date");
            after.setMessage("Try the following format: mm/dd/yyyy");
            after.setNegativeButton("Ok", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                }
            });
            AlertDialog dialog = after.create();
            dialog.show();
        }
        UserIn.getText().clear();

    }

    public int genRandomEntityId () {
        Random randomNumber = new Random();
        return randomNumber.nextInt(numOfEntities);
    }
}