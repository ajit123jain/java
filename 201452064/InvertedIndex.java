import java.io.*;
import java.util.*;
import java.io.BufferedReader;
import java.io.File;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
public class InvertedIndex{
    public static Set<String> set =null;   //hashset for stopword
    public static HashMap<String,HashSet<Integer>>invert_index  = new HashMap<String,HashSet<Integer>>(); //hashmap for inverted index
    public static Steam stm=new Steam();
    public static int id=0,p=0;
    public static BufferedReader bufferedReader=null;
    public static FileInputStream inputfilename=null;
    
public static void main(String[] args) throws Exception {
      long startTime = System.currentTimeMillis();         
      File f = new File("Maps.ser");
      boolean bool = false;
      bool = f.exists();                   //if binary file exists or not
      try{
         if(bool==false){                                 //binary file not extists
	            	File folder = new File("/home/ajit/Desktop/201452064/hindi");  //folder path
                File[] listOfFiles = folder.listFiles();               //stroing filename in array of filetype
       String[] stopwords = { "से", "हैं", "को", "पर", "इस", "होता", "कि", "जो", "कर", "मे", "गया", "करने", "किया", "लिये", "अपने", "ने", "बनी", "नहीं", "तो", "ही", "या", "एवं", "दिया", "हो", "इसका", "था", "द्वारा", "हुआ", "तक", "साथ", "करना", "वाले", "बाद", "लिए", "आप", "कुछ", "सकते", "किसी", "ये", "इसके", "सबसे", "इसमें", "थे", "दो", "होने", "वह", "वे", "करते", "बहुत", "कहा", "वर्ग", "कई", "करें", "होती", "अपनी", "उनके", "थी", "यदि", "हुई", "जा", "ना", "इसे", "कहते", "जब", "होते", "कोई", "हुए", "व", "न", "अभी", "जैसे", "सभी", "करता", "उनकी", "तरह", "उस", "आदि", "कुल", "एस", "रहा", "इसकी", "सकता", "रहे", "उनका", "इसी", "रखें", "अपना", "पे", "उसके"};
              set=new HashSet<String>(Arrays.asList(stopwords));    //conveting  stopword array to set   
      for(File file : listOfFiles){                           //reading file array
           invertedindex(file,id);                            //calling inverted index function where id docid and file name
           id++;                                              //increasing docid
           System.out.println(id);
              }
              
System.out.println("Time after Write"+(System.currentTimeMillis()-startTime));
        FileOutputStream fos = new FileOutputStream("Maps.ser");           
        ObjectOutputStream oos = new ObjectOutputStream(fos);             
        oos.writeObject(invert_index);                          //writing hashmap in objectoutput stream
        oos.close();

    }
else{                                                     //if binary file already exists
FileInputStream fis = new FileInputStream("Maps.ser");
ObjectInputStream ois = new ObjectInputStream(fis);
//Map  anotherMap = (Map) ois.readObject(); //storing object input stream in hashmap
@SuppressWarnings("unchecked")
HashMap<String, HashSet<Integer>> map = (HashMap<String, HashSet<Integer>>) ois.readObject();
            ois.close();
       //System.out.println(anotherMap);
           // System.out.println("delete() invoked");
  BufferedWriter outfile= new BufferedWriter(new OutputStreamWriter(new FileOutputStream("invert_index.txt"), "UTF-8"));
 for (Map.Entry m:map.entrySet()) {
        outfile.write(m.getKey()+" : "+ m.getValue() + "\n");
        //System.out.println(" "+p);
        p++;
         }

         frequency(map,"उत्तम"); 
         term(map,"उत्तम"); 
         System.out.println(map.size());
            // calling function to check stirng present in which docs
        // System.out.println("Helo");
}}
    catch(Exception e){
        	System.out.println(e);
        }
      finally{
long endTime   = System.currentTimeMillis();
long totalTime = endTime - startTime;
System.out.println("finally : "+totalTime);
      }

   }
  
