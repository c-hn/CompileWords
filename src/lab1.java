import com.sun.xml.internal.ws.util.StringUtils;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class lab1 {
    static boolean error=false;
    static int number=0;
    static char [] hexadecimal_digit={'0','1','2','3','4','5','6','7','8','9'
            ,'a','b','c','d','e','f'
            ,'A','B','C','D','E','F'};
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
            a.add(b[i]);
        }
        if(a.size()!=6||!a.get(0).equals("int") || !a.get(1).equals("main()") || !a.get(2).equals("{") || !a.get(3).equals("return") || !a.get(5).equals("}")){
            error=true;
            return;
        }
        if(a.get(4).charAt(a.get(4).length()-1)!=';'){
            error=true;
            return;
        }
        if(a.get(4).charAt(0)=='0'){
            if(a.get(4).charAt(1)=='x'||a.get(4).charAt(1)=='X'){
                for(i=2;i<a.get(4).length()-1;i++){
                    for(j=0;j<22;j++){
                        if(a.get(4).charAt(i)==hexadecimal_digit[j])
                            break;
                    }
                    if(j==22){
                        error=true;
                        return;
                    }
                    if(j>=16)
                        j-=6;
                    number=number*16+j;
                }
            }else {
                for(i=1;i<a.get(4).length()-1;i++){
                    if(!Character.isDigit(a.get(4).charAt(i))||Integer.parseInt(String.valueOf(a.get(4).charAt(i)))>7){
                        error=true;
                        return;
                    }
                    number=number*8+Integer.parseInt(String.valueOf(a.get(4).charAt(i)));
                }
            }
        }else {
            for(i=0;i<a.get(4).length()-1;i++){
                if(!Character.isDigit(a.get(4).charAt(i))){
                    error=true;
                    return;
                }
                number=number*10+Integer.parseInt(String.valueOf(a.get(4).charAt(i)));
            }
        }
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
            System.exit(2);
        else {
            System.out.println("define dso_local i32 @main(){\n" +
                    "    ret i32 " + number +
                    "\n}");
        }
    }
}
