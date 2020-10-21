package HomeWorkDemo;

import java.io.*;
import java.util.concurrent.locks.ReentrantLock;

//多线程读取文件，文件内容自定义，使用多个线程读取一个文件，每个线程读取一部分，然后拼接到另外一个文件，
// 在每个线程读取内容的前面加上线程名。
//        注意：
//        1、在读取的时候，请尽量使用并发执行。
//        2、本次考核其实可以理解为文件的切割与合并。
//        任务提交时间：11月6日之前（20天）。提交方式：github链接+学号姓名。
//        本次任务完成后将会安排一次面试，考核各位同学对于所学习内容的情况。
public class FileReaderDemo {
    public static void main(String[] args) throws IOException {
        Thread1 t1 = new Thread1();
        Thread T1 = new Thread(t1);
        Thread T2 = new Thread(t1);
        T1.setName("线程1");
        T2.setName("线程2");
        T1.start();
        T2.start();
    }

    static class Thread1 implements Runnable {
        static boolean a = true;
        static int off=0;
        ReentrantLock lock = new ReentrantLock();
        static File file = new File("HomeWorkFile.txt");
        static FileReader fr;

        static {
            try {
                fr = new FileReader(file);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }

        static FileWriter fw;

        static {
            try {
                fw = new FileWriter("Write.txt",true);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        static int data;

        static {
            try {
                data = fr.read();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        @Override
        public synchronized void run() {
            try{
            if(a == true){
                char[] A= new char[100];
                for (int i = 0; i < 100; i++) {
                        A[i] = (char) data;
                        data = fr.read();
                }
                fw.write(Thread.currentThread().getName()+"read:"+"\n");
                fw.write(A);
                a=false;
            }else if(a==false){
                char[] B= new char[200];
                for (int i = 0; i < 200; i++) {
                    if (data!=-1){
                        B[i] = (char)data;
                        data = fr.read();
                    }
                }
                fw.write(Thread.currentThread().getName()+"read:"+"\n");
                fw.write(B);
                fw.close();
                fr.close();
            }
        }catch (IOException e){
                e.printStackTrace();
            }
        }
    }
}