  public static void invertedindex(File file,int k) throws Exception{  //function invert index with filename and docid 
   inputfilename = new FileInputStream(file); 
   bufferedReader= new BufferedReader(new InputStreamReader(inputfilename, "UTF-8")); 
           String s;
		        while((s = bufferedReader.readLine()) != null){		   // reading file         	
		        	
		            s = s.replaceAll("\\<.*?>"," ");                      // removing < > mark content like <stroy> ,<Title>
		            if(s.contains("॥") || s.contains(":")|| s.contains("।")||       //Removing special char and punctuation mark
		            s.contains(",")|| s.contains("!")|| s.contains("?")){
		            s=s.replace("॥"," ");
		            s=s.replace(":"," ");
		            s=s.replace("।"," ");
		            s=s.replace(","," ");
		            s=s.replace("!"," ");
		            s=s.replace("?"," ");
		            }                                                   
            StringTokenizer st = new StringTokenizer(s," ");           // change string as string tokenizer 
                while (st.hasMoreTokens()){                            // loop till tokan exists
                String str=(st.nextToken());
                if (set.contains(str)) {                                // check string is stopword or not if yes then continue
                }
                else{                                                   
                         int length=Steam.stem(str);                      //stem the string  
                         char [] arr=new char[length];                    
                           for (int j=0;j<length ;j++ ) {
                               arr[j]=str.charAt(j);                          //get the string
                           }
                        String ss=new String(arr);                         //convert array in string   
                        if(!invert_index.containsKey(ss)){                  //hashmap has this String or not
                        	invert_index.put(ss,new HashSet<>());              //if not put into hashmap 
                        }
                        HashSet<Integer> sat=invert_index.get(ss);            //get hasset of string 
                       // sat.add(file.getName()); 
                        sat.add(k);                        // add document
                }}             
                
                              
                    
            }
  }
  public static void frequency(HashMap<String,HashSet<Integer>> another,String str){
    if(another.containsKey(str))
       System. out.println(""+str+" = "+another.get(str));
   System.out.println(""+another.get(str).size());
   
    }
 public static void term(HashMap<String,HashSet<Integer>> another,String str){
     if(another.containsKey(str)){
     System.out.println("Yes");
     another.get(str).size();
     }
     else{
     	System.out.println("No");
     
     }
 }
}
class Steam {
      static int stem(String st) {           //stemming of a string
// 5
int len=st.length();      
if ((len > 6) && (st.endsWith("ाएंगी")     //if length and ends with this decrease the length
|| st.endsWith("ाएंगे")
|| st.endsWith("ाऊंगी")
|| st.endsWith("ाऊंगा")
|| st.endsWith("ाइयाँ")
|| st.endsWith("ाइयों")
|| st.endsWith("ाइयां")
))

return len - 5;
// 4
if ((len > 5) && (st.endsWith("ाएगी")
||st.endsWith("ाएगा")
|| st.endsWith("ाओगी")
|| st.endsWith("ाओगे")
||st.endsWith("एंगी")
|| st.endsWith("ेंगी")
||st.endsWith("एंगे")
||st.endsWith("ेंगे")
||st.endsWith("ूंगी")
||st.endsWith("ूंगा")
||st.endsWith("ातीं")
||st.endsWith("नाओं")
||st.endsWith("नाएं")
||st.endsWith("ताओं")
||st.endsWith("ताएं")
||st.endsWith("ियाँ")
||st.endsWith("ियों")
|| st.endsWith("ियां")
))
return len - 4;
// 3
if ((len > 4) && (st.endsWith("ाकर")
|| st.endsWith("ाइए")
|| st.endsWith("ाईं")
|| st.endsWith("ाया")
|| st.endsWith("ेगी")
||st.endsWith("ोगी")
|| st.endsWith("ोगे")
|| st.endsWith("ाने")
||st.endsWith("ाना")
||st.endsWith("ाते")
||st.endsWith("ाती")
||st.endsWith("ाता")
||st.endsWith("तीं")
||st.endsWith("ाओं")
||st.endsWith("ाएं")
||st.endsWith("ुओं")
||st.endsWith("ुएं")
||st.endsWith("ुआं")
))
return len - 3;
// 2
if ((len > 3) && (st.endsWith("कर")
|| st.endsWith("ाओ")
|| st.endsWith("िए")
|| st.endsWith("ाई")
|| st.endsWith("ाए")
|| st.endsWith("ने")
|| st.endsWith("नी")
|| st.endsWith("ना")
|| st.endsWith("ते")
|| st.endsWith("ीं")
|| st.endsWith("ती")
|| st.endsWith("ता")
|| st.endsWith("ाँ")
|| st.endsWith("ां")
|| st.endsWith("ों")
|| st.endsWith("ें")
))
return len - 2;
// 1
if ((len > 2) && (st.endsWith("ो")
|| st.endsWith("े")
|| st.endsWith("ू")
||st.endsWith("ु")
|| st.endsWith("ी")
||st.endsWith("ि")
||st.endsWith("ा")
))
return len - 1;
return len;
}
}
    
   
  





