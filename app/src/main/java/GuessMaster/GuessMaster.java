package GuessMaster;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.guessmaster.R;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

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

    public void ChangeEntity(){
        userIn.getText().clear();
    }
    //private String answer;
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
        System.out.println("***************************");////
        System.out.printf(entity.welcomeMessage());////
        if (entity.getName() != "Myself")
            System.out.printf("\n\nGuess %s's birthday\n", entity.getName());
        else
            System.out.printf("\n\nGuess my birthday\n", entity.getName());
        System.out.println("(mm/dd/yyyy)");

        Scanner scanner = new Scanner(System.in);


        String answer = scanner.nextLine();
        answer = answer.replace("\n", "").replace("\r", "");

        if (answer.equals("quit")) {
            System.exit(0);
        }

        Date date = new Date(answer);
//			System.out.println("you guess is: " + date);

        if (date.precedes(entity.getBorn())) {
            System.out.println("Incorrect. Try a later date.");
        } else if (entity.getBorn().precedes(date)) {
            System.out.println("Incorrect. Try an earlier date.");
        } else {
            Random random = new Random();
            int rd = random.nextInt(50);
            rd += 50;
//				for(int i=0; i<=50*100; i++)
//					System.out.println("$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$\n");
            System.out.println("*************Bingo!***************");
//				System.out.printf("***Your bonus is %d$****\n", rd*100);
            System.out.printf("You won %d tickets in this round.\n", entity.getAwardedTicketNumber());
            totalTicketNum += entity.getAwardedTicketNumber();
            System.out.printf("The total number of your tickets is %d.\n", totalTicketNum);
            System.out.printf("**********************************\n", rd * 100);
            System.out.printf(entity.closingMessage());////

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

    public static void main(String[] args) {
        System.out.println("=========================\n");
        System.out.println("     GuessMaster 2.0 \n");
        System.out.println("=========================\n");
        Politician jTrudeau = new Politician("Justin Trudeau", new Date("December", 25, 1971), "Male", "Liberal", 0.25);////
        Singer cDion = new Singer("Celine Dion", new Date("March", 30, 1961), "Female", "La voix du bon Dieu", new Date("November", 6, 1981), 0.5);////
        Person myCreator = new Person("My Creator", new Date("September", 1, 2000), "Female", 1);////
        Country usa = new Country("United States", new Date("July", 4, 1776), "Washinton D.C.", 0.1);////

        GuessMaster gm = new GuessMaster();
        gm.addEntity(jTrudeau);
        gm.addEntity(cDion);
        gm.addEntity(myCreator);
        gm.addEntity(usa);

        gm.playGame();

    }
}
