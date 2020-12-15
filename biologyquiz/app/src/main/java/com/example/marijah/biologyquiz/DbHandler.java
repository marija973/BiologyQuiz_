package com.example.marijah.biologyquiz;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;


public class DbHandler extends SQLiteOpenHelper {

    //DB version, table and database name
    private static final int DB_VERSION = 2;
    private static final String DB_NAME = "quizdbb";
    private static final String DB_TABLE = "quiztable";

    //table
    private static final String KEY_ID = "id";
    private static final String KEY_QUES = "question";
    private static final String KEY_ANSWER = "answer";
    private static final String KEY_OPTA = "optA";
    private static final String KEY_OPTB = "optB";
    private static final String KEY_OPTC = "optC";

    private SQLiteDatabase dbase;
    private int rowCount = 0;

    public DbHandler(Context context){
        super(context,DB_NAME,null,DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        dbase = db;
        String sqlQuery = String.format("CREATE TABLE IF NOT EXISTS %s ( %s INTEGER PRIMARY KEY AUTOINCREMENT, %s TEXT, %s TEXT, %s TEXT, %s TEXT, %s TEXT )", DB_TABLE, KEY_ID, KEY_QUES, KEY_ANSWER, KEY_OPTA, KEY_OPTB, KEY_OPTC);
        Log.d("TaskDBHelper", "Query to form table" + sqlQuery);
        db.execSQL(sqlQuery);
        addQuestions();
    }

    private void addQuestions() {
        Question q1 = new Question("Which one of this animals is jawless ?", "Shark", "Myxine", "Trygon", "Myxine");
        this.addQuestionToDB(q1);
        Question q2 = new Question("Alpha-toxins are produced by ?", "bacteria", "fungi", "viruses", "fungi");
        this.addQuestionToDB(q2);
        Question q3 = new Question("The maximum fixation of solar energy is done by ?", "green plants", "bacteria", "protozoa", " green plants");
        this.addQuestionToDB(q3);
        Question q4 = new Question("Animal protein is called first class protein because it is ", "cheaper in the market", "easily digestibile", "rich in essential amino acids", "rich in essential amino acids");
        this.addQuestionToDB(q4);
        Question q5 = new Question("Nucleic acids are composed of repeating units of ?", "amino acids", "carbos", "nucleotides", "nucleotides");
        this.addQuestionToDB(q5);
        Question q6 = new Question("Outside the nucleus DNA is found in", "mitochodria", "ribosome", "golgi bodies", "mitochondria");
        this.addQuestionToDB(q6);
        Question q7 = new Question("Which one of the following animals belongs to mollusca ?", "Hare", "haliotis", "hydra", "haliotis");
        this.addQuestionToDB(q7);
        Question q8 = new Question("When one pair hides the effect of the other unit.The phenomenon is referred to as?", "mutation", "dominance", "none of this", "dominanace");
        this.addQuestionToDB(q8);
        Question q9 = new Question("Which of the following is biodegradable?", "leather belts", "silver foil", "iron nails", "leather belts");
        this.addQuestionToDB(q9);
        Question q10 = new Question("The part of root involved in water absorption is", "zone of root cap", "zone of root hairs", "zone of cell devision", "zone of root cap");
        this.addQuestionToDB(q10);
        Question q11 = new Question("A member of Liliaceae that shows reticualte venation is", "smilax", "aloe", "allium", "smilax");
        this.addQuestionToDB(q11);
        Question q12 = new Question("Araneology is ?", "study of spiders", "study of mites", "study of beers", "study of spiders");
        this.addQuestionToDB(q12);


    }
    //add questions to db
    public void addQuestionToDB(Question q){
        ContentValues values = new ContentValues();
        values.put(KEY_QUES, q.getQuestion());
        values.put(KEY_ANSWER,q.getAnswer());
        values.put(KEY_OPTA,q.getOptA());
        values.put(KEY_OPTB,q.getOptB());
        values.put(KEY_OPTC,q.getOptC());
        dbase.insert(DB_TABLE, null, values);
    }

    public List<Question> getAllQuestions(){
        List<Question> questionList = new ArrayList<Question>();

        dbase = this.getReadableDatabase();
        String selectQuery = "SELECT * FROM "+DB_TABLE;
        Cursor cursor = dbase.rawQuery(selectQuery,null);
        rowCount = cursor.getCount();

        if(cursor.moveToFirst()){
            do{
                Question q = new Question();
                q.setId(cursor.getInt(0));
                q.setQuestion(cursor.getString(1));
                q.setAnswer(cursor.getString(2));
                q.setOptA(cursor.getString(3));
                q.setOptB(cursor.getString(4));
                q.setOptC(cursor.getString(5));

                questionList.add(q);

            }while (cursor.moveToNext());
        }
        return questionList;
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+DB_TABLE);
        onCreate(db);
    }

    public int getRowCount(){
        return rowCount;
    }
}
