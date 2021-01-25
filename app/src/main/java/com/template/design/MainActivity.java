package com.template.design;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.ComponentName;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.template.design.Build.ApplePCBuilder;
import com.template.design.Build.Builder;
import com.template.design.Build.Director;
import com.template.design.Combination.AbstractFile;
import com.template.design.Combination.Directory;
import com.template.design.Combination.File;
import com.template.design.IPC.Messenger.MessengerService;
import com.template.design.IPC.Socket.SocketServerService;
import com.template.design.IPC.aidl.AIDLService;
import com.template.design.Observer.Coder;
import com.template.design.Observer.DevTechFrontier;
import com.template.design.Proxy.AbstractObject;
import com.template.design.Proxy.ProxyObject;
import com.template.design.Adapter.ClassAdapter;
import com.template.design.Adapter.ObjectAdapter;
import com.template.design.Adapter.Volt220;
import com.template.design.Command.ClientRole;
import com.template.design.Factory.Banana;
import com.template.design.Factory.Cucumber;
import com.template.design.Factory.Fruits;
import com.template.design.Factory.Grower;
import com.template.design.Factory.Sugarcane;
import com.template.design.Singleton.DCL;
import com.template.design.Singleton.Hungry;
import com.template.design.Singleton.Lazy;
import com.template.design.Singleton.SingletonEnum;
import com.template.design.Singleton.SingletonManager;
import com.template.design.Singleton.Static;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private Messenger mMessenger;
    private PrintWriter mPrintWriter;
    private Socket mClientSocket;
    private TextView tv_message;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //单例模式
//        single();

//        工厂方法模式 任何需要生成对象的情况都可使用工厂方法替代生成
//        factory();

        //观察者模式 定义对象间一种一对多的依赖关系，使得每当一个对象改变状态，则所有依赖于它的对象都会得到通知并被自动更新。
//        observer();

        //建造者模式 将一个复杂对象的构建与它的表示分离，使得同样的构建过程可以创建不同的表示。
//        build();

        //代理模式 代理模式给某一个对象提供一个代理对象，并由代理对象控制对原对象的引用。
//        proxy();

        //适配器模式 适配器模式把一个类的接口变换成客户端所期待的另一种接口，从而使原本因接口不匹配而无法在一起工作的两个类能够在一起工作。
//        adapter();

        //命令模式 将一个请求封装成一个对象，从而使你可用不同的请求对客户进行参数化
//        command();

        //组合模式 将对象组合成树形结构以表示“部分—整体”的层次结构，使用户对单个对象和组合对象的使用具有一致性。
//        combination();  不常用

        //IPC 进程间通信
        // 序列化 Messenger AIDL Bundle 文件共享 ContentProvider
