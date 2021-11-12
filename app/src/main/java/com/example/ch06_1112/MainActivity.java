package com.example.ch06_1112;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

public class MainActivity extends AppCompatActivity implements ActionBar.TabListener{

    ActionBar.Tab tabSong, tabArtist, tabAlbum;//프래그먼트랑 연결해야하기 때문에 멤버변수로 격상되었음

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_main);
        //1."액션바" 만들기
        //2."탭" 만들기 - 액션바에 들어갈 탭
        //3."프레그먼트" 만들기
        //4. 탭과 프래그먼트 연결하기

        //1."액션바" 만들기
        ActionBar bar = getSupportActionBar();
        bar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

        //2."탭" 만들기 - 액션바에 들어갈 탭
//        ActionBar.Tab tabSong, tabArtist, tabAlbum;//프래그먼트랑 연결해야하기 때문에 멤버변수로 격상

        tabSong = bar.newTab();//탭 만들기
        tabSong.setText("음악별");//탭 옵션정하기
//        tabSong.setTabListener(리스너) 탭에 리스너를 달기(이벤트처리)
        tabSong.setTabListener(this);//(리스너) 탭에 리스너를 달기(이벤트처리)
        bar.addTab(tabSong);//bar에 탭을 추가

        tabArtist = bar.newTab();
        tabArtist.setText("가수별");
        tabArtist.setTabListener(this);
        bar.addTab(tabArtist);

        tabAlbum = bar.newTab();
        tabAlbum.setText("앨범별");
        tabAlbum.setTabListener(this);
        bar.addTab(tabAlbum);

    }

    @Override
    public void onTabSelected(ActionBar.Tab tab, FragmentTransaction ft) {

        MyTabFragment myTabFrag = null;//맴버변수로 격상
        MyTabFragment[] myFrags = new MyTabFragment[3];

        if(myFrags[tab.getPosition()] == null){ //눌러진 탭인텍스가 배열에 있나 없나 조회
            //처음이라면
            //1.프래그먼트 생성 -> new MyFragment()
            //2.옵션: 프래그먼트 안에 리니어 색상 결정 -> Os로부터 받아온 Bundle의 키 중에서 "tabName"
            //3.프래그먼트와 탭연결
            //4.새로만든 프래그먼트1개 짜리를 프래그먼트배열에 넣어주기

            //1.프래그먼트 생성 -> new MyFragment()
            myTabFrag = new MyTabFragment();

            //2.옵션: 프래그먼트 안에 리니어 색상 결정
            // -> OS로 보내려면 Bundle이 필요함
            //Bundle안에 key를 tabName으로 정함
            //tabName 안에 "가수별","앨범별","음악별" 이런 실제값을 보냄,
            Bundle data = new Bundle();
            data.putString("tabName", tab.getText().toString());
            myTabFrag.setArguments(data);

            //3.프래그먼트와 탭연결// 저아래서 공용으로
//            ft.replace(android.R.id.content, myTabFrag);

            //4.새로만든 프래그먼트1개 짜리를 프래그먼트배열에 넣어주기
            //나중을 위해서, 배열안에 프래그 있는지로 처음인지 아닌지 판별
            myFrags[tab.getPosition()] = myTabFrag;
        }
        else//처음아님, 아까 왔었음
            myTabFrag = myFrags[tab.getPosition()];//프래그먼트 생성 안함! 왜? 배열안에 있으니까, 그거 꺼내옴

        //3.프래그먼트와 탭연결
        ft.replace(android.R.id.content, myTabFrag);
    }

    @Override
    public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction ft) {

    }

    @Override
    public void onTabReselected(ActionBar.Tab tab, FragmentTransaction ft) {

    }

    //3."프레그먼트" 만들기
    //멤버 자리에 - 1.멤변, 2.생성자, 3.메소드, 4,내부클래스
    public static class MyTabFragment extends androidx.fragment.app.Fragment{

        //alt + insert 키로, override method
        //1.onCreate() 와 2.onCreateView() 메소드 2개를 재정의한다.

        String tabName;//멤버변수로 격상됨, 왜? onCreateView()에 쓸꺼니까

        //1.탭과 프래그먼트(=me)를 연결하기 위해 탭이름과 연동 설정
        @Override
        public void onCreate(Bundle saveInstanceState) {
            super.onCreate(saveInstanceState);

            Bundle data = getArguments();//OS와 통신
            tabName = data.getString("tabName");//OS의 Bundle의 키로 값을 가져옴
        }

        //프레그먼트(내부에)가 가지는 들어갈 디자인 만들기, 예)리니어레이아웃
        @Nullable
        @Override
        public View onCreateView(@NonNull LayoutInflater inflater,@NonNull ViewGroup container,@NonNull Bundle savedInstanceState){
            //프레그먼트 뷰임, 리니어레이아웃을 하나 가질 예정임
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.MATCH_PARENT);
            LinearLayout baseLayout = new LinearLayout((super.getActivity()));
            baseLayout.setOrientation(LinearLayout.VERTICAL);
            baseLayout.setLayoutParams(params);

            if(tabName=="음악별") baseLayout.setBackgroundColor(Color.RED);
            if(tabName=="가수별") baseLayout.setBackgroundColor(Color.GREEN);
            if(tabName=="앨범별") baseLayout.setBackgroundColor(Color.BLUE);

            return baseLayout;
//            return 리니어;
//            return super.onCreateView(inflater, container, savedInstanceState);
        }
    }



}