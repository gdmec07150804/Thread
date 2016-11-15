package cn.edu.gdmec.s07150804.thread;

import android.app.Activity;
import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.pdf.PdfRenderer;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Date;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {
    private TextView tv1;
    private int seconds;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tv1=(TextView)findViewById(R.id.tv1);
        Date thelastday=new Date(117,5,23);
        Date today=new Date();
        seconds=(int)(thelastday.getTime()-today.getTime())/1000;
    }
    public void anr(View v){
        for (int i=0;i<10000;i++){
            BitmapFactory.decodeResource(getResources(),R.drawable.aaa);
        }
    }
    public void threadclass(View v){
        class ThreadSample extends Thread{
           Random rm;
            public ThreadSample(String tname){
                super(tname);
                rm=new Random();
            }
            public void run(){
                for (int i=0;i<10;i++){
                    System.out.println(i+" "+getName());
                    try {
                        sleep(rm.nextInt(1000));
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                System.out.println(getName()+"完成");
            }
        }
        ThreadSample thread1=new ThreadSample("线程一");
        thread1.start();
        ThreadSample thread2=new ThreadSample("线程二");
        thread2.start();
    }
    public void runnableinterface(View v){
        class RubnableExample implements Runnable{
            Random rm;
            String name;
            public RubnableExample(String tname){
                this.name=tname;
                rm=new Random();
            }
            @Override
            public void run() {
               for (int i=0;i<10;i++){
                   System.out.println(i+" "+name);
                   try {
                       Thread.sleep(rm.nextInt(1000));
                   } catch (InterruptedException e) {
                       e.printStackTrace();
                   }
               }
                System.out.println(name+"完成");
            }
        }
        Thread thread1=new Thread(new RubnableExample("线程一"));
        thread1.start();
        Thread thread2=new Thread(new RubnableExample("线程二"));
        thread2.start();
    }
    public void timertask(View v){
        class MyThread extends TimerTask{
            Random rm;
            String name;
            public MyThread(String tname){
                this.name=tname;
                rm=new Random();
            }
            @Override
            public void run() {
                for (int i=0;i<10;i++){
                    System.out.println(i+" "+name);
                    try {
                        Thread.sleep(rm.nextInt(1000));
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                System.out.println(name+"完成");
            }
        }
        Timer timer1=new Timer();
        Timer timer2=new Timer();
        MyThread thread1=new MyThread("线程一");
        MyThread thread2=new MyThread("线程二");
        timer1.schedule(thread1,0);
        timer2.schedule(thread2,0);
    }
    public void handlermessage(View v){


        final Handler myHandler=new Handler(){
            public void handlerMessage(Message msg){
                super.handleMessage(msg);
                switch (msg.what){
                    case 1:
                        showmsg(String.valueOf(msg.arg1+msg.getData().get("attach").toString()));
                }
            }

        };
        class MyTask extends TimerTask{
            int countdown;
            double achienement1=1,achienement2=1;
            public MyTask(int seconds){
                this.countdown=seconds;
            }
            @Override
            public void run() {
                Message msg=Message.obtain();
                msg.what=1;
                msg.arg1=countdown--;
                achienement1=achienement1+1.01;
                achienement2=achienement2+1.01;
                Bundle bundle=new Bundle();
                bundle.putString("attach","\n努力多1%:"+achienement1+"\n努力多2%:"+achienement2);
                msg.setData(bundle);
                myHandler.sendMessage(msg);
            }
        }
        Timer timer=new Timer();
        timer.schedule(new MyTask(seconds),1,1000);
    }

    private void showmsg(String msg) {
        tv1.setText(msg);
    }
    public void asynctask(View v){
        class LearHard extends AsyncTask<Long,String,String>{
            private Context context;
            final int duration=10;
            int count=0;
            public LearHard(Activity context){
                this.context=context;
            }
            @Override
            protected String doInBackground(Long... params) {
              Long num=params[0].longValue();
                while (count<duration){
                    num--;
                    count++;
                    String status="离毕业还有"+num+"秒，努力学习"+count+"秒。";
                    publishProgress(status);
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                return "这"+duration+"无虚度";
            }

            @Override
            protected void onProgressUpdate(String... values) {
                ((MainActivity)context).tv1.setText(values[0]);
                super.onProgressUpdate(values);
            }

            @Override
            protected void onPostExecute(String s) {
              showmsg(s);
                super.onPostExecute(s);
            }
        }
        LearHard learHard=new LearHard(this);
        learHard.execute((long)seconds);

    }
}
