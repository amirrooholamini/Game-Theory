package a.s.e.h.f.game_theory_project;

import java.util.ArrayList;
import structure.StructFirstGrid;
import structure.StructGridFill;
import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.graphics.Typeface;
import android.os.Handler;
import android.view.LayoutInflater;


public class Global extends Application {

    public static Context                    context;
    public static LayoutInflater             inflater;
    public static Activity                   currentActivity;
    public static final Handler              HANDLER              = new Handler();
    public static final int                  DOWNLOAD_BUFFER_SIZE = 8 * 1024;
    public static ArrayList<StructFirstGrid> firstGrid            = new ArrayList<StructFirstGrid>();
    public static ArrayList<StructGridFill>  gridFill             = new ArrayList<StructGridFill>();
    public static int                        column               = 0;
    public static int                        selectedItem         = -1;
    public static int                        selectedItem2        = -1;
    public static Typeface                   persionFont;
    public static Typeface                   EnglishFont;
    public static Typeface                   NumberFont;
    public static Typeface                   text;


    @Override
    public void onCreate() {
        super.onCreate();

        context = getApplicationContext();
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        persionFont = Typeface.createFromAsset(getAssets(), "persionname.ttf");
        EnglishFont = Typeface.createFromAsset(getAssets(), "englishname.ttf");
        NumberFont = Typeface.createFromAsset(getAssets(), "numberfont.ttf");
    }

}