//        messenger();
//        aidl();
//        ContentProvider();
        Socket();

        //Binder是Android跨进程通信方式，它实现了IBinder接口，是ServiceManager连接各种Manager(如WindowManager、ActivityManager等)的桥梁

    }

    private void Socket() {
       final EditText et_receive =findViewById(R.id.et_receive);
        Button bt_send=findViewById(R.id.bt_send);
        tv_message = findViewById(R.id.tv_message);
        bt_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        final String str = et_receive.getText().toString();
                        mPrintWriter.println(str);
                        if (!TextUtils.isEmpty(str)&&null!=mPrintWriter){
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    tv_message.setText(tv_message.getText()+"\n"+"客户端："+str);
                                    et_receive.setText("");
                                }
                            });

                        }
                    }
                }).start();

            }
        });

        Intent intent=new Intent(this, SocketServerService.class);
        startService(intent);
        new Thread(new Runnable() {
            @Override
            public void run() {
                connectSocketServer();
            }
        }).start();
    }

    private void connectSocketServer() {
        Socket socket=null;
        while (socket ==null){
            try {
                socket=new Socket("localhost",8688);
                mClientSocket=socket;
                mPrintWriter=new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())),true);
            } catch (IOException e) {
                e.printStackTrace();
            }

        }

        try {
            BufferedReader br=new BufferedReader(new InputStreamReader(socket.getInputStream()));
            while (!isFinishing()){
                final String msg=br.readLine();
                if (msg!=null){
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            tv_message.setText(tv_message.getText()+"\n"+" 服务端："+msg);
                        }
                    });
                }
            }
            mPrintWriter.close();
            br.close();
            socket.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void ContentProvider() {
        Uri uri=Uri.parse("content://com.template.design.IPC.ContentProvider.GameProvider");
        ContentValues mContentValues=new ContentValues();
        mContentValues.put("_id",2);
        mContentValues.put("name","大航海时代ol");
        mContentValues.put("describe","最好玩的航海网游");
        getContentResolver().insert(uri,mContentValues);
        Cursor gameCursor = getContentResolver().query(uri, new String[]{"name", "describe"}, null, null, null);
        while (gameCursor.moveToNext()){
            Game mGame=new Game(gameCursor.getString(0),gameCursor.getString(1));
            Log.e("zzz",mGame.gameName+"---"+mGame.gameDescribe);
        }


    }

    private void aidl() {
        Intent intent=new Intent(this, AIDLService.class);
        bindService(intent,mAIDLServiceConnection, Context.BIND_AUTO_CREATE);
    }

    private void messenger() {
        Intent intent=new Intent(this, MessengerService.class);
        bindService(intent,mServiceConnection, Context.BIND_AUTO_CREATE);
    }

    @SuppressLint("HandlerLeak")
    private Handler mHandler=new Handler(){
        @Override
        public void handleMessage(@NonNull Message msg) {
//            super.handleMessage(msg);
            switch (msg.what){
                case MessengerService.MSG_FROMCLIENT:
                    Log.e(MessengerService.TAG,"收到服务端消息-----------"+msg.getData().get("rep"));
                    break;
            }
        }
    };

    private ServiceConnection mAIDLServiceConnection=new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            IGameManager iGameManager=IGameManager.Stub.asInterface(iBinder);
            Game game=new Game("月影传说","最好玩的武侠单机手游");
            try {
                iGameManager.addGame(game);
                List<Game> gameList = iGameManager.getGameList();
                for (int i = 0; i < gameList.size(); i++) {
                    Game mGame = gameList.get(i);
                    Log.e("AIDLService",mGame.gameName+"-----"+mGame.gameDescribe);
                }
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {

        }
    };

    private ServiceConnection mServiceConnection=new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            mMessenger=new Messenger(iBinder);
            Message mMessage=Message.obtain(null,MessengerService.MSG_FROMCLIENT);
            Bundle mBundle=new Bundle();
            mBundle.putString("msg","这里是客户端，服务端收到了吗");
            mMessage.setData(mBundle);
            mMessage.replyTo=new Messenger(mHandler);
            try {
                mMessenger.send(mMessage);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {

        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbindService(mServiceConnection);
        unbindService(mAIDLServiceConnection);
    }

    private void single() {
        Hungry.getSingle();
        Lazy.getInstance();
        DCL.getInstance();
        Static.getInstance();
        SingletonEnum.INSTANCE.doSomething();
        SingletonManager.registerService("single","??");
        SingletonManager.getService("single");
    }

    private void build() {
        // 构建器
        Builder builder = new ApplePCBuilder();
        // Director
        Director pcDirector = new Director(builder);
        // 封装构建过程, 4核, 内存2GB, Mac系统
        pcDirector.construct(4, 2, "Mac OS X 10.9.1");
        // 构建电脑, 输出相关信息
        System.out.println("Computer Info : " + builder.create().toString());
    }

    private void combination() {
        Directory directory1 = new Directory("程序员");
        File file1 = new File("Android Studio.exe");
        File file2 = new File("Eclipse.exe");
        File file3 = new File("Android源码设计模式解析与实战.pdf");
        File file4 = new File("Android开发艺术探索.pdf");

        Directory directory2 = new Directory("放松");
        File file5 = new File("极品飞车.exe");
        File file6 = new File("明朝那些事儿.txt");
        directory2.addFile(file5);
        directory2.addFile(file6);

        directory1.addFile(file1);
        directory1.addFile(file2);
        directory1.addFile(file3);
        directory1.addFile(file4);
        directory1.addFile(directory2);

        if (directory1.isDirectory()) {
            for (AbstractFile abstractFile1 : directory1.getFiles()) {
                if (abstractFile1.isDirectory()) {
                    for (AbstractFile abstractFile2 : abstractFile1.getFiles()) {
                        Log.e("zzz", "abstractFile2:" + abstractFile2.getName());
                    }
                } else {
                    Log.e("zzz", "abstractFile1:" + abstractFile1.getName());
                }
            }
        }
    }

    private void command() {
        ClientRole client = new ClientRole();
        client.assembleAction();
    }

    private void proxy() {
        AbstractObject obj = new ProxyObject();
        obj.operation();
    }

    private void observer() {
        // 被观察的角色
        DevTechFrontier devTechFrontier = new DevTechFrontier();
        // 观察者
        Coder coder1 = new Coder("coder1");
        Coder coder2 = new Coder("coder2");
        Coder coder3 = new Coder("coder3");
        // 将观察者注册到可观察对象的观察者列表中
        devTechFrontier.addObserver(coder1);
        devTechFrontier.addObserver(coder2);
        devTechFrontier.addObserver(coder3);
        // 发布消息
        devTechFrontier.postNewPublication("新的一期开发技术前线周报发布啦！！！");
    }

    private void adapter() {
        ClassAdapter adapter = new ClassAdapter();
        System.out.println("输出电压 : " + adapter.getVolt5());
        ObjectAdapter adapter2 = new ObjectAdapter(new Volt220());
        System.out.println("输出电压 : " + adapter2.getVolt5());
    }

    private void factory() {
        Fruits banana = Grower.getFruits(Banana.class);
        banana.color();
        banana.weight();

        Fruits cucumber = Grower.getFruits(Cucumber.class);
        cucumber.color();
        cucumber.weight();

        Fruits sugarcane = Grower.getFruits(Sugarcane.class);
        sugarcane.color();
        sugarcane.weight();
    }
}