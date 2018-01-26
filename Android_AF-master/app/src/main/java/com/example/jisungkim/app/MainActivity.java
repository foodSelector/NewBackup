package com.example.jisungkim.app;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    public static String msgStack = "";
    Elements elements;
    ArrayList<String> textList;

    @BindView(R.id.alone) LinearLayout alone;
    @BindView(R.id.date)   LinearLayout date;
    @BindView(R.id.friend)     LinearLayout friend;
    @BindView(R.id.family)     LinearLayout family;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        alone.setOnClickListener(this);
        date.setOnClickListener(this);
        friend.setOnClickListener(this);
        family.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        Intent intent;

        ResConnection res = new ResConnection();
        res.execute();

        switch (view.getId()) {
            case R.id.alone:
                intent = new Intent(this, Alone.class);
//                for (int i=1; i < elements.size(); i++) {
//                    intent.putExtra("a"+i, textList.get(i-1));
//                }
                intent.putExtra("a1","");
                intent.putExtra("a2","혼밥의 고수");
                intent.putExtra("a3","오늘도나혼자밥");
                intent.putExtra("a4","혼밥혼밥");
                startActivity(intent);
                break;
            case R.id.date:
                intent = new Intent(MainActivity.this, Date.class);
//                for (int i=0; i < elements.size(); i++) {
//                    intent.putExtra("a"+i+1, name_list[i]);
//                }
                startActivity(intent);
                break;
            case R.id.friend:
                intent = new Intent(MainActivity.this, Friend.class);
//                for (int i=0; i < elements.size(); i++) {
//                    intent.putExtra("a"+i+1, name_list[i]);
//                }
                startActivity(intent);
                break;
            case R.id.family:
                intent = new Intent(MainActivity.this, Family.class);
//                for (int i=0; i < elements.size(); i++) {
//                    intent.putExtra("a"+i+1, name_list[i]);
//                }
                startActivity(intent);
                break;
        }
    }

    public class ResConnection extends AsyncTask<String, String, ArrayList<String>> {

        @Override
        protected ArrayList<String> doInBackground(String... strings) {
            // Jsoup을 이용한 맛집 데이터 Parsing하기 try
            try{
                //성신여대 맛집,데이트맛집,가족맛집,친구맛집 조회url
                String path = "https://search.naver.com/search.naver?where=nexearch&sm=top_hty&fbm=1&ie=utf8&query=성신여대+맛집";

                Document document = Jsoup.connect(path).get();
                elements = document.select("a.name[title]");

                System.out.println(elements);

                textList = new ArrayList<String>();

                for (int i=0; i < elements.size(); i++) {
                    textList.add(elements.get(i).attr("title").toString());
                }
                return textList;
            }catch (Exception e){
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(ArrayList<String> textList) {
            super.onPostExecute(textList);

            for(int i = 0; i < textList.size(); i++) {
                msgStack += textList.get(i) + " ";
            }

            Toast.makeText(getApplicationContext(), msgStack, Toast.LENGTH_LONG).show();

        }
    }
}
