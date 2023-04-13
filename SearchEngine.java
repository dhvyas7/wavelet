import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;

class Handler implements URLHandler {

    ArrayList<String> op = new ArrayList<>();

    public String handleRequest(URI url) {
        if (url.getPath().equals("/")) {
           return "Possible Searches:"+op.toString();
        } else if (url.getPath().contains("/add")){
            String[] parameters = url.getQuery().split("=");
            if (parameters[0].equals("s")) {
                op.add(parameters[1]);
                return "Added the query";
            }
        }
        else if (url.getPath().contains("/delete")){
            String[] parameters = url.getQuery().split("=");
            if (parameters[0].equals("s")) {
                op.remove(parameters[1]);
                return "Deleted the query";
            }
        }
        else if (url.getPath().contains("/replace")){
            String[] parameters = url.getQuery().split("=");
            if (parameters[0].equals("s")) {
                String original=parameters[1].split("&")[0];
                String rep=parameters[1].split("&")[1];
                for(int i=0;i<op.size();i++)
                {
                    if(op.get(i).equals(original))
                    {
                        op.set(i, rep);
                    }
                }
                return "Replaced the query";
            }
        }
        else {
            System.out.println("Path: " + url.getPath());
            if (url.getPath().contains("/search")) {
                String[] parameters = url.getQuery().split("=");
                String result="";
                if (parameters[0].equals("s")) {
                    for(String s: op)
                    {
                        if(s.contains(parameters[1]))
                        {
                            result += (s+" ");
                        }
                    }
                }
                return result;
            }
            return "404 Not Found!";
        }
        return "";
    }
}

class SearchEngine {
    public static void main(String[] args) throws IOException {
        if(args.length == 0){
            System.out.println("Missing port number! Try any number between 1024 to 49151");
            return;
        }

        int port = Integer.parseInt(args[0]);

        Server.start(port, new Handler());
    }
}
