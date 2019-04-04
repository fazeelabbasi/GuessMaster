package GuessMaster;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.guessmaster.R;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;
import java.util.Scanner;


public class GuessMaster extends AppCompatActivity {
    private int numOfEntities;
    private Entity[] entities;
    private int totalTicketNum = 0;
    private int[] tickets;
    private int numOfTickets;

    //Stores Entity Name
    String entName;
    int entityid = 0;
    int currentTicketWon = 0;
    private String answer;


    private TextView entityName;
    private TextView ticketsum;
    private Button guessButton;
    private EditText userIn;
    private Button btnclearContent;
    private String userinput;
    private ImageView entityImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guess_activity);
        guessButton = (Button) findViewById(R.id.btnGuess);
        userIn = (EditText) findViewById(R.id.editText);
        ticketsum = (TextView) findViewById(R.id.ticket);
        entityName = (TextView) findViewById(R.id.entityName);
        btnclearContent = (Button) findViewById(R.id.btnClear);
        //entityImage = (ImageView) findViewById((R.id.entityImage));
        Politician jTrudeau = new Politician("Justin Trudeau", new Date("December", 25, 1971), "Male", "Liberal", 0.25);////
        Singer cDion = new Singer("Celine Dion", new Date("March", 30, 1961), "Female", "La voix du bon Dieu", new Date("November", 6, 1981), 0.5);////
        Person myCreator = new Person("My Creator", new Date("September", 1, 2000), "Female", 1);////
        Country usa = new Country("United States", new Date("July", 4, 1776), "Washinton D.C.", 0.1);////

        final GuessMaster newGame = this;

        btnclearContent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ChangeEntity();
            }
        });

        guessButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Entity entity = entities[entityId];
//                playGame(entity);
                playGame();
            }
        });
    }

    public void ChangeEntity() {
        userIn.getText().clear();
        entityid = genRandomEntityId();
        Entity entity = entities[entityid];
        entName = entity.getName();
        ImageSetter();
        playGame(entity);
    }

    public void ImageSetter() {
        if (entName.equals("United States")) {
            entityImage.setImageResource(R.drawable.usaflag);
        } else if (entName.equals("Celine Dion")) {
            entityImage.setImageResource(R.drawable.celidion);
        } else if (entName.equals("Justin Trudeau")) {
            entityImage.setImageResource(R.drawable.justint);
        } else {
            entityImage.setImageResource(R.drawable.mycreator);
        }
    }

    public void welcomeToGame(Entity entity) {
        //Welcome Alert
        AlertDialog.Builder welcomealert = new AlertDialog.Builder(GuessMaster.this);

        welcomealert.setTitle("GuessMaster_Game_v3");
        welcomealert.setMessage(entity.welcomeMessage());
        welcomealert.setCancelable(false);
        welcomealert.setNegativeButton("START_GAME", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(getBaseContext(), "Game_is_Starting...Enjoy", Toast.LENGTH_SHORT).show();
            }
        });
        // Show Dialog
        AlertDialog dialog = welcomealert.create();
        dialog.show();
    }


    public GuessMaster() {
        numOfEntities = 0;
        entities = new Entity[10];
    }

    public void addEntity(Entity entity) {
//		entities[numOfEntities++] = new Entity(entity);
//		entities[numOfEntities++] = entity;//////
        entities[numOfEntities++] = entity.clone();
    }

    public void playGame(int entityId) {
        Entity entity = entities[entityId];
        playGame(entity);
    }

    public void playGame(Entity entity) {
        entityName.setText(entity.getName());
        answer = userIn.getText().toString();
        answer = answer.replace("\n", "").replace("\r", "");
        Date date = new Date(answer);

        if (date.precedes(entity.getBorn())) {
            AlertDialog.Builder precedes = new AlertDialog.Builder(GuessMaster.this);
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
            AlertDialog.Builder Succeeds = new AlertDialog.Builder(GuessMaster.this);
            Succeeds.setTitle("Incorrect.");
            Succeeds.setMessage("Try an earlier date.");
            Succeeds.setNegativeButton("Ok", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                }
            });
            AlertDialog dialog = Succeeds.create();
            dialog.show();
        } else {

            currentTicketWon = entity.getAwardedTicketNumber();
            totalTicketNum += entity.getAwardedTicketNumber();
            Integer temp = new Integer(totalTicketNum);
            final String totalTicketNumString = temp.toString();
            AlertDialog.Builder youwin = new AlertDialog.Builder(GuessMaster.this);
            youwin.setTitle("You Won");
            youwin.setMessage("BINGO! " + entity.closingMessage());
            youwin.setCancelable(false);
            youwin.setNegativeButton("Ok", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Toast.makeText(getBaseContext(), " you won: " + currentTicketWon, Toast.LENGTH_SHORT).show();

                    ticketsum.setText(totalTicketNumString);
                    //ContinueGame();

                }
            });
            AlertDialog dialog = youwin.create();
            dialog.show();

        }
    }


    public void playGame() {

        int entityId = genRandomEntityId();
        playGame(entityId);

    }

    public int genRandomEntityId() {
        Random randomNumber = new Random();
        return randomNumber.nextInt(numOfEntities);
    }
}