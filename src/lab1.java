import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class lab1 {
    static boolean error=false;
    static int number=0;
    static char [] hexadecimal_digit={'0','1','2','3','4','5','6','7','8','9'
            ,'a','b','c','d','e','f'
            ,'A','B','C','D','E','F'};
    static String [] attachWords={"main","{","}","(",")",";"};
    static String [] notAttachWords={"int","return"};//不能连接只是后面不能，前面可以
    static String [] lab1words={"int","main","(",")","{","return","",";","}"};
    static List<String> attachWordsList= Arrays.asList(attachWords);
    static List<String> notAttachWodsList= Arrays.asList(notAttachWords);
    static List<String> words=new LinkedList<>();//整理后分开的语句
    static List<Integer> numbers=new LinkedList<>();
    static String addSpace(String s){
        StringBuffer sb=new StringBuffer(s);
        for(int i=sb.length()-1;i>0;i--){
            if(sb.charAt(i)=='/'&&sb.charAt(i-1)=='*')
                sb.insert(i+1,' ');
            if(sb.charAt(i)=='*'&&sb.charAt(i-1)=='/')
                sb.insert(i-1,' ');
            if(sb.charAt(i)=='/'&&sb.charAt(i-1)=='/')
                sb.insert(i-1,' ');
            if(sb.charAt(i)=='\n'){
                sb.insert(i+1,' ');
                sb.insert(i,' ');
            }
        }
        return String.valueOf(sb);
    }
    static void splitWords(String s){
        StringBuilder tmp=new StringBuilder();
        for (int i=0;i<s.length();i++){
            if(Character.isLetterOrDigit(s.charAt(i)))
                tmp.append(s.charAt(i));
            else {
                if(tmp.length()!=0){
                    String str=tmp.toString();
                    if(Character.isDigit(str.charAt(0))){
                        numberChange(str);
                        words.add(String.valueOf(number));
                    }
                    else if(attachWordsList.contains(str)) //注意之后如果是定义一个变量这里也会错
                        words.add(str);
                    else 
                        System.exit(3);
                    tmp=new StringBuilder();
                }
                if(attachWordsList.contains(String.valueOf(s.charAt(i)))){
                    words.add(String.valueOf(s.charAt(i)));
                }
                else
                    System.exit(2);
                
            }
        }
        if(tmp.length()!=0){
            String str=tmp.toString();
            if(Character.isDigit(str.charAt(0))){
                numberChange(str);
                words.add(String.valueOf(number));
                number=0;
            }
            else if(attachWordsList.contains(str)||notAttachWodsList.contains(str)) //注意之后如果是定义一个变量这里也会错
                words.add(str);
            else
                System.exit(4);
        }
    }
    static void numberChange(String s){
        int i,j;
        if(s.charAt(0)=='0'){
            if(s.length()>1&&(s.charAt(1)=='x'||s.charAt(1)=='X')){
                for(i=2;i<s.length();i++){
                    for(j=0;j<22;j++){
                        if(s.charAt(i)==hexadecimal_digit[j])
                            break;
                    }
                    if(j==22){
                        error=true;
                        System.exit(1);
                    }
                    if(j>=16)
                        j-=6;
                    number=number*16+j;
                }
            }else {
                for(i=1;i<s.length();i++){
                    if(!Character.isDigit(s.charAt(i))||Integer.parseInt(String.valueOf(s.charAt(i)))>7){
                        error=true;
                        System.exit(1);
                    }
                    number=number*8+Integer.parseInt(String.valueOf(s.charAt(i)));
                }
            }
        }else {
            for(i=0;i<s.length();i++){
                if(!Character.isDigit(s.charAt(i))){
                    error=true;
                    System.exit(4);
                }
                number=number*10+Integer.parseInt(String.valueOf(s.charAt(i)));
            }
        }
    }
    static void judge(String s){
        String[] b = s.split(" +");
        int index=0;
        List<String> a=new ArrayList<>();

        int i,j;
        for(i=0;i<b.length;i++){
            if(b[i].contains("//")){
                while(true){
                    if(b[++i].contains("\n"))
                        break;
                }
                continue;
            }
            if(b[i].contains("/*")){
                while (true){
                    if(b[++i].contains("*/"))
                        break;
                }
                continue;
            }
            if(b[i].equals("\n"))
                continue;
            if(attachWordsList.contains(b[i])||notAttachWodsList.contains(b[i]))
                words.add(b[i]);
            else
                splitWords(b[i]);
        }
        for(i=0;i<words.size();i++){
            if(i!=6){
                if(!words.get(i).equals(lab1words[i]))
                    System.exit(5);
            }
        }
//        if(a.get(4).charAt(0)=='0'){
//            if(a.get(4).charAt(1)=='x'||a.get(4).charAt(1)=='X'){
//                for(i=2;i<a.get(4).length()-1;i++){
//                    for(j=0;j<22;j++){
//                        if(a.get(4).charAt(i)==hexadecimal_digit[j])
//                            break;
//                    }
//                    if(j==22){
//                        error=true;
//                        return;
//                    }
//                    if(j>=16)
//                        j-=6;
//                    number=number*16+j;
//                }
//            }else {
//                for(i=1;i<a.get(4).length()-1;i++){
//                    if(!Character.isDigit(a.get(4).charAt(i))||Integer.parseInt(String.valueOf(a.get(4).charAt(i)))>7){
//                        error=true;
//                        return;
//                    }
//                    number=number*8+Integer.parseInt(String.valueOf(a.get(4).charAt(i)));
//                }
//            }
//        }else {
//            for(i=0;i<a.get(4).length()-1;i++){
//                if(!Character.isDigit(a.get(4).charAt(i))){
//                    error=true;
//                    return;
//                }
//                number=number*10+Integer.parseInt(String.valueOf(a.get(4).charAt(i)));
//            }
//        }
    }

    public static void main(String[] args) throws IOException {
        File file = new File(args[0]);
        FileReader reader = new FileReader(file);
        BufferedReader bReader = new BufferedReader(reader);
        String s;
        StringBuilder s1= new StringBuilder();
        while ((s =bReader.readLine()) != null) {
            s1.append(s);
            s1.append('\n');
        }
        judge(addSpace(s1.toString()));
        if(error)
            System.exit(1);
        else {

            FileWriter fw = new FileWriter(args[1], true);
            PrintWriter pw = new PrintWriter(fw);
            pw.print("define dso_local i32 @main(){\n" +
                    "    ret i32 " + number +
                    "\n}");
            pw.flush();
        }
    }
}
