package hehe.he.apple;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;

import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.core.view.WindowCompat;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import java.util.Random;

import hehe.he.apple.databinding.Universal1Binding;

public class Level1 extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        Dialog dialog;

        final int[] numLeft = new int[1]; //для левой картинки //ВОПРОСЫ
        final int[] numRight = new int[1]; //для правой        //ВОПРОСЫ
        Array array = new Array();
        Random random = new Random();
        final int[][] count = {{0}};


        super.onCreate(savedInstanceState);
        setContentView(R.layout.universal1);

        //создаем переменную text_levels
        TextView text_levels = findViewById(R.id.text_levels);
        text_levels.setText(R.string.level1);

        final ImageView img_left = (ImageView)findViewById(R.id.img_left);
        //код скругляет углы
        img_left.setClipToOutline(true);
        final ImageView img_right = (ImageView)findViewById(R.id.img_right);
        //код скругляет углы
        img_right.setClipToOutline(true);

        //путь к левой TextViev
        final TextView text_left = findViewById(R.id.text_left);
        //путь к правой TextView
        final TextView text_right = findViewById(R.id.text_right);



        //razvernut igry
        Window w = getWindow();
        w.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);

        //вызов диалогового окна в начале игры
        dialog = new Dialog(this);//создаем новое диалоговое окно
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);//скрываем заголовок
        dialog.setContentView(R.layout.previewdialog);//путь к макету диалогового окна
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));//прозрачный фон диалогового окна
        dialog.setCancelable(false);//окно нельзя закрыть кнопкой назад

        //кнопка, закрытия
        TextView btnclose = (TextView)dialog.findViewById(R.id.btnclose);
        btnclose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //обрабатываем нажатие кнопки
                try {
                    //вернуться назад к выбору уровня
                    Intent intent = new Intent(Level1.this, GameLevels.class);
                    startActivity(intent);
                    finish();
                }catch(Exception e){}
                dialog.dismiss();
            }
        });

        //кнопка продолжить
        Button btncontinue = (Button)dialog.findViewById(R.id.btncontinue);
        btncontinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });


        dialog.show();//показать диалоговое окно

        //кнопка назад
        Button btn_back = (Button) findViewById(R.id.button_back);
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    //вернуться к выбору уровня
                    Intent intent = new Intent(Level1.this, GameLevels.class);
                    startActivity(intent);
                    finish();

                }catch(Exception e){}
            }
        });

        //массив для прогресса игры
        final int[] progress = {
                R.id.point1, R.id.point2, R.id.point3, R.id.point4, R.id.point5, R.id.point6, R.id.point7,
        };


        //подключаем анимацию
        final Animation a = AnimationUtils.loadAnimation(Level1.this, R.anim.alpha);


        numLeft[0] = random.nextInt(10);
        img_left.setImageResource(array.images1[numLeft[0]]);
        text_left.setText(array.texts1[numLeft[0]]);

        numRight[0] = random.nextInt(10);
        //цикл, проверяющий равенство чисел
        while (numLeft[0] == numRight[0]) {
            numRight[0] = random.nextInt(10);
        }
        img_right.setImageResource(array.images1[numRight[0]]);
        text_right.setText(array.texts1[numRight[0]]);

        //обрабатываем нажатие на левую картнку
        int finalNumRight = numRight[0];
        int finalNumRight1 = numRight[0];
        img_left.setOnTouchListener(new View.OnTouchListener() {
            @SuppressLint("ClickableViewAccessibility")
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                //условие касания картинки
                if (event.getAction()==MotionEvent.ACTION_DOWN){
                    //если коснулся картинки
                    img_right.setEnabled(false);//блокируем правую
                    if (numLeft[0] > finalNumRight){
                        img_left.setImageResource(R.drawable.img_true);
                    }else{
                        img_left.setImageResource(R.drawable.img_false);
                    }
                }else if (event.getAction()==MotionEvent.ACTION_UP){
                    //если отпустил палец
                    if (numLeft[0] > finalNumRight1){
                        //если левая картинка больше
                        if(count[0][0] <7){
                            count[0][0] +=1;
                        }

                        //закрашиваем прогресс серым
                        for (int i=0; i<7; i++){
                            TextView tv = findViewById(progress[i]);
                            tv.setBackgroundResource(R.drawable.style_points);
                        }
                        //определяем правильнность ответа и закрашиваем зеленым
                        for (int i = 0; i< count[0][0]; i++){
                            TextView tv = findViewById(progress[i]);
                            tv.setBackgroundResource(R.drawable.style_points_green);

                        }
                    }else{
                        //если левая картинка меньше
                        if(count[0][0]>0){
                            if(count[0][0]==1){
                                count[0][0]=0;
                            }else{
                                count[0][0]= count[0][0]-2;
                            }
                        }
                        //закрашиваем прогресс серым
                        for (int i=0; i<6; i++){
                            TextView tv = findViewById(progress[i]);
                            tv.setBackgroundResource(R.drawable.style_points);
                        }
                        //определяем правильнность ответа и закрашиваем зеленым
                        for (int i = 0; i< count[0][0]; i++){
                            TextView tv = findViewById(progress[i]);
                            tv.setBackgroundResource(R.drawable.style_points_green);

                        }

                    }
                    //если отпустил палец конец
                    if(count[0][0]==7){
                        //ВЫХОД ИЗ УРОВНЯ
                    }else{
                        numLeft[0] = random.nextInt(10);
                        img_left.setImageResource(array.images1[numLeft[0]]);
                        img_left.startAnimation(a);
                        text_left.setText(array.texts1[numLeft[0]]);

                        numRight[0] = random.nextInt(10);
                        //цикл, проверяющий равенство чисел
                        while (numLeft[0] == numRight[0]) {
                            numRight[0] = random.nextInt(10);
                        }
                        img_right.setImageResource(array.images1[numRight[0]]);
                        img_right.startAnimation(a);
                        text_right.setText(array.texts1[numRight[0]]);

                        img_right.setEnabled(true);//включаем правую картинку

                    }
                }
                return true;
            }
        });
        //правая кнопка
        img_right.setOnTouchListener(new View.OnTouchListener() {
            @SuppressLint("ClickableViewAccessibility")
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                //условие касания картинки
                if (event.getAction()==MotionEvent.ACTION_DOWN){
                    //если коснулся картинки
                    img_left.setEnabled(false);//блокируем левую картинку
                    if (numLeft[0] < finalNumRight){
                        img_right.setImageResource(R.drawable.img_true);
                    }else{
                        img_right.setImageResource(R.drawable.img_false);
                    }
                }else if (event.getAction()==MotionEvent.ACTION_UP){
                    //если отпустил палец
                    if (numLeft[0] < finalNumRight1){
                        //если правая картинка больше
                        if(count[0][0] <7){
                            count[0][0] +=1;
                        }
                        //закрашиваем прогресс серым
                        for (int i=0; i<7; i++){
                            TextView tv = findViewById(progress[i]);
                            tv.setBackgroundResource(R.drawable.style_points);
                        }
                        //определяем правильнность ответа и закрашиваем зеленым
                        for (int i = 0; i< count[0][0]; i++){
                            TextView tv = findViewById(progress[i]);
                            tv.setBackgroundResource(R.drawable.style_points_green);

                        }
                    }else{
                        //если левая картинка меньше
                        if(count[0][0]>0){
                            if(count[0][0]==1){
                                count[0][0]=0;
                            }else{
                                count[0][0]= count[0][0]-2;
                            }
                        }
                        //закрашиваем прогресс серым
                        for (int i=0; i<6; i++){
                            TextView tv = findViewById(progress[i]);
                            tv.setBackgroundResource(R.drawable.style_points);
                        }
                        //определяем правильнность ответа и закрашиваем зеленым
                        for (int i = 0; i< count[0][0]; i++){
                            TextView tv = findViewById(progress[i]);
                            tv.setBackgroundResource(R.drawable.style_points_green);

                        }

                    }
                    //если отпустил палец конец
                    if(count[0][0]==7){
                        //ВЫХОД ИЗ УРОВНЯ
                    }else{
                        numLeft[0] = random.nextInt(10);
                        img_left.setImageResource(array.images1[numLeft[0]]);
                        img_left.startAnimation(a);
                        text_left.setText(array.texts1[numLeft[0]]);

                        numRight[0] = random.nextInt(10);
                        //цикл, проверяющий равенство чисел
                        while (numLeft[0] == numRight[0]) {
                            numRight[0] = random.nextInt(10);
                        }
                        img_right.setImageResource(array.images1[numRight[0]]);
                        img_right.startAnimation(a);
                        text_right.setText(array.texts1[numRight[0]]);

                        img_left.setEnabled(true);//ключаем левую картинку

                    }
                }
                return true;
            }
        });

    }
    //системная кнопка назад
    @Override
    public void onBackPressed(){
        try {
            //вернуться к выбору уровня
            Intent intent = new Intent(Level1.this, GameLevels.class);
            startActivity(intent);
            finish();

        }catch(Exception e){}
    }

    }
