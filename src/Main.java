import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.lang.*;

public class Main {
    static Character [] alpahas={'=','>','<','+','*','/','(',')','{','}','_',';'};
    static String [] before={"if","else","while","break","continue","return","=",";","(",")","{","}","+","*","/","<",">","=="};
    static String [] after={"If","Else","While","Break","Continue","Return","Assign","Semicolon","LPar","RPar",
        "LBrace","RBrace","Plus","Mult","Div","Lt","Gt","Eq"};
    static List<String> beforelist= Arrays.asList(before);
    static boolean error=false;
    static void judge(String s){
        int index=beforelist.indexOf(s);
        if(index!=-1){
            System.out.println(after[index]);
        }else {
            boolean number=false;
            boolean ident=false;
            StringBuilder numOrIde= new StringBuilder();
            for(int j=0;j<s.length();j++){
                if(!(Character.isLetterOrDigit(s.charAt(j))|| Arrays.asList(alpahas).contains(s.charAt(j)))){
                    error=true;
                    System.out.println("Err");
                    break;
                }
            }
            if(error)
                return;
            for(int j=0;j<s.length();j++){
                if(Character.isLetterOrDigit(s.charAt(j)) || s.charAt(j)=='_'){
                    if(numOrIde.length()==0){
                        if(Character.isDigit(s.charAt(j)))
                            number=true;
                        else if(Character.isLowerCase(s.charAt(j)) || Character.isUpperCase(s.charAt(j)) || s.charAt(j)=='_')
                            ident=true;
                        numOrIde.append(s.charAt(j));
                    }else {
                        if(Character.isDigit(s.charAt(j)))
                            numOrIde.append(s.charAt(j));
                        else if(Character.isLowerCase(s.charAt(j)) || Character.isUpperCase(s.charAt(j)) || s.charAt(j)=='_'){
                            if(ident)
                                numOrIde.append(s.charAt(j));
                            if(number){
                                System.out.println("Number("+numOrIde+")");
                                numOrIde=new StringBuilder();
                                numOrIde.append(s.charAt(j));
                                number=false;
                                ident=true;
                            }
                        }
                    }
                }else {
                    if(numOrIde.length()!=0){
                        if(number)
                            System.out.println("Number("+numOrIde+")");
                        if(ident){
                            int index2=beforelist.indexOf(numOrIde.toString());
                            if(index2!=-1)
                                System.out.println(after[index2]);
                            else
                                System.out.println("Ident("+numOrIde+")");
                        }
                    }
                    numOrIde=new StringBuilder();
                    number=false;
                    ident=false;
                    if(j<s.length()-1&&s.charAt(j)=='='&&s.charAt(j+1)=='='){
                        System.out.println("Eq");
                        if(j==s.length()-2){
                            break;
                        }
                        j++;
                    }else if(!(Character.isLetterOrDigit(s.charAt(j))||s.charAt(j)=='_')){
                        String s1=String.valueOf(s.charAt(j));
                        int index1=beforelist.indexOf(s1);
                        System.out.println(after[index1]);
                    }
                }

            }
            if(numOrIde.length()!=0){
                if(number)
                    System.out.println("Number("+numOrIde+")");
                if(ident){
                    int index2=beforelist.indexOf(numOrIde.toString());
                    if(index2!=-1)
                        System.out.println(after[index2]);
                    else
                        System.out.println("Ident("+numOrIde+")");
                }
            }
        }
    }
    public static void main(String[] args) throws IOException {
        File file = new File(args[0]);
        FileReader reader = new FileReader(file);
        BufferedReader bReader = new BufferedReader(reader);
        String s ;

        while ((s =bReader.readLine()) != null) {
            if(error){
                break;
            }
            String[] a = s.split("\\n|\\s");
            for (int i=0;i<a.length;i++){
                if(error){
                    break;
                }
                judge(a[i]);
            }
        }
    }
}
